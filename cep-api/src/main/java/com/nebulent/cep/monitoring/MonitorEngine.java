/**
 * 
 */
package com.nebulent.cep.monitoring;

import nebulent.schema.software.cep.types._1.Monitor;


/**
 * @author Max Fedorov
 *
 */
public interface MonitorEngine {

	/**
	 * 
	 */
	public void execute();
	
	/**
	 * @param monitor
	 */
	public void execute(Monitor monitor);
	
	/**
	 * @param event
	 */
	public abstract void sendEvent(Object event);
}
