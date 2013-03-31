package com.nebulent.cep.monitoring.esper;

import java.util.List;

import org.springframework.beans.factory.BeanNameAware;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.nebulent.cep.monitoring.esper.statement.StatementBean;

/**
 * @author Max Fedorov
 *
 */
public class EsperBean implements BeanNameAware {

	/*properties*/
    private String name;
    private List<StatementAwareUpdateListener> listeners;
    private String eventTypeAutoNamePackageName;
    private String annotationPackageName;
    
    /*ESPER services*/
    private EPServiceProvider epServiceProvider;
    private EPRuntime epRuntime;

    /**
     * @param statementBean
     */
    public void addStatement(StatementBean statementBean) {
        registerStatement(statementBean);
    }

    /**
     * @param event
     */
    public void sendEvent(Object event) {
        getEpRuntime().sendEvent(event);
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    public void setBeanName(String name) {
        this.name = name;
    }
    
    /**
     * @param provider
     * @param statementBean
     */
    private void registerStatement(StatementBean statementBean) {
    	statementBean.setEPStatement(getEpServiceProvider().getEPAdministrator().createEPL(statementBean.getEPL()));
    	
    	if(listeners == null || listeners.isEmpty()) {
    		return;
    	}
        for (StatementAwareUpdateListener listener : listeners) {
            statementBean.addListener(listener);
        }
    }

    /**
     * @return
     */
    private EPRuntime getEpRuntime() {
        if (epRuntime == null) {
            epRuntime = getEpServiceProvider().getEPRuntime();
        }
        return epRuntime;
    }

    /**
     * @return
     */
    private EPServiceProvider getEpServiceProvider() {
        if (epServiceProvider == null) {
        	Configuration configuration = new Configuration();
        	
        	/*adding events type*/
        	configuration.addEventTypeAutoName(eventTypeAutoNamePackageName);
        	/*if(eventClassNames != null && !eventClassNames.isEmpty()){
	        	for (String eventName : eventClassNames.keySet()) {
	        		configuration.addEventType(eventName, eventClassNames.get(eventName));
				}
        	}*/
        	
        	/*adding annotations*/
        	configuration.addImport(annotationPackageName);
        	
            epServiceProvider = EPServiceProviderManager.getProvider(name, configuration);
        }
        return epServiceProvider;
    }

    /**
     * @throws Exception
     */
    public void destroy() throws Exception {
        getEpServiceProvider().destroy();
    }

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(List<StatementAwareUpdateListener> listeners) {
		this.listeners = listeners;
	}

	/**
	 * @param eventTypeAutoNamePackageName the eventTypeAutoNamePackageName to set
	 */
	public void setEventTypeAutoNamePackageName(String eventTypeAutoNamePackageName) {
		this.eventTypeAutoNamePackageName = eventTypeAutoNamePackageName;
	}

	/**
	 * @param annotationPackageName the annotationPackageName to set
	 */
	public void setAnnotationPackageName(String annotationPackageName) {
		this.annotationPackageName = annotationPackageName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
