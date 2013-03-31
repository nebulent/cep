/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

/**
 * @author mfedorov
 *
 */
public class DatabaseSlowQueryEventGenerator implements Runnable {

	private DatabaseRetentionCommand databaseRetentionCommand;
	private int queryCount = 5;
	
	/**
	 * @param databaseRetentionCommand
	 */
	public DatabaseSlowQueryEventGenerator(
			DatabaseRetentionCommand databaseRetentionCommand) {
		this.databaseRetentionCommand = databaseRetentionCommand;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		queryCount += 1;
		databaseRetentionCommand.getGenerator().getEsperBean().sendEvent(databaseRetentionCommand.createDatabaseSlowQueryEvent(queryCount));
	}
}
