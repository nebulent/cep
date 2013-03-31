package com.nebulent.cep.monitoring.service;

import nebulent.schema.software.cep.types._1.Monitor;

public interface MonitorService {

	/**
	 * @param monitor
	 */
	public abstract void startMonitor(Monitor monitor);

	/**
	 * @param monitor
	 */
	public abstract void stopMonitor(Monitor monitor);

	/**
	 * @param event
	 */
	public abstract void sendEvent(Object event);
}