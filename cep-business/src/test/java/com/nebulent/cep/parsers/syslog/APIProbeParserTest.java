/**
 * 
 */
package com.nebulent.cep.parsers.syslog;

import nebulent.schema.software.cep.events._1.APIProbeSysLogEvent;

import org.junit.Test;

/**
 * @author mfedorov
 *
 */
public class APIProbeParserTest {
	
	@Test
	public void testApiProbeParser(){
		String[] logs = new String[]{
				"Jan  15 14:06:17 misupportqa bdprobe: BD-APIPROBE-5-UPDOWN: OFF-OPCOCD-DS changed state from UP to DOWN",
				//,"<158>Mar  5 09:30:50 miloghost2 stats-ptree[29552]:     653   /usr/local/vmware/sbin/vmware-memctld --background /var/run/vmware-me"
				//,"<165>Mar  5 09:30:50 ds3qa trigdstats[5122]: 03/05 09:30:50   5122.1     ThreadPoolQueue(150)   DEFAULT 0  queryStatistics: pe=<2013-03-05 09:29:00>"
				"<11>2013-03-04T15:11:01.531132-05:00 parallels-Parallels-Virtual-Platform bdprobe: BD-APIPROBE-0-UPDOWN: OFF-GMCI-DS changed state from DOWN to UP3"
				};
		for (String log : logs) {
			APIProbeParser parser = new APIProbeParser();
			parser.parse(log);
			APIProbeSysLogEvent e = parser.getLogType();
			if(e != null){
				System.out.println("host:" + e.getHost() 
						 + ", programName=" + e.getProgramName()
						 + ", cyscoEventTag=" + e.getCiscoEventTag()
						 + ", criticality=" + e.getCriticality()
						 + ", source=" + e.getSource()
						 + ", logDate=" + e.getLogDate().getTime()
						 + ", up=" + e.isUp()
						 + ", message=" + e.getMessage());
			}
		}
	}
}
