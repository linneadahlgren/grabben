package server;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.File;
import java.io.IOException;

import javax.swing.*;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.synth.*;


import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.synth.*;
import javax.swing.text.DocumentFilter;

import javafx.*;
/**
 * 
 * User Interface for the server. The class is controlled by the serverController.The UI displays the current and next user in lite to play
 * and takes inputs of new users and puts them in line to play.
 * @author toverumar
 *
 */
public class ServerUserUI extends JFrame implements WindowListener{

		
	
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
	private JLabel centertextLbl=new JLabel("NO ONE IS PLAYING");
	private JLabel nextPlayerLbl=new JLabel("NO ONE IN LINE");
	private Font titleFont;
	private Font textFont;
	private Font smallTextFont;
	private Font mediumTextFont;
	private JLabel[] scoreLabels= new JLabel[10];
	private JLabel highScoreLbl=new JLabel();
	private JPanel loginPanel=new JPanel();
	private JButton loginBtn=new JButton("GET IN LINE");
	private JLabel loginLbl=new JLabel("ENTER YOUR NAME");
	private JTextField loginTxt=new JTextField();
	
	private JLabel blinkingMsg=new JLabel("WElCOME");
	private JPanel blinkPanel=new JPanel();
	private User currentUser=new User();
	private User nextUser=new User();
	private Color lightBlue=new Color(51,204,255);
	private Color purple=new Color(102,0,153);
	private Color pink=new Color(255,20,147);

	
	public ServerUserUI(ServerController controller){

		
		titleFont= createFont("/Users/toverumar/Documents/Fonts/dragonmark/dragonmark.ttf",160f);
		textFont=createFont("/Users/toverumar/Documents/Fonts/linestrider-mini/linestrider-mini.ttf",100f);
		smallTextFont=createFont("/Users/toverumar/Documents/Fonts/linestrider-mini/linestrider-mini.ttf",30f);

		
		setPreferredSize(new Dimension(1500,900));
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		mediumTextFont=createFont("/Users/toverumar/Documents/Fonts/linestrider-mini/linestrider-mini.ttf",60f);
		this.controller = controller;
	
		groundPanel.setPreferredSize(new Dimension(1500,900));
		groundPanel.setBackground(Color.BLACK);
		groundPanel.setLayout(new BorderLayout());
		
		highscorePanel.setPreferredSize(new Dimension(300,900));
		highscorePanel.setBackground(Color.BLACK);
		highscorePanel.setForeground(pink);
		highscorePanel.setLayout(new BoxLayout(highscorePanel,BoxLayout.PAGE_AXIS));
		highScoreLbl.setText("HIGHSCORE");
		highScoreLbl.setVerticalAlignment(JLabel.CENTER);
		highScoreLbl.setHorizontalAlignment(JLabel.CENTER);
		highScoreLbl.setMinimumSize(new Dimension(300,100));
		highScoreLbl.setMaximumSize(new Dimension(300,100));
		highScoreLbl.setFont(mediumTextFont);
		highScoreLbl.setForeground(pink);
		highscorePanel.add(highScoreLbl);
		
		centerPanel.setPreferredSize(new Dimension(200,200));
		centerPanel.setBackground(Color.BLACK);
		centerPanel.setLayout(new BorderLayout());
		centertextLbl.setBackground(Color.BLACK);
		centertextLbl.setForeground(lightBlue);
		centertextLbl.setPreferredSize(new Dimension(400,400));
		centertextLbl.setFont(textFont);
		centertextLbl.setVerticalAlignment(JLabel.CENTER);
		centertextLbl.setHorizontalAlignment(JLabel.CENTER);
		centerPanel.add(titlePanel,BorderLayout.NORTH);
		
		groundPanel.add(highscorePanel,BorderLayout.EAST);
		groundPanel.add(centerPanel,BorderLayout.CENTER);
		groundPanel.add(westPanel, BorderLayout.WEST);
		add(groundPanel);
		
		nextPlayerLbl.setText("Next Player:");
		nextPlayerLbl.setFont(textFont);
		nextPlayerLbl.setBackground(Color.BLACK);
		nextPlayerLbl.setForeground(purple);
		nextPlayerLbl.setPreferredSize(new Dimension(400,200));
		nextPlayerLbl.setVerticalAlignment(JLabel.CENTER);
		nextPlayerLbl.setHorizontalAlignment(JLabel.CENTER);
		centerPanel.add(centertextLbl);
		centerPanel.add(nextPlayerLbl,BorderLayout.SOUTH);
		
		loginPanel.setPreferredSize(new Dimension(300,400));
		loginPanel.setBackground(Color.BLACK);
		loginPanel.setLayout(new BorderLayout());
		loginLbl.setPreferredSize(new Dimension(300,100));
		loginLbl.setBackground(Color.BLACK);
		loginLbl.setFont(smallTextFont);
		loginLbl.setForeground(purple);
		loginLbl.setHorizontalAlignment(JLabel.CENTER);
		loginTxt.setPreferredSize(new Dimension(250,100));
		loginTxt.setBackground(Color.BLACK);
		loginTxt.setForeground(pink);
		loginTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		loginTxt.setFont(smallTextFont);
		loginTxt.setCaretColor(pink);
		loginTxt.setHorizontalAlignment(JLabel.CENTER);
		loginTxt .addKeyListener(new KeyAdapter() {
	        
	        public void keyTyped(KeyEvent e) {
	            if (loginTxt.getText().length() >= 15 ) // Maximum typed length is 15 characters. Longer usernames can be copied in textfield without limit
	                e.consume();
	        }
	    });
		
		loginBtn.setPreferredSize(new Dimension(300,100));
		loginBtn.setOpaque(true);
		loginBtn.setBackground(lightBlue);
		loginBtn.setForeground(purple);
		loginBtn.setBorder(BorderFactory.createLineBorder(purple, 2));
		loginBtn.setFont(smallTextFont);
		loginPanel.add(loginLbl,BorderLayout.NORTH);
		loginPanel.add(loginTxt, BorderLayout.CENTER);
		loginPanel.add(loginBtn, BorderLayout.SOUTH);
		loginBtn.addActionListener(new BtnListener());
		
		titlePanel.setPreferredSize(new Dimension(800,200));
		titlePanel.setBackground(Color.BLACK);	
		titleLabel.setText("CLAW");
		titleLabel.setFont(titleFont);
		titleLabel.setForeground(lightBlue);
		titlePanel.add(titleLabel);
		
		blinkingMsg.setPreferredSize(new Dimension(300,400));
		blinkingMsg.setText("<html>W<br>E<br>L<br>C<br>O<br>M<br>E</html>");
		blinkingMsg.setBackground(Color.BLACK);
		blinkingMsg.setFont(smallTextFont);
		blinkingMsg.setForeground(pink);
		blinkingMsg.setHorizontalAlignment(JLabel.CENTER);
		blinkPanel.setPreferredSize(new Dimension(300,400));
		blinkPanel.setBackground(Color.BLACK);
		blinkPanel.add(blinkingMsg);
		
		westPanel.setPreferredSize(new Dimension(300,800));
		westPanel.setLayout(new BorderLayout());
		westPanel.setBackground(Color.BLACK);
		
		westPanel.add(loginPanel,BorderLayout.SOUTH);
		westPanel.add(blinkPanel,BorderLayout.NORTH);
		
		groundPanel.add(highscorePanel,BorderLayout.EAST);
		centerPanel.add(titlePanel,BorderLayout.NORTH);
		groundPanel.add(centerPanel,BorderLayout.CENTER);
		groundPanel.add(westPanel, BorderLayout.WEST);

		
		add(groundPanel);
		
		new BlinkThread().start();
		
		
		addWindowListener(this);
		pack();
		setVisible(true);	
		
	}
	
