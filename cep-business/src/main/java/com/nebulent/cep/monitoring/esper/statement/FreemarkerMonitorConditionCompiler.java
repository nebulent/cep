/**
 * 
 */
package com.nebulent.cep.monitoring.esper.statement;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nebulent.schema.software.cep.metadata._1.ConditionComparator;
import nebulent.schema.software.cep.metadata._1.Configuration;
import nebulent.schema.software.cep.metadata._1.MonitorConditionParameterType;
import nebulent.schema.software.cep.metadata._1.MonitorConditionType;
import nebulent.schema.software.cep.metadata._1.MonitorOutParameterType;
import nebulent.schema.software.cep.metadata._1.MonitorParameter;
import nebulent.schema.software.cep.metadata._1.MonitorParameters;
import nebulent.schema.software.cep.metadata._1.MonitoredResourceType;
import nebulent.schema.software.cep.types._1.Monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Unmarshaller;
import org.springframework.util.Assert;

import com.nebulent.cep.monitoring.MonitorDefinitionStore;
import com.nebulent.cep.utils.MarshallerUtils;

/**
 * @author Max Fedorov
 *
 */
public class FreemarkerMonitorConditionCompiler implements MonitorConditionCompiler {
	
	/*LOGGER*/
	private static Logger logger = LoggerFactory.getLogger(FreemarkerMonitorConditionCompiler.class);

	/*SERVICEs*/
	private MonitorDefinitionStore monitorDefinitionStore;
	
	/*UNMARSHALLER PROPERTIEs*/
	private Unmarshaller unmarshaller;

	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.esper.statement.MonitorConditionCompiler#compile(bonddeskgroup.schema.software.cep.types._1.Monitor)
	 */
	public List<CompilationResult> compile(Monitor monitor) {
		Assert.notNull(monitor);
		logger.debug("Compiling the monitor '{}'", monitor.getId());
		List<MonitorConditionCompiler.CompilationResult> results = new ArrayList<MonitorConditionCompiler.CompilationResult>();
		
		Configuration configuration = monitorDefinitionStore.getMonitorConfiguration();
		Assert.notNull(configuration, "Monitor definitions cannot be null !!!");
		MonitoredResourceType monitoredResource = getMonitoredResource(configuration, monitor.getResourceCode());
		Assert.notNull(monitoredResource);
		MonitorConditionType monitorCondition = getMonitorCondition(monitoredResource, monitor.getConditionCode());
		Assert.notNull(monitorCondition);
		
		Assert.notNull(monitor);
    	Assert.notEmpty(monitor.getConditions());
    	String expression = monitor.getConditions().iterator().next().getExpression();
    	Assert.notNull(expression);
    	InputStream inputStream = new ByteArrayInputStream(expression.getBytes());
    	
		MonitorParameters arguments = MarshallerUtils.unmarshallUsingStaxSource(MonitorParameters.class, unmarshaller, inputStream);
		String expressionQuery = monitorCondition.getExpression();
		CompilationResult result = new CompilationResult();
		for (MonitorParameter argument : arguments.getMonitorParameters()) {
			expressionQuery = normalizeComparatorFromExpression(expressionQuery, argument);
			if(isInteger(argument.getValue())) {
				result.getData().put(argument.getId(), Integer.parseInt(argument.getValue()));
			} else if(isDouble(argument.getValue())) {
				result.getData().put(argument.getId(), Double.parseDouble(argument.getValue()));
			} else {
				result.getData().put(argument.getId(), argument.getValue());
			}
		}
		result.setExpression(expressionQuery);
		results.add(result);
		
		// Let's take care of managed conditions.
		List<MonitorConditionType> managedConditions = getManagedMonitorCondition(monitoredResource, monitor.getConditionCode());
		for (MonitorConditionType managedMonitorCondition : managedConditions) {
			result = new CompilationResult();
			expressionQuery = managedMonitorCondition.getExpression();
			for (MonitorConditionParameterType argument : managedMonitorCondition.getSupportedParameters()) {
				if(isInteger(argument.getDefaultValue())) {
					result.getData().put(argument.getId(), Integer.parseInt(argument.getDefaultValue()));
				} else if(isDouble(argument.getDefaultValue())) {
					result.getData().put(argument.getId(), Double.parseDouble(argument.getDefaultValue()));
				} else {
					result.getData().put(argument.getId(), argument.getDefaultValue());
				}
			}
			result.setExpression(expressionQuery);
			result.setManagedExpression(true);
			results.add(result);
		}
		
		return results;
	}
	
