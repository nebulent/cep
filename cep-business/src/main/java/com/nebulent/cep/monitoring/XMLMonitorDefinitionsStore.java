package com.nebulent.cep.monitoring;

import nebulent.schema.software.cep.metadata._1.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import com.nebulent.cep.utils.MarshallerUtils;

/**
 * @author Max Fedorov
 *
 */
public class XMLMonitorDefinitionsStore implements MonitorDefinitionStore{
	
	/*PROPERTIEs*/
	private Unmarshaller unmarshaller;
	private Resource monitorDefinitionsResource;
	private Configuration configuration;
	
	/**
	 * @throws Exception
	 */
	public void init() throws Exception {
		configuration = MarshallerUtils.unmarshallUsingStaxSource(Configuration.class, unmarshaller, monitorDefinitionsResource.getFile());
	}
	
	/* (non-Javadoc)
	 * @see com.nebulent.cep.monitoring.MonitorDefinitionStore#getMonitorConfiguration()
	 */
	@Override
	public Configuration getMonitorConfiguration() {
		return configuration;
	}

	/**
	 * @param unmarshaller the unmarshaller to set
	 */
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	/**
	 * @param monitorDefinitionsResource the monitorDefinitionsResource to set
	 */
	public void setMonitorDefinitionsResource(Resource monitorDefinitionsResource) {
		this.monitorDefinitionsResource = monitorDefinitionsResource;
	}
}
