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
	
	public void sendToEs(String instruction) {
		try {
			System.out.println(instruction);
			controller.writeToLog("Computer sent: " + instruction);
			
			esHandler.getOutputStream().write(instruction);
			esHandler.getOutputStream().flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendToComp(String instruction) {
		try {
			controller.writeToLog("Embedded System sent: " + instruction);
			computerHandler.getOutputStream().write(instruction);
			computerHandler.getOutputStream().flush();
		}catch(IOException e) {
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
		private BufferedReader input;
		private BufferedWriter output;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			start();
		}
		public BufferedWriter getOutputStream() {
			return output;
		}

		public void run() {
			System.out.println("ClientHandler thread");
			try{
				output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String name = input.readLine();
				System.out.println(name);
				if(name.equals("COMPUTER")) {
					computerHandler = this;
				}else if(name.equals("E")) {
					esHandler = this;
				}
				
				while (!socket.isClosed()) {
					String temp = input.readLine();
					
					if (this.equals(computerHandler)) {
						sendToEs(temp);
						
					}else if(this.equals(esHandler)) {
						sendToComp(temp);
					}
							
//					System.out.println("" + temp);
//					controller.writeToLog("" + temp);
//					dos.writeUTF("" + temp);
					output.flush();
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
