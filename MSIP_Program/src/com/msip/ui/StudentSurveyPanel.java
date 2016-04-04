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
import java.util.ArrayList;
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
import javax.swing.JLabel;

public class StudentSurveyPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton skipButton;
	private JButton submitButton;
	private JRadioButton rbutton1, rbutton2, rbutton3, rbutton4, rbutton5;
	private JTextPane surveyQ;
	private MISPCore manager;
	private ButtonGroup groupChoices;
	private SurveyTable surveyTable;
	private JLabel labelValue2;
	private JLabel labelValue3;
	private JLabel labelValue4;
	private JLabel labelValue5;
	private JLabel labelValue1;
	
	public StudentSurveyPanel(final MISPCore manager, Timer timer)
	{
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setLayout(null);
		
		this.manager = manager;
		surveyTable = this.manager.getSurveyTable();
		
		skipButton = new JButton("Skip Survey\r\n");
		skipButton.setBounds(235, 339, 145, 46);
		skipButton.addActionListener(this);
		add(skipButton);

		submitButton = new JButton("Submit");
		submitButton.setBounds(406, 339, 145, 46);
		submitButton.addActionListener(this);
		add(submitButton);

		rbutton1 = new JRadioButton("");
		rbutton1.setBackground(Color.WHITE);
		rbutton1.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		rbutton1.setBounds(8, 197, 132, 85);
		add(rbutton1);

		rbutton2 = new JRadioButton("");
		rbutton2.setBackground(Color.WHITE);
		rbutton2.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton2.setBounds(176, 197, 132, 85);
		add(rbutton2);

		rbutton3 = new JRadioButton("");
		rbutton3.setBackground(Color.WHITE);
		rbutton3.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton3.setBounds(339, 197, 132, 85);
		add(rbutton3);

		rbutton4 = new JRadioButton("");
		rbutton4.setBackground(Color.WHITE);
		rbutton4.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton4.setBounds(498, 197, 132, 85);
		add(rbutton4);

		rbutton5 = new JRadioButton("");
		rbutton5.setBackground(Color.WHITE);
		rbutton5.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton5.setBounds(658, 197, 132, 85);
		add(rbutton5);

		surveyQ = new JTextPane();
		surveyQ.setBackground(new Color(255, 255, 255));
		surveyQ.setContentType("text/plain/");
		//TODO should not add a question if their is no question
		//surveyQ.setText(this.manager.getQuestion());
	
		StyledDocument doc = surveyQ.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		surveyQ.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		surveyQ.setEditable(false);
		surveyQ.setBounds(10, 0, 780, 150);
		add(surveyQ);
	
		//Create a Button Group
		groupChoices = new ButtonGroup();
		groupChoices.add(rbutton1);
		groupChoices.add(rbutton2);
		groupChoices.add(rbutton3);
		groupChoices.add(rbutton4);
		groupChoices.add(rbutton5);
		
		labelValue1 = new JLabel("Value1");
		labelValue1.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue1.setLabelFor(rbutton1);
		labelValue1.setBounds(47, 172, 56, 16);
		add(labelValue1);
		
		labelValue2 = new JLabel("Value2");
		labelValue2.setLabelFor(rbutton2);
		labelValue2.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue2.setBounds(217, 172, 56, 16);
		add(labelValue2);
		
		labelValue3 = new JLabel("Value3");
		labelValue3.setLabelFor(rbutton3);
		labelValue3.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue3.setBounds(380, 172, 56, 16);
		add(labelValue3);
		
		labelValue4 = new JLabel("Value4");
		labelValue4.setLabelFor(rbutton4);
		labelValue4.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue4.setBounds(535, 172, 56, 16);
		add(labelValue4);
		
		labelValue5 = new JLabel("Value5");
		labelValue5.setLabelFor(rbutton5);
		labelValue5.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue5.setBounds(693, 172, 56, 16);
		add(labelValue5);
	}
	
	
	public JLabel getLabelValue2() {
		return labelValue2;
	}


	public void setLabelValue2(JLabel labelValue2) {
		this.labelValue2 = labelValue2;
	}


	public JLabel getLabelValue3() {
		return labelValue3;
	}


	public void setLabelValue3(JLabel labelValue3) {
		this.labelValue3 = labelValue3;
	}


	public JLabel getLabelValue4() {
		return labelValue4;
	}


	public void setLabelValue4(JLabel labelValue4) {
		this.labelValue4 = labelValue4;
	}


	public JLabel getLabelValue5() {
		return labelValue5;
	}


	public void setLabelValue5(JLabel labelValue5) {
		this.labelValue5 = labelValue5;
	}


	public JLabel getLabelValue1() {
		return labelValue1;
	}


	public void setLabelValue1(JLabel labelValue1) {
		this.labelValue1 = labelValue1;
	}


	public void setButtonValue(int choice, String buttonValue){
		switch(choice){
		case 1:{
			labelValue1.setText(buttonValue);
			break;
		}
		case 2:{
			labelValue2.setText(buttonValue);
			break;
		}
		case 3:{
			labelValue3.setText(buttonValue);
			break;
		}
		case 4:{
			labelValue4.setText(buttonValue);
			break;
		}
		case 5:{
			labelValue5.setText(buttonValue);
			break;
		}
		default: break;
		}
	}
	public void clearRButtons(){
		groupChoices.clearSelection();
	}
	public void setQuestion(String question){
		surveyQ.setText(question);
	}
	public void actionPerformed(ActionEvent e)
	{
		if (skipButton == e.getSource()) {
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(), GlobalUI.LoginPanel);
		} else if (submitButton == e.getSource()) {
			// TODO log the information to the surveyTable
			if (rbutton1.isSelected()) {
				this.manager.addResults(GlobalUI.responseOne);
			}

			else {
				if (rbutton2.isSelected()) {
					manager.addResults(GlobalUI.responseTwo);
				}

				else {
					if (rbutton3.isSelected()) {
						manager.addResults(GlobalUI.responseThree);
					}

					else {
						if (rbutton4.isSelected()) {
							manager.addResults(GlobalUI.responseFour);
						}

						else {
							if (rbutton5.isSelected()) {
								manager.addResults(GlobalUI.responseFive);
							}

						}

					}

				}

			}
			//Revert choice back to default
			clearRButtons();
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
