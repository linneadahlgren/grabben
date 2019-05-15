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

public class ServerUserUI extends JFrame implements WindowListener{

		
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

//	private JLabel currentPlayerLbl=new JLabel("No one is playing");
//	private JLabel nextPlayerLbl=new JLabel();
//	private Font titleFont;
//	private Font textFont;
//	private Font smallTextFont;
//	private JLabel[] scoreLabels= new JLabel[10];
	

	private JLabel nextPlayerLbl=new JLabel("NO ONE IS PLAYING");
	
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

		//this.controller = cont;
		setPreferredSize(new Dimension(1500,800));
		

		mediumTextFont=createFont("/Users/toverumar/Documents/Fonts/linestrider-mini/linestrider-mini.ttf",60f);
		this.controller = controller;
		
		setPreferredSize(new Dimension(1500,800));
	

		groundPanel.setPreferredSize(new Dimension(1500,800));
		groundPanel.setBackground(Color.BLACK);
		groundPanel.setLayout(new BorderLayout());
		
		highscorePanel.setPreferredSize(new Dimension(300,800));
		highscorePanel.setBackground(Color.BLACK);

//		highscorePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
//		highscorePanel.setLayout(new BoxLayout(highscorePanel,BoxLayout.PAGE_AXIS));
		
//		centerPanel.setPreferredSize(new Dimension(200,200));
//		centerPanel.setBackground(Color.BLACK);
//		centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//		centerPanel.setLayout(new BorderLayout());
//		centertextLbl.setForeground(Color.CYAN);
//		centertextLbl.setPreferredSize(new Dimension(400,400));
//		centertextLbl.setText("<html>CURRENT PLAYER<br>Tove</html>");

		highscorePanel.setForeground(pink);
		//highscorePanel.setBorder(BorderFactory.createLineBorder(pink));
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
		//centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		centerPanel.setLayout(new BorderLayout());
		centertextLbl.setBackground(Color.BLACK);
		centertextLbl.setForeground(lightBlue);
		centertextLbl.setPreferredSize(new Dimension(400,400));
		centertextLbl.setText("<html>NOW PLAYING:<br><center>NO ONE</html>");

		centertextLbl.setFont(textFont);
		centertextLbl.setVerticalAlignment(JLabel.CENTER);
		centertextLbl.setHorizontalAlignment(JLabel.CENTER);
		

//		centertextLbl.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//		currentPlayerLbl.setText("Next Player:");
//		currentPlayerLbl.setFont(textFont);
//		currentPlayerLbl.setForeground(Color.GREEN);
//		currentPlayerLbl.setPreferredSize(new Dimension(400,200));
//		currentPlayerLbl.setVerticalAlignment(JLabel.CENTER);
//		currentPlayerLbl.setHorizontalAlignment(JLabel.CENTER);
//		centerPanel.add(centertextLbl);
//		centerPanel.add(currentPlayerLbl,BorderLayout.SOUTH);
		
//		titlePanel.setPreferredSize(new Dimension(800,200));
//		titlePanel.setBackground(Color.BLACK);
//		titlePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//		
//		titleLabel.setText("CLAW");
//		
//		titleLabel.setFont(titleFont);
//		titleLabel.setForeground(Color.BLUE);
//		titlePanel.add(titleLabel);
		
//		setHighscorePanel();

//		westPanel.setPreferredSize(new Dimension(300,800));
//		westPanel.setBackground(Color.BLACK);
//		westPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		centerPanel.add(titlePanel,BorderLayout.NORTH);
		groundPanel.add(highscorePanel,BorderLayout.EAST);
		groundPanel.add(centerPanel,BorderLayout.CENTER);
		groundPanel.add(westPanel, BorderLayout.WEST);
		
		add(groundPanel);
		
		//centertextLbl.setBorder(BorderFactory.createLineBorder(lightBlue));
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
	            if (loginTxt.getText().length() >= 15 ) // limit to 3 characters
	                e.consume();
	        }
	    });
		
		//loginTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		loginBtn.setPreferredSize(new Dimension(300,100));
		loginBtn.setForeground(purple);
		//loginBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.GRAY,Color.BLACK));
		
		loginBtn.setFont(smallTextFont);
		loginPanel.add(loginLbl,BorderLayout.NORTH);
		loginPanel.add(loginTxt, BorderLayout.CENTER);
		
		loginPanel.add(loginBtn, BorderLayout.SOUTH);
		loginBtn.addActionListener(new BtnListener());
		
		titlePanel.setPreferredSize(new Dimension(800,200));
		titlePanel.setBackground(Color.BLACK);
		//titlePanel.setBorder(BorderFactory.createLineBorder(purple));
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
		
		//westPanel.setBorder(BorderFactory.createLineBorder(pink));


		
		westPanel.add(loginPanel,BorderLayout.SOUTH);
		westPanel.add(blinkPanel,BorderLayout.NORTH);
		
		groundPanel.add(highscorePanel,BorderLayout.EAST);
		centerPanel.add(titlePanel,BorderLayout.NORTH);
		groundPanel.add(centerPanel,BorderLayout.CENTER);
		groundPanel.add(westPanel, BorderLayout.WEST);

		
		add(groundPanel);
		
		new BlinkThread().start();
		
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		pack();
		setVisible(true);
		
	
		
		
	}
	
	public void updateCurrentUSer(User currentUser) {
		this.currentUser=currentUser;
		
		centertextLbl.setText("<html>NOW PLAYING<br><center>"+currentUser.getName()+"</html>");
	}
	
	public void updateNextUser(User nextUser) {
		
		this.nextUser=nextUser;
		
		
		
	}
	
	public void updateHighscore(User[] highScoreList) {
		for(int i=0;i<scoreLabels.length;i++) {
			scoreLabels[i].setText(highScoreList[i].getName()+": " + highScoreList[i].getPoints());
		}
		
	}
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
	}
	
	public void shutDown() {
		System.out.println("shutDown");
		controller.saveHighScore();
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
				
		
			if(loginBtn == e.getSource()) {
				String newUser=loginTxt.getText().toUpperCase();
				controller.addToQueue(newUser);	
				loginTxt.setText("");

				
		
			}
		}
	}

	

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
		System.out.println("window closing");
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

	
