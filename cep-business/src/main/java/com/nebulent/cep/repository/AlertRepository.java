package com.nebulent.cep.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nebulent.cep.domain.model.CepAlert;

public interface AlertRepository extends CrudRepository<CepAlert, BigInteger> {

	List<CepAlert> findByMonitor(BigInteger monitorId); 
	
}
