/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

/**
 * @author mfedorov
 *
 */
public class AppAlertJMXEventGenerator implements Runnable {

	private AppAlertJMXCommand appAlertCommand;
	private int alertCount = 100;
	private int lineNumber = 100;
	
	/**
	 * @param databaseRetentionCommand
	 */
	public AppAlertJMXEventGenerator(
			AppAlertJMXCommand appAlertCommand) {
		this.appAlertCommand = appAlertCommand;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		appAlertCommand.getGenerator().getEsperBean().sendEvent(appAlertCommand.createAppAlertEvent(alertCount++, --lineNumber));
	}
}
