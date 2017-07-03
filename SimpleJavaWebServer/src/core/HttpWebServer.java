package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpWebServer {
	
	//Server root folder. All requested file will be searched relative to this root.
	public static final String WEB_ROOT = "C:\\htdocs";
	
	public static final int DEFAULT_PORT = 6789;
	private ServerSocket httpServer;
	

	public HttpWebServer() throws IOException {
		this(DEFAULT_PORT);
	}

	public HttpWebServer(int port) throws IOException {
		initServer(port);
	}

	private void initServer(int port) throws IOException {
		// TODO Auto-generated method stub
		httpServer = new ServerSocket(port);
		
	}


	public void waitForClient() throws IOException {
		// TODO Auto-generated method stub
		System.out.printf("Serving HTTP on %s port %d\n",httpServer
				.getInetAddress().getHostName(),httpServer.getLocalPort());
	
		//Server runs on infinite loop
		//Because it waits for client all the time
		
		while(true){
			
			//1. Server connects or accepts client that has requested
			Socket socket = httpServer.accept();
			System.out.println("Received a Client");
			
			//2. Server than process client request and serve accordingly
			RequestProcessor processor = new RequestProcessor(socket);
			processor.processRequest();
			
			//3. After processing client request, server disconnects client
			processor.disconnect();
			System.out.println("Client disconnected");
			
		}
		
	}

}
