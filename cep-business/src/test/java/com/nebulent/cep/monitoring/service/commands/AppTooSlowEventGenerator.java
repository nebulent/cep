/**
 * 
 */
package com.nebulent.cep.monitoring.service.commands;

/**
 * @author mfedorov
 *
 */
public class AppTooSlowEventGenerator implements Runnable {

	private AppTooSlowCommand command;
	int count = 10;
	
	/**
	 * @param command
	 */
	public AppTooSlowEventGenerator(AppTooSlowCommand command) {
		this.command = command;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//for(int i = 0; i < count; i++){
			//System.out.println("Generating: " + command.createAPIProbeSysLogEvent());
			command.getGenerator().getEsperBean().sendEvent(command.createAPIProbeSysLogEvent());
		//}
		count++;
	}
}
