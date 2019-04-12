package server;

import java.io.*;
import java.net.*;

public class Server {
	private ServerController controller;
	private ClientHandler computerHandler;
	private ClientHandler esHandler;

	public Server(ServerController controller, int port) {
		this.controller = controller;
		new Connection(port).start();
	}
	
	public void sendToEs(char instruction) {
		try {
			System.out.println(instruction);
			esHandler.getOutputStream().writeChar(instruction);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Connection extends Thread {
		private int port;

		public Connection(int port) {
			this.port = port;
		}
		

		public void run() {
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while (true) {
					try {
						Socket socket = serverSocket.accept();
						System.out.println("Client connected");
						new ClientHandler(socket);
					} catch (IOException e) {
						System.out.println("No connection with Client");
					}
				}

			} catch (IOException e) {
				System.out.println("Server down");
			}
		}
		

	}

	private class ClientHandler extends Thread {
		private Socket socket;
		private DataInputStream dis;
		private DataOutputStream dos;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			start();
		}
		public DataOutputStream getOutputStream() {
			return dos;
		}

		public void run() {
			System.out.println("ClientHandler thread");
			try{
				dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				char name = dis.readChar();
				System.out.println(name);
				if(name == 'C') {
					computerHandler = this;
				}else if(name == 'E') {
					esHandler = this;
				}
				
				while (!socket.isClosed()) {
					char temp = dis.readChar();
					
					if (this.equals(computerHandler)) {
						sendToEs(temp);
						
					}else if(this.equals(esHandler)) {
						
					}
							
//					System.out.println("" + temp);
//					controller.writeToLog("" + temp);
//					dos.writeUTF("" + temp);
					dos.flush();
				}
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
