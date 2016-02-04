package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import com.msip.manager.MISPCore;

import javax.swing.JLabel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class ReportPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	private JComboBox<Object> studentSearch;
	private JComboBox<Object> dateTypeSearch;
	private JButton save;
	private JPanel actionPanel;
	private JPanel graphPanel;

	public ReportPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		// JLabel lblReportPanel = new JLabel("Report Panel");
		// add(lblReportPanel, BorderLayout.NORTH);

		actionPanel = new JPanel();
		add(actionPanel, BorderLayout.NORTH);

		studentSearch = new JComboBox<Object>();
		studentSearch.setBounds(0, 0, 137, 26);
		actionPanel.add(studentSearch, BorderLayout.NORTH);
		studentSearch.addItem("All Student's");

		dateTypeSearch = new JComboBox<Object>();
		String[] reportTypes = { "Hour", "Day", "Week", "Month" };
		dateTypeSearch.setBounds(0, 0, 137, 26);
		actionPanel.add(dateTypeSearch, BorderLayout.NORTH);
		
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
		actionPanel.add(startDatePicker, BorderLayout.NORTH);
		
		UtilDateModel endModel = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.year", "Year");
		p2.put("text.month", "Month");
		p2.put("text.today", "Today");
		JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, p2);
		JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel, null);
		endDatePicker.setBounds(0, 0, 137, 26);
		actionPanel.add(endDatePicker, BorderLayout.NORTH);
		
		save = new JButton("Save Report");
		save.setBounds(0, 0, 137, 26);
		actionPanel.add(save, BorderLayout.NORTH);
		
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
