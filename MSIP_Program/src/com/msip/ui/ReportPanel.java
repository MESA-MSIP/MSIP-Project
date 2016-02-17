package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.itextpdf.text.DocumentException;
import com.msip.manager.MISPCore;
import com.msip.model.Student;
import com.msip.external.ReportMakerCSV;
import com.msip.external.ReportMakerPDF;

import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;

import javax.swing.Box;

public class ReportPanel extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	private JComboBox<Object> jCBoxStudentSearch;
	private JComboBox<Object> jCBoxReporTypeSearch;
	private JButton saveReportButton;
	private JPanel actionPanel;
	private JLabel lblChooseAStudent;
	private JLabel lblReportType;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private ArrayList<String> studentList = new ArrayList<String>();
	private ArrayList<Student> listOfStudents = new ArrayList<Student>();
	private String[] reportTypes = { "Hours", "Days", "Weeks", "Months" };
	private String student = "";
	private String reportType = "";
	private Date selectedStartDate = null;
	private Date selectedEndDate = null;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component verticalStrut;
	private JDatePickerImpl endDatePicker;
	private JDatePanelImpl endDatePanel;
	private JDatePickerImpl startDatePicker;
	private JDatePanelImpl startDatePanel;
	private int studentKnumber;
	private boolean isOn;
	private GeneralGraph graph = new GeneralGraph("");;
	private ReportPanel panel;

	public ReportPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		panel = this;
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		actionPanel = new JPanel();
		actionPanel.setPreferredSize(new Dimension(100, 75));
		add(actionPanel, BorderLayout.NORTH);
		actionPanel.setLayout(null);

		jCBoxStudentSearch = new JComboBox<Object>();
		jCBoxStudentSearch.setBounds(15, 40, 137, 26);
		actionPanel.add(jCBoxStudentSearch);

		// Your able to choose all students. This sets All Students as the
		// default and is at index 0 of the combo box list
		jCBoxStudentSearch.addItem("All Student's");

		// Adds all students to combo box.
		for (int i = 0; i < msipCore.getStudents().size(); i++) {
			// Adds a student to a String Array
			studentList.add(msipCore.getStudents().get(i).getFullName());
			// Adds a student to a Student Array
			listOfStudents.add(msipCore.getStudents().get(i));
			// Adds a student to combo box.
			jCBoxStudentSearch.addItem(studentList.get(i));

		}
		jCBoxStudentSearch.addItemListener(this);
		// sets all students as default.
		studentKnumber = 0;

		jCBoxReporTypeSearch = new JComboBox<Object>();
		jCBoxReporTypeSearch.setBounds(179, 40, 137, 26);
		actionPanel.add(jCBoxReporTypeSearch);

		// Adds different report types to combo box.
		for (String date : reportTypes) {
			jCBoxReporTypeSearch.addItem(date);
		}
		jCBoxReporTypeSearch.addItemListener(this);

		// Creates the date picker
		UtilDateModel startModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.month", "Month");
		p.put("text.today", "Today");
		p.put("text.year", "Year");
		startDatePanel = new JDatePanelImpl(startModel, p);
		startDatePicker = new JDatePickerImpl(startDatePanel, new DateComponentFormatter());
		actionPanel.add(startDatePicker);
		// sets last months date by default.
		startModel.setDate(startModel.getYear() - 1, startModel.getMonth(), startModel.getDay());
		startModel.setSelected(true);
		startDatePicker.setBounds(341, 40, 137, 26);
		startDatePicker.addActionListener(this);
		// sets last months date to this global variable. If not we will get a
		// null error.
		selectedStartDate = startModel.getValue();
		isOn = false;

		UtilDateModel endModel = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.year", "Year");
		p2.put("text.month", "Month");
		p2.put("text.today", "Today");
		endDatePanel = new JDatePanelImpl(endModel, p2);
		endDatePicker = new JDatePickerImpl(endDatePanel, new DateComponentFormatter());
		endDatePicker.setBounds(496, 40, 137, 26);
		actionPanel.add(endDatePicker);
		// set current date by default
		endModel.setSelected(true);
		endDatePicker.addActionListener(this);
		// sets current date to this global variable. If not we will get a null
		// error.
		selectedEndDate = endModel.getValue();

		saveReportButton = new JButton("Save Report");
		saveReportButton.setBounds(648, 40, 137, 26);
		actionPanel.add(saveReportButton);
		saveReportButton.addActionListener(this);

		lblChooseAStudent = new JLabel("Choose a Student(s):");
		lblChooseAStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblChooseAStudent.setBounds(25, 16, 155, 20);
		actionPanel.add(lblChooseAStudent);

		lblReportType = new JLabel("Report Type:");
		lblReportType.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblReportType.setBounds(208, 16, 93, 20);
		actionPanel.add(lblReportType);

		lblStartDate = new JLabel("Start Date:");
		lblStartDate.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblStartDate.setBounds(366, 16, 76, 20);
		actionPanel.add(lblStartDate);

		lblEndDate = new JLabel("End Date:");
		lblEndDate.setBounds(520, 16, 76, 20);
		actionPanel.add(lblEndDate);

		// TODO starting values generalPanel
		
		//Date startDate = (Date) startDatePicker.getModel().getValue();
		//Date endDate = (Date) endDatePicker.getModel().getValue();
		//ArrayList<Date> dates = msipCore.getStudentDataRange(33333333, startDate, endDate);
		//int graphIndex = jCBoxReporTypeSearch.getSelectedIndex();
		//graph.createGraph(graphIndex, dates);
		// add(graph.getGraph(), BorderLayout.CENTER);
		add(new JPanel(), BorderLayout.CENTER);

		horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(3, 0));
		add(horizontalStrut, BorderLayout.WEST);

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(3, 0));
		add(horizontalStrut_1, BorderLayout.EAST);

		verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(5, 5));
		add(verticalStrut, BorderLayout.SOUTH);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateGraph();
			}
		});


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

	@Override
	public void actionPerformed(ActionEvent e) {
		// Lets you choose the start date with out the end date choosing the
		// same date as the start date
		if (isOn == false) {
			// returns the chosen date.
			selectedStartDate = (Date) startDatePicker.getModel().getValue();
			System.out.println(selectedStartDate);
			isOn = true;
			updateGraph();

		} else {

			selectedEndDate = (Date) endDatePicker.getModel().getValue();
			System.out.println(selectedEndDate);
			isOn = false;
			updateGraph();

		}
		// saves data as a csv or pdf report.
		if (e.getSource().equals(saveReportButton)) {

			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			FileNameExtensionFilter csvFilter = new FileNameExtensionFilter(".csv", "Report Type");
			FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter(".pdf", "Report Type");
			fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
			fc.addChoosableFileFilter(csvFilter);
			fc.addChoosableFileFilter(pdfFilter);
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// When a user chooses to save to a csv file it will execute the
				// saveToCSV method, otherwise it executes saveToPDF method.
				if (fc.getFileFilter().getDescription().equals(".csv")) {

					saveToCSV(fc);
				} else {
					saveToPDF(fc);
				}
			}
		}

	}

	public void saveToCSV(JFileChooser fc) {
		System.out.println(fc.getFileFilter().getDescription());

		File yourFolder = fc.getSelectedFile();
		System.out.println(yourFolder);
		String numOfLogins = "";
		File pathToCSV = new File(yourFolder.getAbsolutePath() + File.separator + "Fernando'sReport_csv.csv");

		ReportMakerCSV csv = new ReportMakerCSV(pathToCSV);
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd");
		int size = manager.getStudentDataRange(studentKnumber, selectedStartDate, selectedEndDate).size();
		Date[] d = manager.getStudentDataRange(studentKnumber, selectedStartDate, selectedEndDate)
				.toArray(new Date[size]);
		// for (int i = 0; i < d.length; i++) {
		// d[i] = new Date();
		// }

		String[] s = new String[size];
		for (int i = 0; i < s.length; i++) {
			s[i] = str.format(d[i]);
		}
		numOfLogins = Integer.toString(s.length);
		csv.addHeader("Name,times Present,Dates present");
		csv.CreateCSVFile(student, numOfLogins, s);

	}

	public void saveToPDF(JFileChooser fc) {
		File yourFolder = fc.getSelectedFile();
		File pathToPDF = new File(yourFolder.getAbsolutePath() + File.separator + "Fernando'sReport.pdf");
		try {
			int size = manager.getStudentDataRange(studentKnumber, selectedStartDate, selectedEndDate).size();
			Date[] d = manager.getStudentDataRange(studentKnumber, selectedStartDate, selectedEndDate)
					.toArray(new Date[size]);

			String timesPresent = Integer.toString(d.length);
			String knumber = Integer.toString(studentKnumber);

			ReportMakerPDF pdf = new ReportMakerPDF(pathToPDF);
			pdf.addMettaData("Report Title", "Report Subject", "Juan Zepeda");
			pdf.addHeader("Report Title", student, reportType, "John Smith");
			pdf.addStudent(student, knumber, timesPresent, d);
			pdf.closeReport();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		switch (e.getStateChange()) {
		case ItemEvent.DESELECTED:
			if (e.getSource().equals(jCBoxStudentSearch)) {
				// returns the users choice as an index based on the combo box
				// list.
				int comboBoxIndex = jCBoxStudentSearch.getSelectedIndex();
				int studentListIndex = 0;
				// When ever user chooses All students it prints out all of the
				// students.
				if (comboBoxIndex == 0) {
					for (int i = 0; i < studentList.size(); i++) {
						student = studentList.get(i);
						studentKnumber = listOfStudents.get(i).getkNumber();
						System.out.println(student);
						System.out.println(studentKnumber);

					}
					studentKnumber = 0;
					updateGraph();
				} else {
					// otherwise it gets the index of the combo box and
					// subtracts it by one. To choose the same person from the
					// student list.
					studentListIndex = comboBoxIndex - 1;
					student = studentList.get(studentListIndex);
					studentKnumber = listOfStudents.get(studentListIndex).getkNumber();
					System.out.println(student);
					System.out.println(studentKnumber);
					updateGraph();
				}

			} else if (e.getSource().equals(jCBoxReporTypeSearch)) {
				int reportTypeIndex = jCBoxReporTypeSearch.getSelectedIndex();
				// prints out users choice of report.
				reportType = reportTypes[reportTypeIndex];
				System.out.println(reportType);
				updateGraph();
			}
			break;
		case ItemEvent.SELECTED:
			break;
		}
	}

	private void updateGraph() {
		ArrayList<Date> dates = manager.getStudentDataRange(studentKnumber, selectedStartDate, selectedEndDate);
		graph.createGraph(jCBoxReporTypeSearch.getSelectedIndex(), dates);
		BorderLayout layout = (BorderLayout) panel.getLayout();
		panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		add(graph.getGraph(), BorderLayout.CENTER);
		revalidate();
	}
}
