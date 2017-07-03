import java.io.IOException;

import core.HttpWebServer;

public class StartHttpServer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		if(args.length == 1)
		{
			//if <port> is given then, Create HttpWebServer and wait for client
			
			//1. parse port from args/cmd
			int port = Integer.parseInt(args[0]);
			
			//2. create http webserver
			HttpWebServer WebServer = new HttpWebServer(port);
			
			//3. Wait for client
			
			WebServer.waitForClient();
			
			
		}else{
			System.out.println("Usage: Java StartHttpServer <port>");
		}
	}

}
