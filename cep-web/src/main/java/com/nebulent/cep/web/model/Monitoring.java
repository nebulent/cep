package com.nebulent.cep.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nebulent.schema.software.cep.metadata._1.ConditionComparatorType;
import nebulent.schema.software.cep.metadata._1.Criticality;
import nebulent.schema.software.cep.metadata._1.MonitorConditionParameterType;
import nebulent.schema.software.cep.metadata._1.MonitorConditionType;
import nebulent.schema.software.cep.metadata._1.MonitorParameter;
import nebulent.schema.software.cep.metadata._1.MonitorParameters;
import nebulent.schema.software.cep.metadata._1.MonitoredResourceCode;
import nebulent.schema.software.cep.metadata._1.MonitoredResourceType;
import nebulent.schema.software.cep.types._1.Condition;
import nebulent.schema.software.cep.types._1.Monitor;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nebulent.cep.monitoring.MonitorDefinitionStore;

public class Monitoring {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<Monitor> monitors = new ArrayList<Monitor>();
	private List<String> deletedMonitors = new ArrayList<String>();
	private Map<String, MonitorParameters> monitorParameters = new HashMap<String, MonitorParameters>();
	
	private XMLMonitorDefinitionStore monitorDefinitionStore;
	
	/**
	 * @return the monitors
	 */
	public List<Monitor> getMonitors() {
		return monitors;
	}

	/**
	 * @param monitors the monitors to set
	 */
	public void setMonitors(List<Monitor> monitors) {
		this.monitors = monitors;
		processConditionExpressions();
	}
	
	/**
	 * @return the monitorDefinitionStore
	 */
	public MonitorDefinitionStore getMonitorDefinitionStore() {
		return monitorDefinitionStore;
	}

	/**
	 * @param monitorDefinitionStore the monitorDefinitionStore to set
	 */
	public void setMonitorDefinitionStore(
			XMLMonitorDefinitionStore monitorDefinitionStore) {
		this.monitorDefinitionStore = monitorDefinitionStore;
	}

	/**
	 * 
	 */
	public void showMonitorDefinitionStore() {
		if (monitorDefinitionStore != null) {
			logger.debug("monitorDefinitionStore isn't null");
			for (MonitoredResourceType type : monitorDefinitionStore.getMonitorConfiguration().getMonitoringMetadata().getMonitoredResources()) {
				logger.debug("1");
				logger.debug(type.getDescription());
				logger.debug(type.getLabel());

				MonitoredResourceCode rCode = type.getId();
				logger.debug(rCode.toString());
				
				for (MonitorConditionType cond : type.getSupportedConditions()) {
					logger.debug("2");
					logger.debug(cond.getDescription());
					logger.debug(cond.getExpression());
					logger.debug(cond.getLabel());
					logger.debug(cond.getId());
					
					for (MonitorConditionParameterType par : cond.getSupportedParameters()) {
						logger.debug("3");
						logger.debug(par.getLabel());
						logger.debug(par.getId());
						
						for (ConditionComparatorType comp : par.getSupportedComparators()) {
							logger.debug("4");
							logger.debug(comp.getLabel());
							logger.debug(comp.getCode().toString());
						}
					}
				}
			}
		}
		else {
			logger.debug("monitorDefinitionStore is null");
		}
	}
	
	/**
	 * @param value
	 * @return
	 */
	public String escape(String value){
		return StringEscapeUtils.escapeJavaScript(value);
	}
	
	/**
	 * @return
	 */
	public Map<String, String> getCriticalities() {
		Map<String, String> criticalities = new HashMap<String, String>();
		criticalities.put(Criticality.FAILURE.toString(), Criticality.FAILURE.value());
		criticalities.put(Criticality.PROBLEM.toString(), Criticality.PROBLEM.value());
		criticalities.put(Criticality.WARNING.toString(), Criticality.WARNING.value());
		criticalities.put(Criticality.INFO.toString(), Criticality.INFO.value());
		
		return criticalities;
	}
	
	/**
	 * @return
	 */
	public List<MonitoredResourceType> getMonitoredResources() {
		return monitorDefinitionStore.getMonitorConfiguration().getMonitoringMetadata().getMonitoredResources();
	}
	
	/**
	 * @return
	 */
	public List<MonitorConditionType> getConditions(MonitoredResourceCode code) {
		if (monitorDefinitionStore != null) {
			for (MonitoredResourceType resourceType :  monitorDefinitionStore.getMonitorConfiguration().getMonitoringMetadata().getMonitoredResources()) {
				if ((resourceType.getId() != null) &&
					(resourceType.getId().toString().equals(code.toString()))) {
					return resourceType.getSupportedConditions();
				}
			}
		}
		return null;
	}

	/**
	 * Unmarshall parameters.
	 */
	private void processConditionExpressions(){
		for (Monitor monitor : monitors) {
			List<Condition> conditions = monitor.getConditions();
			if(conditions != null && !conditions.isEmpty()){
				Condition condition = conditions.get(0);
				MonitorParameters parameters = monitorDefinitionStore.unmarshallUsingStaxSource(MonitorParameters.class, condition.getExpression().getBytes());
				monitorParameters.put(String.valueOf(monitor.getId()), parameters);
			}
		}
	}
	
	/**
	 * @return the monitorParameters
	 */
	public List<MonitorParameter> getMonitorParameters(Long id) {
		return monitorParameters.get(id.toString()).getMonitorParameters();
	}
	
	/**
	 * @return the monitorParameters
	 */
	public Map<String, MonitorParameters> getMonitorParameters() {
		return monitorParameters;
	}

	/**
	 * @return the deletedMonitors
	 */
	public List<String> getDeletedMonitors() {
		return deletedMonitors;
	}

	/**
	 * @param deletedMonitors the deletedMonitors to set
	 */
	public void setDeletedMonitors(List<String> deletedMonitors) {
		this.deletedMonitors = deletedMonitors;
	}
}
