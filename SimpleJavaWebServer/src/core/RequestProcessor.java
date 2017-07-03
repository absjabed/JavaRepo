package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import javax.xml.crypto.Data;

import core.HttpRequest;

import parser.HttpRequestParser;

public class RequestProcessor {

	// Basic HTTP response template
	//Example:
	//			HTTP/1.0 200 OK
	//			Content-Type: text/html
	//			Content-Length: 100
	private static final String RESPONSE_TEMPLATE ="HTTP/1.0 %d %s\n"+
												   "Content-Type: %s\n"+
												   "Content-Length: %d\n\n"; 
	
	// HTML response to send when a requested file is not found in the server.
	// Example:
	//			<h2> 404 - File Not Found! </h2>
	//			<p>Requested File - /index.html </p>
	private static final String NOT_FOUND_HTML = "<h2> 404 - File Not Found! </h2>"+
												"<p>Requested File - %s</p>";
	
	private static final String CONTENT_TYPE_HTML = "text/html";
	
	// Socket to hold requested client
	private Socket client;
	// Client's HTTP request objects representation
	private HttpRequest request;
	

	private static final String WEB_ROOT = "C:\\htdocs";
	
	public RequestProcessor(Socket client) {
		this.client = client;
	}
	
	/*
	 * Process client request and generate response accordingly*/

	public void processRequest() throws IOException{
		//1. Parse client request and store in 'request' field
		request = new HttpRequestParser(client.getInputStream()).parseRequest();
		
		//2. Check client request type and process accordingly
		switch(request.getRequestType()){
		
			case GET:
				processGetRequest();
				break;
				
			case POST:
				processPostRequest();
				break;
				
			case PUT:
				
				break;
				
			case DELETE:
				
				break;
			
			
			
			
		}
		
	}
	
//private helper method to use POST parameters
	// We only print POST params in console
	private void processPostRequest() throws IOException {
		
		for(Map.Entry<String, String> param : request.getParams().entrySet()){
			System.out.println(param.getKey()+ " => "+ param.getValue());
		}
	}
	
	//private helper method to generate GET response
	private void processGetRequest() throws IOException{
		  
		//1. Create a File for requested file
		Path requestedFilePath = new File(WEB_ROOT + request.getUrl()).toPath();
		File requestedFile = requestedFilePath.toFile();
		
		//2. Check whether file exists or not
		boolean isFileExists = requestedFile.exists() && requestedFile.isFile();
		
		if(isFileExists){
			//File Exists. So generate response
			
			//1. Read File data
			FileInputStream fio = new FileInputStream(requestedFile);
			byte[] fileData = new byte[(int)requestedFile.length()];
			fio.read(fileData);
			fio.close();
			
			//2. Find content type of file
			String contentType = Files.probeContentType(requestedFile.toPath());
			System.out.println("Content Type: "+contentType);
			//3. send response to client
			sendResponse(HttpRequest.HTTP_STATUS_OK, HttpRequest.HTTP_STATUS_OK_MESSAGE, contentType, fileData);       
			
		}else{
			//File not found in server so generate not found response.
			
			// 1. Generate Not Found HTML
			String notFoundHtml = String.format(NOT_FOUND_HTML, request.getUrl());
			
			//2. Send not found response to client
			sendResponse(HttpRequest.HTTP_STATUS_NOT_FOUND, HttpRequest.HTTP_STATUS_NOT_FOUND_MESSAGE, CONTENT_TYPE_HTML, notFoundHtml.getBytes());       
			
			
		}
		
	}

	private void sendResponse(int status, String statusMessage, String contentType, byte[] data) throws IOException {
		
		//1. Format response header
		String responseHeader = String.format(RESPONSE_TEMPLATE, status, statusMessage, contentType, data.length);
		
		OutputStream clientOutput = client.getOutputStream();
		
		//2. Write Header information in client socket
		clientOutput.write(responseHeader.getBytes());
		
		
		//3. Write main file content in client socket
		clientOutput.write(data);
		
		
		//4. Close client output Stream
		clientOutput.close();
	}

	public void disconnect() {
		
		// TODO Auto-generated method stub
		
	}
}