	/**
	 * @param configuration
	 * @param resourceCode
	 * @return
	 */
	public static  MonitoredResourceType getMonitoredResource(Configuration configuration, String resourceCode) {
		for (MonitoredResourceType monitoredResource : configuration.getMonitoringMetadata().getMonitoredResources()) {
			if(monitoredResource.getId().value().equalsIgnoreCase(resourceCode)) {
				return monitoredResource;
			}
		}
		return null;
	}
	
	/**
	 * @param monitoredResource
	 * @param conditionId
	 * @return
	 */
	public static MonitorConditionType getMonitorCondition(MonitoredResourceType monitoredResource, String conditionCode) {
		logger.debug("Trying to find condition code:" + conditionCode + " in resource:" + monitoredResource.getId());
		for (MonitorConditionType monitorCondition : monitoredResource.getSupportedConditions()) {
			if(monitorCondition.getId().equals(conditionCode)) {
				return monitorCondition;
			}
		}
		return null;
	}
	
	/**
	 * @param configuration
	 * @param resourceCode
	 * @param conditionCode
	 * @return
	 */
	public static List<MonitorOutParameterType> getMonitorConditionOutParameters(Configuration configuration, String resourceCode, String conditionCode) {
		if(resourceCode == null || conditionCode == null) return null;
		MonitoredResourceType monitoredResource = getMonitoredResource(configuration, resourceCode);
		if(monitoredResource != null){
			MonitorConditionType monitorCondition = getMonitorCondition(monitoredResource,conditionCode);
			if(monitorCondition != null){
				return monitorCondition.getOutParameters();
			}
		}
		return null;
	}
	
	/**
	 * @param monitoredResource
	 * @param conditionId
	 * @return
	 */
	private List<MonitorConditionType> getManagedMonitorCondition(MonitoredResourceType monitoredResource, String conditionCode) {
		List<MonitorConditionType> managedConditions = new ArrayList<MonitorConditionType>();
		for (MonitorConditionType monitorCondition : monitoredResource.getManagedConditions()) {
			if(monitorCondition.getId().equals(conditionCode)) {
				managedConditions.add(monitorCondition);
			}
		}
		return managedConditions;
	}
	
	/**
	 * @param expression
	 * @param monitorParameter
	 * @return
	 */
	private String normalizeComparatorFromExpression(String expression, MonitorParameter monitorParameter) {
		return normalizeComparatorFromExpression(expression, monitorParameter.getId(), monitorParameter.getComparator());
	}
	
	/**
	 * @param expression
	 * @param parameterId
	 * @param comparator
	 * @return
	 */
	private String normalizeComparatorFromExpression(String expression, String parameterId, ConditionComparator comparator) {
		String toReplace = "$comparator[\"" + parameterId + "\"]";
		String replaceWith = null;
		if (comparator == null) return expression;
		switch (comparator) {
			case GREATER_THAN:
				replaceWith = ">";
				break;
			case GREATER_THAN_EQUALS:
				replaceWith = ">=";
				break;
			case LESS_THAN:
				replaceWith = "<";
				break;
			case LESS_THAN_EQUALS:
				replaceWith = "<=";
				break;
			case EQUALS:
				replaceWith = "==";
				break;
			case NOT_EQUALS:
				replaceWith = "!=";
				break;
		}
		return expression.replace(toReplace, replaceWith);
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException exc) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException exc) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param monitorDefinitionStore the monitorDefinitionStore to set
	 */
	public void setMonitorDefinitionStore(
			MonitorDefinitionStore monitorDefinitionStore) {
		this.monitorDefinitionStore = monitorDefinitionStore;
	}

	/**
	 * @param unmarshaller the unmarshaller to set
	 */
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
}
