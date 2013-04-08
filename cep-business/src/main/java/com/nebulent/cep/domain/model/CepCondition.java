package com.nebulent.cep.domain.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * @author Max Fedorov
 *
 */
@Entity(name = "cep_conditions")
public class CepCondition extends CepBaseEntity {

     /**/
	private static final long serialVersionUID = 1L;
	
    @Column(name = "tenant_id", nullable = false)
	private int tenantId;
    
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    
    @Column(name = "definition", length = 65535)
    private String definition;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cep_monitor_conditions", joinColumns = {@JoinColumn(name = "condition_id", nullable = false)}, inverseJoinColumns = {@JoinColumn(name = "monitor_id", nullable = false)})
    private Set<CepMonitor> monitors = new HashSet<CepMonitor>(0);

    /**
     * 
     */
    public CepCondition() {
    }
	
    /**
     * @param id
     * @param name
     * @param definition
     */
    public CepCondition(BigInteger id, String name, String expression) {
        this.id = id;
        this.name = name;
        this.definition = expression;
    }
    
    /**
     * @param id
     * @param name
     * @param definition
     * @param monitors
     */
    public CepCondition(BigInteger id, String name, String expression, Set<CepMonitor> monitors) {
       this.id = id;
       this.name = name;
       this.definition = expression;
       this.monitors = monitors;
    }

	/**
	 * @return the tenantId
	 */
	public int getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(String expression) {
		this.definition = expression;
	}

	/**
	 * @return the monitors
	 */
	public Set<CepMonitor> getMonitors() {
		return monitors;
	}

	/**
	 * @param monitors the monitors to set
	 */
	public void setMonitors(Set<CepMonitor> monitors) {
		this.monitors = monitors;
	}
   
}


