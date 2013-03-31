package com.nebulent.cep.service.resource.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusResponse {

	public enum Status {
		SUCCESS("Success"), FAILURE("Failure");
		private String value;

		private Status(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	protected Status status;

	/**
	 * @return
	 */
	public static StatusResponse success() {
		return new StatusResponse(Status.SUCCESS);
	}

	/**
	 * @return
	 */
	public static StatusResponse failure() {
		return new StatusResponse(Status.FAILURE);
	}

	/**
     *
     */
	public StatusResponse() {
	}

	/**
	 * @param status
	 */
	public StatusResponse(boolean status) {
		this.status = status ? Status.SUCCESS : Status.FAILURE;
	}

	/**
	 * @param status
	 */
	public StatusResponse(Status status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
}
