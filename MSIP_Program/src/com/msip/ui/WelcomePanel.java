package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

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
	public WelcomePanel(final MISPCore manager) 
	{
		setBackground(Color.WHITE);
		this.manager = manager;
		setBounds(new Rectangle(0, 0, 800, 480));
		setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);
		
		JButton logOutbtn = new JButton("New button");
		logOutbtn.addActionListener(this);
		panel.add(logOutbtn);
		
		JLabel messageToast = new JLabel("Message");
		messageToast.setFont(new Font("Tahoma", Font.PLAIN, 40));
		messageToast.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(messageToast);
		
		JLabel animTimer = new JLabel("Timer\r\n");
		panel.add(animTimer);
		
		JPanel welcomeCards = new JPanel();
		welcomeCards.setLayout(new CardLayout(0, 0));
		
		
		//Construct Cards
		JPanel StudentSurveyPanel = new StudentSurveyPanel(manager);
		JPanel NotificationsPanel = new NotificationsPanel(manager);
		
		//Add the Cards to the JPanel
		
		welcomeCards.add(StudentSurveyPanel, GlobalUI.StudentSurveyPanel);
		welcomeCards.add(NotificationsPanel, GlobalUI.NotificationsPanel);
		
		add(welcomeCards, BorderLayout.CENTER);
		
		
		
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
			
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(),GlobalUI.LoginPanel);
		
	}
}
