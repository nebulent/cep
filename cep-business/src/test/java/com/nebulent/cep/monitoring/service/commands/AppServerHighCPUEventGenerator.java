/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

/**
 * @author mfedorov
 *
 */
public class AppServerHighCPUEventGenerator implements Runnable {

	private DatabaseRetentionCommand databaseRetentionCommand;
	private double initialCPU = 60.5;
	
	/**
	 * @param databaseRetentionCommand
	 */
	public AppServerHighCPUEventGenerator(
			DatabaseRetentionCommand databaseRetentionCommand) {
		this.databaseRetentionCommand = databaseRetentionCommand;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		initialCPU += 0.1;
		databaseRetentionCommand.getGenerator().getEsperBean().sendEvent(databaseRetentionCommand.createHighCPU(initialCPU));
	}
}
