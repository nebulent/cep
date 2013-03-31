/**
 * 
 */
package com.nebulent.cep.monitoring.esper.statement;

import java.util.Map;
import java.util.UUID;

import org.springframework.util.Assert;

import com.nebulent.cep.monitoring.utils.transformers.FreemarkerTemplateInput;
import com.nebulent.cep.monitoring.utils.transformers.TemplateTransformer;

/**
 * @author Max Fedorov
 *
 */
public class FreemarkerMonitorConditionEvaluator implements MonitorConditionEvaluator {

	/*SERVICEs*/
	private TemplateTransformer<FreemarkerTemplateInput, String> templateTransformer;
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.esper.statement.MonitorConditionEvaluator#match(java.lang.String, java.util.Map)
	 */
	@Override
	public boolean match(String expression, Map<String, Object> data) {
		Assert.notNull(expression);
		return Boolean.parseBoolean(new String(templateTransformer.transform(new FreemarkerTemplateInput(generateUUID(), expression, data))));
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.esper.statement.MonitorConditionEvaluator#evaluate(java.lang.String, java.util.Map)
	 */
	@Override
	public String evaluate(String expression, Map<String, Object> data) {
		return new String(templateTransformer.transform(new FreemarkerTemplateInput(generateUUID(), expression, data)));
	}

	/**
	 * @param templateTransformer the templateTransformer to set
	 */
	public void setTemplateTransformer(TemplateTransformer<FreemarkerTemplateInput, String> templateTransformer) {
		this.templateTransformer = templateTransformer;
	}
	
	/**
	 * @return
	 */
	// TODO: replace with monitor id, so template is not loaded into cache on every call.
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
}
