package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.itextpdf.text.DocumentException;
import com.msip.db.Global;
import com.msip.manager.MISPCore;
import com.msip.model.Student;
import com.msip.external.ReportMakerCSV;
import com.msip.external.ReportMakerPDF;
import com.toedter.calendar.JDateChooser;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.TilePane;
import org.jfree.chart.ChartUtilities;

public class ReportPanel extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	// Drop down box. Studnet List.
	private JComboBox<Object> jCBoxStudentSearch;
	private JComboBox<Object> jCBoxReporTypeSearch;
	private JButton saveReportButton;
	private JFXPanel actionPanel;
	// Text labels
	private JLabel lblChooseAStudent;
	private JLabel lblReportType;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	// List of all students.
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
	private DatePicker dBegin;
	private DatePicker dEnd;
	private JFileChooser fc;
	private AdminToolsPanel adminToolsPanel;

	public ReportPanel(MISPCore msipCore, AdminToolsPanel adminToolsPanel) {
		this.setManager(msipCore);
		this.setAdminToolsPanel(adminToolsPanel);
		panel = this;
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		actionPanel = new JFXPanel();
		actionPanel.setPreferredSize(new Dimension(100, 80));
		actionPanel.setBackground(Color.white);
		add(actionPanel, BorderLayout.NORTH);
		actionPanel.setLayout(null);

		jCBoxStudentSearch = new JComboBox<Object>();
		jCBoxStudentSearch.setBounds(15, 40, 137, GlobalUI.TEXTBOXHEIGHT);
		actionPanel.add(jCBoxStudentSearch);
		jCBoxStudentSearch.addItem("All Student's");

		// Your able to choose all students. This sets All Students as the
		// default and is at index 0 of the combo box list
		// jCBoxStudentSearch.addItem("All Student's");

		// Adds all students to combo box.
		updateStudentCBox();
		// Collections.sort(listOfStudents);
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

		//Tile pane for Date Pickers
		TilePane t = new TilePane();
		t.setLayoutX(341);
		t.setLayoutY(40);
		t.setHgap(18);

		//Begin Date
		dBegin = new DatePicker();
		dBegin.setPrefSize(137, GlobalUI.TEXTBOXHEIGHT);
		dBegin.setStyle("-fx-font-size: 0.65em;");
		t.getChildren().add(dBegin);
		String lastMonthString = ZonedDateTime.now().minusMonths(1)
				.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		dBegin.setValue(LocalDate.parse(lastMonthString, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		selectedStartDate = java.sql.Date.valueOf(dBegin.getValue());
		//When Date is Changed
		dBegin.valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
				if (java.sql.Date.valueOf(newValue) == null || java.sql.Date.valueOf(newValue).after(selectedEndDate)) {
					getAdminToolsPanel().setStatusMsg(
							"Please set a correct start date.");
					dBegin.setStyle("-fx-border-color: #ff0000;" + "-fx-font-size: 0.65em;" + "-fx-focus-color: #ff0000;");
					saveReportButton.setEnabled(false);
					selectedStartDate = java.sql.Date.valueOf(oldValue);
				} else {
					dBegin.setStyle("-fx-font-size: 0.65em;");
					dEnd.setStyle("-fx-font-size: 0.65em;");
					saveReportButton.setEnabled(true);
					selectedStartDate = java.sql.Date.valueOf(newValue);
					updateGraph();
				}
			}
		});


		//End Date
		dEnd = new DatePicker();
		dEnd.setPrefSize(137, GlobalUI.TEXTBOXHEIGHT);
		dEnd.setStyle("-fx-font-size: 0.65em;");
		t.getChildren().add(dEnd);
		String thisMonthString = ZonedDateTime.now().plusMonths(1)
				.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		dEnd.setValue(LocalDate.parse(thisMonthString, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		selectedEndDate = java.sql.Date.valueOf(dEnd.getValue());
		//When Date is Changed
		dEnd.valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
				if (java.sql.Date.valueOf(newValue) == null || java.sql.Date.valueOf(newValue).before(selectedStartDate)) {
					getAdminToolsPanel().setStatusMsg(
							"Please set a correct start date.");
					dEnd.setStyle("-fx-border-color: #ff0000;" + "-fx-font-size: 0.65em;" + "-fx-focus-color: #ff0000;");
					saveReportButton.setEnabled(false);
					selectedEndDate = java.sql.Date.valueOf(oldValue);
				} else {
					dBegin.setStyle("-fx-font-size: 0.65em;");
					dEnd.setStyle("-fx-font-size: 0.65em;");
					saveReportButton.setEnabled(true);
					selectedEndDate = java.sql.Date.valueOf(newValue);
					updateGraph();
				}
			}
		});

		Scene calendars = new Scene(t);
		actionPanel.setScene(calendars);

		saveReportButton = new JButton("Save Report");
		saveReportButton.setBounds(648, 40, 130, GlobalUI.BUTTONHEIGHT);
		GlobalUI.formatButtonAdmin(saveReportButton, 130,  GlobalUI.GlobalFont);
		actionPanel.add(saveReportButton);
		saveReportButton.addActionListener(this);

		//Labels Above Dropdowns
		lblChooseAStudent = GlobalUI.reportPanelLabelFormat("Choose a Student(s):", Component.CENTER_ALIGNMENT, 20, 5, 155, GlobalUI.LABELHEIGHT);
		actionPanel.add(lblChooseAStudent);
		lblReportType = GlobalUI.reportPanelLabelFormat("Report Type:", Component.CENTER_ALIGNMENT, 208, 5, 93, GlobalUI.LABELHEIGHT);
        actionPanel.add(lblReportType);
		lblStartDate = GlobalUI.reportPanelLabelFormat("Start Date:", Component.CENTER_ALIGNMENT, 366, 5, 76, GlobalUI.LABELHEIGHT);
        actionPanel.add(lblStartDate);
		lblEndDate = GlobalUI.reportPanelLabelFormat("End Date:", GlobalUI.NOALIGNMENT, 520, 5, 76, GlobalUI.LABELHEIGHT);
        actionPanel.add(lblEndDate);

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
				updateStudentCBox();
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

	/**
	 * @param adminToolsPanel
	 *            the adminToolsPanel to set
	 */
	public void setAdminToolsPanel(AdminToolsPanel adminToolsPanel) {
		this.adminToolsPanel = adminToolsPanel;
	}

	/**
	 * @return the adminToolsPanel
	 */
	public AdminToolsPanel getAdminToolsPanel() {
		return adminToolsPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// saves data as a csv or pdf report.
		if (e.getSource().equals(saveReportButton)) {

			// Set to MesaReport directory
			fc = new JFileChooser(System.getProperty("file.separator")
					+ "MesaReports");
			initalizeFileChooser(fc);
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
		// Formats a date to Year-month-day
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "Report_" + reportType + "_" + student + "_"
				+ str.format(selectedStartDate) + "_"
				+ str.format(selectedEndDate) + ".csv";
		File yourFolder = fc.getSelectedFile();
		File pathToCSV = new File(yourFolder.getAbsolutePath() + File.separator
				+ fileName);

		ReportMakerCSV csv = new ReportMakerCSV(pathToCSV);

		// stores the login entries for a specific student.
		ArrayList<Date> datesInLoginTable = manager.getStudentDataRange(
				studentKnumber, selectedStartDate, selectedEndDate);
		// Sorts the dates
		Collections.sort(datesInLoginTable);
		int size = datesInLoginTable.size();
		String numberOfLogins = Integer.toString(size);

		// converts a date array to a string array
		String[] stringDate = new String[size];
		for (int i = 0; i < datesInLoginTable.size(); i++) {
			stringDate[i] = str.format(datesInLoginTable.get(i));
		}

		// when user choose all students if would skip the if and go to the else
		if (!student.equals("All Student's")) {

			// creates a csv file for a single student.
			csv.addHeader("Name,times Present,Dates present");
			csv.CreateCSVFile(student, numberOfLogins, stringDate);

		} else {
			// creates a csv file for more than one students.
			csv.addHeader("Name,times Present,Dates present");

			for (int i = 0; i < listOfStudents.size(); i++) {
				// new array with new login entries based on student knumber
				datesInLoginTable = manager.getStudentDataRange(listOfStudents
						.get(i).getkNumber(), selectedStartDate,
						selectedEndDate);

				// If a student doesn't have login entries it skips them.
				if (datesInLoginTable.size() >= 1) {
					// sorts by dates
					Collections.sort(datesInLoginTable);
					size = datesInLoginTable.size();
					numberOfLogins = Integer.toString(size);
					stringDate = new String[size];

					// formats a date to a specific string date format.
					for (int j = 0; j < datesInLoginTable.size(); j++) {
						stringDate[j] = str.format(datesInLoginTable.get(j));
					}

					// on the first iteration it creates the csv file.
					if (i < 1) {
						csv.CreateCSVFile(listOfStudents.get(i).getFullName(),
								numberOfLogins, stringDate);
					} else {
						// after it just adds students to the existing file from
						// above.
						csv.addStudent(listOfStudents.get(i).getFullName(),
								numberOfLogins, stringDate);
					}

				}
			}
		}

	}

	private void saveToPDF(JFileChooser fc) {

		// formats a date object
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd");
		// default file name format.
		String fileName = "Report_" + reportType + "_" + student + "_"
				+ str.format(selectedStartDate) + "_"
				+ str.format(selectedEndDate) + ".pdf";
		File yourFolder = fc.getSelectedFile();

		File pathToPDF = new File(yourFolder.getAbsolutePath() + File.separator
				+ fileName);

		ArrayList<Date> datesInLoginTable = manager.getStudentDataRange(
				studentKnumber, selectedStartDate, selectedEndDate);
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

			// When the user chose all students it would skip the if and go to
			// the else.
			if (!student.equals("All Student's")) {

				pdf.addMettaData("Mesa Student Activity", "Report Subject",
						"Admin");
				pdf.addHeader("Mesa Student Activity", "Admin", reportType,
						student);
				pdf.addStudent(student, knumber, timesPresent, datesPresent);
				File imageFile = new File("ReportGraph.jpg");
				saveImage(imageFile);
				pdf.addImage(imageFile.getAbsolutePath());
				pdf.closeReport();

			} else {
				pdf.addMettaData("Mesa Student Activity", "Report Subject",
						"Admin");
				pdf.addHeader("Mesa Student Activity", "Admin", reportType,
						"All Student's");

				for (int i = 0; i < listOfStudents.size(); i++) {
					ArrayList<Date> loginDates = manager.getStudentDataRange(
							listOfStudents.get(i).getkNumber(),
							selectedStartDate, selectedEndDate);
					// sorts the dates .
					Collections.sort(loginDates);
					// size of the student login entries
					int sizeOfStuList = loginDates.size();
					// converts student int knumber to string
					String studentKnumber = Integer.toString(listOfStudents
							.get(i).getkNumber());
					// Creates an array of student login dates
					Date[] date = loginDates.toArray(new Date[sizeOfStuList]);
					// number of times a student signed in to the messa center.
					String numOfTimesPressent = Integer.toString(sizeOfStuList);

					if (date.length == 0) {
						pdf.addStudent(listOfStudents.get(i).getFullName(),
								studentKnumber, numOfTimesPressent, date);
					} else {
						pdf.addStudent(listOfStudents.get(i).getFullName(),
								studentKnumber, numOfTimesPressent, date);
					}
				}
				File imageFile = new File("ReportGraph.jpg");
				saveImage(imageFile);
				pdf.addImage(imageFile.getAbsolutePath());
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
				// When ever user chooses All students it prints out all of the
				// students.
				if (comboBoxIndex == 0) {
					student = "All Student's";
					studentKnumber = 0;
					updateGraph();
					System.out.println("You chose all students");
				} else {
					// otherwise it gets the index of the combo box and
					// subtracts it by one. To choose the same person from the
					// student list.
					// studentListIndex = comboBoxIndex;
					student = listOfStudents.get(comboBoxIndex - 1)
							.getFullName();
					// subtracts 1 because the combobox has set all students as
					// index 0.
					studentKnumber = listOfStudents.get(comboBoxIndex - 1)
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

	private void initalizeFileChooser(JFileChooser fileChooser) {
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setApproveButtonText("Save");
		FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter(".pdf",
				"Report Type");
		FileNameExtensionFilter csvFilter = new FileNameExtensionFilter(".csv",
				"Report Type");
		fileChooser.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
		fileChooser.addChoosableFileFilter(pdfFilter);
		fileChooser.addChoosableFileFilter(csvFilter);
		fileChooser.setFileFilter(pdfFilter);
	}

	private void saveImage(File imageFile) {
		Dimension size = graph.getGraph().getSize();
		BufferedImage img = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_RGB);
		graph.getGraph().paint(img.getGraphics());
		try {
			ImageIO.write(img, "JPEG", imageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateGraph() {
		ArrayList<Date> dates = manager.getStudentDataRange(studentKnumber,
				selectedStartDate, selectedEndDate);
		graph.createGraph(jCBoxReporTypeSearch.getSelectedIndex(), dates);
		BorderLayout layout = (BorderLayout) panel.getLayout();
		panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		add(graph.getGraph(), BorderLayout.CENTER);
		revalidate();
	}

	private void updateStudentCBox() {

		if (listOfStudents.size() > manager.getStudents().size()) {
			removeStudentCBox();
		} else {
			listOfStudents = manager.getStudents();
			for (int i = 0; i < manager.getStudents().size(); i++){
				jCBoxStudentSearch.insertItemAt(listOfStudents.get(i), i);				
			}
			refreshComboBox();
		}
	}

	/**
	 * removes every student that was deleted from the database from the student
	 * combobox
	 */
	private void removeStudentCBox() {

		for (int i = (listOfStudents.size() - 1); i >= 0; i--) {
			String removedStudent = listOfStudents.get(i).getFullName();
			String currentStudent = null;
			boolean matchFound = false;

			for (int j = (manager.getStudents().size() - 1); j >= 0; j--) {
				currentStudent = manager.getStudents().get(j).getFullName();
				if (removedStudent.equals(currentStudent)) {
					matchFound = true;
					break;
				}
			}

			if (matchFound == false) {
				jCBoxStudentSearch.removeItem(listOfStudents.get(i)
						.getFullName());
				listOfStudents.remove(i);
			}
		}

	}
	private void refreshComboBox(){
		sortStudentList();
		//clear the jcombobox
		jCBoxStudentSearch.setModel(new DefaultComboBoxModel());
		jCBoxStudentSearch.addItem(GlobalUI.allStudents);
		for(int p = 0; p < listOfStudents.size(); p++){
			jCBoxStudentSearch.addItem(listOfStudents.get(p).getLastNameFirstName());
		}
	}
	private void sortStudentList(){
		Collections.sort(listOfStudents);
	}
}
