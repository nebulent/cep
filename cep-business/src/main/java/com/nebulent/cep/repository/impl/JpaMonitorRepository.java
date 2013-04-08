/**
 * 
 */
package com.nebulent.cep.repository.impl;

import java.math.BigInteger;
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

	@Override
	public <S extends CepMonitor> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends CepMonitor> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CepMonitor findOne(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(BigInteger id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<CepMonitor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<CepMonitor> findAll(Iterable<BigInteger> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(BigInteger id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(CepMonitor entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends CepMonitor> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}
}
