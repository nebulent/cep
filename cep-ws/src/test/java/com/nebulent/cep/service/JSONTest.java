package com.nebulent.cep.service;

import java.io.IOException;
import java.util.Calendar;

import nebulent.schema.software.cep.events._1.APIProbeSysLogEvent;
import nebulent.schema.software.cep.events._1.AppServerHighCPUEvent;
import nebulent.schema.software.cep.events._1.DatabaseSlowQueryEvent;
import nebulent.schema.software.cep.events._1.JMXApplicationAlertEvent;
import nebulent.schema.software.cep.events._1.LoginEvent;
import nebulent.schema.software.cep.metadata._1.Criticality;
import nebulent.schema.software.cep.metadata._1.MonitoredResourceCode;
import nebulent.schema.software.cep.types._1.Alert;
import nebulent.schema.software.cep.types._1.Monitor;

import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:META-INF/spring/test-bonddesk-cep.xml"})
public class JSONTest {
	
    public JSONTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }
    
   @Test
    public void alertToJsonTest() {
	   	Monitor monitor = new Monitor();
		monitor.setId(1L);
		monitor.setChangeBy("mfedorov");
    	monitor.setChangeDate(Calendar.getInstance());
    	monitor.setCriticalityTypeCode(Criticality.INFO.value());
    	monitor.setMessage("Application server is getting overloaded...");
    	monitor.setName("Application Server APP01");
    	monitor.setResourceCode(MonitoredResourceCode.APPLICATION.value());
    	monitor.setConditionCode("HighCPUCondition");
    	monitor.setStatus("A");
    	
		Alert alert = new Alert();
		alert.setChangeBy("mfedorov");
		alert.setChangeDate(Calendar.getInstance());
		alert.setEndTime(Calendar.getInstance());
		alert.setMessage("Here is the test message.");
		alert.setStartTime(Calendar.getInstance());
		alert.setStatus("A");
		alert.setMonitor(monitor);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(alert));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
   
   	@Test
   	public void testAppServerHighCPUEventJson(){
	   	AppServerHighCPUEvent event = new AppServerHighCPUEvent();
	   	event.setApplicationID("APP01");
		event.setHost("HOST01");
		event.setCpuPerc(65);
		event.setIdlePerc(100D-event.getCpuPerc());
		event.setOccurredOn(Calendar.getInstance());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(event));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   	
   	@Test
   	public void testDatabaseSlowQueryEventJson(){
   		DatabaseSlowQueryEvent event = new DatabaseSlowQueryEvent();
    	event.setDatabaseName("DB01");
    	event.setNumberOfQueries(30);
    	event.setHost("HOST01");
		event.setOccurredOn(Calendar.getInstance());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(event));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   	}
   	
   	@Test
   	public void testLoginEvent(){
   		LoginEvent event = new LoginEvent();
   		event.setApplicationID("APP01");
    	event.setUserName(RandomStringUtils.randomAlphabetic(6));
    	event.setHost("HOST01");
		event.setOccurredOn(Calendar.getInstance());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(event));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   	}
   	
   	@Test
   	public void testAPIProbeSysLogEvent(){
   		APIProbeSysLogEvent event = new APIProbeSysLogEvent();
    	event.setHost("HOST01"); // misupportqa
    	event.setCiscoEventTag("BD-APIPROBE-5-UPDOWN");
    	event.setSource("OFF-OPCOCD-DS");
    	event.setCriticality("1");
    	event.setProgramName("bdprobe");
    	event.setUp(false);
		event.setLogDate(Calendar.getInstance());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(event));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   	}
   	
   	@Test
   	public void testJMXApplicationAlertEvent(){
   		JMXApplicationAlertEvent event = new JMXApplicationAlertEvent();
    	event.setHost("HOST01"); // misupportqa
    	event.setCriticality("1");
    	event.setProgramName("bdprobe");
    	event.setAlertCount(100);
    	event.setActive(true);
    	event.setClearCount(50);
    	event.setDeveloper("mfedorov");
    	event.setMessage("ERRMES() smth is wrong!");
    	event.setSource("Serious.cpp(500)");
		event.setLogDate(Calendar.getInstance());
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(event));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   	}
}
