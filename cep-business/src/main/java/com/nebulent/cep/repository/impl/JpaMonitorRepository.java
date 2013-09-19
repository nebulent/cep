/**
 * 
 */
package com.nebulent.cep.repository.impl;

import java.math.BigInteger;

import javax.persistence.Query;

import com.nebulent.cep.domain.model.CepMonitor;
import com.nebulent.cep.repository.JpaAbstractRepository;
import com.nebulent.cep.repository.MonitorRepository;
import com.nebulent.cep.repository.RepositoryException;

/**
 * @author Max Fedorov
 *
 */
public class JpaMonitorRepository extends JpaAbstractRepository implements MonitorRepository {


	@Override
	public <S extends CepMonitor> S save(S monitor) {
        if (!persist(monitor)){
        	throw new RepositoryException("Failed to create monitor:" + monitor.getName());
        }
        return monitor;

	}

	@Override
	public <S extends CepMonitor> Iterable<S> save(Iterable<S> entities) {
		for (S s : entities) {
			save(s);
		}
		return entities;
	}

	@Override
	public CepMonitor findOne(BigInteger id) {
		return entityManager.find(CepMonitor.class, id);
	}

	@Override
	public boolean exists(BigInteger id) {
		return findOne(id) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<CepMonitor> findAll() {
		return entityManager.createQuery("FROM com.nebulent.cep.domain.model.CepMonitor monitor JOIN FETCH monitor.conditions").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<CepMonitor> findAll(Iterable<BigInteger> ids) {
		return entityManager.createQuery("FROM com.nebulent.cep.domain.model.CepMonitor monitor JOIN FETCH monitor.conditions").getResultList();
	}

	@Override
	public long count() {
		Query q = entityManager.createQuery ("SELECT count(*) FROM com.nebulent.cep.domain.model.CepMonitor");
		Number result = (Number) q.getSingleResult();
		if(result != null){
			return result.longValue();
		}
		return 0;
	}

	@Override
	public void delete(BigInteger id) {
		if (!remove(CepMonitor.class, id.longValue())){
        	throw new RepositoryException("Failed to remove monitor:" + id);
        }
	}

	@Override
	public void delete(CepMonitor entity) {
		entityManager.remove(entity);
	}

	@Override
	public void delete(Iterable<? extends CepMonitor> entities) {
		for (CepMonitor monitor : entities) {
			delete(monitor);
		}
	}

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException();
	}
}
