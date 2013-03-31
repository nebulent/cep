package com.nebulent.cep.monitoring.service;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nebulent.cep.monitoring.esper.EsperBean;
import com.nebulent.cep.monitoring.esper.statement.StatementLoader;
import com.nebulent.cep.monitoring.service.commands.AppAlertJMXCommand;
import com.nebulent.cep.monitoring.service.commands.AppTooSlowCommand;
import com.nebulent.cep.monitoring.service.commands.DatabaseRetentionCommand;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-cep.xml"})
public class EsperMonitorTest {
	
	@Autowired
    EsperBean esperBean;

	@Autowired
	StatementLoader statementLoader;
	
    public EsperMonitorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    	Assert.assertNotNull(statementLoader);
    	Assert.assertNotNull(esperBean);
    	
    	/*List<StatementBean> statements = statementLoader.loadStatements();
    	for (StatementBean statementBean : statements) {
    		esperBean.addStatement(statementBean);
		}*/
    }
    
    /*
    @MonitorInfo(monitorId=1, resourceCode='Database', conditionCode='DatabaseRetentionCondition', criticalityTypeCode='Info') 
	@Description('Database is rataining...') 
	@Name('Database Retension MIDB01') 
	
	SELECT Server.host , Server.cpuPerc, Server_PAST.cpuPerc, count(Login.userName), count(Login_PAST.userName), Db.numberOfQueries
	FROM
	AppServerHighCPUEvent.win:time(1 min) as Server
	, AppServerHighCPUEvent.win:ext_timed(current_timestamp(), 3 min) as Server_PAST
	, LoginEvent.win:time(1 min) as Login
	, LoginEvent.win:ext_timed(current_timestamp(), 3 min) as Login_PAST
	, DatabaseSlowQueryEvent.win:time (1 min) as Db
	HAVING
	count(Login.userName) > count(Login_PAST.userName) * 1.1 
	AND avg(Server.cpuPerc) < avg(Server_PAST.cpuPerc) * 1.05
	AND Db.numberOfQueries > 10
     */
    @Test
    public void databaseRetentionSimulatorTest() {
        System.out.println("Launching Database Retention simulator...");
        
        Generator generator = new Generator(esperBean);
        Runnable command = new DatabaseRetentionCommand(generator, "APP02", "HOST02", "DB02");
        generator.setCommand(command);
        generator.scheduleCommand(generator, 0);
        
        try {
			generator.getLock().await(10, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        finally{
        	generator.destroy();
        }
    }
    
    @Test
    public void appServerHighCPUSimulatorTest() {
        System.out.println("Launching AppServer High CPU simulator...");
        
        Generator generator = new Generator(esperBean);
        Runnable command = new DatabaseRetentionCommand(generator, "APP01", "HOST01", "DB01");
        generator.setCommand(command);
        generator.scheduleCommand(generator, 0);
        
        try {
			generator.getLock().await(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        finally{
        	generator.destroy();
        }
    }
    
    @Test
    public void appTooSlowSimulatorTest() {
        System.out.println("Launching App Too Slow simulator...");
        
        Generator generator = new Generator(esperBean);
        Runnable command = new AppTooSlowCommand(generator, "HOST01");
        ((AppTooSlowCommand)command).setProgramName("bdprobe");
        ((AppTooSlowCommand)command).setSource("OFF-OPCOCD-DS");
        
        generator.setCommand(command);
        generator.scheduleCommand(generator, 0);
        
        try {
			generator.getLock().await(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        finally{
        	generator.destroy();
        }
    }
    
    @Test
    public void AppAlertPassThroughSimulatorTest() {
        System.out.println("Launching App Alert JMX pass-through simulator...");
        
        Generator generator = new Generator(esperBean);
        Runnable command = new AppAlertJMXCommand(generator, "ps6p");
        ((AppAlertJMXCommand)command).setProgramName("execAPI2");
        
        generator.setCommand(command);
        generator.scheduleCommand(generator, 0);
        
        try {
			generator.getLock().await(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        finally{
        	generator.destroy();
        }
    }
}
