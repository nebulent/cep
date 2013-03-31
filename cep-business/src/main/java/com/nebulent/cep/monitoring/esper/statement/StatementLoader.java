package com.nebulent.cep.monitoring.esper.statement;

import java.util.List;

import nebulent.schema.software.cep.types._1.Monitor;

/**
 * @author Max Fedorov
 *
 */
public interface StatementLoader {

    /**
     * @return
     */
    public List<StatementBean> loadStatements();
    
    /**
     * @param monitor
     * @return
     */
    public List<StatementBean> createStatement(Monitor monitor);
}
