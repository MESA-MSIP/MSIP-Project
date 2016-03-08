package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.itextpdf.text.DocumentException;
import com.msip.manager.MISPCore;
import com.msip.model.Student;
import com.msip.external.ReportMakerCSV;
import com.msip.external.ReportMakerPDF;
import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
	private int studentKnumber;
	private GeneralGraph graph = new GeneralGraph("");;
	private ReportPanel panel;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	private JFileChooser fc;

	public ReportPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		panel = this;
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		actionPanel = new JPanel();
		actionPanel.setPreferredSize(new Dimension(100, 80));
		add(actionPanel, BorderLayout.NORTH);
		actionPanel.setLayout(null);

		jCBoxStudentSearch = new JComboBox<Object>();
		jCBoxStudentSearch.setBounds(15, 40, 137, GlobalUI.TEXTBOXHEIGHT);
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
		student = "All Student's";

		jCBoxReporTypeSearch = new JComboBox<Object>();
		jCBoxReporTypeSearch.setBounds(179, 40, 137, GlobalUI.TEXTBOXHEIGHT);
		actionPanel.add(jCBoxReporTypeSearch);

		// Adds different report types to combo box.
		for (String date : reportTypes) {
			jCBoxReporTypeSearch.addItem(date);
		}
		jCBoxReporTypeSearch.addItemListener(this);

		// Creates the date picker
		startDateChooser = new JDateChooser();
		startDateChooser.setBounds(341, 40, 137, GlobalUI.TEXTBOXHEIGHT);
		actionPanel.add(startDateChooser);
		// Sets last months date.
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String lastMonthString = ZonedDateTime.now().minusMonths(1)
				.format(DateTimeFormatter.ISO_LOCAL_DATE);
		Date lastMonth = null;
		try {
			lastMonth = formatter.parse(lastMonthString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		startDateChooser.setDate(lastMonth);
		selectedStartDate = startDateChooser.getDate();
		startDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						// When the user picks a date it sets it to the text box
						// and retrieves that date.
						selectedStartDate = startDateChooser.getDate();
						updateGraph();
					}
				});

		// Creates a date picker
		endDateChooser = new JDateChooser();
		endDateChooser.setBounds(496, 40, 137, GlobalUI.TEXTBOXHEIGHT);
		actionPanel.add(endDateChooser);
		// sets current date
		endDateChooser.setDate(new Date());
		selectedEndDate = endDateChooser.getDate();
		endDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						// When the user picks a date it sets it to the text box
						// and retrieves that date.
						selectedEndDate = endDateChooser.getDate();
						updateGraph();
					}
				});

		saveReportButton = new JButton("Save Report");
		saveReportButton.setBounds(648, 40, 137, GlobalUI.BUTTONHEIGHT);
		saveReportButton.setFont(GlobalUI.LableFont);
		actionPanel.add(saveReportButton);
		saveReportButton.addActionListener(this);

		lblChooseAStudent = new JLabel("Choose a Student(s):");
		lblChooseAStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblChooseAStudent.setBounds(20, 5, 155, GlobalUI.LABELHEIGHT);
		lblChooseAStudent.setFont(GlobalUI.LableFont);
		actionPanel.add(lblChooseAStudent);

		lblReportType = new JLabel("Report Type:");
		lblReportType.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblReportType.setBounds(208, 5, 93, GlobalUI.LABELHEIGHT);
		lblReportType.setFont(GlobalUI.LableFont);
		actionPanel.add(lblReportType);

		lblStartDate = new JLabel("Start Date:");
		lblStartDate.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblStartDate.setBounds(366, 5, 76, GlobalUI.LABELHEIGHT);
		lblStartDate.setFont(GlobalUI.LableFont);
		actionPanel.add(lblStartDate);

		lblEndDate = new JLabel("End Date:");
		lblEndDate.setBounds(520, 5, 76, GlobalUI.LABELHEIGHT);
		lblEndDate.setFont(GlobalUI.LableFont);
		actionPanel.add(lblEndDate);

		// TODO starting values generalPanel

		// Date startDate = (Date) startDatePicker.getModel().getValue();
		// Date endDate = (Date) endDatePicker.getModel().getValue();
		// ArrayList<Date> dates = msipCore.getStudentDataRange(33333333,
		// startDate, endDate);
		// int graphIndex = jCBoxReporTypeSearch.getSelectedIndex();
		// graph.createGraph(graphIndex, dates);
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

		// saves data as a csv or pdf report.
		if (e.getSource().equals(saveReportButton)) {

			fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter(
					".pdf", "Report Type");
			FileNameExtensionFilter csvFilter = new FileNameExtensionFilter(
					".csv", "Report Type");
			fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
			fc.addChoosableFileFilter(pdfFilter);
			fc.addChoosableFileFilter(csvFilter);
			fc.setFileFilter(pdfFilter);
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// When a user chooses to save to a csv file it will execute the
				// saveToCSV method, otherwise it executes saveToPDF method.
				if (fc.getFileFilter().getDescription().equals(".pdf")) {
					saveToPDF(fc);
				} else {
					saveToCSV(fc);
				}
			}
		}

	}

	private void saveToCSV(JFileChooser fc) {
		System.out.println(fc.getFileFilter().getDescription());
		File yourFolder = fc.getSelectedFile();
		System.out.println(yourFolder);
		String numOfLogins = "";
		File pathToCSV = new File(yourFolder.getAbsolutePath() + File.separator
				+ "Fernando'sReport_csv.csv");

		ReportMakerCSV csv = new ReportMakerCSV(pathToCSV);
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd");
		int size = manager.getStudentDataRange(studentKnumber,
				selectedStartDate, selectedEndDate).size();
		Date[] d = manager.getStudentDataRange(studentKnumber,
				selectedStartDate, selectedEndDate).toArray(new Date[size]);
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

	private void saveToPDF(JFileChooser fc) {
		
		File yourFolder = fc.getSelectedFile();
		File pathToPDF = new File(yourFolder.getAbsolutePath() + File.separator
				+ "Meas Student Report.pdf");
		ArrayList<Date> datesInLoginTable = manager.getStudentDataRange(studentKnumber,
				selectedStartDate, selectedEndDate);
		try {
			// size of student logins in the last two weeks
			int size = manager.getStudentDataRange(studentKnumber,
					selectedStartDate, selectedEndDate).size();
			// Sorts the dates
			Collections.sort(datesInLoginTable);
			Date[] datesPresent = datesInLoginTable.toArray(new Date[size]);

			String timesPresent = Integer.toString(datesPresent.length);
			String knumber = Integer.toString(studentKnumber);
			ReportMakerPDF pdf = new ReportMakerPDF(pathToPDF);

			//When the user chose all students it would skip the if and go to the else.
			if (!student.equals("All Student's")) {
				
				pdf.addMettaData("Mesa Student Activity", "Report Subject","Virginia");
				pdf.addHeader("Mesa Student Activity", "Virginia", reportType,student);
				pdf.addStudent(student, knumber, timesPresent, datesPresent);
				pdf.closeReport();
				
			} else {
				pdf.addMettaData("Mesa Student Activity", "Report Subject","Virginia");
				pdf.addHeader("Mesa Student Activity", "Virginia", reportType,"All Student's");

				for (int i = 0; i < studentList.size(); i++) {
					ArrayList<Date> loginDates = manager.getStudentDataRange(listOfStudents.get(i).getkNumber(),
							selectedStartDate, selectedEndDate);
					//sorts the dates .
					Collections.sort(loginDates);
					//size of the student login entries
					int sizeOfStuList = loginDates.size();
					//converts student int knumber to string	
					String studentKnumber = Integer.toString(listOfStudents.get(i).getkNumber());
					//Creates an array of student login dates
					Date[] date = loginDates.toArray(new Date[sizeOfStuList]);
					//number of times a student signed in to the messa center.
					String numOfTimesPressent = Integer.toString(sizeOfStuList);
					
					pdf.addStudent(listOfStudents.get(i).getFullName(),studentKnumber, numOfTimesPressent, date);
				}
				pdf.closeReport();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
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
					student = "All Student's";
					studentKnumber = 0;
					updateGraph();
				} else {
					// otherwise it gets the index of the combo box and
					// subtracts it by one. To choose the same person from the
					// student list.
					studentListIndex = comboBoxIndex - 1;
					student = studentList.get(studentListIndex);
					studentKnumber = listOfStudents.get(studentListIndex)
							.getkNumber();
					updateGraph();
				}

			} else if (e.getSource().equals(jCBoxReporTypeSearch)) {
				int reportTypeIndex = jCBoxReporTypeSearch.getSelectedIndex();
				// prints out users choice of report.
				reportType = reportTypes[reportTypeIndex];
				updateGraph();
			}
			break;
		case ItemEvent.SELECTED:
			break;
		}
	}

	private void updateGraph() {
		ArrayList<Date> dates = manager.getStudentDataRange(studentKnumber,
				selectedStartDate, selectedEndDate);
		graph.createGraph(jCBoxReporTypeSearch.getSelectedIndex(), dates);
		BorderLayout layout = (BorderLayout) panel.getLayout();
		panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		add(graph.getGraph(), BorderLayout.CENTER);
		revalidate();
	}
}
