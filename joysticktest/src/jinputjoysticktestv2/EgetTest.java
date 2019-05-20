package jinputjoysticktestv2;


import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Version;



public class EgetTest {
	private Controller axisController;
	private Component[] components;
	private String currentDirection = "RELEASED";
	private ClientController clientcontroller;
	

/*
 * 
 * getController metoden letar efter kontrolleneheter som är kopplade till ens dator
 * när den gjort det går den vidare till att läsa data från kontrollenehten 
 * 
 * */	
	public void getController() {
		Controller[] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();

		for(int i = 0; i < controllerList.length; i++) {
			System.out.println(controllerList[i].getName());
			if(controllerList[i].getName().contains("AXIS")){
				axisController = controllerList[i];
			}
		}


		polling();
	}

	/**
	 * 
	 * Polling metoden läser data från axiskontrollern och går igenom alla dess komponenter 
	 * 
	 * */
	public void polling() {
		while(true) {
			axisController.poll();
			
			Component[] components = axisController.getComponents();
			StringBuffer buffer = new StringBuffer();
			
			
			for(int i=0;i<components.length;i++) {
				
				float axisValue = components[i].getPollData();
				
				int axisValueInPercentage = getAxisValueInPercentage(axisValue);


				//if((axisValueInPercentage == 50) || (axisValueInPercentage == 49)) {
				if(axisValueInPercentage > 90 || axisValueInPercentage < 10 || axisValueInPercentage == 50){
					//System.out.println(components[i].getName() + " :      " + axisValueInPercentage );

					//enda viktiga raden i denna metoden lol
					String direction = axisValueInString(axisValueInPercentage, components[i].getName());

					if(!direction.equals(currentDirection)) {
							
						if(direction.startsWith("X") || direction.startsWith("Z") || direction.startsWith("Y")){
							currentDirection = "RELEASED";
						}else {
							System.out.println(direction + "     "+  components[i].getName() + "         "+ axisValueInPercentage );
							currentDirection = direction;
						}

					}else{
						
					}
				}
			}
		}
	}

	/*
	 * Denna metoden tar axeln och värdet från axeln i procent. Metoden kommer
	 * retunera en sträng med riktningen 
	 * 
	 * */
	public String axisValueInString(int value, String name) {
		if(name.startsWith("X")) {
			if(value > 50) {
				return "RIGHT";
			}else if(value < 50) {
				return "LEFT";
			}else {
				return "XCENTERED";
			}
		}else if(name.startsWith("Y")) {
			if(value > 50) {
				return "FORWARD";
			}else if(value < 50) {
				return "BACK";
			}else {
				return "YCENTERED";
			}
		}else if(name.startsWith("Z")) {
			if(value > 50) {
				return "UP";
			}else if(value < 50) {
				return "DOWN";
			}else {
				return "ZCENTERED";
			}
		}

		return "RELEASED";
	}
	
	/*
	 * vet inte riktigt vad denna metoden gör men tror att värdet man har innan 
	 * är skit som man inte riktigt kan förstå 
	 * */
	public int getAxisValueInPercentage(float axisValue) {
		return (int)(((2 - (1 - axisValue)) * 100) / 2);
	}


	public static void main(String[] args) {
		EgetTest test = new EgetTest();
		test.getController();
	}


}
