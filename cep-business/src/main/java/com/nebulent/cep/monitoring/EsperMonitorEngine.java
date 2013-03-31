/**
 * 
 */
package com.nebulent.cep.monitoring;

import java.util.List;

import nebulent.schema.software.cep.types._1.Monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.nebulent.cep.monitoring.esper.EsperBean;
import com.nebulent.cep.monitoring.esper.statement.StatementBean;
import com.nebulent.cep.monitoring.esper.statement.StatementLoader;

/**
 * @author Max Fedorov
 *
 */
public class EsperMonitorEngine implements MonitorEngine {

	/*logger*/
	private static Logger logger = LoggerFactory.getLogger(EsperMonitorEngine.class);
	
	/*monitor loader*/
	private StatementLoader statementLoader;
	
	/*services*/
	private EsperBean esperBean;
	
	/**
	 * 
	 */
	public void init() {
		execute();
	}
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.engine.MonitorEngine#execute()
	 */
	@Override
	public void execute() {
		logger.debug("Starting all monitors ...");
		List<StatementBean> monitors = statementLoader.loadStatements();
		if(monitors != null){
	    	for (StatementBean statementBean : monitors) {
				esperBean.addStatement(statementBean);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.engine.MonitorEngine#execute(bonddeskgroup.schema.software.cep.types._1.Monitor)
	 */
	@Override
	public void execute(Monitor monitor) {
		logger.debug("Starting monitor ...");
		Assert.notNull(monitor, "Monitor is required !!!");
		List<StatementBean> steatementBeans = statementLoader.createStatement(monitor);
		for (StatementBean statementBean : steatementBeans) {
			esperBean.addStatement(statementBean);
		}
	}

	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.MonitorEngine#sendEvent(java.lang.Object)
	 */
	@Override
	public void sendEvent(Object event) {
		esperBean.sendEvent(event);
	}

	/**
	 * @param statementLoader the statementLoader to set
	 */
	public void setStatementLoader(StatementLoader statementLoader) {
		this.statementLoader = statementLoader;
	}

	/**
	 * @param esperBean the esperBean to set
	 */
	public void setEsperBean(EsperBean esperBean) {
		this.esperBean = esperBean;
	}
}
