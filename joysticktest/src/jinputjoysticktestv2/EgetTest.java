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
	private String clawState = "RELEASE";
	
	public EgetTest(ClientController con) {
		clientcontroller = con;
		getController();
	}
	public EgetTest() {
		getController();
	}

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
		
		new XYHandler(axisController);

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
		
			
			
			
			for(int i=0;i< components.length;i++) {

				float axisValue = components[i].getPollData();

				int axisValueInPercentage = getAxisValueInPercentage(axisValue);
				if(components[i].getName().contains("Knapp")){
					checkButtons(components[i], axisValueInPercentage);
				}
//				if(components[i].getName().equals("X-axeln") || components[i].getName().equals("Y-axeln") || components[i].getName().equals("Z-axeln")  ) {
//					if(axisValueInPercentage > 90 || axisValueInPercentage < 10 || axisValueInPercentage == 50){
//						//System.out.println(components[i].getName() + " :      " + axisValueInPercentage );
//
//						//enda viktiga raden i denna metoden lol
//						String direction = axisValueInString(axisValueInPercentage, components[i].getName());
//
//						if(!direction.equals(currentDirection)) {
//
//							if(direction.startsWith("X") || direction.startsWith("Z") || direction.startsWith("Y")){
//								//joysticken är centrerad 
//							}
//
//							//System.out.println(direction);
//							else {
//								System.out.println( "RELEASE");
//								//clientcontroller.send("RELEASE\n");
//								System.out.println(direction + "     "+  components[i].getName() + "         "+ axisValueInPercentage );
//								currentDirection = direction;
//								//clientcontroller.send(direction + "\n");
//							}
//
//						}else{
//
//						}
//					}
//					
//					
//				}
			}
		}
	}
	
	
	/*
	 * detta hanterar om komponenterna är knapparna som ska öppna och stänga
	 * */
	public void checkButtons(Component component, int axisValueInPercentage) {
		if(component.getName().equals("Knapp 4") && axisValueInPercentage == 100) {
			//open
			if(clawState.equals("CLOSE") || clawState.equals("RELEASE")) {
				clawState = "OPEN";
				System.out.println("OPEN");
				clientcontroller.send("OPEN");
			}
		}else if(component.getName().equals("Knapp 4") && axisValueInPercentage == 0) {
			clawState = "RELEASE";
		}else if(component.getName().equals("Knapp 5") && axisValueInPercentage == 100) {
			if(clawState.equals("OPEN") || clawState.equals("RELEASE")) {
				clawState = "CLOSE";
				System.out.println("CLOSE");
				clientcontroller.send("CLOSE");
			}
		}else if(component.getName().equals("Knapp 5") && axisValueInPercentage == 0) {
			clawState = "RELEASE";
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
			if(value < 50) {
				return "FORWARD";
			}else if(value > 50) {
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

	private class XYHandler extends Thread{
		private Component XAxis;
		private Component YAxis;
		private Component ZAxis;
		
		
		private String nextDirection ="";
		private String current ="";
		
		private int previousXValue = 0;
		private int previousYValue = 0;
		private int previousZValue = 0;
		private ClientController clinetCon;
		
		public XYHandler(Controller c) {
			//clinetCon = con;
			
			System.out.println("kommer till ny thread");
			Component[] components = c.getComponents();
			
			for(int i = 0; i < components.length; i++) {
				if(components[i].getName().equals("X-axeln")) {
					XAxis = components[i];
				}else if(components[i].getName().equals("Y-axeln")) {
					YAxis = components[i];
				}else if(components[i].getName().equals("Z-axeln")) {
					ZAxis = components[i];
				}
			}
			start();
			
			
			
		}
		public void run() {
			System.out.println("nu kör nya tråden");
			while(true) {
				int XValue = getAxisValueInPercentage(XAxis.getPollData());
				int YValue = getAxisValueInPercentage(YAxis.getPollData());
				
				if(XValue != previousXValue || YValue != previousYValue) {
					
					
					if(XValue > 80 && YValue > 80) {
						if(XValue > YValue) {
							nextDirection = "RIGHT";
						}else if(YValue > XValue) {
							nextDirection = "BACK";
						}else {
						}
					}
					
				
					else if(XValue < 20 && YValue < 20) {
						if(XValue < YValue) {
							nextDirection = "LEFT";
						}else if(XValue > YValue) {
							nextDirection = "FORWARD";
						}else {
							//nextDirection ="test 2";
						}
					}else if(XValue > 80 && YValue < 20) {
						int XCompare = 100 - XValue;
						if(XCompare < YValue) {
							nextDirection = "RIGHT";
						}else if(XCompare > YValue) {
							nextDirection = "FORWARD";
						}else {
						//	nextDirection ="test 3";
						}
					}else if(XValue < 20 && YValue > 80) {
						int YCompare = 100 - YValue;
						if(XValue < YCompare) {
							nextDirection = "LEFT";
						}else if(XValue > YCompare) {
							nextDirection = "BACK";
						}else {
							//nextDirection ="test 4";
						}
					}else if(XValue > 20 && XValue < 80) {
						if(YValue > 80) {
							nextDirection = "BACK";
						}else if(YValue < 20) {
							nextDirection = "FORWARD";
						}else {
							int ZValue = getAxisValueInPercentage(ZAxis.getPollData());
							if(ZValue < 20) {
								nextDirection = "DOWN";
							}else if(ZValue > 80) {
								nextDirection = "UP";
							}else if(ZValue == 50) {
								nextDirection ="RELEASE";
							}
						}
					}else if(YValue > 20 && YValue < 80) {
						if(XValue > 80) {
							nextDirection = "RIGHT";
						}else if(XValue < 20) {
							nextDirection = "LEFT";
						}else {
						//	nextDirection ="test 6";
						}
					}
//					else if(YValue < 65 && XValue < 65){ 
//						if( YValue > 35 && XValue > 35) {
//							System.out.println("kommer vi hit?");
//						}
//				}
					
				}
				if(!nextDirection.equals(current)) {
					System.out.println("nytt värde");
					clientcontroller.send("RELEASE");
					
					
					
					System.out.println(nextDirection);
					current = nextDirection;
					clientcontroller.send(current);
				}
				
				
			}
			
			
		}
	}

//	public static void main(String[] args) {
//		EgetTest test = new EgetTest();
//		test.getController();
//	}


}
