/**
 * 
 */
package com.nebulent.cep.monitoring.esper.annotation;


/**
 * @author Max Fedorov
 *
 */
public @interface MonitorInfo {
	/*monitor id*/
	long monitorId();
	/*monitor resourceCode*/
	String resourceCode();
	/*monitor conditionCode*/
	String conditionCode();
	/*monitor criticality*/
	String criticalityTypeCode();
	/*monitor query type*/
	boolean managed();
}
