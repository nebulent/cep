package com.nebulent.cep.monitoring.esper.statement;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.StatementAwareUpdateListener;

public class StatementBean {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/*properties*/
    private String epl;
    private Set<StatementAwareUpdateListener> listeners = new LinkedHashSet<StatementAwareUpdateListener>();
    
    /*ESPER properties*/
    private EPStatement epStatement;

    /**
     * @param epl
     */
    public StatementBean(String epl) {
        this.epl = epl;
    }

    /**
     * @return
     */
    public String getEPL() {
        return epl;
    }

    /**
     * @param listeners
     */
    public void setListeners(StatementAwareUpdateListener... listeners) {
        for (StatementAwareUpdateListener listener : listeners) {
            addListener(listener);
        }
    }

    /**
     * @param listener
     */
    public void addListener(StatementAwareUpdateListener listener) {
    	logger.info("Adding Esper listener:" + listener.getClass());
        listeners.add(listener);
        if (epStatement != null) {
            epStatement.addListener(listener);
        }
    }

    /**
     * @param epStatement
     */
    public void setEPStatement(EPStatement epStatement) {
        this.epStatement = epStatement;
        for (StatementAwareUpdateListener listener : listeners) {
        	this.epStatement.addListener(listener);
        }
    }
}
