package jinputjoysticktestv2;

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
	
	private EgetTest joystick;
	
	public ClientController() {
		viewer=new UIClient(this);
		//joystick = new EgetTest(this);
		
		if(thread==null) {
			thread=new ClientThread();
			thread.start();
			
		}
	
		
	}
	public void connect(String ip, int port) {
		this.ip=ip;
		this.port=port;
		System.out.println("Connecting to server...");
		try {
			socket=new Socket(ip,port);
			System.out.println("Connected to server");
			
		
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
		public void send(String instruction) {
			instruction+="\n";
			if(client==null) {
				try {
					wait();
					
				}catch(InterruptedException e) {
					e.printStackTrace();
					System.out.println("Inetrruption occured");
				}
			}
			try {
				
				client.getOutputStream().write(instruction);
				
				client.getOutputStream().flush();
				
			}catch(IOException e) {
				e.printStackTrace();
				System.out.println("Exception occured when trying to send instruction");
			}
			
		}
	
	private class ClientThread extends Thread{
		
		public ClientThread() {
			Client client;
		}
		public void run() {
			
			connect("192.168.0.20", 5000);
		
			client=new Client(socket);
		
			send("E");
			
			while(!socket.isClosed()) {
				
				try {
					String incoming = client.getInputStream().readLine();
					System.out.println(incoming);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public static void main(String [] args) {
		ClientController controller=new ClientController();
		
	}
}



