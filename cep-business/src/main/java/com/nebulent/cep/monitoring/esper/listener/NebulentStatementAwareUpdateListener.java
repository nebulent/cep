/**
 * 
 */
package com.nebulent.cep.monitoring.esper.listener;

import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nebulent.schema.software.cep.metadata._1.Configuration;
import nebulent.schema.software.cep.metadata._1.MonitorOutParameterType;
import nebulent.schema.software.cep.types._1.Alert;
import nebulent.schema.software.cep.types._1.Monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.client.annotation.Description;
import com.nebulent.cep.monitoring.MonitorDefinitionStore;
import com.nebulent.cep.monitoring.esper.annotation.MonitorInfo;
import com.nebulent.cep.monitoring.esper.statement.FreemarkerMonitorConditionCompiler;
import com.nebulent.cep.monitoring.service.integration.EventProcessor;
import com.nebulent.cep.monitoring.utils.transformers.FreemarkerTemplateInput;
import com.nebulent.cep.monitoring.utils.transformers.TemplateTransformer;

/**
 * @author Max Fedorov
 * 
 */
public class NebulentStatementAwareUpdateListener implements StatementAwareUpdateListener {

	/*logger*/
	private static Logger logger = LoggerFactory.getLogger(NebulentStatementAwareUpdateListener.class);
	
	/*services*/
	private EventProcessor<Alert> eventProcessor;
	private TemplateTransformer<FreemarkerTemplateInput, String> messageBuilder;
	private MonitorDefinitionStore monitorDefinitionStore;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.espertech.esper.client.StatementAwareUpdateListener#update(com.espertech
	 * .esper.client.EventBean[], com.espertech.esper.client.EventBean[],
	 * com.espertech.esper.client.EPStatement,
	 * com.espertech.esper.client.EPServiceProvider)
	 */
	public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPServiceProvider serviceProvider) {
		Monitor monitor = getMonitorInfo(statement.getAnnotations());
		monitor.setName(statement.getName());
		
		logger.debug("Got " + (monitor.isManagedQuery() ? " managed " : "") + newEvents.length + " events!");
		
		if(monitor.isManagedQuery()) return;
		
		Configuration configuration = monitorDefinitionStore.getMonitorConfiguration();
		Assert.notNull(configuration, "Monitor configuration cannot be null !!!");
		List<MonitorOutParameterType> outParameters = FreemarkerMonitorConditionCompiler.getMonitorConditionOutParameters(configuration, monitor.getResourceCode(), monitor.getConditionCode());
		
		if(monitor != null) {
			for (EventBean event : newEvents) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("monitor", monitor);
				params.put("event", event.getUnderlying());
				
				/*
				2013-01-22 16:06:41,086 DEBUG NebulentStatementAwareUpdateListener.java:58 - Got 1 events!
				2013-01-22 16:06:41,087 DEBUG NebulentStatementAwareUpdateListener.java:73 - EventType:com.espertech.esper.event.map.MapEventType@74b65a68
				2013-01-22 16:06:41,087 DEBUG NebulentStatementAwareUpdateListener.java:74 - Event:{cpuPercPast=-2.8999999999999972, host=HOST02, numberOfLoginsPast=273.0, cpuPerc=-5.600000000000021, maxNumberOfQueries=19, numberOfLogins=309.0}
				2013-01-22 16:06:41,087 DEBUG Log4JLoggerFactory.java:81 - Updating source, info for cause: lastModifiedNotChanged=false, cache lastModified=1358888796007 != file lastModified=1358888801087
				2013-01-22 16:06:41,087 DEBUG Log4JLoggerFactory.java:81 - Compiling FreeMarker template "1"["en_US",MacRoman,parsed]  from "freemarker.cache.StringTemplateLoader$StringTemplateSource@31"
				2013-01-22 16:06:41,087 DEBUG NebulentStatementAwareUpdateListener.java:84 - Database is rataining...
				2013-01-22 16:06:41,087 DEBUG NebulentStatementAwareUpdateListener.java:91 - 
				host:HOST02
				cpuPerc:-5.600000000000021
				cpuPercPast:-2.8999999999999972
				numberOfLogins:309.0
				numberOfLoginsPast:273.0
				maxNumberOfQueries:19
				 */
				logger.debug("EventType:" + event.getEventType());
				logger.debug("Event:" + event.getUnderlying());
				//logger.debug("Event Host:" + event.get("host"));
				//logger.debug("Event Host:" + event.getFragment("cpuPerc"));
				
				// Generate message.
				String message = messageBuilder.transform(new FreemarkerTemplateInput(String.valueOf(monitor.getId().longValue()), monitor.getMessage(), params));
				
				// Create alert.
				Alert alert = new Alert();
				alert.setMonitor(monitor);
				alert.setChangeBy("esper");
				alert.setChangeDate(Calendar.getInstance());
				alert.setStartTime(Calendar.getInstance());
				alert.setMessage(message);
				alert.setStatus("A");
				
				logger.info("Alert:" + alert);
				
				if(eventProcessor != null){
					eventProcessor.process(alert);
				}
				
				if(logger.isDebugEnabled()){
					logger.debug(message);
					
					if(outParameters != null){
						StringBuilder builder = new StringBuilder();
						for (MonitorOutParameterType outParam : outParameters) {
							builder.append(outParam.getId()).append(":").append(event.get(outParam.getId())).append("\n");
						}
						logger.debug(builder.toString());
					}
				}
			}
		}
	}
	
	/**
	 * @param annotations
	 * @return
	 */
	private Monitor getMonitorInfo(Annotation[] annotations) {
		Monitor monitor = new Monitor();
        if (annotations != null) {
            for (Annotation a : annotations) {
                if (a instanceof MonitorInfo) {
                	MonitorInfo monitorInfo = ((MonitorInfo) a);
                	
                	monitor.setId(monitorInfo.monitorId());
                	monitor.setConditionCode(monitorInfo.conditionCode());
                	monitor.setResourceCode(monitorInfo.resourceCode());
                	monitor.setCriticalityTypeCode(monitorInfo.criticalityTypeCode());
                	monitor.setManagedQuery(monitorInfo.managed());
                }
                else if (a instanceof Description) {
                	monitor.setMessage(((Description) a).value());
                }
            }
        }
        return monitor;
	}

	/**
	 * @param eventProcessor the eventProcessor to set
	 */
	public void setEventProcessor(EventProcessor<Alert> eventProcessor) {
		this.eventProcessor = eventProcessor;
	}

	/**
	 * @param messageBuilder the messageBuilder to set
	 */
	public void setMessageBuilder(
			TemplateTransformer<FreemarkerTemplateInput, String> messageBuilder) {
		this.messageBuilder = messageBuilder;
	}

	/**
	 * @param monitorDefinitionStore the monitorDefinitionStore to set
	 */
	public void setMonitorDefinitionStore(
			MonitorDefinitionStore monitorDefinitionStore) {
		this.monitorDefinitionStore = monitorDefinitionStore;
	}
}
