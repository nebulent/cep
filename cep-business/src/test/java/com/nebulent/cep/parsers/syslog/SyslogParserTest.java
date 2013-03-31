/**
 * 
 */
package com.nebulent.cep.parsers.syslog;

import nebulent.schema.software.cep.events._1.BaseSysLogEvent;

import org.junit.Test;

/**
 * @author mfedorov
 *
 */
public class SyslogParserTest {
	
	@Test
	public void testSyslogParser(){
		String[] logs = new String[]{
				"Jan 15 14:06:17 misupportqa bdprobe: BD-APIPROBE-5-UPDOWN: OFF-OPCOCD-DS changed state from UP to DOWN"
				,"Jan  15 14:06:17 misupportqa bdprobe: BD-APIPROBE-5-UPDOWN: OFF-OPCOCD-DS changed state from UP to DOWN"
				,"<158>Jan 5 09:30:50 miloghost2 stats-ptree[29552]:     653   /usr/local/vmware/sbin/vmware-memctld --background /var/run/vmware-me"
				,"<165>Mar  5 09:30:50 ds3qa trigdstats[5122]: 03/05 09:30:50   5122.1     ThreadPoolQueue(150)   DEFAULT 0  queryStatistics: pe=<2013-03-05 09:29:00>"
				,"<11>2013-03-04T15:11:01.531132-05:00 parallels-Parallels-Virtual-Platform bdprobe: BD-APIPROBE-0-UPDOWN: OFF-GMCI-DS changed state from DOWN to UP3"
				,"Jun  5 13:15:18 ds1t db-waits: SEC_IN_WAIT=\"13\" EVENT=\"Streams AQ: qmn slave idle wait\" Killed=\" \" SID=\"1579\" SERIAL=\"1\" SPID=\"18756\"\" PROGRAM=\"oracle@ds1t (Q000)\" MACHINE=\"ds1t\""
				,"Jul 5 13:15:18 ds1t db-waits: SEC_IN_WAIT=\"37647\" EVENT=\"VKTM Logical Idle Wait\" Killed=\" \" SID=\"1261\" SERIAL=\"1\" SPID=\"18283\"\" PROGRAM=\"oracle@ds1t (VKTM)\" MACHINE=\"ds1t\""
				};
		for (String log : logs) {
			SyslogParser parser = new SyslogParser();
			parser.parse(log);
			BaseSysLogEvent e = parser.getLogType();
			if(e != null){
				System.out.println("host:" + e.getHost() 
						 + ", programName=" + e.getProgramName()
						 + ", criticality=" + e.getCriticality()
						 + ", source=" + e.getSource()
						 + ", logDate=" + e.getLogDate().getTime()
						 + ", message=" + e.getMessage());
			}
			else{
				System.out.println("Failed to parse:" + log);
			}
		}
	}
}
