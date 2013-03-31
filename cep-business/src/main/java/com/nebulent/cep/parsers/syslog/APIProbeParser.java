/**
 * 
 */
package com.nebulent.cep.parsers.syslog;

import nebulent.schema.software.cep.events._1.APIProbeSysLogEvent;

import org.apache.commons.lang.StringUtils;

/**
 * @author mfedorov
 *
 */
public class APIProbeParser extends BaseSyslogParser<APIProbeSysLogEvent> {

	public static final String PROGRAM_NAME_BDPROBE = "bdprobe";

	/**
	 * 
	 */
	private static final long serialVersionUID = 6666956549579544414L;
	
	private String ciscoEventTag;
	
	APIProbeSysLogEvent event = new APIProbeSysLogEvent();
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.parsers.syslog.BaseSyslogParser#parseMessage(java.lang.String)
	 */
	@Override
	public void parseMessage(String message) {
		if(StringUtils.isBlank(message) || (StringUtils.isBlank(getProgramName()) || !PROGRAM_NAME_BDPROBE.equalsIgnoreCase(getProgramName()))) return;
		message = message.trim();
		
		String[] parts = StringUtils.split(message, SYSLOGLOG_SEP);
		if(parts != null){
			if(parts.length > 0){
				if(parts.length >= 1 && StringUtils.isNotBlank(parts[0])){
					ciscoEventTag = parts[0].trim();
				}
				if(parts.length >= 2 && StringUtils.isNotBlank(parts[1])){
					message = parts[1].trim();
				}
			}
		}
		
		int firstSpace = message.indexOf(' ');
		if(firstSpace > 0){
			setSource(message.substring(0, firstSpace));
		}
		
		if(StringUtils.isNotBlank(getCiscoEventTag())){
			String[] cyscoTagParts = StringUtils.split(getCiscoEventTag(), '-');
			if(cyscoTagParts != null && cyscoTagParts.length > 2){
				event.setUp("0".equals(cyscoTagParts[2]));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.parsers.syslog.BaseSyslogParser#getLogType()
	 */
	@Override
	public APIProbeSysLogEvent getLogType() {
		if(PROGRAM_NAME_BDPROBE.equalsIgnoreCase(getProgramName())){
			copy(event);
			return event;
		}
		return null;
	}

	/**
	 * @return the ciscoEventTag
	 */
	public String getCiscoEventTag() {
		return ciscoEventTag;
	}

	/**
	 * @param ciscoEventTag the ciscoEventTag to set
	 */
	public void setCiscoEventTag(String ciscoEventTag) {
		this.ciscoEventTag = ciscoEventTag;
	}
}
