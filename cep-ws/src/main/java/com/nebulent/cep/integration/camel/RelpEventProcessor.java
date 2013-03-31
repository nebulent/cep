/**
 * 
 */
package com.nebulent.cep.integration.camel;

import nebulent.schema.software.cep.events._1.BaseSysLogEvent;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nebulent.cep.monitoring.service.integration.EventProcessor;


/**
 * @author Max Fedorov
 *
 */
public class RelpEventProcessor implements EventProcessor<String> {

	private Logger logger = LoggerFactory.getLogger(RelpEventProcessor.class);
	
	/*properties*/
	private ProducerTemplate producer;
	private String destination;
	private SyslogEventProcessor syslogEventProcessor;
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.service.integration.EventProcessor#process(java.lang.Object)
	 */
	public void process(String syslog) {
		if(logger.isDebugEnabled()){
			logger.debug("RELP EventProcessor: Trying to process log:" + syslog);
		}
		if(syslogEventProcessor != null){
			BaseSysLogEvent e = syslogEventProcessor.process(syslog);
			if(e != null){
				producer.sendBody(destination, e);
			}
		}
	}
	
	/**
	 * @param camelContext
	 */
	public void setCamelContext(CamelContext camelContext) {
		this.producer = camelContext.createProducerTemplate();
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the syslogEventProcessor
	 */
	public SyslogEventProcessor getSyslogEventProcessor() {
		return syslogEventProcessor;
	}

	/**
	 * @param syslogEventProcessor the syslogEventProcessor to set
	 */
	public void setSyslogEventProcessor(SyslogEventProcessor syslogEventProcessor) {
		this.syslogEventProcessor = syslogEventProcessor;
	}
}
