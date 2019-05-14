package server;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class ServerController {
	private ServerViewer viewer;
	private ServerUserUI userUI;
	private Queue userQueue;
	private User[] highScoreList;
	private User currentUser=new User();
	private User nextUser=new User();

	
	public ServerController() {
		userUI = new ServerUserUI(this);
		userQueue = new Queue();
		highScoreList=new User[10];
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
		for(int i=0;i<highScoreList.length;i++) {
			highScoreList[i]=new User("NO ONE",10);
		}
		
		userUI.setHighscorePanel(highScoreList);
	}
	public void compareScore() {
		if (currentUser!=null) {
			
			
			if(currentUser.getPoints()>=highScoreList[highScoreList.length-1].getPoints()) {
				
				highScoreList[9]=currentUser;
				
				System.out.println("före sort");
				for(User user:highScoreList) {
					System.out.println(user.getPoints()+user.getName());
					
				}
				   Arrays.sort(highScoreList);
				   
				   System.out.println("efter sort");
					for(User user:highScoreList) {
						System.out.println(user.getPoints()+user.getName());
				   
				   userUI.updateHighscore(highScoreList);	
					}
				}							
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
	
	

			
		
	public static void main(String[] args) {
		ServerController controller = new ServerController();
		controller.showUI(new ServerViewer());
		//new Server(controller, 5000);
		//controller.setCurrentUser("Pontus",70);
		
		//controller.compareScore();
		
		controller.add(new User("Ludvig"));
		controller.add(new User("Linnea"));
		controller.getNextUser();
		
		
		
	
	
		
	}
}
