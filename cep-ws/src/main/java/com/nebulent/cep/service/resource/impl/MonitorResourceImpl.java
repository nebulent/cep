/**
 * 
 */
package com.nebulent.cep.service.resource.impl;

import java.util.ArrayList;
import java.util.List;

import nebulent.schema.software.cep.types._1.Alert;
import nebulent.schema.software.cep.types._1.Monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.domain.model.CepMonitor;
import com.nebulent.cep.monitoring.service.MonitorService;
import com.nebulent.cep.repository.MonitorRepository;
import com.nebulent.cep.repository.RepositoryException;
import com.nebulent.cep.service.resource.MonitorResource;
import com.nebulent.cep.service.resource.response.StatusResponse;
import com.nebulent.cep.utils.DomainUtil;

/**
 * @author mfedorov
 *
 */
@Transactional
public class MonitorResourceImpl implements MonitorResource {

	private Logger logger = LoggerFactory.getLogger(MonitorResourceImpl.class);
	
	private MonitorRepository monitorRepository;
	private MonitorService monitorService;
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.MonitorResource#getMonitors()
	 */
	@Override
	public List<Monitor> getMonitors() {
		logger.debug("In getMonitors()");
		List<CepMonitor> monitors = monitorRepository.getAllMonitors();
		List<Monitor> monitorTypes = new ArrayList<Monitor>(monitors.size());
		for (CepMonitor monitor : monitors) {
			monitorTypes.add(DomainUtil.toXmlType(monitor));
		}
		return monitorTypes;
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.MonitorResource#getMonitor(java.lang.Long)
	 */
	@Override
	public Monitor getMonitor(Long monitorId) {
		logger.debug("In getMonitor(" + monitorId + ")");
		CepMonitor monitor = monitorRepository.getMonitorById(monitorId);
		return DomainUtil.toXmlType(monitor);
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.MonitorResource#updateMonitor(java.lang.Long, bonddeskgroup.schema.software.cep.types._1.Monitor)
	 */
	@Override
	public Monitor updateMonitor(Long monitorId, Monitor monitor) {
		logger.debug("In updateMonitor(" + monitor + ")");
		return DomainUtil.toXmlType(monitorRepository.updateMonitor(DomainUtil.toDomainType(monitor)));
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.MonitorResource#createMonitor(bonddeskgroup.schema.software.cep.types._1.Monitor)
	 */
	@Override
	public Monitor createMonitor(Monitor monitor) {
		logger.debug("In createMonitor(" + monitor + ")");
		return DomainUtil.toXmlType(monitorRepository.createMonitor(DomainUtil.toDomainType(monitor)));
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.MonitorResource#startMonitor(java.lang.Long)
	 */
	@Override
	public StatusResponse startMonitor(Long monitorId) {
		logger.debug("In startMonitor(" + monitorId + ")");
		CepMonitor monitor = monitorRepository.getMonitorById(monitorId);
		monitorService.startMonitor(DomainUtil.toXmlType(monitor));
		return StatusResponse.success();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.MonitorResource#stopMonitor(java.lang.Long)
	 */
	@Override
	public StatusResponse stopMonitor(Long monitorId) {
		logger.debug("In stopMonitor(" + monitorId + ")");
		CepMonitor monitor = monitorRepository.getMonitorById(monitorId);
		monitorService.stopMonitor(DomainUtil.toXmlType(monitor));
		return StatusResponse.success();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.MonitorResource#removeMonitor(java.lang.Long)
	 */
	@Override
	public StatusResponse removeMonitor(Long monitorId) {
		logger.debug("In removeMonitor(" + monitorId + ")");
		try{
			monitorRepository.removeMonitor(monitorId);
		}
		catch(RepositoryException re){
			return StatusResponse.failure();
		}
		return StatusResponse.success();
	}
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.MonitorResource#getAlerts(java.lang.Long)
	 */
	@Override
	public List<Alert> getAlerts(Long monitorId) {
		logger.debug("In getMonitorAlerts(" + monitorId + ")");
		List<CepAlert> monitorAlerts = monitorRepository.getAllAlertsByMonitor(monitorId);
		List<Alert> alerts = new ArrayList<Alert>(monitorAlerts.size());
		for (CepAlert monitorAlert : monitorAlerts) {
			alerts.add(DomainUtil.toXmlType(monitorAlert));
		}
		return alerts;
	}

	/**
	 * @return the monitorRepository
	 */
	public MonitorRepository getMonitorRepository() {
		return monitorRepository;
	}

	/**
	 * @param monitorRepository the monitorRepository to set
	 */
	public void setMonitorRepository(MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}

	/**
	 * @return the monitorService
	 */
	public MonitorService getMonitorService() {
		return monitorService;
	}

	/**
	 * @param monitorService the monitorService to set
	 */
	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}
}
