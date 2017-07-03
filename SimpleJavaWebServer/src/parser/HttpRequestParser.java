package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import core.HttpRequest;

public class HttpRequestParser {

	private InputStream input;
	
	private static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";
	

	public HttpRequestParser(InputStream input) throws IOException {
		this.input = input;
	}
	
	
	public HttpRequest parseRequest() throws IOException{
	
	//Initialize empty params
	HttpRequest.RequestType requestType = null;
	String url = "";
	Map<String, String> headers = new HashMap<String, String>();
	Map<String, String> params = new HashMap<String, String>();
	int lineNumber = 1;
	String line;
	
	// 1. Create a reader from client input. BufferedReader is used for easier access
	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	//reader
	// Read while there exists a line 
	while((line = reader.readLine()) != null){
		//1. Process first line and decide GET/POST
		 // First line for GET is like
		 //	GET /index.html HTTP/1.0
		 //	Date: ....
		 //.. 
		//[blank line]
		 
		//First line for POST is like
		// POST /login.php HTTP/1.0
		//..
		// Content-Length: 29
		// [blank Line]
		//username=dimik&password=12345  
		 
		 if(lineNumber == 1){
		 	requestType = parseRequestType(line);
		 	
		 	//splits url from the request
		 	url = line.split(" ")[1];
		 	
		 	//Checks if the url is requesting to root '/' therefore sets the url to index.html
		 if(url.codePointAt(0)=='/' || url.codePointAt(0)==' '){
			 url = "/index.html";
		 }
		 	
		 }else if(line.trim().equals("")){
		 
			 //get the post requested data from the form
			 if(requestType == HttpRequest.RequestType.POST){
				 int byteLeft = Integer.parseInt(headers.get(HTTP_HEADER_CONTENT_LENGTH));
				 
				 StringBuffer postParamString = new StringBuffer();
				 
				 for(int cursorPosition = 0; cursorPosition<byteLeft; cursorPosition++){
					 postParamString.append((char)reader.read());
				 }
				 
				 String[]	postParams = postParamString.toString().split("&");
				 
				 for(String postParam : postParams){
					 //Each postParam like 'username=dimik'
					 //So we can split again with '='
					 // Then we get
					 // parameter name => username
					 //parameter value => dimik
					 String[] postParamParts = postParam.split("=");
					 params.put(postParamParts[0], postParamParts[1]);
				 }
				 
			 }
			 break;
		 }else{
		 		//If it's not first line or blank line
		 		//then it's a Header information file
		 		// Header information is like
		 		//Content-Length: 100
		 		
		 		
		 		//1. So we can split line with ':'
		 	 String[] paramParts = line.split(":");
		 		
		 	// 2. Store Header information in headers
		 	 headers.put(paramParts[0], paramParts[1].trim());
		 }
		 
		 lineNumber += lineNumber;
		 System.out.println(lineNumber + " : "+line);
	}
		return new HttpRequest(requestType, url, headers,params);
	}
	
	
	//private helper method which gets RequestType from HTTP headetr String
	private HttpRequest.RequestType parseRequestType(String line){
		
		if(line.startsWith(HttpRequest.HTTP_GET)){
			return HttpRequest.RequestType.GET;
		}else if(line.startsWith(HttpRequest.HTTP_POST)){
			return HttpRequest.RequestType.POST;
		}else{
			return null;
		}
		
	}
	
}
