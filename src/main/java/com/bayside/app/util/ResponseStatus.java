package com.bayside.app.util;

public enum ResponseStatus {
	Success(000, "Success"),
	Error(511, "Error");
	private final int value;

	private final String reasonPhrase;

	private ResponseStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}
	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}
	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return reasonPhrase;
	}
}

