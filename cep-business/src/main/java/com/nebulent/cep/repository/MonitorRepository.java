/**
 * 
 */
package com.nebulent.cep.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.domain.model.CepMonitor;

/**
 * @author Max Fedorov
 *
 */
public interface MonitorRepository extends CrudRepository<CepMonitor, BigInteger> {
	
}
