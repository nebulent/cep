/**
 * 
 */
package com.nebulent.cep.service.resource.impl;

import static com.nebulent.cep.utils.DomainUtil.toBigInteger;

import java.util.ArrayList;
import java.util.List;

import nebulent.schema.software.cep.types._1.Alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.repository.AlertRepository;
import com.nebulent.cep.repository.jpa.RepositoryException;
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
	
	@Autowired
	private AlertRepository alertRepository;
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.AlertResource#getAlerts()
	 */
	@Override
	public List<Alert> getAlerts() {
		logger.debug("In getAlerts()");
		Iterable<CepAlert> alerts = alertRepository.findAll();
		List<Alert> alertTypes = new ArrayList<Alert>();//alerts.size());
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
		CepAlert alert = alertRepository.findOne(toBigInteger(alertId));
		return DomainUtil.toXmlType(alert);
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.AlertResource#updateAlert(java.lang.Long, bonddeskgroup.schema.software.cep.types._1.Alert)
	 */
	@Override
	public Alert updateAlert(Long alertId, Alert alert) {
		logger.debug("In updateAlert(" + alert + ")");
		return DomainUtil.toXmlType(alertRepository.save(DomainUtil.toDomainType(alert)));
	}
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.service.resource.AlertResource#removeAlert(java.lang.Long)
	 */
	@Override
	public StatusResponse removeAlert(Long alertId) {
		logger.debug("In removeAlert(" + alertId + ")");
		try{
			alertRepository.delete(toBigInteger(alertId));
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
		return DomainUtil.toXmlType(alertRepository.save(DomainUtil.toDomainType(alert)));
	}

	/**
	 * @return the alertRepository
	 */
	public AlertRepository getAlertRepository() {
		return alertRepository;
	}

	/**
	 * @param alertRepository the alertRepository to set
	 */
	public void setAlertRepository(AlertRepository alertRepository) {
		this.alertRepository = alertRepository;
	}
}
