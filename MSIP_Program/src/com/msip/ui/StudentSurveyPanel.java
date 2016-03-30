package com.msip.ui;

import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.msip.db.SurveyTable;
import com.msip.manager.MISPCore;

import javax.swing.JRadioButton;
import javax.swing.JTextPane;

public class StudentSurveyPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton skipButton;
	private JButton submitButton;
	private JRadioButton rbutton1, rbutton2, rbutton3, rbutton4, rbutton5;
	private JTextPane surveyQ;
	private MISPCore manager;
	private SurveyTable surveyTable;
	private ButtonGroup groupChoices;
	
	public StudentSurveyPanel(final MISPCore manager, Timer timer)
	{
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setLayout(null);
		
		this.manager = manager;
		
		skipButton = new JButton("Skip Survey\r\n");
		skipButton.setBounds(235, 339, 145, 46);
		skipButton.addActionListener(this);
		add(skipButton);

		submitButton = new JButton("Submit");
		submitButton.setBounds(406, 339, 145, 46);
		submitButton.addActionListener(this);
		add(submitButton);

		rbutton1 = new JRadioButton("\r\nValue 1:");
		rbutton1.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		rbutton1.setBounds(8, 197, 132, 85);
		add(rbutton1);

		rbutton2 = new JRadioButton("Value 2:");
		rbutton2.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton2.setBounds(176, 197, 132, 85);
		add(rbutton2);

		rbutton3 = new JRadioButton("Value 3:");
		rbutton3.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton3.setBounds(339, 197, 132, 85);
		add(rbutton3);

		rbutton4 = new JRadioButton("Value 4:");
		rbutton4.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton4.setBounds(498, 197, 132, 85);
		add(rbutton4);

		rbutton5 = new JRadioButton("Value 5:");
		rbutton5.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton5.setBounds(658, 197, 132, 85);
		add(rbutton5);

		surveyQ = new JTextPane();
		surveyQ.setBackground(new Color(255, 255, 255));
		surveyQ.setContentType("text/plain/");
		surveyQ.setText(this.manager.getSurveyTable().getQuestion());
		StyledDocument doc = surveyQ.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		surveyQ.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		surveyQ.setBounds(8, 25, 780, 150);
		add(surveyQ);
	
		//Create a Button Group
		groupChoices = new ButtonGroup();
		groupChoices.add(rbutton1);
		groupChoices.add(rbutton2);
		groupChoices.add(rbutton3);
		groupChoices.add(rbutton4);
		groupChoices.add(rbutton5);

		
		addComponentListener(new ComponentAdapter() {

			public void componentShown(ComponentEvent e) {

				
		}});

	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (skipButton == e.getSource()) {
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(), GlobalUI.LoginPanel);
		} else if (submitButton == e.getSource()) {
			// TODO log the information to the surveyTable
			if (rbutton1.isSelected()) {
				surveyTable.addResults(GlobalUI.responseOne);
			}

			else {
				if (rbutton2.isSelected()) {
					surveyTable.addResults(GlobalUI.responseTwo);
				}

				else {
					if (rbutton3.isSelected()) {
						surveyTable.addResults(GlobalUI.responseThree);
					}

					else {
						if (rbutton4.isSelected()) {
							surveyTable.addResults(GlobalUI.responseFour);
						}

						else {
							if (rbutton5.isSelected()) {
								surveyTable.addResults(GlobalUI.responseFive);
							}

						}

					}

				}

			}
			//Revert choice back to default
			rbutton1.setSelected(false);
			rbutton2.setSelected(false);
			rbutton3.setSelected(false);
			rbutton4.setSelected(false);
			rbutton5.setSelected(false);
			//Show the LoginPanel
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(),GlobalUI.LoginPanel);
				}
		else
		{
			return;
		}
	}
}
