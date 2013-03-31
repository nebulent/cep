/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

import java.util.Calendar;

import nebulent.schema.software.cep.events._1.APIProbeSysLogEvent;

import com.nebulent.cep.monitoring.service.Generator;

/**
 * @author mfedorov
 *
 */
public class AppTooSlowCommand implements Runnable {

	private Generator generator;
	private String programName;
	private String host;
	private String source;
	
	/**
	 * @param generator
	 * @param host
	 */
	public AppTooSlowCommand(Generator generator, String host) {
		this.generator = generator;
		this.host = host;
	}

	/**
	 * @param generator
	 */
	public AppTooSlowCommand(Generator generator) {
		this.generator = generator;
	}

    /**
     * @param appID
     * @param host
     * @return
     */
    public APIProbeSysLogEvent createAPIProbeSysLogEvent(){
    	APIProbeSysLogEvent event = new APIProbeSysLogEvent();
    	event.setProgramName(programName);
    	event.setHost(host);
    	event.setSource(source);
    	event.setLogDate(Calendar.getInstance());
    	System.out.println(event.getLogDate().getTime() + "-> APIProbeSysLogEvent(source='" + event.getSource() + "', host='" + event.getHost() + "', programName='" + event.getProgramName() + "')");
    	return event;
    }

	/**
	 * @return the generator
	 */
	public Generator getGenerator() {
		return generator;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public void run() {
		generator.scheduleCommand(new AppTooSlowEventGenerator(this), 1, 200);
	}
}
