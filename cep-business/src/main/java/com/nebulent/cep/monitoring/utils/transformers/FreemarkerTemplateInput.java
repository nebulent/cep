/**
 * 
 */
package com.nebulent.cep.monitoring.utils.transformers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Max Fedorov
 *
 */
public class FreemarkerTemplateInput implements Serializable {

	/**/
	private static final long serialVersionUID = 1L;

	/*PROPERTIEs*/
	private String templateName;
	private String templateContent;
	private Map<String, Object> parameters = new HashMap<String, Object>(0);
	private boolean loadTemplateFromCache;
	
	/**
	 * 
	 */
	public FreemarkerTemplateInput() {
	}
	
	/**
	 * @param templateName
	 * @param templateContent
	 * @param parameters
	 */
	public FreemarkerTemplateInput(String templateName, String templateContent, Map<String, Object> parameters) {
		this.templateName = templateName;
		this.templateContent = templateContent;
		this.parameters = parameters;
	}
	
	/**
	 * @param templateName
	 * @param templateContent
	 * @param parameters
	 * @param loadTemplateFromCache
	 */
	public FreemarkerTemplateInput(String templateName, String templateContent, Map<String, Object> parameters, boolean loadTemplateFromCache) {
		this.templateName = templateName;
		this.templateContent = templateContent;
		this.parameters = parameters;
		this.loadTemplateFromCache = loadTemplateFromCache;
	}

	/**
	 * @return the templateContent
	 */
	public String getTemplateContent() {
		return templateContent;
	}

	/**
	 * @param templateContent the templateContent to set
	 */
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the loadTemplateFromCache
	 */
	public boolean isLoadTemplateFromCache() {
		return loadTemplateFromCache;
	}

	/**
	 * @param loadTemplateFromCache the loadTemplateFromCache to set
	 */
	public void setLoadTemplateFromCache(boolean loadTemplateFromCache) {
		this.loadTemplateFromCache = loadTemplateFromCache;
	}
}
