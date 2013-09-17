package com.nebulent.cep.domain.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.nebulent.cep.utils.DomainUtil;

@MappedSuperclass
public class CepBaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected BigInteger id;
    
	/**
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = DomainUtil.toBigInteger(id);
	}

}
