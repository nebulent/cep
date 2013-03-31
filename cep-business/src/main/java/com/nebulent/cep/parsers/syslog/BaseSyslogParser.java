/**
 * 
 */
package com.nebulent.cep.parsers.syslog;

import java.util.Arrays;

import nebulent.schema.software.cep.events._1.BaseSysLogEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.nebulent.cep.utils.DomainUtil;

/**
 * @author mfedorov
 *
 *
 *
 *
Jan 15 14:06:17 misupportqa bdprobe: BD-APIPROBE-5-UPDOWN: OFF-OPCOCD-DS changed state from UP to DOWN

Jan 15 14:06:17 - When the message was logged
misupportqa - reporting host
bdprobe - program name <see formats>

# from this point on the message body has no specific format.  This is an example of a Cisco style tag that encodes some unique information about the message

BD-APIPROBE-5-UPDOWN # cisco style tag
OFF-OPCOCD-DS # API Source
changed state from UP to DOWN - down
changed state from DOWN to UP - up

Program name formats:

bdprobe: # program name or tag as specified in the -t option to logger
bdprobe[3453]: # program name plus pid (in this case 3453)

Cisco style tags:

BD-APIPROBE-5-UPDOWN: # BD-APIPROBE messages - 5 == changed state to down - 0 == changed state to up
%SYS-5-CONFIG_I # Cisco System event - 5 = event severity - Notifying system == CONFIG_I

Cisco event criticality mapping (The number in the middle of a cisco style tag):

[012] => Critical (generates a page)
[34] => Major
5 => Minor
[67] => Warning
*/
public abstract class BaseSyslogParser<LOGTYPE> extends BaseSysLogEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5883533416071161734L;

	public static final char SYSLOGLOG_SEP = ':';

	// FIXME: We need to append the YEAR.
	
	//private static final DateFormat SYSLOG_FORMAT = new SimpleDateFormat("MMM dd HH:mm:ss");
	private static final DateTimeFormatter SYSLOG_FORMATS[] = {DateTimeFormat.forPattern("MMM dd HH:mm:ss yyyy"), DateTimeFormat.forPattern("MMM  dd HH:mm:ss yyyy")};//.withZone(DateTimeZone.UTC);
	private static final DateTimeFormatter XML_DATE_TIME_FORMAT = ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC);
	
	/**
	 * 
	 */
	public BaseSyslogParser() {
		//SYSLOG_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * @param log
	 */
	public abstract LOGTYPE getLogType();
	
	/**
	 * @param log
	 */
	public LOGTYPE parse(String log){
		if(StringUtils.isBlank(log) || log.length() < 15) return null;
		
		DateTime jodaDateTime = new DateTime();
		
		String logNoDate = "";
		
		// Get date.
		if(log.startsWith("<")){
			int rAngPos = log.indexOf(">");
			if(rAngPos > -1){
				try {
					for (int i= 0; i < SYSLOG_FORMATS.length ; i++) {
						String dateStr = log.substring(rAngPos + 1, rAngPos + 15 + i) + " " + jodaDateTime.getYear();
						try{
							logDate = DomainUtil.toCalendar(SYSLOG_FORMATS[i].parseDateTime(dateStr).getMillis());
							logNoDate = log.substring(rAngPos + 15 + i).trim(); // If there is extra space in the date, add that in.
							break;
						}
						catch (Throwable e) {
							//System.out.println(e.getMessage());
							if(i == (SYSLOG_FORMATS.length -1)){
								throw new Throwable("Failed to parse:" + dateStr + " based on format:" + i);
							}
						}
					}
				}
				catch (Throwable e) {
					//System.out.println(e.getMessage());
					int spaceInd = log.indexOf(" ", rAngPos);
					if(spaceInd > -1){
						String dateStr = log.substring(rAngPos + 1, spaceInd);
						logDate = DomainUtil.toCalendar(XML_DATE_TIME_FORMAT.parseDateTime(dateStr).getMillis());
						logNoDate = log.substring(spaceInd).trim();
					}
				}
			}
		}
		else{
			for (int i= 0; i < SYSLOG_FORMATS.length ; i++) {
				String dateStr = log.substring(0, 14 + i) + " " + jodaDateTime.getYear();
				logNoDate = log.substring(15 + i).trim(); // If there is extra space in the date, add that in.
				try{
					//System.out.println("NO<>" + dateStr);
					DateTime dt = SYSLOG_FORMATS[i].parseDateTime(dateStr);
					if(dt != null){
						logDate = DomainUtil.toCalendar(dt.getMillis());
						break;
					}
				}
				catch (Throwable e) {
					//System.out.println("NO<>" + e.getMessage());
				}
			}
		}
		
		//System.out.println("DATE:" + logDate.getTime() + "\nLOGNODATE:" + logNoDate);
		
		String[] parts = StringUtils.split(logNoDate, SYSLOGLOG_SEP);
		if(parts != null){
			if(parts.length > 0){
				String[] hostAndProgram = StringUtils.split(parts[0], ' ');
				if(hostAndProgram != null && hostAndProgram.length > 1){
					host = hostAndProgram[0];
					programName = hostAndProgram[1];
					if(StringUtils.isNotBlank(programName)){
						int pidPos = programName.indexOf('[');
						if(pidPos > -1){
							programName = programName.substring(0, pidPos);
						}
					}
				}
				String message = StringUtils.join(Arrays.copyOfRange(parts, 1, parts.length), SYSLOGLOG_SEP);
				setMessage(message);
				parseMessage(message);
			}
		}
		
		return getLogType();
	}
	
	/**
	 * @param message
	 */
	public abstract void parseMessage(String message);
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        return ToStringBuilder.reflectionToString(this);
	}
	
	/**
	 * @param event
	 */
	public void copy(BaseSysLogEvent event){
		event.setCriticality(getCriticality());
		event.setHost(getHost());
		event.setLogDate(getLogDate());
		event.setProgramName(getProgramName());
		event.setSource(getSource());
		event.setMessage(getMessage());
	}
}
