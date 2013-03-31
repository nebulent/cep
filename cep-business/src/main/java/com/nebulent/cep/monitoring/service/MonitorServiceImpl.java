/**
 * 
 */
package com.nebulent.cep.monitoring.service;

import nebulent.schema.software.cep.types._1.Monitor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.nebulent.cep.monitoring.MonitorEngine;

/**
 * @author Max Fedorov
 *
 */
@Transactional
public class MonitorServiceImpl implements MonitorService{

	/*services*/
	private MonitorEngine monitorEngine;
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.service.MonitorService#startMonitor(bonddeskgroup.schema.software.cep.types._1.Monitor)
	 */
	@Override
	public void startMonitor(Monitor monitor) {
		Assert.notNull(monitor);
		monitorEngine.execute(monitor);
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.service.MonitorService#stopMonitor(bonddeskgroup.schema.software.cep.types._1.Monitor)
	 */
	@Override
	public void stopMonitor(Monitor monitor) {
		Assert.notNull(monitor);
		//monitorEngine.execute(monitor);
	}
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.service.MonitorService#sendEvent(java.lang.Object)
	 */
	@Override
	public void sendEvent(Object event) {
		monitorEngine.sendEvent(event);
	}

	/**
	 * @param monitorEngine the monitorEngine to set
	 */
	public void setMonitorEngine(MonitorEngine monitorEngine) {
		this.monitorEngine = monitorEngine;
	}
}
