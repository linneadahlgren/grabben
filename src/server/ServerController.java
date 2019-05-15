package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;



public class ServerController {
	private ServerViewer viewer;
	private ServerUserUI userUI;
	private Queue userQueue;
	private User[] highScoreList;
	private User currentUser=new User();
	private User nextUser=new User();
	private String highscoreFile="files/highscoreFile";
	
	public ServerController() {
		userUI = new ServerUserUI(this);
		userQueue = new Queue();
		highScoreList=new User[]{new User(),new User(),new User(),new User(),new User(),new User(),new User(),new User(),new User(),new User()};
		userUI.setHighscorePanel(highScoreList);
		readOldHighScore();
		
	}
	public void showUI(ServerViewer viewer) {
		this.viewer = viewer;
	}
	
	public void writeToLog(String msg) {
		viewer.addText(msg);
	}
	
	
	/**
	 * @param user, String of the "user" to add to the queue
	 */
	public void addToQueue(String user) {
		writeToLog("New user in line to play: " + user);
		userQueue.add(user);
	}
	public void readOldHighScore() {
		
		new HighScoreReader().start();

		
	}
	public void compareScore() {
		if (currentUser!=null) {
			
			
			if(currentUser.getPoints()>=highScoreList[highScoreList.length-1].getPoints()) {
				
				highScoreList[9]=currentUser;
					
				}
				   Arrays.sort(highScoreList);
				   
				   userUI.updateHighscore(highScoreList);	
					}
				}							
			
		
	
	
	/**
	 * @return string of next user in the queue, sets it as the current user and
	 *  also removes it from the queue. 
	 */
	public String getNextUser() {
		currentUser=new User(userQueue.remove());
		
		userUI.updateCurrentUSer(currentUser);
		setNextUser();
		
		return currentUser.getName();
	}
	public String setNextUser() {
		nextUser=new User(userQueue.element());
		
		userUI.updateNextUser(nextUser);
		
		return nextUser.getName();
	}
	public void saveHighScore() {
		System.out.println("starting HighscoreSaver");
		new HighscoreSaver().start();
		
	
	}
	/**
	 * 
	 * 
	 * Messages below are stubs for testing
	 */
	public void add(User user) {
		userQueue.add(user.getName());
	}
	public void setScore(int points) {
		currentUser.setPoints(points);
	}
	public void setCurrentUser(String username,int points) {
		currentUser.setName(username);
		currentUser.setPoints(points);
	}
	
	

	private class HighscoreSaver extends Thread{

		public void run() {
			System.out.println("highscoresaver started");
				try{
					System.out.println("trying to setup streams");
					ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(highscoreFile)));
					System.out.println("stream is setup");
					for(int i=0;i<highScoreList.length;i++) {
						
						System.out.println(highScoreList[i].getName());
						output.writeObject(highScoreList[i]);
						output.flush();
					}
					
					output.close();


				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	
	private class HighScoreReader extends Thread{

		public void run() {
			
			try(ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(highscoreFile)))){
				
				for(int i = 0; i < highScoreList.length; i++) {
					User user =  (User) input.readObject();
					if (user!=null) {
						highScoreList[i]=user;
					}
					else if(user==null) {
						highScoreList[i]=new User();
					}
					System.out.println(highScoreList[i].getName());
					   
				}
				input.close();
				userUI.updateHighscore(highScoreList);	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	public static void main(String[] args) {
		ServerController controller = new ServerController();
		controller.showUI(new ServerViewer());
		//new Server(controller, 5000);
		//controller.setCurrentUser("Pontus",70);
		
	
		
		controller.add(new User("LUDVIG"));
		controller.add(new User("LINNEA"));
		controller.add(new User("LUDVIG"));
		controller.add(new User("LINNEA"));
		controller.add(new User("LUDVIG"));
		controller.add(new User("LINNEA"));
		controller.add(new User("LUDVIG"));
		controller.add(new User("LINNEA"));
//		controller.getNextUser();
//		controller.setScore(50);
//		controller.compareScore();
//		controller.getNextUser();
//		controller.setScore(10);
//		controller.compareScore();
//		controller.getNextUser();
//		controller.setScore(20);
//		controller.compareScore();
//		controller.getNextUser();
//		controller.setScore(700);
//		controller.compareScore();
//		controller.getNextUser();
//		controller.setScore(200);
//		controller.compareScore();
	}
		
}

