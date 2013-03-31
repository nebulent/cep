package com.nebulent.cep.web.modules.actions;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nebulent.cep.service.resource.AlertResource;
import com.nebulent.cep.service.resource.MonitorResource;

public abstract class ActionSupport implements SessionAware, ServletRequestAware {
	
	/* logger */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/* session map */
	private Map<String, Object> session;

	/* request */
	protected HttpServletRequest request;
	protected String filename; 
	protected InputStream requestInputStream; 
	
	private String redirect;
	private String pageTitle;
	
	@Autowired
	protected MonitorResource monitorRestService;
	
	@Autowired
	protected AlertResource alertRestService;
	
	/**
	 * @param <T>
	 * @param key
	 * @param data
	 */
	protected <T> void addDataToSession(String key, T data) {
		session.put(key, data);
	}

	/**
	 * @param <T>
	 * @param clazz
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getDataFromSession(Class<T> clazz, String key) {
		return (T) session.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(
	 * javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the redirect
	 */
	public String getRedirect() {
		return redirect;
	}

	/**
	 * @param redirect the redirect to set
	 */
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	/**
	 * @return the pageTitle
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * @param pageTitle the pageTitle to set
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the requestInputStream
	 */
	public InputStream getRequestInputStream() {
		return requestInputStream;
	}
	
	/**
	 * @return the monitorRestService
	 */
	public MonitorResource getMonitorRestService() {
		return monitorRestService;
	}

	/**
	 * @param monitorRestService the monitorRestService to set
	 */
	public void setMonitorRestService(MonitorResource monitorRestService) {
		this.monitorRestService = monitorRestService;
	}

	/**
	 * @return the alertRestService
	 */
	public AlertResource getAlertRestService() {
		return alertRestService;
	}

	/**
	 * @param alertRestService the alertRestService to set
	 */
	public void setAlertRestService(AlertResource alertRestService) {
		this.alertRestService = alertRestService;
	}
}
