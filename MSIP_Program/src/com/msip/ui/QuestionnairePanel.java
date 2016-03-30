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
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

/**
 * @author juanz
 *
 */
public class QuestionnairePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textQuestion;
	private Date startDate;
	private JTextField textValue5;
	private JTextField textValue1;
	private JTextField textValue4;
	private JTextField textValue2;
	private JTextField textValue3;
	private ArrayList<Integer> results;
	//private GraphReport pieGraph = new GraphReport("");
	private JPanel graphPanel;
	private JPanel questionPanel;
	private JButton editQButton;
	private JPanel valuePanel;
	private JLabel labelValue1;
	private JLabel labelValue2;
	private JLabel labelValue3;
	private JComponent labelValue4;
	private JLabel labelValue5;
	private MISPCore manager;

	public QuestionnairePanel(MISPCore manager) {
		
		this.manager = manager;
		
		
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		questionPanel = new JPanel();
		questionPanel.setBackground(Color.WHITE);
		add(questionPanel, BorderLayout.NORTH);

		editQButton = new JButton("Edit Question");
		editQButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		editQButton.addActionListener(this);
		questionPanel.add(editQButton);

		textQuestion = new JTextField();
		textQuestion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		textQuestion.addActionListener(this);
		questionPanel.add(textQuestion);
		textQuestion.setColumns(30);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		questionPanel.add(verticalStrut_4);

		 valuePanel = new JPanel();
		valuePanel.setBackground(Color.WHITE);
		add(valuePanel, BorderLayout.WEST);
		valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.Y_AXIS));

		 labelValue1 = new JLabel("Value 1:");
		labelValue1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelValue1.setHorizontalAlignment(SwingConstants.CENTER);
		valuePanel.add(labelValue1);

		textValue1 = new JTextField();
		valuePanel.add(textValue1);
		textValue1.setColumns(15);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut_1);

		labelValue2 = new JLabel("Value 2:");
		labelValue2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue2);

		textValue2 = new JTextField();
		valuePanel.add(textValue2);
		textValue2.setColumns(15);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut);

		 labelValue3 = new JLabel("Value 3:");
		labelValue3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue3);

		textValue3 = new JTextField();
		valuePanel.add(textValue3);
		textValue3.setColumns(15);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut_2);

		 labelValue4 = new JLabel("Value 4:");
		labelValue4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue4);

		textValue4 = new JTextField();
		valuePanel.add(textValue4);
		textValue4.setColumns(15);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setPreferredSize(new Dimension(0, 20));
		valuePanel.add(verticalStrut_3);

		 labelValue5 = new JLabel("Value 5:");
		labelValue5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		valuePanel.add(labelValue5);

		textValue5 = new JTextField();
		textValue5.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		textValue5.setBackground(new Color(255, 255, 255));
		textValue5.setHorizontalAlignment(SwingConstants.CENTER);
		valuePanel.add(textValue5);
		textValue5.setColumns(15);

		graphPanel = new JPanel();
		graphPanel.setBackground(Color.WHITE);
		add(graphPanel, BorderLayout.CENTER);

		//TODO fix the graph to make it like reportPanel
		startDate = new Date();

	}
	public void updateGraph(){
		
	}

	public ArrayList<Integer> getResult() {
		ArrayList<Integer> results = new ArrayList<Integer>();
		results = this.manager.getSurveyTable().getResults();
		return results;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(editQButton == e.getSource()){
			textQuestion.setText(GlobalUI.CLEAR);
			textQuestion.setEditable(true);
		}
		if(textQuestion == e.getSource()){
			addQuestion();
			
		}

	}
	public void addQuestion(){
		
		manager.removeAll();
		String question  = textQuestion.getText();
		manager.addQuestion(question, startDate);
		textQuestion.setText(question);
		textQuestion.setEditable(false);
	}
	

}
