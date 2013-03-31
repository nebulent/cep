/**
 * 
 */
package com.nebulent.cep.repository;

import java.util.List;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.domain.model.CepMonitor;

/**
 * @author Max Fedorov
 *
 */
public interface MonitorRepository {

	/**
	 * @param monitor
	 * @return
	 * @throws RepositoryException
	 */
	CepMonitor createMonitor(CepMonitor monitor) throws RepositoryException;
	
	/**
	 * @param monitor
	 * @return
	 * @throws RepositoryException
	 */
	CepMonitor updateMonitor(CepMonitor monitor) throws RepositoryException;
	
	/**
	 * @param id
	 * @throws RepositoryException
	 */
	void removeMonitor(long id) throws RepositoryException;
	
	/**
	 * @return
	 * @throws RepositoryException
	 */
	List<CepMonitor> getAllMonitors() throws RepositoryException;
	
	/**
	 * @param id
	 * @return
	 */
	CepMonitor getMonitorById(long id);
	
	/**
	 * @param monitorId
	 * @return
	 * @throws RepositoryException
	 */
	List<CepAlert> getAllAlertsByMonitor(long monitorId) throws RepositoryException;
	
	/**
	 * @param alert
	 * @return
	 * @throws RepositoryException
	 */
	CepAlert createAlert(CepAlert alert) throws RepositoryException;
	
	/**
	 * @param alert
	 * @return
	 * @throws RepositoryException
	 */
	CepAlert updateAlert(CepAlert alert) throws RepositoryException;
	
	/**
	 * @param id
	 * @throws RepositoryException
	 */
	void removeAlert(long id) throws RepositoryException;
	
	/**
	 * @return
	 * @throws RepositoryException
	 */
	List<CepAlert> getAllAlerts() throws RepositoryException;
	
	/**
	 * @param id
	 * @return
	 */
	CepAlert getAlertById(long id);
}
