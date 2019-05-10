package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.synth.*;
import javafx.*;

public class ServerUserUI extends JFrame{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addUserBtn = new JButton("put user");
	private JTextField 	userInput = new JTextField();
	private ServerController controller;
	private JPanel  groundPanel= new JPanel();
	private JPanel titlePanel=new JPanel();
	private JPanel highscorePanel=new JPanel();
	private JPanel centerPanel=new JPanel();
	private JPanel westPanel=new JPanel();
	private JLabel titleLabel=new JLabel();
	private JLabel centertextLbl=new JLabel();
	private JLabel currentPlayerLbl=new JLabel("No one is playing");
	private JLabel nextPlayerLbl=new JLabel();
	private Font titleFont;
	private Font textFont;
	private Font smallTextFont;
	private JLabel[] scoreLabels= new JLabel[10];
	//private User[] userList;
	
	
	//private User currentUser;
	
	
	public ServerUserUI(){
		
		titleFont= createFont("/Users/toverumar/Documents/Fonts/dragonmark/dragonmark.ttf",160f);
		textFont=createFont("/Users/toverumar/Documents/Fonts/linestrider-mini/linestrider-mini.ttf",100f);
		smallTextFont=createFont("/Users/toverumar/Documents/Fonts/linestrider-mini/linestrider-mini.ttf",30f);
		//this.controller = cont;
		setPreferredSize(new Dimension(1500,800));
		
		groundPanel.setPreferredSize(new Dimension(1500,800));
		groundPanel.setBackground(Color.BLACK);
		groundPanel.setLayout(new BorderLayout());
		
		highscorePanel.setPreferredSize(new Dimension(300,800));
		highscorePanel.setBackground(Color.BLACK);
		highscorePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		highscorePanel.setLayout(new BoxLayout(highscorePanel,BoxLayout.PAGE_AXIS));
		
		centerPanel.setPreferredSize(new Dimension(200,200));
		centerPanel.setBackground(Color.BLACK);
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		centerPanel.setLayout(new BorderLayout());
		centertextLbl.setForeground(Color.CYAN);
		centertextLbl.setPreferredSize(new Dimension(400,400));
		centertextLbl.setText("<html>CURRENT PLAYER<br>Tove</html>");
		centertextLbl.setFont(textFont);
		centertextLbl.setVerticalAlignment(JLabel.CENTER);
		centertextLbl.setHorizontalAlignment(JLabel.CENTER);
		
		centertextLbl.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		currentPlayerLbl.setText("Next Player:");
		currentPlayerLbl.setFont(textFont);
		currentPlayerLbl.setForeground(Color.GREEN);
		currentPlayerLbl.setPreferredSize(new Dimension(400,200));
		currentPlayerLbl.setVerticalAlignment(JLabel.CENTER);
		currentPlayerLbl.setHorizontalAlignment(JLabel.CENTER);
		centerPanel.add(centertextLbl);
		centerPanel.add(currentPlayerLbl,BorderLayout.SOUTH);
		
		titlePanel.setPreferredSize(new Dimension(800,200));
		titlePanel.setBackground(Color.BLACK);
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		titleLabel.setText("CLAW");
		
		titleLabel.setFont(titleFont);
		titleLabel.setForeground(Color.BLUE);
		titlePanel.add(titleLabel);
		
		setHighscorePanel();

		westPanel.setPreferredSize(new Dimension(300,800));
		westPanel.setBackground(Color.BLACK);
		westPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		centerPanel.add(titlePanel,BorderLayout.NORTH);
		groundPanel.add(highscorePanel,BorderLayout.EAST);
		groundPanel.add(centerPanel,BorderLayout.CENTER);
		groundPanel.add(westPanel, BorderLayout.WEST);
		
		add(groundPanel);
		
		
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
	}
	
	public void updateHighscore(User[] highScoreList) {
		for(int i=0;i<scoreLabels.length;i++) {
			
		}
		
	}
	public void setHighscorePanel() {
		for(int i=0;i<scoreLabels.length;i++) {
			scoreLabels[i]=new JLabel("NOONE:0");
			scoreLabels[i].setVerticalAlignment(JLabel.CENTER);
			scoreLabels[i].setHorizontalAlignment(JLabel.CENTER);
			scoreLabels[i].setMinimumSize(new Dimension(300,50));
			scoreLabels[i].setMaximumSize(new Dimension(300,50));
			scoreLabels[i].setBorder(BorderFactory.createLineBorder(Color.GREEN));
			scoreLabels[i].setFont(smallTextFont);
			scoreLabels[i].setForeground(Color.GREEN);
			
			highscorePanel.add(scoreLabels[i]);
			
		}
	}
	public Font createFont(String filename,float size) {
		Font customFont=null;
		try {
		   
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File(filename)).deriveFont(size);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    
		    ge.registerFont(customFont);
		    
		    
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}

		return customFont;
	}
	
	private class BtnListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(addUserBtn == e.getSource()) {
				String newUser=userInput.getText();
				controller.addToQueue(newUser);	
				
			}
		}
	}
	public static void main(String[]args) {
		ServerUserUI ui=new ServerUserUI();
		User user=new User("TOVE",2000);
		ui.updateHighscore(user,3);
		
	}
			
	}	
	
