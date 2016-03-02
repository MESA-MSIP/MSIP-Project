/**
 * 
 */
package com.msip.ui;

import javax.swing.JPanel;

import com.msip.db.SurveyTable;
import com.msip.manager.MISPCore;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Font;

/**
 * @author juanz
 *
 */
public class QuestionnairePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textQuestion;
	private SurveyTable surveyTable;
	private Date startDate;
	private JTextField textValue5;
	private JTextField textValue1;
	private JTextField textValue4;
	private JTextField textValue2;
	private JTextField textValue3;
	private ArrayList<Integer> results;
	

	public QuestionnairePanel(MISPCore msipCore) 
	{
		
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);
		
		JButton editQButton = new JButton("Edit Question");
		panel.add(editQButton);
		
		textQuestion = new JTextField();
		textQuestion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textQuestion);
		textQuestion.setColumns(30);
		
		Component verticalStrut_4 = Box.createVerticalStrut(50);
		panel.add(verticalStrut_4);
		
		JPanel valuePanel = new JPanel();
		valuePanel.setBackground(Color.WHITE);
		add(valuePanel, BorderLayout.WEST);
		valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));
		
		JLabel labelValue1 = new JLabel("Value 1:");
		labelValue1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelValue1.setHorizontalAlignment(SwingConstants.CENTER);
		valuePanel.add(labelValue1);
		
		textValue1 = new JTextField();
		valuePanel.add(textValue1);
		textValue1.setColumns(15);
		
		Component verticalStrut_1 = Box.createVerticalStrut(50);
		valuePanel.add(verticalStrut_1);
		
		JLabel labelValue2 = new JLabel("Value 2:");
		labelValue2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue2);
		
		textValue2 = new JTextField();
		valuePanel.add(textValue2);
		textValue2.setColumns(15);
		
		Component verticalStrut = Box.createVerticalStrut(50);
		valuePanel.add(verticalStrut);
		
		JLabel labelValue3 = new JLabel("Value 3:");
		labelValue3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue3);
		
		textValue3 = new JTextField();
		valuePanel.add(textValue3);
		textValue3.setColumns(15);
		
		Component verticalStrut_2 = Box.createVerticalStrut(50);
		valuePanel.add(verticalStrut_2);
		
		JLabel labelValue4 = new JLabel("Value 4:");
		labelValue4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue4);
		
		textValue4 = new JTextField();
		valuePanel.add(textValue4);
		textValue4.setColumns(15);
		
		Component verticalStrut_3 = Box.createVerticalStrut(50);
		valuePanel.add(verticalStrut_3);
		
		JLabel labelValue5 = new JLabel("Value 5:");
		labelValue5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue5);
		
		textValue5 = new JTextField();
		textValue5.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		textValue5.setBackground(new Color(255, 255, 255));
		textValue5.setHorizontalAlignment(SwingConstants.CENTER);
		valuePanel.add(textValue5);
		textValue5.setColumns(15);
		//
		ArrayList<Integer> test = new ArrayList<Integer>();
		test = getResult();
		for (int i = 0; i < test.size(); i++)
		{
			System.out.println("Number " + i + ": " + test.get(i));
		}
		
		
		surveyTable = new SurveyTable();
		//surveyTable.addQuestion("Rate the MESA Center.", startDate);
		
	}
	public ArrayList<Integer> getResult()
	{
		//ArrayList<Integer> results = surveyTable.getResults();
		//Hard coded results for now
		
		
		results = new ArrayList<Integer>();
		results.add(0, 12);
		results.add(1, 13);
		results.add(2, 7);
		results.add(3, 10);
		results.add(4, 8);
		return results;
		
	}
	
	
	public void addQuestion()
	{
		surveyTable.addQuestion("Rate the Available Tutors", startDate);
	}

}
