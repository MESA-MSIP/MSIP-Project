package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import com.msip.manager.MISPCore;
import com.msip.model.Person;

import javax.swing.JLabel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.GridLayout;

import net.miginfocom.swing.MigLayout;

public class ReportPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	private JComboBox<Object> studentSearch;
	private JComboBox<Object> dateTypeSearch;
	private JButton save;
	private JPanel actionPanel;
	private JPanel graphPanel;
	private JLabel lblChooseAStudent;
	private JLabel lblReportType;
	private JLabel lblStartDate;

	public ReportPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		// JLabel lblReportPanel = new JLabel("Report Panel");
		// add(lblReportPanel, BorderLayout.NORTH);
		actionPanel = new JPanel();
		add(actionPanel, BorderLayout.NORTH);
		actionPanel.setLayout(new MigLayout("", "[160px][160px][160px][160px][160px]", "[29px][29px][29px]"));

		studentSearch = new JComboBox<Object>();
		studentSearch.setBounds(0, 0, 137, 26);
		actionPanel.add(studentSearch, "cell 0 0,grow");
		//ArrayList<String> students = new ArrayList<String>();
		studentSearch.addItem("All Student's");
		for( int i = 0; i < msipCore.getStudents().size(); i++){
			studentSearch.addItem(msipCore.getStudents().get(i).getFullName());
		}

		dateTypeSearch = new JComboBox<Object>();
		String[] reportTypes = { "Hour", "Day", "Week", "Month" };
		dateTypeSearch.setBounds(0, 0, 137, 26);
		actionPanel.add(dateTypeSearch, "cell 1 0,grow");
		
		for (String date : reportTypes) {
			dateTypeSearch.addItem(date);
		}
		
		UtilDateModel startModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.year", "Year");
		p.put("text.month", "Month");
		p.put("text.today", "Today");
		JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, p);
		JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel, null);
		startDatePicker.setBounds(0, 0, 137, 26);
		actionPanel.add(startDatePicker, "cell 2 0,grow");
		
		UtilDateModel endModel = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.year", "Year");
		p2.put("text.month", "Month");
		p2.put("text.today", "Today");
		JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, p2);
		JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel, null);
		endDatePicker.setBounds(0, 0, 137, 26);
		actionPanel.add(endDatePicker);
		
		save = new JButton("Save Report");
		save.setBounds(0, 0, 137, 26);
		actionPanel.add(save, "cell 4 0,grow");
		
		lblChooseAStudent = DefaultComponentFactory.getInstance().createLabel("Choose a Student:");
		actionPanel.add(lblChooseAStudent, "cell 0 1,grow");
		
		lblReportType = new JLabel("Report Type");
		actionPanel.add(lblReportType, "cell 1 1,grow");
		
		lblStartDate = new JLabel("Start Date");
		actionPanel.add(lblStartDate, "cell 2 1,grow");
		
		JLabel lblEndDate = new JLabel("End Date");
		actionPanel.add(lblEndDate, "cell 3 1,grow");
		
		
		graphPanel = new JPanel();
		add(graphPanel, BorderLayout.CENTER);
		


	}

	/**
	 * @return the manager
	 */
	public MISPCore getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(MISPCore manager) {
		this.manager = manager;
	}
}
