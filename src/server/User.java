package server;



import java.io.Serializable;

public class User implements Comparable, Serializable{

	private String name;
	private int points;
	
	public User() {
		name="NO ONE";
		points=0;

	}
	
	public User(String name) {
		
		this.name=name;
		points=0; 

				

	}
	public User(String name,int points) {
		this.name=name;
		this.points=points;

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int compareTo(Object o) {
		
		if(this.getPoints()>=((User) o).getPoints())
			return -1;

		else 
			return 1;
			
	}

	
	
	
}


