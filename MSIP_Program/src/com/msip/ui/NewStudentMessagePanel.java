package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.MatteBorder;

import com.msip.manager.MISPCore;

public class NewStudentMessagePanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3443885170442642668L;
	private JPanel northPanel;
	private JButton exitButton;
	private JLabel newStudentMessage;
	private MISPCore manager;

	public NewStudentMessagePanel(final MISPCore manager) {
		this.manager = manager;
		setBackground(Color.WHITE);
		setBounds(new Rectangle(0, 0, 840, 480));
		setLayout(new BorderLayout(0, 0));
		
		northPanel = new JPanel();
		northPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		northPanel.setBackground(Color.WHITE);
		add(northPanel, BorderLayout.NORTH);
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(new Rectangle(0, 0, 120, 0));
		exitButton.addActionListener(this);
		northPanel.add(exitButton);
		
		newStudentMessage = new JLabel(GlobalUI.newStudentMessage);
		newStudentMessage.setFont(new Font("Tahoma", Font.PLAIN, 54));
		newStudentMessage.setHorizontalAlignment(SwingConstants.CENTER);
		add(newStudentMessage, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		if (exitButton == e.getSource()){
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(), GlobalUI.LoginPanel);
		}
	}
	

}
