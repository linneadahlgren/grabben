package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;



public class ClientController {
	private ClientViewer viewer;
	private String ip;
	private int port;
	private Socket socket;
	private Client client;
	private ClientThread thread;
	
	
	
	public ClientController() {
		viewer=new UIClient(this);
		
	}
	public void connect(String ip, int port) {
		this.ip=ip;
		this.port=port;

		
		try {
			socket=new Socket(ip,port);
			System.out.println("Connecting to server...");
			if(thread==null) {
				thread=new ClientThread();
				thread.start();
				System.out.println("connected to server");
			}
		
	}catch (UnknownHostException e) {
		e.printStackTrace();
		System.out.println("Cannot find server");
		
	}catch (IOException e) {
		e.printStackTrace();
		System.out.println("IO Exception when trying to connect to server");
	}
}
	public void disconnect() {
		if(socket!=null) {
			try {
				socket.close();
			}catch (IOException e) {
				e.printStackTrace();
				System.out.println("IO Exception when trying to close the connection");
			}
		}
	}
		public void send(char instruction) {
			if(client==null) {
				try {
					wait();
					
				}catch(InterruptedException e) {
					e.printStackTrace();
					System.out.println("Inetrruption occured");
				}
			}
			try {
				client.getOutputStream().writeChar(instruction);
				client.getOutputStream().flush();
				
			}catch(IOException e) {
				e.printStackTrace();
				System.out.println("Exception occured when trying to send instruction");
			}
			
		}
	
	private class ClientThread extends Thread{
		
		public ClientThread() {
			client=new Client(socket);
		}
		public void run() {
		
			send('C');
			
			while(!socket.isClosed()) {
				
				try {
					char incoming = client.getInputStream().readChar();
					System.out.println(incoming);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public static void main(String [] args) {
		ClientController controller=new ClientController();
		controller.connect("127.0.0.1", 5000);
	}
}



