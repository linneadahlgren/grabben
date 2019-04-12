package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerController controller;

	public Server(ServerController controller, int port) {
		this.controller = controller;
		 new Connection(port).start();
	}
	private class Connection extends Thread{
		private int port;
		private Socket socket;
		private ServerSocket serverSocket;
		
		public Connection(int port) {
			this.port = port;
		}
		public void run() {
			try(Server)
		}
		
	}
 

}
