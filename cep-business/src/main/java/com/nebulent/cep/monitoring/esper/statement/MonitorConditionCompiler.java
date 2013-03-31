/**
 * 
 */
package com.nebulent.cep.monitoring.esper.statement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nebulent.schema.software.cep.types._1.Monitor;

/**
 * @author Max Fedorov
 *
 */
public interface MonitorConditionCompiler {

	/**
	 * @param monitor
	 * @return
	 */
	List<CompilationResult> compile(Monitor monitor);
	
	/**
	 * @author Max Fedorov
	 *
	 */
	public static class CompilationResult implements Serializable {
		/**/
		private static final long serialVersionUID = 1L;
		
		/*PROPERTIEs*/
		private String expression;
		private Map<String, Object> data = new HashMap<String, Object>(0);
		private boolean managedExpression;
		
		/**
		 * 
		 */
		public CompilationResult() {
		}

		/**
		 * @param expression
		 * @param data
		 */
		public CompilationResult(String expression, Map<String, Object> data) {
			this.expression = expression;
			this.data = data;
		}

		/**
		 * @return the expression
		 */
		public String getExpression() {
			return expression;
		}

		/**
		 * @param expression the expression to set
		 */
		public void setExpression(String expression) {
			this.expression = expression;
		}

		/**
		 * @return the data
		 */
		public Map<String, Object> getData() {
			return data;
		}

		/**
		 * @param data the data to set
		 */
		public void setData(Map<String, Object> data) {
			this.data = data;
		}
		
		/**
		 * @return the managedExpression
		 */
		public boolean isManagedExpression() {
			return managedExpression;
		}

		/**
		 * @param managedExpression the managedExpression to set
		 */
		public void setManagedExpression(boolean managedExpression) {
			this.managedExpression = managedExpression;
		}
	}
}
