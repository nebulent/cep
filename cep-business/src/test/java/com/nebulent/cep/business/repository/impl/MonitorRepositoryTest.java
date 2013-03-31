package com.nebulent.cep.business.repository.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import nebulent.schema.software.cep.metadata._1.ConditionComparator;
import nebulent.schema.software.cep.metadata._1.Criticality;
import nebulent.schema.software.cep.metadata._1.MonitorParameter;
import nebulent.schema.software.cep.metadata._1.MonitorParameters;
import nebulent.schema.software.cep.metadata._1.MonitoredResourceCode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.domain.model.CepCondition;
import com.nebulent.cep.domain.model.CepMonitor;
import com.nebulent.cep.repository.impl.JpaMonitorRepository;
import com.nebulent.cep.utils.MarshallerUtils;

/**
 * @author Max Fedorov
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-cep.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MonitorRepositoryTest {
    
	protected final static Logger LOGGER = LoggerFactory.getLogger(MonitorRepositoryTest.class);
	
	@Autowired
    private JpaMonitorRepository monitorRepository;
	
	@Autowired
	private Marshaller marshaller;
	
	@Test
    public void testCreateAlert() {
		CepMonitor monitor = new CepMonitor();
		monitor.setId(1L);
		
		CepAlert alert = new CepAlert();
		alert.setChangeBy("mfedorov");
		alert.setChangeDate(System.currentTimeMillis());
		alert.setEndTime(System.currentTimeMillis());
		alert.setMessage("Here is the test message.");
		alert.setStartTime(System.currentTimeMillis());
		alert.setStatus("A");
		alert.setMonitor(monitor);
		
		alert = monitorRepository.createAlert(alert);
		System.out.println(alert.getId() + "," + alert.getMessage());
	}
	
	@Test
    public void testGetAlerts() {
		List<CepAlert> alerts = monitorRepository.getAllAlerts();
		for (CepAlert alert : alerts) {
			System.out.println(alert.getId() + "," + alert.getMessage());
		}
	}
	
	@Test
    public void testCreateApplicationHighCPUMonitor() {
    	/*
    	SELECT Server.applicationID, Server.host, avg(Server.cpuPerc) FROM AppServerHighCPUEvent(applicationID='${applicationID}', host='${host}').win:time(${cpuCaptureInterval}) as Server
		GROUP BY Server.applicationID, Server.host
		HAVING avg(Server.cpuPerc) $comparator["cpuPercAverageComparator"] ${cpuPercAverageComparator}
    	*/
    	
    	MonitorParameters params = new MonitorParameters();
    	
    	MonitorParameter param = new MonitorParameter();
    	param.setId("applicationID");
    	param.setValue("APP01");
    	params.getMonitorParameters().add(param);
    	
    	param = new MonitorParameter();
    	param.setId("host");
    	param.setValue("HOST01");
    	params.getMonitorParameters().add(param);
    	
    	param = new MonitorParameter();
    	param.setId("cpuCaptureInterval");
    	param.setValue("2 min");
    	params.getMonitorParameters().add(param);
    	
    	param = new MonitorParameter();
    	param.setId("cpuPercAverageComparator");
    	param.setValue("50.00");
    	param.setComparator(ConditionComparator.GREATER_THAN);
    	params.getMonitorParameters().add(param);
    	
    	CepMonitor monitor = new CepMonitor();
    	monitor.setChangeBy("mfedorov");
    	monitor.setChangeDate(System.currentTimeMillis());
    	monitor.setCriticalityTypeCode(Criticality.INFO.value());
    	monitor.setMessage("Application server is getting overloaded...");
    	monitor.setName("Application Server APP01");
    	monitor.setResourceCode(MonitoredResourceCode.APPLICATION.value());
    	monitor.setConditionCode("HighCPUCondition");
    	monitor.setStatus('A');
    	
    	CepCondition condition = new CepCondition();
    	try {
			condition.setDefinition(new String(MarshallerUtils.marshallUsingStreamResult(marshaller, params), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	condition.setName(monitor.getName());
    	
    	monitor.getConditions().add(condition);
    	
    	monitorRepository.createMonitor(monitor);
	}
	
	
	@Test
    public void testCreateAppDownTooLongConditionMonitor() {
    	/*
    	select apiProbeDown.source as source, apiProbeDown.host as host, apiProbeDown.programName as programName, apiProbeDown.logDate as logDate
		from pattern [
		every-distinct(apiProbeDown.source, apiProbeDown.host, apiProbeDown.programName) apiProbeDown = APIProbeSysLogEvent(source='OFF-OPCOCD-DS', host='${host}', programName='bdprobe') 
		-> 
		(not apiProbeUp = APIProbeSysLogEvent(source=apiProbeDown.source, host=apiProbeDown.host, programName=apiProbeDown.programName)) where timer:within(${probeCaptureInterval})
		]
    	*/
    	
    	MonitorParameters params = new MonitorParameters();
    	
    	MonitorParameter param = new MonitorParameter();
    	param.setId("host");
    	param.setValue("HOST01");
    	params.getMonitorParameters().add(param);
    	
    	param = new MonitorParameter();
    	param.setId("probeCaptureInterval");
    	param.setValue("3 min");
    	params.getMonitorParameters().add(param);
    	
    	CepMonitor monitor = new CepMonitor();
    	monitor.setChangeBy("mfedorov");
    	monitor.setChangeDate(System.currentTimeMillis());
    	monitor.setCriticalityTypeCode(Criticality.WARNING.value());
    	monitor.setMessage("Application on host ${event.host} never came up since ${event.logDate}");
    	monitor.setName("Application Down Too Long HOST01");
    	monitor.setResourceCode(MonitoredResourceCode.APPLICATION.value());
    	monitor.setConditionCode("AppDownTooLongCondition");
    	monitor.setStatus('A');
    	
    	CepCondition condition = new CepCondition();
    	try {
			condition.setDefinition(new String(MarshallerUtils.marshallUsingStreamResult(marshaller, params), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	condition.setName(monitor.getName());
    	
    	monitor.getConditions().add(condition);
    	
    	monitorRepository.createMonitor(monitor);
	}
	
	
	@Test
    public void testAppAlertPassThroughConditionMonitor() {
    	MonitorParameters params = new MonitorParameters();
    	
    	MonitorParameter param = new MonitorParameter();
    	param.setId("host");
    	param.setValue("ps6p");
    	params.getMonitorParameters().add(param);
    	
    	param = new MonitorParameter();
    	param.setId("probeCaptureInterval");
    	param.setValue("1 day");
    	params.getMonitorParameters().add(param);
    	
    	param = new MonitorParameter();
    	param.setId("programName");
    	param.setValue("execAPI2");
    	params.getMonitorParameters().add(param);
    	
    	CepMonitor monitor = new CepMonitor();
    	monitor.setChangeBy("mfedorov");
    	monitor.setChangeDate(System.currentTimeMillis());
    	monitor.setCriticalityTypeCode(Criticality.WARNING.value());
    	monitor.setMessage("Application ${event.programName} on host ${event.host} received alert ${event.logDate}");
    	monitor.setName("Application alert (JMX)");
    	monitor.setResourceCode(MonitoredResourceCode.APPLICATION.value());
    	monitor.setConditionCode("AppAlertPassThroughCondition");
    	monitor.setStatus('A');
    	
    	CepCondition condition = new CepCondition();
    	try {
			condition.setDefinition(new String(MarshallerUtils.marshallUsingStreamResult(marshaller, params), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	condition.setName(monitor.getName());
    	
    	monitor.getConditions().add(condition);
    	
    	monitorRepository.createMonitor(monitor);
	}
	
    /**
     * 
     */
    @Test
    public void testCreateDatabaseRetentionMonitor() {
    	/*
    	SELECT Server.host , Server.cpuPerc, Server_PAST.cpu, count(Login.userName), count(Login_PAST.userName), Db.numberOfQueries
    	FROM
    	AppServerHighCPUEvent.win:time(1 min) as Server
    	, Server.win:ext_timed(current_timestamp(), 3 min) as Server_PAST
    	, LoginEvent.win:time(1 min) as Login
    	, LoginEvent.win:ext_timed(current_timestamp(), 3 min) as Login_PAST
    	, DatabaseSlowQueryEvent.win:time (1min) as Db
    	HAVING
    	count(Login.userName) > count(Login_PAST.userName) * 1.10 
    	AND avg(Server.cpuPerc) < avg (Server_PAST.cpuPerc) * 1.05 
    	AND Db.numberOfQueries > 10
    	*/
    	
    	MonitorParameters params = new MonitorParameters();
    	
    	/*
    	<supportedParameters id="cpuCaptureInterval" label="CPU Capture Interval" datatype="String" required="true">
			<description>Specify time window to collect Application Server High CPU events (for example, '1 min' or '1 hour')</description>
		</supportedParameters>
    	*/
    	MonitorParameter param = new MonitorParameter();
    	param.setId("cpuCaptureInterval");
    	param.setValue("1 min");
    	params.getMonitorParameters().add(param);
    	
    	/*
    	<supportedParameters id="cpuCaptureFromPastInterval" label="CPU Capture from past Interval" datatype="String" required="true">
			<description>Specify time window to collect historical Application Server High CPU events. Must be greater than CPU Capture Interval (for example, '5 min' or '3 hour')</description>
		</supportedParameters>
		*/
    	param = new MonitorParameter();
    	param.setId("cpuCaptureFromPastInterval");
    	param.setValue("3 min");
    	params.getMonitorParameters().add(param);
    	
    	/*
    	<supportedParameters id="loginCaptureInterval" label="Login Capture Interval" datatype="String" required="true">
			<description>Specify time window to collect Login events (for example, '1 min' or '1 hour')</description>
		</supportedParameters>
		*/
    	param = new MonitorParameter();
    	param.setId("loginCaptureInterval");
    	param.setValue("1 min");
    	params.getMonitorParameters().add(param);
    	
    	/*
    	<supportedParameters id="loginCaptureFromPastInterval" label="Login Capture from past Interval" datatype="String" required="true">
			<description>Specify time window to collect historical Login events. Must be greater than Login Capture Interval (for example, '5 min' or '3 hour')</description>
		</supportedParameters>
		*/
    	param = new MonitorParameter();
    	param.setId("loginCaptureFromPastInterval");
    	param.setValue("3 min");
    	params.getMonitorParameters().add(param);
    	
    	/*
    	<supportedParameters id="databaseCaptureInterval" label="Database Capture Interval" datatype="String" required="true">
			<description>Specify time window to collect Login events (for example, '1 min' or '1 hour')</description>
		</supportedParameters>
		*/
    	param = new MonitorParameter();
    	param.setId("databaseCaptureInterval");
    	param.setValue("1 min");
    	params.getMonitorParameters().add(param);
    	
    	/*
    	<supportedParameters id="presentToPastLoginCountComparator" label="Present to past Login Count Comparator" datatype="String" required="true">
			<description>Specify comparator between number of present logins (see Login Capture Interval parameter) to number of logins in the past (see login Capture from past Interval parameter)</description>
			<supportedComparators code="GreaterThan" label="Greater Than"/>
			<supportedComparators code="GreaterThanEquals" label="Greater Than Equals"/>
			<supportedComparators code="LessThan" label="Less Than"/>
			<supportedComparators code="LessThanEquals" label="Less Than Equals"/>
			<supportedComparators code="Equals" label="Equals"/>
			<supportedComparators code="NotEquals" label="Not Equals"/>
		</supportedParameters>
		*/
    	param = new MonitorParameter();
    	param.setId("presentToPastLoginCountComparator");
    	param.setValue("1.10");
    	param.setComparator(ConditionComparator.GREATER_THAN);
    	params.getMonitorParameters().add(param);
    	
    	/*
    	<supportedParameters id="presentToPastCPUAverageComparator" label="Present to past CPU Average Comparator" datatype="String" required="true">
			<description>Specify comparator between present CPU average (see cpuCaptureInterval parameter) and CPU average in the past (see CPU Capture from past Interval parameter)</description>
			<supportedComparators code="GreaterThan" label="Greater Than"/>
			<supportedComparators code="GreaterThanEquals" label="Greater Than Equals"/>
			<supportedComparators code="LessThan" label="Less Than"/>
			<supportedComparators code="LessThanEquals" label="Less Than Equals"/>
			<supportedComparators code="Equals" label="Equals"/>
			<supportedComparators code="NotEquals" label="Not Equals"/>
		</supportedParameters>
		*/
    	param = new MonitorParameter();
    	param.setId("presentToPastCPUAverageComparator");
    	param.setValue("1.05");
    	param.setComparator(ConditionComparator.LESS_THAN);
    	params.getMonitorParameters().add(param);
    	
    	/*
    	<supportedParameters id="databaseNumberOfQueriesThresholdComparator" label="Database Number of Queries Threshold Comparator" datatype="String" required="true">
			<description>Specify comparator between number of queries received in database event and numeric threshold.</description>
			<supportedComparators code="GreaterThan" label="Greater Than"/>
			<supportedComparators code="GreaterThanEquals" label="Greater Than Equals"/>
			<supportedComparators code="LessThan" label="Less Than"/>
			<supportedComparators code="LessThanEquals" label="Less Than Equals"/>
			<supportedComparators code="Equals" label="Equals"/>
			<supportedComparators code="NotEquals" label="Not Equals"/>
		</supportedParameters>
    	*/
    	param = new MonitorParameter();
    	param.setId("databaseNumberOfQueriesThresholdComparator");
    	param.setValue("10");
    	param.setComparator(ConditionComparator.GREATER_THAN);
    	params.getMonitorParameters().add(param);
    	
    	CepMonitor monitor = new CepMonitor();
    	monitor.setChangeBy("mfedorov");
    	monitor.setChangeDate(System.currentTimeMillis());
    	monitor.setCriticalityTypeCode(Criticality.INFO.value());
    	monitor.setMessage("Database is rataining...");
    	monitor.setName("Database Retension MIDB01");
    	monitor.setResourceCode(MonitoredResourceCode.DATABASE.value());
    	monitor.setConditionCode("DatabaseRetentionCondition");
    	monitor.setStatus('A');
    	
    	CepCondition condition = new CepCondition();
    	try {
			condition.setDefinition(new String(MarshallerUtils.marshallUsingStreamResult(marshaller, params), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	condition.setName(monitor.getName());
    	
    	monitor.getConditions().add(condition);
    	
    	monitorRepository.createMonitor(monitor);
    }
}