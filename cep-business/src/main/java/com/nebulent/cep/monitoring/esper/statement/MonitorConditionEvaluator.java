/**
 * 
 */
package com.nebulent.cep.monitoring.esper.statement;

import java.util.Map;

/**
 * @author Max Fedorov
 *
 */
public interface MonitorConditionEvaluator {

	/**
	 * @param expression
	 * @param data
	 * @return
	 */
	boolean match(String expression, Map<String, Object> data);
	
	/**
	 * @param expression
	 * @param data
	 * @return
	 */
	String evaluate(String expression, Map<String, Object> data);
}
