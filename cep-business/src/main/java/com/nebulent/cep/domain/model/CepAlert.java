package com.nebulent.cep.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Max Fedorov
 *
 */
@Entity(name = "cep_alerts")
public class CepAlert implements Serializable {

     /**/
	private static final long serialVersionUID = 1L;
	
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    @Column(name = "tenant_id", nullable = false)
	private int tenantId;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
    private CepAlert parent;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "monitor_id", nullable = false)
    private CepMonitor monitor;
    
    @Column(name = "status", length=8, nullable = false)
    private String status;
    
    @Column(name = "start_time", nullable = false)
    private long startTime;
    
    @Column(name = "end_time")
    private Long endTime;
    
    @Column(name = "message", length=2024)
    private String message;
    
    @Column(name = "change_date")
    private Long changeDate;
    
    @Column(name = "change_by")
    private String changeBy;

    /**
     * 
     */
    public CepAlert() {
    }
	
    /**
     * @param monitor
     * @param status
     * @param startTime
     */
    public CepAlert(CepMonitor monitor, String eventStatusCode, long startTime) {
        this.monitor = monitor;
        this.status = eventStatusCode;
        this.startTime = startTime;
    }
    
    /**
     * @param monitor
     * @param status
     * @param startTime
     * @param endTime
     * @param message
     * @param changeDate
     * @param changeBy
     */
    public CepAlert(CepMonitor monitor, String eventStatusCode, long startTime, Long endTime, String message, Long changeDate, String changeBy) {
       this.monitor = monitor;
       this.status = eventStatusCode;
       this.startTime = startTime;
       this.endTime = endTime;
       this.message = message;
       this.changeDate = changeDate;
       this.changeBy = changeBy;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the monitor
	 */
	public CepMonitor getMonitor() {
		return monitor;
	}

	/**
	 * @param monitor the monitor to set
	 */
	public void setMonitor(CepMonitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * @return the parent
	 */
	public CepAlert getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(CepAlert parent) {
		this.parent = parent;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String eventStatusCode) {
		this.status = eventStatusCode;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the changeDate
	 */
	public Long getChangeDate() {
		return changeDate;
	}

	/**
	 * @param changeDate the changeDate to set
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
	 * @param changeBy the changeBy to set
	 */
	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}
   
}


