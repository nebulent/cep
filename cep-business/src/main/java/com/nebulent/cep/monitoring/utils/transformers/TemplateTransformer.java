/**
 * 
 */
package com.nebulent.cep.monitoring.utils.transformers;

/**
 * @author Max Fedorov
 *
 */
public interface TemplateTransformer<INPUT, OUTPUT> {

	/**
	 * @param input
	 * @return
	 */
	OUTPUT transform(INPUT input);
}
