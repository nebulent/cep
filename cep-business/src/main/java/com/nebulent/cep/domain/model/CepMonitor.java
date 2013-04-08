package com.nebulent.cep.domain.model;

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
import javax.persistence.OneToMany;

/**
 * @author Max Fedorov
 * 
 */
@Entity(name = "cep_monitors")
public class CepMonitor extends CepBaseEntity {

	/**/
	private static final long serialVersionUID = 1L;

	@Column(name = "tenant_id", nullable = false)
	private int tenantId;
	
	@Column(name = "resource_code", length=64)
	private String resourceCode;

	@Column(name = "condition_code", length=64)
	private String conditionCode;
	
	@Column(name = "criticality_type_code", length=16)
	private String criticalityTypeCode;

	@Column(name = "name", length=256)
	private String name;

	@Column(name = "message", length=2024)
	private String message;

	@Column(name = "occurrence_interval")
	private Integer occurrenceInterval;

	@Column(name = "change_date")
	private Long changeDate;

	@Column(name = "change_by")
	private String changeBy;

	@Column(name = "status", length = 1)
	private char status;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "cep_monitor_conditions", joinColumns = { @JoinColumn(name = "monitor_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "condition_id", nullable = false) })
	private Set<CepCondition> conditions = new HashSet<CepCondition>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "monitor", cascade = { CascadeType.ALL })
	private Set<CepAlert> alerts = new HashSet<CepAlert>(0);

	/**
     * 
     */
	public CepMonitor() {
	}

	/**
	 * @param resourceCode
	 * @param conditionCode
	 * @param criticalityTypeCode
	 * @param name
	 * @param message
	 * @param status
	 */
	public CepMonitor(String resourceCode, String conditionCode, String criticalityTypeCode, String name,
			String message, char status) {
		this.resourceCode = resourceCode;
		this.conditionCode = conditionCode;
		this.criticalityTypeCode = criticalityTypeCode;
		this.name = name;
		this.message = message;
		this.status = status;
	}

	/**
	 * @param resourceCode
	 * @param conditionCode
	 * @param criticalityTypeCode
	 * @param name
	 * @param message
	 * @param occurrenceInterval
	 * @param changeDate
	 * @param changeBy
	 * @param status
	 * @param conditions
	 * @param alerts
	 */
	public CepMonitor(String resourceCode, String conditionCode, String criticalityTypeCode, String name,
			String message, Integer occurrenceInterval, Long changeDate,
			String changeBy, char status, Set<CepCondition> conditions,
			Set<CepAlert> alerts) {
		this.resourceCode = resourceCode;
		this.conditionCode = conditionCode;
		this.criticalityTypeCode = criticalityTypeCode;
		this.name = name;
		this.message = message;
		this.occurrenceInterval = occurrenceInterval;
		this.changeDate = changeDate;
		this.changeBy = changeBy;
		this.status = status;
		this.conditions = conditions;
		this.alerts = alerts;
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
	 * @return the resourceCode
	 */
	public String getResourceCode() {
		return resourceCode;
	}

	/**
	 * @param resourceCode
	 *            the resourceCode to set
	 */
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	/**
	 * @return the conditionCode
	 */
	public String getConditionCode() {
		return conditionCode;
	}

	/**
	 * @param conditionCode the conditionCode to set
	 */
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}

	/**
	 * @return the criticalityTypeCode
	 */
	public String getCriticalityTypeCode() {
		return criticalityTypeCode;
	}

	/**
	 * @param criticalityTypeCode
	 *            the criticalityTypeCode to set
	 */
	public void setCriticalityTypeCode(String criticalityTypeCode) {
		this.criticalityTypeCode = criticalityTypeCode;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the occurrenceInterval
	 */
	public Integer getOccurrenceInterval() {
		return occurrenceInterval;
	}

	/**
	 * @param occurrenceInterval
	 *            the occurrenceInterval to set
	 */
	public void setOccurrenceInterval(Integer occurrenceInterval) {
		this.occurrenceInterval = occurrenceInterval;
	}

	/**
	 * @return the changeDate
	 */
	public Long getChangeDate() {
		return changeDate;
	}

	/**
	 * @param changeDate
	 *            the changeDate to set
	 */
	public void setChangeDate(Long changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * @return the changeBy
	 */
	public String getChangeBy() {
		return changeBy;
	}

	/**
	 * @param changeBy
	 *            the changeBy to set
	 */
	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}

	/**
	 * @return the status
	 */
	public char getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(char status) {
		this.status = status;
	}

	/**
	 * @return the conditions
	 */
	public Set<CepCondition> getConditions() {
		return conditions;
	}

	/**
	 * @param conditions
	 *            the conditions to set
	 */
	public void setConditions(Set<CepCondition> conditions) {
		this.conditions = conditions;
	}
	
	/**
	 * @return the alerts
	 */
	public Set<CepAlert> getAlerts() {
		return alerts;
	}

	/**
	 * @param alerts
	 *            the alerts to set
	 */
	public void setAlerts(Set<CepAlert> events) {
		this.alerts = events;
	}
}
