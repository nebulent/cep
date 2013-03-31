/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

/**
 * @author mfedorov
 *
 */
public class LoginEventGenerator implements Runnable {

	private DatabaseRetentionCommand databaseRetentionCommand;
	int count = 1;
	
	/**
	 * @param databaseRetentionCommand
	 */
	public LoginEventGenerator(
			DatabaseRetentionCommand databaseRetentionCommand) {
		this.databaseRetentionCommand = databaseRetentionCommand;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		for(int i = 0; i < count; i++){
			databaseRetentionCommand.getGenerator().getEsperBean().sendEvent(databaseRetentionCommand.createLoginEvent());
		}
		count++;
	}
}
