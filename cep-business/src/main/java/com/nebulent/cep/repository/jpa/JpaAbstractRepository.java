/**
 * 
 */
package com.nebulent.cep.repository.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author Max Fedorov
 *
 */
public class JpaAbstractRepository {

    /*logger*/
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    /*entity manager*/
    protected EntityManager entityManager;

    /**
     * @param entityManager
     */
    @PersistenceContext(unitName = "cep-db")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @param entity
     * @return
     */
    protected boolean persist(Object entity) {
    	Assert.notNull(entity);
        logger.debug("JpaAbstractRepository: in persist");
        try {
        	entityManager.persist(entity);
        	return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
    }
    
    /**
     * @param entity
     * @return
     */
//  @Transactional
    protected boolean merge(Object entity) {
    	Assert.notNull(entity);
    	logger.debug("JpaAbstractRepository: in merge");
    	try {
    		entityManager.merge(entity);
    		return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
    }

    /**
     * @param id
     * @param clazz
     */
    protected void findAndRemove(Object id, Class<?> clazz) {
        Object entity = entityManager.find(clazz, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
    
    /**
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
	protected <T> Collection<T> getEntityList(Class<T> clazz){
    	Assert.notNull(clazz);
    	Query query = entityManager.createQuery("FROM " + clazz.getName());
    	return (Collection<T>) query.getResultList();
    }
    
    /**
     * @param clazz
     * @param id
     * @return
     */
    protected boolean remove(Class<?> clazz, long id) {
    	Assert.notNull(clazz);
    	Assert.notNull(id);
    	Query query = entityManager.createQuery("DELETE from " + clazz.getName() + " WHERE id=:ID");
    	query.setParameter("ID", id);
    	try {
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		} 
    }
    
    /**
     * @param clazz
     * @param ids
     * @return
     */
    protected boolean removeList(Class<?> clazz, long... ids){
    	Assert.notNull(clazz);
    	Assert.notNull(ids);
    	Query query = entityManager.createQuery("DELETE from " + clazz.getName() + " WHERE id in :IDS");
    	List<Long> listIds = new ArrayList<Long>();
    	for (long id : ids) {
    		listIds.add(id);
    	}
    	query.setParameter("IDS", listIds/*Arrays.asList(ids)*/);
    	try {
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
    }
    
    /**
     * @param clazz
     * @param id
     * @return
     */
    protected <T> T getEntity(Class<T> clazz, long id) {
    	Assert.notNull(clazz);
    	Assert.notNull(id);
        return entityManager.find(clazz, id);
    }

    /**
     * @param query
     * @return
     */
    protected Query createQuery(String query) {
        return entityManager.createQuery(query);
    }

    /**
     * @param lst
     * @return
     */
    protected Object getSingleResult(List<Object> lst) {
    	if(lst == null || lst.isEmpty()) {
    		return null;
    	}
    	return lst.get(0);
    }
    
    /**
     * @return
     */
    protected String createUUID() {
    	return UUID.randomUUID().toString();
    }
    
    /**
     * @param query
     * @param params
     */
    protected void setParameters(Query query, Map<String, Object> params) {
    	for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
    }

}














