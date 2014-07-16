/**
 * 
 */
package com.nebulent.cep.repository.jpa.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.repository.AlertRepository;
import com.nebulent.cep.repository.jpa.JpaAbstractRepository;
import com.nebulent.cep.repository.jpa.RepositoryException;

/**
 * @author mfedorov
 *
 */
public class JpaAlertRepository extends JpaAbstractRepository implements AlertRepository{

	@Override
	public <S extends CepAlert> S save(S alert) {
		if (!persist(alert)){
        	throw new RepositoryException("Failed to create alert:" + alert.getMessage() + ", id=" + alert.getId());
        }
        return alert;
	}

	@Override
	public <S extends CepAlert> Iterable<S> save(Iterable<S> entities) {
		for (S s : entities) {
			save(s);
		}
		return entities;
	}

	@Override
	public CepAlert findOne(BigInteger id) {
		return entityManager.find(CepAlert.class, id);
	}

	@Override
	public boolean exists(BigInteger id) {
		return findOne(id) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<CepAlert> findAll() {
		return entityManager.createQuery("FROM com.nebulent.cep.domain.model.CepAlert alert JOIN FETCH alert.monitor WHERE alert.startTime != NULL ORDER BY alert.startTime DESC").getResultList();
	}

	@Override
	public Iterable<CepAlert> findAll(Iterable<BigInteger> ids) {
		Query query = entityManager.createQuery("FROM com.nebulent.cep.domain.model.CepAlert alert JOIN FETCH alert.monitor WHERE alert.startTime != NULL AND alert.id IN(:IDS) ORDER BY alert.startTime DESC");
		query.setParameter("IDS", StringUtils.join(ids.iterator(), ','));
		return null;
	}

	@Override
	public long count() {
		Query q = entityManager.createQuery ("SELECT count(*) FROM com.nebulent.cep.domain.model.CepAlert");
		Number result = (Number) q.getSingleResult();
		if(result != null){
			return result.longValue();
		}
		return 0;
	}

	@Override
	public void delete(BigInteger id) {
		if (!remove(CepAlert.class, id.longValue())){
        	throw new RepositoryException("Failed to remove alert:" + id);
        }
	}

	@Override
	public void delete(CepAlert entity) {
		delete(entity.getId());
	}

	@Override
	public void delete(Iterable<? extends CepAlert> entities) {
		for (CepAlert alert : entities) {
			delete(alert);
		}
	}

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CepAlert> findByMonitor(BigInteger monitorId) {
		javax.persistence.Query query = entityManager.createQuery("FROM com.nebulent.cep.domain.model.CepAlert alert JOIN FETCH alert.monitor WHERE alert.monitor.id = :ID and alert.startTime != NULL ORDER BY alert.startTime DESC");
		query.setParameter("ID", monitorId);
		return query.getResultList();
	}

}
