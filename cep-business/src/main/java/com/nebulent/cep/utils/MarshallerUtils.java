/**
 * 
 */
package com.nebulent.cep.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.util.Assert;
import org.springframework.util.xml.StaxUtils;
import org.xml.sax.InputSource;

/**
 * @author Max Fedorov
 *
 */
public class MarshallerUtils {
	
	protected final static Logger logger = LoggerFactory.getLogger(MarshallerUtils.class);
	
	/**
	 * @param marshaller
	 * @param object
	 * @return
	 */
	public static byte[] marshallUsingStreamResult(Marshaller marshaller, Object object) {
		Assert.notNull(marshaller);
		Assert.notNull(object);
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(outputStream);
			marshaller.marshal(object, result);
			return outputStream.toByteArray();
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		} finally {
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException exc) {
					throw new RuntimeException(exc);
				}
			}
		}
	}
	
	/**
	 * @param marshaller
	 * @param object
	 * @return
	 */
	public static byte[] marshallUsingStaxResult(Marshaller marshaller, Object object) {
		Assert.notNull(marshaller);
		Assert.notNull(object);
		XMLStreamWriter streamWriter = null;
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			streamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream);
			Result result = StaxUtils.createStaxResult(streamWriter);
			marshaller.marshal(object, result);
			return outputStream.toByteArray();
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		} finally {
			if(streamWriter != null) {
				try {
					streamWriter.close();
				} catch (XMLStreamException exc) {
					throw new RuntimeException(exc);
				}
			}
			if(outputStream != null) {
				try {
					outputStream.close();
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
	 * @param xmlData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <TYPE> TYPE unmarshallUsingSaxSource(Class<TYPE> clazz, Unmarshaller unmarshaller, byte[] xmlData) {
		Assert.notNull(clazz);
		Assert.notNull(unmarshaller);
		Assert.notNull(xmlData);
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(xmlData);
			InputSource inputSource = new InputSource(inputStream);
			Source source = new SAXSource(inputSource);
			return (TYPE)unmarshaller.unmarshal(source);
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		} finally {
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
	public static <TYPE> TYPE unmarshallUsingStaxSource(Class<TYPE> clazz, Unmarshaller unmarshaller, File file) {
		Assert.notNull(file);
		try {
			return unmarshallUsingStaxSource(clazz, unmarshaller, new FileInputStream(file));
		} catch (FileNotFoundException exc) {
			throw new RuntimeException(exc);
		}
	}
	
	/**
	 * @param <TYPE>
	 * @param clazz
	 * @param unmarshaller
	 * @param xmlData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <TYPE> TYPE unmarshallUsingStreamSource(Class<TYPE> clazz, Unmarshaller unmarshaller, byte[] xmlData) {
		Assert.notNull(clazz);
		Assert.notNull(unmarshaller);
		Assert.notNull(xmlData);
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(xmlData);
			return (TYPE)unmarshaller.unmarshal(new StreamSource(inputStream));
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		} finally {
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
	 * @param xmlData
	 * @return
	 */
	public static <TYPE> TYPE unmarshallUsingStaxSource(Class<TYPE> clazz, Unmarshaller unmarshaller, byte[] xmlData) {
		Assert.notNull(xmlData);
		//logger.debug(new String(xmlData));
		return unmarshallUsingStaxSource(clazz, unmarshaller, new ByteArrayInputStream(xmlData));
	}
	
	/**
	 * @param <TYPE>
	 * @param clazz
	 * @param unmarshaller
	 * @param inputStream
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <TYPE> TYPE unmarshallUsingStaxSource(Class<TYPE> clazz, Unmarshaller unmarshaller, InputStream inputStream) {
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
	
}