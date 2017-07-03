package core;

import java.util.Map;

public class HttpRequest {

	//HTTP Status Code
	
	public static final int HTTP_STATUS_OK = 200;
	public static final int HTTP_STATUS_NOT_FOUND = 404;
	
	//HTTP status messages
	public static final String HTTP_STATUS_OK_MESSAGE = "OK";
	public static final String HTTP_STATUS_NOT_FOUND_MESSAGE = "404";
	
	
	public static final String HTTP_GET = "GET";
	public static final String HTTP_POST = "POST";
	
	public enum RequestType{
		GET, POST, PUT, DELETE
	}
	
	//To put HTTP request type
	private RequestType requestType;
	// URL of requested file
	private String url;
	//Map to store HTTP headers
	private Map<String, String> headres; 
	//Map to store HTTP POST params
	private Map<String, String> params;
	
	public HttpRequest(RequestType requestType, String url, 
			Map<String, String> headers, Map<String, String> params){
		this.requestType = requestType;
		this.setUrl(url);
		this.setHeadres(headers);
		this.setParams(params);
	}
	
	public RequestType getRequestType() {
		// TODO Auto-generated method stub
		return requestType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeadres() {
		return headres;
	}

	public void setHeadres(Map<String, String> headres) {
		this.headres = headres;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	

}
