package server;

import java.util.LinkedList;

public class Queue {
	private LinkedList<String> queue = new LinkedList<String>();
	
	public void add(String text) {
		queue.addLast(text);
	}
	public String remove() {
		if(isEmpty() == false) {
			return queue.removeFirst();
		}else {
			
			return "NO ONE";
		}
	
	}
	
	public String element() {
		if(isEmpty() == false) {
			return queue.get(0);
		}else {
			return "NO ONE";
		}
	}
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	public int size() {
		return queue.size();
	}
	
	public String entireQueue() {
		String entireQueue = "alla i k�n: \n";

		for(int i = 0; i < queue.size(); i++) {

				entireQueue += queue.get(i) + " ";
		}
		return entireQueue;
		
	}
	

}
