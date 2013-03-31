/**
 * 
 */
package com.nebulent.cep.monitoring.service.integration;

/**
 * @author Max Fedorov
 *
 */
public interface EventProcessor<REQUEST> {

	/**
	 * @param request
	 */
	void process(REQUEST request);
}
