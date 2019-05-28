
package server;

import java.io.*;
import java.net.*;

/**
 * 
 * @author Tove Rumar and John Lindahl
 * Class that handles the communication between the server, clients and embedded system
 *
 */

public class Server {
	private ServerController controller;
	private ClientHandler computerHandler;
	private ClientHandler esHandler;
	
	
	/**
	 * @param controller 
	 * @param port used in server
	 */
	public Server(ServerController controller, int port) {
		this.controller = controller;
		new Connection(port).start();
	}
	
	/**
	 * @param instruction command to embedded system
	 */
	public void sendToEs(String instruction) {
		try {
			System.out.print(instruction);
			
			esHandler.getOutputStream().write(instruction);
			
			esHandler.getOutputStream().flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param instruction command to client
	 */
	public void sendToComp(String instruction) {
		try {
			instruction+="\n";
			
			computerHandler.getOutputStream().write(instruction);
			computerHandler.getOutputStream().flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author John Lindahl, Tove Rumar
	 * Class that handles the connection of each client and gives them a socket
	 */
	private class Connection extends Thread {
		private int port;

		/**
		 * @param port used in server
		 */
		public Connection(int port) {
			this.port = port;
		}
		

		/**
		 * @see java.lang.Thread#run()
		 */
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

	/**
	 * @author john9
	 *
	 *	Class that sets up the streams between the server and the client
	 */
	private class ClientHandler extends Thread {
		private Socket socket;
		private BufferedReader input;
		private BufferedWriter output;

		/**
		 * @param socket to set up streams
		 * 
		 */
		public ClientHandler(Socket socket) {
			this.socket = socket;
			start();
		}
		/**
		 * @return output stream to client
		 */
		public BufferedWriter getOutputStream() {
			return output;
		}

		/**
		 * This method waits for a command sent by a client so each client can communicate to each other
		 * @see java.lang.Thread#run()
		 * 
		 */
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
					
					System.out.println("INCOMING MSG IN SERVER: "+temp);
					if (this.equals(computerHandler)) {
						if(temp.equals("GETNEXTUSER")) {
							controller.writeToLog("App sent: " + temp);
							String sendUser = "USER:" + controller.getNextUser();

							sendToComp(sendUser);
						}
						else if(temp.startsWith("NEWUSER:")) {
								controller.writeToLog("App sent: " + temp);
								User newUser=new User(temp.substring(8));
								System.out.println("IN SERVER"+newUser.getName());
								controller.setCurrentUser(newUser);
								
						}
						else if(temp.startsWith("GAMEOVER")) {
							controller.writeToLog("App sent: " + temp);
							controller.compareScore();
							System.out.println("trying to send open");
							//sendToEs("OPEN\n");
							//sendToEs("UP\n");
							
						}
						else if(temp.startsWith("CLASSIC")) {
							System.out.println("Tagit emot classic");
							controller.setClassicText();
						}
						else {
							controller.writeToLog("App sent: " + temp);
							System.out.println("skickar till es " + temp);
							sendToEs(temp);
						}
					}else if(this.equals(esHandler)) {
						controller.writeToLog("ES sent: " + temp);
						controller.setScore(temp);
						
					}
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
