package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.msip.manager.MISPCore;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class WelcomePanel extends JPanel implements ActionListener
{
	private MISPCore manager;
	private JButton logOutbtn;
	private Timer newTimer;
	private JPanel panel;
	private JLabel messageToast;
	private JLabel animTimer;
	private JPanel welcomeCards;
	
	
	
	public WelcomePanel(final MISPCore manager) 
	{
		
		//TODO componentListener for timer
		
		setBackground(Color.WHITE);
		this.manager = manager;
		setBounds(new Rectangle(0, 0, 800, 480));
		setLayout(new BorderLayout(0, 0));
		
		
		panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);
		
		logOutbtn = new JButton("New button");
		logOutbtn.addActionListener(this);
		panel.add(logOutbtn);
		
		messageToast = new JLabel("Message");
		messageToast.setFont(new Font("Tahoma", Font.PLAIN, 40));
		messageToast.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(messageToast);
		
		animTimer = new JLabel("Timer\r\n");
		panel.add(animTimer);
		
		welcomeCards = new JPanel();
		welcomeCards.setLayout(new CardLayout(0, 0));
		
		//TODO Random # by Chance
			//70% see notifications, 30% will see survey
			//below .7 / .3
		
		//Construct Cards
		JPanel StudentSurveyPanel = new StudentSurveyPanel(manager);
		JPanel NotificationsPanel = new NotificationsPanel(manager);
		
		//Add the Cards to the JPanel
		
		welcomeCards.add(StudentSurveyPanel, GlobalUI.StudentSurveyPanel);
		welcomeCards.add(NotificationsPanel, GlobalUI.NotificationsPanel);
		
		add(welcomeCards, BorderLayout.CENTER);
		
		
		//Set up A Auto Time-Out for 6 Seconds
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					public void run() 
					{
						CardLayout cl = (CardLayout) WelcomePanel.this.manager.getCards().getLayout();
						cl.show(WelcomePanel.this.manager.getCards(),GlobalUI.LoginPanel);
					}
				}, 6000L);
				
			}
		});
	}
	public void setMessage(String message)
	{
		messageToast.setText(message);
		return;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
			
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(),GlobalUI.LoginPanel);
		
	}
}
