/**
 * 
 */
package com.nebulent.cep.service.resource.impl;

import java.util.ArrayList;
import java.util.List;

import nebulent.schema.software.cep.types._1.Alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.repository.MonitorRepository;
import com.nebulent.cep.repository.RepositoryException;
import com.nebulent.cep.service.resource.AlertResource;
import com.nebulent.cep.service.resource.response.StatusResponse;
import com.nebulent.cep.utils.DomainUtil;

/**
 * @author mfedorov
 *
 */
@Transactional
public class AlertResourceImpl implements AlertResource {

	private Logger logger = LoggerFactory.getLogger(AlertResourceImpl.class);
	
	private MonitorRepository monitorRepository;
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.AlertResource#getAlerts()
	 */
	@Override
	public List<Alert> getAlerts() {
		logger.debug("In getAlerts()");
		List<CepAlert> alerts = monitorRepository.getAllAlerts();
		List<Alert> alertTypes = new ArrayList<Alert>(alerts.size());
		for (CepAlert alert : alerts) {
			alertTypes.add(DomainUtil.toXmlType(alert));
		}
		return alertTypes;
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.AlertResource#getAlert(java.lang.Long)
	 */
	@Override
	public Alert getAlert(Long alertId) {
		logger.debug("In getAlert(" + alertId + ")");
		CepAlert alert = monitorRepository.getAlertById(alertId);
		return DomainUtil.toXmlType(alert);
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.AlertResource#updateAlert(java.lang.Long, bonddeskgroup.schema.software.cep.types._1.Alert)
	 */
	@Override
	public Alert updateAlert(Long alertId, Alert alert) {
		logger.debug("In updateAlert(" + alert + ")");
		return DomainUtil.toXmlType(monitorRepository.updateAlert(DomainUtil.toDomainType(alert)));
	}
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.AlertResource#removeAlert(java.lang.Long)
	 */
	@Override
	public StatusResponse removeAlert(Long alertId) {
		logger.debug("In removeAlert(" + alertId + ")");
		try{
			monitorRepository.removeAlert(alertId);
		}
		catch(RepositoryException re){
			return StatusResponse.failure();
		}
		return StatusResponse.success();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.AlertResource#createAlert(bonddeskgroup.schema.software.cep.types._1.Alert)
	 */
	@Override
	public Alert createAlert(Alert alert){
		logger.debug("In createAlert(" + alert + ")");
		return DomainUtil.toXmlType(monitorRepository.createAlert(DomainUtil.toDomainType(alert)));
	}
	
	/**
	 * @param monitorRepository the monitorRepository to set
	 */
	public void setMonitorRepository(MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}
}
