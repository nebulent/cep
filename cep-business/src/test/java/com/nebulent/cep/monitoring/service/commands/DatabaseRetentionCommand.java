/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

import java.util.Calendar;
import java.util.Date;

import nebulent.schema.software.cep.events._1.AppServerHighCPUEvent;
import nebulent.schema.software.cep.events._1.DatabaseSlowQueryEvent;
import nebulent.schema.software.cep.events._1.LoginEvent;

import org.apache.commons.lang.RandomStringUtils;

import com.nebulent.cep.monitoring.service.Generator;

/**
 * @author mfedorov
 *
 */
public class DatabaseRetentionCommand implements Runnable {

	private Generator generator;
	private String applicationID;
	private String host;
	private String databaseName;
	
	/**
	 * @param generator
	 * @param applicationID
	 * @param host
	 * @param databaseName
	 */
	public DatabaseRetentionCommand(Generator generator, String applicationID,
			String host, String databaseName) {
		this(generator);
		this.applicationID = applicationID;
		this.host = host;
		this.databaseName = databaseName;
	}

	/**
	 * @param generator
	 */
	public DatabaseRetentionCommand(Generator generator) {
		this.generator = generator;
	}

	/**
	 * @return the applicationID
	 */
	public String getApplicationID() {
		return applicationID;
	}

	/**
	 * @param applicationID the applicationID to set
	 */
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
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
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
     * @param appID
     * @param host
     * @param cpuPerc
     * @return
     */
    public AppServerHighCPUEvent createHighCPU(double cpuPerc){
    	System.out.println(new Date() + "-> HighCPU(" + cpuPerc + ")");
    	AppServerHighCPUEvent event = new AppServerHighCPUEvent();
    	event.setApplicationID(getApplicationID());
    	event.setHost(getHost());
    	event.setCpuPerc(cpuPerc);
    	event.setIdlePerc(100D-cpuPerc);
    	event.setOccurredOn(Calendar.getInstance());
    	return event;
    }
    
    /**
     * @param databaseName
     * @param host
     * @param slowQueryCount
     * @return
     */
    public DatabaseSlowQueryEvent createDatabaseSlowQueryEvent(int slowQueryCount){
    	System.out.println(new Date() + "-> DatabaseSlowQueryEvent(" + slowQueryCount + ")");
    	DatabaseSlowQueryEvent event = new DatabaseSlowQueryEvent();
    	event.setDatabaseName(databaseName);
    	event.setHost(host);
    	event.setNumberOfQueries(slowQueryCount);
    	event.setOccurredOn(Calendar.getInstance());
    	return event;
    }
    
    /**
     * @param appID
     * @param host
     * @return
     */
    public LoginEvent createLoginEvent(){
    	LoginEvent event = new LoginEvent();
    	event.setApplicationID(getApplicationID());
    	event.setHost(host);
    	event.setUserName(RandomStringUtils.randomAlphabetic(6));
    	event.setOccurredOn(Calendar.getInstance());
    	System.out.println(new Date() + "-> LoginEvent(" + event.getUserName() + ")");
    	return event;
    }

	/**
	 * @return the generator
	 */
	public Generator getGenerator() {
		return generator;
	}

	@Override
	public void run() {
		//generator.scheduleCommand(new AppServerHighCPUEventGenerator(this), 1, 20);
		generator.scheduleCommand(new AppServerHighCPUEventDecreasingGenerator(this), 1, 20);
		
		generator.scheduleCommand(new LoginEventGenerator(this), 1, 10);
		generator.scheduleCommand(new DatabaseSlowQueryEventGenerator(this), 1, 45);
	}
}
