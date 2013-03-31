/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

import java.util.Calendar;

import nebulent.schema.software.cep.events._1.JMXApplicationAlertEvent;
import nebulent.schema.software.cep.metadata._1.Criticality;

import com.nebulent.cep.monitoring.service.Generator;

/**
 * @author mfedorov
 *
 */
public class AppAlertJMXCommand implements Runnable {

	private Generator generator;
	private String programName;
	private String host;
	private String source;
	
	protected String developer;
    protected Calendar lastAlertDate;
    protected Calendar lastClearDate;
    protected int alertCount;
    protected int clearCount;
    protected boolean active;
	
	/**
	 * @param generator
	 * @param host
	 */
	public AppAlertJMXCommand(Generator generator, String host) {
		this.generator = generator;
		this.host = host;
	}

	/**
	 * @param generator
	 */
	public AppAlertJMXCommand(Generator generator) {
		this.generator = generator;
	}
	
    /**
     * @param alertCount
     * @param lineNumber
     * @return
     */
    public JMXApplicationAlertEvent createAppAlertEvent(int alertCount, int lineNumber){
    	JMXApplicationAlertEvent event = new JMXApplicationAlertEvent();
    	event.setProgramName(programName);
    	event.setHost(host);
    	event.setSource(source);
    	event.setActive(true);
    	event.setAlertCount(alertCount);
    	event.setClearCount(100);
    	event.setCriticality(Criticality.INFO.value());
    	event.setLastAlertDate(Calendar.getInstance());
    	event.setLogDate(Calendar.getInstance());
    	event.setMessage("ERROR() - BREAK-ON-RECV: Connection reset by peer");
    	event.setSource("BDECNConn.cpp:" + lineNumber);
    	System.out.println(event.getLogDate().getTime() + "-> JMXApplicationAlertEvent(source='" + event.getSource() + "', host='" + event.getHost() + "', programName='" + event.getProgramName() + "', alertCount='" + event.getAlertCount() + "')");
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
		generator.scheduleCommand(new AppAlertJMXEventGenerator(this), 1, 200);
	}
}
