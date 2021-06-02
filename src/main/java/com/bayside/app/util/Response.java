package com.bayside.app.util;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>Title:CommonBeanResponse</p>
 * <p>Description:应用系统共用的返回类</p>
 * <p>Copyright:Copyright(c)2015</p>
 * @author Wenyu
 * @version 1.0
 */
public class Response implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	private ResponseStatus responseStatus;
	
	private Object object;

	private boolean succeed;
	
	

	public Response() {
		super();
	}



	public Response(ResponseStatus responseStatus, Object object, boolean succeed) {
		super();
		this.responseStatus = responseStatus;
		this.object = object;
		this.succeed = succeed;
	}


	@JsonSerialize  
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}



	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}



	public Object getObject() {
		return object;
	}



	public void setObject(Object object) {
		this.object = object;
	}



	public boolean isSucceed() {
		return succeed;
	}



	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
}
