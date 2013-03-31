/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

/**
 * @author mfedorov
 *
 */
public class AppServerHighCPUEventDecreasingGenerator implements Runnable {

	private DatabaseRetentionCommand databaseRetentionCommand;
	private double initialCPU = 20.5;
	
	/**
	 * @param databaseRetentionCommand
	 */
	public AppServerHighCPUEventDecreasingGenerator(
			DatabaseRetentionCommand databaseRetentionCommand) {
		this.databaseRetentionCommand = databaseRetentionCommand;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		initialCPU -= 0.9;
		databaseRetentionCommand.getGenerator().getEsperBean().sendEvent(databaseRetentionCommand.createHighCPU(initialCPU));
	}
}
