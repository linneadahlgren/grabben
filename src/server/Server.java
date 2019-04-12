package server;

import java.io.*;
import java.net.*;

public class Server {
	private ServerController controller;

	public Server(ServerController controller, int port) {
		this.controller = controller;
		new Connection(port).start();
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
						System.out.println("Client connected11");
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

		public ClientHandler(Socket socket) {
			this.socket = socket;
			start();
		}

		public void run() {
			System.out.println("ClientHandler thread");
			try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
					DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {
				while (!socket.isClosed()) {
					String temp = dis.readUTF();
					System.out.println(temp);
					controller.writeToLog(temp);
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

}
