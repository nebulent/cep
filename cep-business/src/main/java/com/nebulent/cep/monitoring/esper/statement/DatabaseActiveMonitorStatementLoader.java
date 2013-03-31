package com.nebulent.cep.monitoring.esper.statement;

import java.util.ArrayList;
import java.util.List;

import nebulent.schema.software.cep.types._1.Monitor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.nebulent.cep.domain.model.CepMonitor;
import com.nebulent.cep.monitoring.esper.statement.MonitorConditionCompiler.CompilationResult;
import com.nebulent.cep.repository.MonitorRepository;
import com.nebulent.cep.utils.DomainUtil;

/**
 * @author Max Fedorov
 *
 */
public class DatabaseActiveMonitorStatementLoader implements StatementLoader {

	/*logger*/
	private static Logger logger = LoggerFactory.getLogger(DatabaseActiveMonitorStatementLoader.class);
	
	/*repositories*/
	private MonitorRepository monitorRepository;
	
	/*compiler*/
	private MonitorConditionCompiler monitorConditionCompiler;
	
	/*evaluator*/
	private MonitorConditionEvaluator monitorConditionEvaluator;
	
    /* (non-Javadoc)
     * @see com.nebulent.cep.monitoring.monitor.esper.statement.StatementLoader#loadStatements()
     */
	//@Transactional
    public List<StatementBean> loadStatements() {
    	List<StatementBean> statementBeans = new ArrayList<StatementBean>();
    	List<CepMonitor> monitors = monitorRepository.getAllMonitors();
    	if(monitors != null) {
    		for (CepMonitor monitor : monitors) {
    			if('A' == monitor.getStatus()){
	    			try {
	    				statementBeans.addAll(createStatement(DomainUtil.toXmlType(monitor)));
	    			} catch (Exception exc) {
	    				logger.error("Error:", exc);
	    			}
    			}
    		}
    	}
        return statementBeans;
    }
    
    /* (non-Javadoc)
     * @see com.nebulent.cep.monitoring.esper.statement.StatementLoader#createStatement(bonddeskgroup.schema.software.cep.types._1.Monitor)
     */
    public List<StatementBean> createStatement(Monitor monitor) {
    	Assert.notNull(monitor);
    	List<StatementBean> statementBeans = new ArrayList<StatementBean>();
    	
    	List<CompilationResult> compilationResults = monitorConditionCompiler.compile(monitor);
    	Assert.notEmpty(compilationResults);
    	int count = 0;
    	for (CompilationResult compilationResult : compilationResults) {
    		String eplExpression = monitorConditionEvaluator.evaluate(compilationResult.getExpression(), compilationResult.getData());
        	Assert.hasLength(eplExpression);
        	
        	/*Add additional monitor info, like monitor database pk*/
        	StringBuilder metadataAnnotations = new StringBuilder();
        	metadataAnnotations.append("@MonitorInfo(monitorId=").append(monitor.getId())
        	.append(", resourceCode='").append(monitor.getResourceCode())
        	.append("', conditionCode='").append(monitor.getConditionCode())
        	.append("', criticalityTypeCode='").append(monitor.getCriticalityTypeCode())
        	.append("', managed=").append(compilationResult.isManagedExpression())
        	.append(") \n")
        	.append("@Description('" + StringUtils.replaceChars(monitor.getMessage(), '\'', '"') + "') \n")
        	.append("@Name('" + monitor.getName() + (++count) + "') \n");
        	
        	eplExpression = metadataAnnotations.toString() + eplExpression;
        	
        	logger.info(eplExpression);
        	
        	statementBeans.add(new StatementBean(eplExpression));
		}
    	return statementBeans;
    }

	/**
	 * @param monitorRepository the monitorRepository to set
	 */
	public void setMonitorRepository(MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}

	/**
	 * @param monitorConditionCompiler the monitorConditionCompiler to set
	 */
	public void setMonitorConditionCompiler(
			MonitorConditionCompiler monitorConditionCompiler) {
		this.monitorConditionCompiler = monitorConditionCompiler;
	}

	/**
	 * @param monitorConditionEvaluator the monitorConditionEvaluator to set
	 */
	public void setMonitorConditionEvaluator(
			MonitorConditionEvaluator monitorConditionEvaluator) {
		this.monitorConditionEvaluator = monitorConditionEvaluator;
	}
}