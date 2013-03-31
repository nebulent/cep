package com.nebulent.cep.monitoring.esper.listener;

import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.client.annotation.Description;

/**
 * @author Max Fedorov
 *
 */
public class ConsoleListener implements StatementAwareUpdateListener {

    private static final Log logger = LogFactory.getLog(ConsoleListener.class);

    /* (non-Javadoc)
     * @see com.espertech.esper.client.StatementAwareUpdateListener#update(com.espertech.esper.client.EventBean[], com.espertech.esper.client.EventBean[], com.espertech.esper.client.EPStatement, com.espertech.esper.client.EPServiceProvider)
     */
    public void update(EventBean[] newEvents, EventBean[] oldevents, EPStatement statement, EPServiceProvider epServiceProvider) {
        for (EventBean eventBean : newEvents) {
            String description = getDescriptionValue(statement.getAnnotations());
            String monitorName = statement.getName();
            logger.debug("*** Monitor name: " + monitorName + " [" + description + "], event type: " + eventBean.getUnderlying().getClass());
        }
    }

    /**
     * @param annotations
     * @return
     */
    protected String getDescriptionValue(Annotation[] annotations) {
        if (annotations != null) {
            for (Annotation a : annotations) {
                if (a instanceof Description) {
                    return ((Description) a).value();
                }
            }
        }
        return null;
    }
}
