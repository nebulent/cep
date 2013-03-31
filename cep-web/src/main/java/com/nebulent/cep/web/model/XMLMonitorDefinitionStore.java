package com.nebulent.cep.web.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nebulent.schema.software.cep.metadata._1.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.util.Assert;
import org.springframework.util.xml.StaxUtils;

import com.nebulent.cep.monitoring.MonitorDefinitionStore;

public class XMLMonitorDefinitionStore implements MonitorDefinitionStore{
	
	/*PROPERTIEs*/
	private Unmarshaller unmarshaller;
	private Resource monitorDefinitionsResource;
	private Configuration configuration;
	
	/**
	 * @throws Exception
	 */
	public void init() throws Exception {
		configuration = unmarshallUsingStaxSource(Configuration.class, monitorDefinitionsResource.getFile());
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
	
	/**
	 * @param <TYPE>
	 * @param clazz
	 * @param unmarshaller
	 * @param xmlData
	 * @return
	 */
	public <TYPE> TYPE unmarshallUsingStaxSource(Class<TYPE> clazz, byte[] xmlData) {
		Assert.notNull(xmlData);
		//logger.debug(new String(xmlData));
		return unmarshallUsingStaxSource(clazz, new ByteArrayInputStream(xmlData));
	}
	
	/**
	 * @param <TYPE>
	 * @param clazz
	 * @param unmarshaller
	 * @param inputStream
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <TYPE> TYPE unmarshallUsingStaxSource(Class<TYPE> clazz, InputStream inputStream) {
		Assert.notNull(clazz);
		Assert.notNull(unmarshaller);
		Assert.notNull(inputStream);
		XMLStreamReader streamReader = null;
		try {
			streamReader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
			return (TYPE)unmarshaller.unmarshal(StaxUtils.createStaxSource(streamReader));
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		} finally {
			if(streamReader != null) {
				try {
					streamReader.close();
				} catch (XMLStreamException exc) {
					throw new RuntimeException(exc);
				}
			}
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException exc) {
					throw new RuntimeException(exc);
				}
			}
		}
	}
	
	/**
	 * @param <TYPE>
	 * @param clazz
	 * @param unmarshaller
	 * @param file
	 * @return
	 */
	public <TYPE> TYPE unmarshallUsingStaxSource(Class<TYPE> clazz, File file) {
		Assert.notNull(file);
		try {
			return unmarshallUsingStaxSource(clazz, new FileInputStream(file));
		} catch (FileNotFoundException exc) {
			throw new RuntimeException(exc);
		}
	}
}