	/**
	 * Sets the currentUser to be displayed.
	 * If username is NO ONE,the UI shows "ENTER ON TABLET"
	 * otherwise it shows the username
	 * 
	 * 
	 * @param currentUser
	 */
	public void updateCurrentUser(User currentUser) {
		this.currentUser=currentUser;
		
		if(currentUser.getName().equals("NO ONE")) {
			centertextLbl.setText("<html>NO ONE PLAYING<br><center> ENTER ON TABLET</html>");
		}
		else{
		centertextLbl.setText("<html><center>PLAYING:<br><center>"+ currentUser.getName()+"<br><center>"+currentUser.getPoints()+"</html>");
		}
		repaint();
	}
	
	/**
	 * Sets next user in line to play and then repaints the window to display it
	 * @param nextUser
	 */
	
	public void updateNextUser(User nextUser) {		
		this.nextUser=nextUser;
		repaint();
		
	}
	public void updatePoints(int points) {
		System.out.println("In UI-method" +points);
		currentUser.setPoints(points);
		centertextLbl.setText("<html><center>PLAYING:<br><center>"+ currentUser.getName()+"<br><center>"+currentUser.getPoints()+"</html>");
		System.out.println("currentuser In UI" +currentUser.getPoints());
		
		repaint();
	}
	/**
	 * 
	 * Updates the highscorelist and then repaints the window to display it
	 * @param highScoreList
	 */
	public void updateHighscore(User[] highScoreList) {
		for(int i=0;i<scoreLabels.length;i++) {
			scoreLabels[i].setText(highScoreList[i].getName()+": " + highScoreList[i].getPoints());
			
		}
		repaint();
	}
	
