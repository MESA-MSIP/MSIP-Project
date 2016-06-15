package com.msip.ui;

import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.msip.db.SurveyTableLables;
import com.msip.manager.MISPCore;

import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class StudentSurveyPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton skipButton;
	private JButton submitButton;
	private JRadioButton rbutton1, rbutton2, rbutton3, rbutton4, rbutton5;
	private JTextPane surveyQ;
	private MISPCore manager;
	private ButtonGroup groupChoices;
	private JLabel labelValue2;
	private JLabel labelValue3;
	private JLabel labelValue4;
	private JLabel labelValue5;
	private JLabel labelValue1;
	private ImageIcon yellowStarIcon;
	private ImageIcon clearStarIcon;
	private SurveyTableLables surveyTableLables;
	private Insets m;
	
	public StudentSurveyPanel(final MISPCore manager, Timer timer)
	{
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setLayout(null);
		m = new Insets(0, 30, 0, 30);
		
		
		this.manager = manager;
		surveyTableLables = this.manager.getSurveyTableLables();
		
		skipButton = new JButton("Skip Survey\r\n");
		skipButton.setBounds(218, 312, 145, 46);
		skipButton.addActionListener(this);
		add(skipButton);

		submitButton = new JButton("Submit");
		submitButton.setBounds(412, 312, 145, 46);
		submitButton.addActionListener(this);
		add(submitButton);
		
		yellowStarIcon = new ImageIcon(StudentSurveyPanel.class.getResource("/images/yellowStar.png"));
		clearStarIcon = new ImageIcon(StudentSurveyPanel.class.getResource("/images/starEmpty.png"));
		
		
		

		rbutton1 = new JRadioButton("");
		rbutton1.setBackground(Color.WHITE);
		rbutton1.setIcon(clearStarIcon);
		rbutton1.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		rbutton1.setBounds(0, 197, 132, 85);
		rbutton1.addActionListener(this);
		add(rbutton1);


		rbutton2 = new JRadioButton("");
		rbutton2.setBackground(Color.WHITE);
		rbutton2.setIcon(clearStarIcon);
		rbutton2.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton2.setBounds(160, 197, 132, 85);
		rbutton2.addActionListener(this);
		add(rbutton2);

		rbutton3 = new JRadioButton("");
		rbutton3.setBackground(Color.WHITE);
		rbutton3.setIcon(clearStarIcon);
		rbutton3.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton3.setBounds(320, 197, 132, 85);
		rbutton3.addActionListener(this);
		add(rbutton3);

		rbutton4 = new JRadioButton("");
		rbutton4.setBackground(Color.WHITE);
		rbutton4.setIcon(clearStarIcon);
		rbutton4.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton4.setBounds(480, 197, 132, 85);
		rbutton4.addActionListener(this);
		add(rbutton4);

		rbutton5 = new JRadioButton("");
		rbutton5.setBackground(Color.WHITE);
		rbutton5.setIcon(clearStarIcon);
		rbutton5.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton5.setBounds(640, 197, 132, 85);
		rbutton5.addActionListener(this);
		add(rbutton5);


		
		surveyQ = new JTextPane();
		surveyQ.setBackground(new Color(255, 255, 255));
		surveyQ.setFont(new Font("Segoe UI", Font.PLAIN, 45));
		surveyQ.setEditable(false);
		surveyQ.setBounds(10, 0, 780, 150);
		surveyQ.setMargin(m);
		
		StyledDocument doc = surveyQ.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		add(surveyQ);
	
		//Create a Button Group
		groupChoices = new ButtonGroup();
		groupChoices.add(rbutton1);
		groupChoices.add(rbutton2);
		groupChoices.add(rbutton3);
		groupChoices.add(rbutton4);
		groupChoices.add(rbutton5);
		
		labelValue1 = new JLabel("Value1");
		labelValue1.setLabelFor(rbutton1);
		labelValue1.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue1.setBounds(7, 170, 120, 16);
		add(labelValue1);
		
		labelValue2 = new JLabel("Value2");
		labelValue2.setLabelFor(rbutton2);
		labelValue2.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue2.setBounds(167, 170, 120, 16);
		add(labelValue2);
		
		labelValue3 = new JLabel("Value3");
		labelValue3.setLabelFor(rbutton3);
		labelValue3.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue3.setBounds(327, 170, 120, 16);
		add(labelValue3);
		
		labelValue4 = new JLabel("Value4");
		labelValue4.setLabelFor(rbutton4);
		labelValue4.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue4.setBounds(487, 170, 120, 16);
		add(labelValue4);
		
		labelValue5 = new JLabel("Value5");
		labelValue5.setLabelFor(rbutton5);
		labelValue5.setHorizontalAlignment(SwingConstants.CENTER);
		labelValue5.setBounds(647, 170, 120, 16);
		add(labelValue5);
		//
		if(surveyTableLables.getValueLables().size() != 0){
			setTextFromLabelTables();
		}
		
	
	}
	
	public void setClearIcon(JRadioButton button){
		button.setIcon(clearStarIcon);
	}
	public void setSelectedIcon(JRadioButton button){
		button.setIcon(yellowStarIcon);
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
	public void setTextFromLabelTables(){
		ArrayList<String> labels = new ArrayList<String>();
		labels = surveyTableLables.getValueLables();
		labelValue1.setText(labels.get(0));
		labelValue2.setText(labels.get(1));
		labelValue3.setText(labels.get(2));
		labelValue4.setText(labels.get(3));
		labelValue5.setText(labels.get(4));
		
	}
	public void clearRButtons(){
		groupChoices.clearSelection();
		rbutton1.setIcon(clearStarIcon);
		rbutton2.setIcon(clearStarIcon);
		rbutton3.setIcon(clearStarIcon);
		rbutton4.setIcon(clearStarIcon);
		rbutton5.setIcon(clearStarIcon);

	}
	public void setQuestion(String question){
		surveyQ.setText(question);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(rbutton1 == e.getSource()){
			setSelectedIcon(rbutton1);
			setClearIcon(rbutton2);
			setClearIcon(rbutton3);
			setClearIcon(rbutton4);
			setClearIcon(rbutton5);
		}
		if(rbutton2 == e.getSource()){
			setClearIcon(rbutton1);
			setSelectedIcon(rbutton2);
			setClearIcon(rbutton3);
			setClearIcon(rbutton4);
			setClearIcon(rbutton5);
		}
		if(rbutton3 == e.getSource()){
			setClearIcon(rbutton1);
			setClearIcon(rbutton2);
			setSelectedIcon(rbutton3);
			setClearIcon(rbutton4);
			setClearIcon(rbutton5);
		}
		if(rbutton4 == e.getSource()){
			setClearIcon(rbutton1);
			setClearIcon(rbutton2);
			setClearIcon(rbutton3);
			setSelectedIcon(rbutton4);
			setClearIcon(rbutton5);
		}
		if(rbutton5 == e.getSource()){
			setClearIcon(rbutton1);
			setClearIcon(rbutton2);
			setClearIcon(rbutton3);
			setClearIcon(rbutton4);
			setSelectedIcon(rbutton5);
		}
		
		if (skipButton == e.getSource()) {
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(), GlobalUI.LoginPanel);
		} else if (submitButton == e.getSource()) {
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
