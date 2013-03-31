/**
 * 
 */
package com.nebulent.cep.service.resource.impl;

import nebulent.schema.software.cep.events._1.APIProbeSysLogEvent;
import nebulent.schema.software.cep.events._1.AppServerHighCPUEvent;
import nebulent.schema.software.cep.events._1.DatabaseSlowQueryEvent;
import nebulent.schema.software.cep.events._1.JMXApplicationAlertEvent;
import nebulent.schema.software.cep.events._1.LoginEvent;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nebulent.cep.monitoring.service.MonitorService;
import com.nebulent.cep.service.resource.CepResource;
import com.nebulent.cep.service.resource.response.StatusResponse;

/**
 * @author mfedorov
 *
 */
@Transactional
public class CepResourceImpl implements CepResource {

	private Logger logger = LoggerFactory.getLogger(CepResourceImpl.class);
	
	/*properties*/
	protected ProducerTemplate producer;
	protected String route;
	protected MonitorService monitorService;
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.CepResource#logAnyEvent(java.lang.Object)
	 */
	@Override
	public Object logAnyEvent(Object event) {
		if(event != null){
			monitorService.sendEvent(event);
			//producer.sendBody(route, message);
		}
		return event;
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.CepResource#log(bonddeskgroup.schema.software.cep.monitors._1.DatabaseSlowQueryEvent)
	 */
	@Override
	public StatusResponse log(DatabaseSlowQueryEvent event) {
		logger.debug("In logDatabaseSlowQueryEvent(" + event + ")");
		logAnyEvent(event);
		return StatusResponse.success();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.CepResource#log(bonddeskgroup.schema.software.cep.monitors._1.LoginEvent)
	 */
	@Override
	public StatusResponse log(LoginEvent event) {
		logger.debug("In LoginEvent(" + event + ")");
		logAnyEvent(event);
		return StatusResponse.success();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.CepResource#log(bonddeskgroup.schema.software.cep.monitors._1.AppServerHighCPUEvent)
	 */
	@Override
	public StatusResponse log(AppServerHighCPUEvent event) {
		logger.debug("In AppServerHighCPUEvent(" + event + ")");
		logAnyEvent(event);
		return StatusResponse.success();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.CepResource#log(bonddeskgroup.schema.software.cep.monitors._1.APIProbeSysLogEvent)
	 */
	@Override
	public StatusResponse log(APIProbeSysLogEvent event) {
		logger.debug("In APIProbeSysLogEvent(" + event + ")");
		logAnyEvent(event);
		return StatusResponse.success();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.CepResource#log(bonddeskgroup.schema.software.cep.monitors._1.JMXApplicationAlertEvent)
	 */
	@Override
	public StatusResponse log(JMXApplicationAlertEvent event) {
		logger.debug("In JMXApplicationAlertEvent(" + event + ")");
		logAnyEvent(event);
		return StatusResponse.success();
	}
	
	/**
	 * @param camelContext
	 */
	public void setCamelContext(CamelContext camelContext) {
		this.producer = camelContext.createProducerTemplate();
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @param monitorService the monitorService to set
	 */
	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}
}
