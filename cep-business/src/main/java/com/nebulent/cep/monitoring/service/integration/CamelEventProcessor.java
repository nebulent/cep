/**
 * 
 */
package com.nebulent.cep.monitoring.service.integration;

import nebulent.schema.software.cep.types._1.Alert;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Max Fedorov
 *
 */
public class CamelEventProcessor implements EventProcessor<Alert> {

	private Logger logger = LoggerFactory.getLogger(CamelEventProcessor.class);
	
	/*properties*/
	private ProducerTemplate producer;
	private String destination;
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.monitor.service.EventProcessor#process(java.lang.Object)
	 */
	public void process(Alert alert) {
		logger.debug("EventProcessor: Trying to process alert:" + alert);
		producer.sendBody(destination, alert);
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
	
}