	/**
	 * 
	 * Sets the highscorePanel. Should only be used on startup. 
	 * @param highScoreList
	 */
	public void setHighscorePanel(User[] highScoreList) {
		for(int i=0;i<scoreLabels.length;i++) {
			scoreLabels[i]=new JLabel(highScoreList[i].getName()+": " + highScoreList[i].getPoints());
			scoreLabels[i].setVerticalAlignment(JLabel.CENTER);
			scoreLabels[i].setHorizontalAlignment(JLabel.CENTER);
			scoreLabels[i].setMinimumSize(new Dimension(300,100));
			scoreLabels[i].setMaximumSize(new Dimension(300,100));
			scoreLabels[i].setFont(smallTextFont);
			scoreLabels[i].setForeground(pink);
			highscorePanel.add(scoreLabels[i]);
			
		}
		repaint();
	}
	/**
	 * method that executes on closing the window. Starts the saveHighscore-method in controller
	 */
	
	public void shutDown() {
		
		controller.saveHighScore();
	}
	/**
	 * Takes a filename and a size to read in fonts to the UI.
	 * 
	 * @param filename
	 * @param size
	 * @return
	 */
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
	/**
	 * Listens to the button. Upon press it takes the string in the loginTxtfield and checks if it is longer than 0 characters. 
	 * If it is longer it sends it to the controllerClass
	 * @author toverumar
	 *
	 */
	private class BtnListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {

			// TODO Auto-generated method stub
				
		
			if(loginBtn == e.getSource()) {
				String newUser=loginTxt.getText().toUpperCase();
				if (newUser.equals("")){
					
				}
				else {
				controller.addToQueue(newUser);	
				loginTxt.setText("");
				}
				
		
			}
		}
	}

	/**
	 * 
	 * Blinks texts in the UI.
	 * @author toverumar
	 *
	 */
	private class BlinkThread extends Thread{
		public void run() {
			while(true) {
				nextPlayerLbl.setText(nextUser.getName());
				
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nextPlayerLbl.setText("NEXT PLAYER: ");
			
			repaint();
			

			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!blinkingMsg.getText().equals("WELCOME"))
			blinkingMsg.setText("WELCOME");
			else 
				blinkingMsg.setText("<html>W<br>E<br>L<br>C<br>O<br>M<br>E</html>");
				
			}
		}
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		shutDown();
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	}

	
