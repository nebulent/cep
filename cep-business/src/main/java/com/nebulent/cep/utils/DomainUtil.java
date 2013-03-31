package com.nebulent.cep.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nebulent.schema.software.cep.types._1.Alert;
import nebulent.schema.software.cep.types._1.Condition;
import nebulent.schema.software.cep.types._1.Monitor;
import nebulent.schema.software.cep.types._1.ObjectFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nebulent.cep.domain.model.CepAlert;
import com.nebulent.cep.domain.model.CepCondition;
import com.nebulent.cep.domain.model.CepMonitor;

/**
 * @author
 *
 */
public class DomainUtil {

	protected static final Log logger = LogFactory.getLog(DomainUtil.class);
	
    public static final Character NO = 'N';
    public static final Character YES = 'Y';
    public static ObjectFactory cepTypesFactory = new ObjectFactory();
    
    /**
     * @param dateTime
     * @return
     */
    public static Calendar toCalendar(Long dateTime) {
        if (dateTime != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(dateTime));
            return cal;
        }
        return null;
    }

    /**
     * @param Calendar
     * @return
     */
    public static Long toLong(Calendar cal) {
        if (cal == null) {
            return null;
        }
        return cal.getTime().getTime();
    }

    /**
     * @param integer
     * @return
     */
    public static Long toLong(BigInteger integer) {
        if (integer == null) {
            return null;
        }
        return integer.longValue();
    }

    /**
     * @param str
     * @return
     */
    public static long toLong(String str) {
        if (str == null) {
            return 0;
        }
        return Long.parseLong(str);
    }

    /**
     * @param array
     * @return
     */
    public static List<Byte> toList(byte[] array) {
    	if(array != null && array.length > 0) {
    		List<Byte> rsp = new ArrayList<Byte>();
    		for (byte value : array) {
    			rsp.add(value);
    		}
    		return rsp;
    	}
    	return null;
    }
    
    /**
     * @param monitor
     * @return
     */
    public static CepMonitor toDomainType(Monitor monitor) {
    	if(monitor == null) {
    		return null;
    	}
    	CepMonitor entity = new CepMonitor();
    	entity.setCriticalityTypeCode(monitor.getCriticalityTypeCode());
    	entity.setName(monitor.getName());
    	entity.setMessage(monitor.getMessage());
    	entity.setChangeBy(monitor.getChangeBy());
    	entity.setId(monitor.getId());
    	entity.setResourceCode(monitor.getResourceCode() != null ? monitor.getResourceCode() : "0");
    	entity.setConditionCode(monitor.getConditionCode() != null ? monitor.getConditionCode() : "0");
    	entity.setOccurrenceInterval(monitor.getOccurrenceInterval() != null ? monitor.getOccurrenceInterval().intValue() : null);
    	if(monitor.getChangeDate() != null){
    		entity.setChangeDate(monitor.getChangeDate().getTimeInMillis());
    	}
    	if(monitor.getStatus() != null && monitor.getStatus().length() > 0) {
    		entity.setStatus(monitor.getStatus().toCharArray()[0]);
    	}
    	if(monitor.getConditions() != null && !monitor.getConditions().isEmpty()) {
    		entity.setConditions(new HashSet<CepCondition>());
    		for (Condition condition : monitor.getConditions()) {
    			entity.getConditions().add(DomainUtil.toDomainType(condition));
    		}
    	}
    	return entity;
    }
    
    /**
     * @param monitor
     * @return
     */
    public static Monitor toXmlType(CepMonitor monitor) {
    	return toXmlType(true, true, monitor);
    }
    
    /**
     * @param setMonitor
     * @param setConditions
     * @param monitor
     * @return
     */
    public static Monitor toXmlType(boolean setMonitor, boolean setConditions, CepMonitor monitor) {
    	if(monitor == null) {
    		return null;
    	}
    	Monitor meta = new Monitor();
    	meta.setId(monitor.getId());
    	
    	if(setMonitor){
	    	meta.setCriticalityTypeCode(monitor.getCriticalityTypeCode());
	    	meta.setName(monitor.getName());
	    	meta.setMessage(monitor.getMessage());
	    	meta.setChangeBy(monitor.getChangeBy());
	    	meta.setResourceCode(monitor.getResourceCode());
	    	meta.setConditionCode(monitor.getConditionCode());
	    	meta.setOccurrenceInterval(monitor.getOccurrenceInterval() != null ? new BigInteger(String.valueOf(monitor.getOccurrenceInterval())) : null);
	    	meta.setChangeDate(toCalendar(monitor.getChangeDate()));
	    	meta.setStatus(String.valueOf(monitor.getStatus()));
	    	if(meta.getStatus() != null) {
	    		meta.setStatus(meta.getStatus().trim());
	    	}
    	}
    	
    	// Set conditions.
    	if(setConditions){
	    	Set<CepCondition> conditions = monitor.getConditions();
	    	for (CepCondition bondDeskCondition : conditions) {
				meta.getConditions().add(toXmlType(bondDeskCondition));
			}
    	}
    	return meta;
    }
    
    /**
     * @param condition
     * @return
     */
    public static CepCondition toDomainType(Condition condition) {
    	if(condition == null) {
    		return null;
    	}
    	CepCondition entity = new CepCondition();
    	entity.setName(condition.getName());
    	entity.setDefinition(condition.getExpression());
    	return entity;
    }
    
    /**
     * @param condition
     * @return
     */
    public static Condition toXmlType(CepCondition condition) {
    	if(condition == null) {
    		return null;
    	}
    	Condition conditionType = new Condition();
    	conditionType.setId(condition.getId());
    	conditionType.setName(condition.getName());
    	conditionType.setExpression(condition.getDefinition());
    	return conditionType;
    }
    
    /**
     * @param alert
     * @return
     */
    public static CepAlert toDomainType(Alert alert) {
    	if(alert == null) {
    		return null;
    	}
    	CepAlert entity = new CepAlert();
    	entity.setId(alert.getId());
    	entity.setChangeBy(alert.getChangeBy());
    	if(alert.getChangeDate() != null){
    		entity.setChangeDate(alert.getChangeDate().getTimeInMillis());
    	}
    	if(alert.getStartTime() != null){
    		entity.setStartTime(alert.getStartTime().getTimeInMillis());
    	}
    	if(alert.getEndTime() != null){
    		entity.setEndTime(alert.getEndTime().getTimeInMillis());
    	}
    	entity.setMessage(alert.getMessage());
    	entity.setStatus(alert.getStatus());
    	entity.setMonitor(toDomainType(alert.getMonitor()));
    	return entity;
    }
    
    /**
     * @param alertEntity
     * @return
     */
    public static Alert toXmlType(CepAlert alertEntity) {
    	if(alertEntity == null) {
    		return null;
    	}
    	Alert alert = new Alert();
    	alert.setId(alertEntity.getId());
    	alert.setChangeBy(alertEntity.getChangeBy());
    	if(alertEntity.getChangeDate() != null){
    		alert.setChangeDate(toCalendar(alertEntity.getChangeDate()));
    	}
    	alert.setStartTime(toCalendar(alertEntity.getStartTime()));
    	if(alertEntity.getEndTime() != null){
    		alert.setEndTime(toCalendar(alertEntity.getEndTime()));
    	}
    	alert.setMessage(alertEntity.getMessage());
    	alert.setStatus(alertEntity.getStatus());
    	alert.setMonitor(toXmlType(true, false, alertEntity.getMonitor()));
    	return alert;
    }
}