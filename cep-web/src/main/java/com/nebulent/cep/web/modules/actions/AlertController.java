package com.nebulent.cep.web.modules.actions;

import java.util.List;

import nebulent.schema.software.cep.types._1.Alert;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;

public class AlertController extends ActionSupport implements ModelDriven<Alert> {
	
	public static final String ALERTS_NAMESPACE = "/alert";
	
	private String id;
	private List<Alert> alerts;
	private Alert alert;
	
	/**
	 *	GET /alert/ - get all alerts.
	 */
	public String index() {
		setPageTitle("Alerts");
		
		alerts = getAlertRestService().getAlerts();
		
		return "success";
	}
	
	/**
	 * GET /alert/{id} - get alert details.
	 */
	public HttpHeaders show() {
		logger.debug("Show Alert:");
		logger.debug(id);
		
		if(NumberUtils.isDigits(id)){
			alert = getAlertRestService().getAlert(new Long(id));
		}
		return new DefaultHttpHeaders("show")
        .withETag(alert.getChangeBy())
        .lastModified(alert.getChangeDate().getTime());
	}
	
	
	/**
	 * DELETE /alert/{id} - delete alert.
	 * 
	 * @return
	 */
	public String destroy() {
		logger.debug("Delete Alert:" + id);
		
		if(NumberUtils.isDigits(id)){
			getAlertRestService().removeAlert(new Long(id));
		}
		
		return "redirect";
	}
	
	/**
	 * PUT /alert/{id} - update alert
	 * 
	 * @return
	 */
	public String update() {
		logger.debug("Update alerts");
		
		
		
		return "redirect";
	}
	
	/* Interface implementations */
	
	@Override
	public Alert getModel() {
		return alert;
	}
	
	/**
	 * @return
	 */
	public String getControllerNamespace() {
		return ALERTS_NAMESPACE;
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
	 * @return the alert
	 */
	public Alert getAlert() {
		return alert;
	}

	/**
	 * @return the alerts
	 */
	public List<Alert> getAlerts() {
		return alerts;
	}
}
