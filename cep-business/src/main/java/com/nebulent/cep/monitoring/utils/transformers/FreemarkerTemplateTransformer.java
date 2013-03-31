/**
 * 
 */
package com.nebulent.cep.monitoring.utils.transformers;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

/**
 * @author Max Fedorov
 *
 */
public class FreemarkerTemplateTransformer implements TemplateTransformer<FreemarkerTemplateInput, String> {

	/*PROPERTIEs*/
	private Configuration freemarkerConfiguration;
	private StringTemplateLoader stringTemplateLoader;
	
	@Override
	public String transform(FreemarkerTemplateInput input) {
		Assert.notNull(input);
		Assert.notEmpty(input.getParameters());
		Assert.hasLength(input.getTemplateContent());
		try {
			if(!input.isLoadTemplateFromCache() || stringTemplateLoader.findTemplateSource(input.getTemplateName()) == null) {
				stringTemplateLoader.putTemplate(input.getTemplateName(), new String(input.getTemplateContent()));
			}
			String response = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(input.getTemplateName()), input.getParameters());
			//return response != null ? response.getBytes() : null;
			return response;
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	/**
	 * @param freemarkerConfiguration the freemarkerConfiguration to set
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
		this.stringTemplateLoader = (StringTemplateLoader)freemarkerConfiguration.getTemplateLoader();
	}
}
















