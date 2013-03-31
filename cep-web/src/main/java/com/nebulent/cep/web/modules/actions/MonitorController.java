package com.nebulent.cep.web.modules.actions;

import java.util.List;

import nebulent.schema.software.cep.metadata._1.MonitoredResourceCode;
import nebulent.schema.software.cep.types._1.Monitor;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.nebulent.cep.web.model.XMLMonitorDefinitionStore;
import com.nebulent.cep.web.model.Monitoring;
import com.opensymphony.xwork2.ModelDriven;

public class MonitorController extends ActionSupport implements ModelDriven<Monitor> {
	
	public static final String MONITORS_NAMESPACE = "/monitor";
	
	private String id;
	
	private List<Monitor> monitors;
	private Monitor monitor;
	private Monitoring monitoring;
	
	@Autowired
	private XMLMonitorDefinitionStore monitorDefinitionStore;
	
	/**
	 *	GET /monitor/ - get all monitors.
	 */
	public String index() {
		setPageTitle("Monitors");
		
		monitors = getMonitorRestService().getMonitors();
		
		// Create monitoring model.
		monitoring = new Monitoring();
		monitoring.setMonitorDefinitionStore(monitorDefinitionStore);
		monitoring.setMonitors(monitors);
		monitoring.showMonitorDefinitionStore();
		
		return "success";
	}
	
	/**
	 * GET /monitor/{id} - get monitor details.
	 */
	public HttpHeaders show() {
		logger.debug("Show Monitor:");
		logger.debug(id);
		
		if(NumberUtils.isDigits(id)){
			monitor = getMonitorRestService().getMonitor(new Long(id));
		}
		return new DefaultHttpHeaders("show")
        .withETag(monitor.getChangeBy())
        .lastModified(monitor.getChangeDate().getTime());
	}
	
	
	/**
	 * DELETE /monitor/{id} - delete monitor.
	 * 
	 * @return
	 */
	public String destroy() {
		logger.debug("Delete Monitor:" + id);
		
		if(NumberUtils.isDigits(id)){
			getMonitorRestService().removeMonitor(new Long(id));
		}
		
		return "redirect";
	}
	
	/**
	 * PUT /monitor/{id} - update monitor
	 * 
	 * @return
	 */
	public String update() {
		logger.debug("Update monitors");
		
		for (Monitor monitor : monitoring.getMonitors()) {
			monitor.setResourceCode(MonitoredResourceCode.APPLICATION.value());
			monitor.setStatus("I");
			
			logger.debug("Name = "+monitor.getName());
			logger.debug("UUID = "+monitor.getId());
			logger.debug("Message = "+monitor.getMessage());
			logger.debug("Criticality = "+monitor.getCriticalityTypeCode().toString());
			logger.debug("ResourceCode = "+monitor.getResourceCode());
			logger.debug("Occurrence Interval = "+monitor.getOccurrenceInterval());
		}
		
		logger.debug("Deleting Existing Monitors");
		if (monitoring.getDeletedMonitors() != null && (monitoring.getDeletedMonitors().size() != 0)) {
			for (String deleteMonitor : monitoring.getDeletedMonitors()) {
				logger.debug("Delete Existing Monitor:" + deleteMonitor);
				monitorRestService.removeMonitor(new Long(deleteMonitor));
			}
		}
		
		logger.debug("Create new Monitors");
		
		return "redirect";
	}
	
	/* Interface implementations */
	
	@Override
	public Monitor getModel() {
		return monitor;
	}
	
	/**
	 * @return
	 */
	public String getControllerNamespace() {
		return MONITORS_NAMESPACE;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the monitors
	 */
	public List<Monitor> getMonitors() {
		return monitors;
	}

	/**
	 * @return the monitor
	 */
	public Monitor getMonitor() {
		return monitor;
	}

	/**
	 * @param monitor the monitor to set
	 */
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * @return the monitoring
	 */
	public Monitoring getMonitoring() {
		return monitoring;
	}

	/**
	 * @param monitoring the monitoring to set
	 */
	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	/**
	 * @return the monitorDefinitionStore
	 */
	public XMLMonitorDefinitionStore getMonitorDefinitionStore() {
		return monitorDefinitionStore;
	}

	/**
	 * @param monitorDefinitionStore the monitorDefinitionStore to set
	 */
	public void setMonitorDefinitionStore(
			XMLMonitorDefinitionStore monitorDefinitionStore) {
		this.monitorDefinitionStore = monitorDefinitionStore;
	}
}
