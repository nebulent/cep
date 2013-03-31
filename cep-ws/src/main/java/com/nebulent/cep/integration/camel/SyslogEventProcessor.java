/**
 * 
 */
package com.nebulent.cep.integration.camel;

import nebulent.schema.software.cep.events._1.BaseSysLogEvent;

import com.nebulent.cep.parsers.syslog.APIProbeParser;
import com.nebulent.cep.parsers.syslog.SyslogParser;

/**
 * @author mfedorov
 *
 */
public class SyslogEventProcessor{

	/**
	 * @param message
	 * @return
	 */
	public BaseSysLogEvent process(String message) {
		BaseSysLogEvent event = new SyslogParser().parse(message);
		if(event != null){
			if(APIProbeParser.PROGRAM_NAME_BDPROBE.equalsIgnoreCase(event.getProgramName())){
				APIProbeParser parser = new APIProbeParser();
				parser.copy(event);
				parser.parseMessage(message);
				return parser.getLogType();
			}
		}
		return null;
	}
}
