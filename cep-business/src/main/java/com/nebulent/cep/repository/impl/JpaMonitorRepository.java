/**
 * 
 */
package com.nebulent.cep.repository.impl;

import java.util.List;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.domain.model.CepMonitor;
import com.nebulent.cep.repository.JpaAbstractRepository;
import com.nebulent.cep.repository.MonitorRepository;
import com.nebulent.cep.repository.RepositoryException;

/**
 * @author Max Fedorov
 *
 */
public class JpaMonitorRepository extends JpaAbstractRepository implements MonitorRepository {

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#createMonitor(com.nebulent.cep.domain.model.CepMonitor)
	 */
	public CepMonitor createMonitor(CepMonitor monitor) throws RepositoryException {
        if (!persist(monitor)){
        	throw new RepositoryException("Failed to create monitor:" + monitor.getName());
        }
        return monitor;
    }
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#getAllMonitors()
	 */
	@SuppressWarnings("unchecked")
	public List<CepMonitor> getAllMonitors() {
		return entityManager.createQuery("FROM com.nebulent.cep.domain.model.CepMonitor monitor JOIN FETCH monitor.conditions").getResultList();
	}
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#getMonitorById(long)
	 */
	public CepMonitor getMonitorById(long id) {
		return entityManager.find(CepMonitor.class, id);
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#updateMonitor(com.nebulent.cep.domain.model.CepMonitor)
	 */
	@Override
	public CepMonitor updateMonitor(CepMonitor monitor) throws RepositoryException {
		if (!persist(monitor)){
        	throw new RepositoryException("Failed to update monitor:" + monitor.getName() + ", id=" + monitor.getId());
        }
        return monitor;
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#removeMonitor(long)
	 */
	@Override
	public void removeMonitor(long id) throws RepositoryException {
		if (!remove(CepMonitor.class, id)){
        	throw new RepositoryException("Failed to remove monitor:" + id);
        }
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#getAllAlertsByMonitor(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CepAlert> getAllAlertsByMonitor(long monitorId)
			throws RepositoryException {
		javax.persistence.Query query = entityManager.createQuery("FROM com.nebulent.cep.domain.model.CepAlert alert JOIN FETCH alert.monitor WHERE alert.monitor.id = :ID and alert.startTime != NULL ORDER BY alert.startTime DESC");
		query.setParameter("ID", monitorId);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#createAlert(com.nebulent.cep.domain.model.CepAlert)
	 */
	@Override
	public CepAlert createAlert(CepAlert alert)
			throws RepositoryException {
		if (!persist(alert)){
        	throw new RepositoryException("Failed to create alert:" + alert.getMessage());
        }
        return alert;
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#updateAlert(com.nebulent.cep.domain.model.CepAlert)
	 */
	@Override
	public CepAlert updateAlert(CepAlert alert)
			throws RepositoryException {
		if (!persist(alert)){
        	throw new RepositoryException("Failed to update monitor:" + alert.getMessage() + ", id=" + alert.getId());
        }
        return alert;
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#removeAlert(long)
	 */
	@Override
	public void removeAlert(long id) throws RepositoryException {
		if (!remove(CepAlert.class, id)){
        	throw new RepositoryException("Failed to remove alert:" + id);
        }
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#getAllAlerts()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CepAlert> getAllAlerts() throws RepositoryException {
		return entityManager.createQuery("FROM com.nebulent.cep.domain.model.CepAlert alert JOIN FETCH alert.monitor WHERE alert.startTime != NULL ORDER BY alert.startTime DESC").getResultList();
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.repository.MonitorRepository#getAlertById(long)
	 */
	@Override
	public CepAlert getAlertById(long id) {
		return entityManager.find(CepAlert.class, id);
	}
}
