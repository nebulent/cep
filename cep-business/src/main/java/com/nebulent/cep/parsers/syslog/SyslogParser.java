/**
 * 
 */
package com.nebulent.cep.parsers.syslog;

import nebulent.schema.software.cep.events._1.BaseSysLogEvent;

/**
 * @author mfedorov
 *
 */
public class SyslogParser extends BaseSyslogParser<BaseSysLogEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6237580400332132674L;

	/* (non-Javadoc)
	 * @see com.nebulent.cep.parsers.syslog.BaseSyslogParser#getLogType()
	 */
	@Override
	public BaseSysLogEvent getLogType() {
		BaseSysLogEvent event = new BaseSysLogEvent();
		copy(event);
		return event;
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.parsers.syslog.BaseSyslogParser#parse(java.lang.String)
	 */
	@Override
	public BaseSysLogEvent parse(String log) {
		return super.parse(log);
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.parsers.syslog.BaseSyslogParser#parseMessage(java.lang.String)
	 */
	@Override
	public void parseMessage(String message) {
		
	}
}
