package com.nebulent.cep.monitoring;

import nebulent.schema.software.cep.metadata._1.Configuration;


public interface MonitorDefinitionStore {

	/**
	 * @return
	 */
	public abstract Configuration getMonitorConfiguration();

}