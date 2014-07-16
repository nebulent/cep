package com.nebulent.cep.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import nebulent.schema.software.cep.types._1.Alert;

import com.nebulent.cep.service.resource.AlertResource;
import com.nebulent.cep.service.resource.MonitorResource;

@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class AjaxService {

	AlertResource alertResource;
	
	MonitorResource monitorResource;
	
	@GET
	@Path("alertsdistribution")
	public Map<String, Integer> getAlertsDistribution() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<Alert> alerts = alertResource.getAlerts();
		for (Alert alert : alerts) {
			String key = alert.getMonitor().getName();
			if(StringUtils.isBlank(key)){
				key = alert.getMonitor().getId() + ":" + alert.getMonitor().getConditionCode();
			}
			Integer count = result.get(key);
			if (count == null) {
				count = 0;
			}
			count++;
			result.put(key, count);
		}
		return result;
	}

	public void setAlertResource(AlertResource alertResource) {
		this.alertResource = alertResource;
	}

	public void setMonitorResource(MonitorResource monitorResource) {
		this.monitorResource = monitorResource;
	}
	
}
