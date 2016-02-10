package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import com.itextpdf.text.DocumentException;
import com.msip.manager.MISPCore;
import com.msip.model.Person;
import com.msip.model.Student;
import com.msip.external.ReportMakerCSV;
import com.msip.external.ReportMakerPDF;

import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DateFormatter;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReportPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	private JComboBox<Object> studentSearch;
	private JComboBox<Object> dateTypeSearch;
	private JButton saveReport;
	private JPanel actionPanel;
	private JPanel graphPanel;
	private JLabel lblChooseAStudent;
	private JLabel lblReportType;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private ArrayList<String> studentList = new ArrayList<String>();
	private ArrayList<Student> listOfStudents = new ArrayList<Student>();
	private String[] reportTypes = { "Hour", "Day", "Week", "Month" };
	private String student = "";
	private String reportType = "";
	private Date selectedStartDate = null;
	private Date selectedEndDate = null;

	public ReportPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);

		actionPanel = new JPanel();
		actionPanel.setPreferredSize(new Dimension(100, 100));
		add(actionPanel, BorderLayout.NORTH);
		actionPanel.setLayout(null);

		studentSearch = new JComboBox<Object>();
		studentSearch.setBounds(15, 40, 137, 26);
		actionPanel.add(studentSearch);

		studentSearch.addItem("All Student's");

		// Adds all students to combo box.
		for (int i = 0; i < msipCore.getStudents().size(); i++) {
			// Adds a student to a String Array
			studentList.add(msipCore.getStudents().get(i).getFullName());
			// Adds a student to a Student Array
			listOfStudents.add(msipCore.getStudents().get(i));
			// Adds a student to combo box.
			studentSearch.addItem(studentList.get(i));
		}

		studentSearch.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				// returns the users choice as an index based on the combo box
				// list.
				int comboBoxIndex = studentSearch.getSelectedIndex();
				int studentListIndex = 0;
				// When ever user chooses All students it prints out all of the
				// students.
				if (comboBoxIndex == 0) {
					for (int i = 0; i < studentList.size(); i++) {
						student = studentList.get(i);
						System.out.println(studentList.get(i));
					}
					// otherwise it gets the index of the combo box and
					// subtracts it by one. To choose the same person from the
					// student list.
				} else {
					studentListIndex = comboBoxIndex - 1;
					student = studentList.get(studentListIndex);
					System.out.println(studentList.get(studentListIndex));
				}
			}
		});

		dateTypeSearch = new JComboBox<Object>();
		dateTypeSearch.setBounds(179, 40, 137, 26);
		actionPanel.add(dateTypeSearch);

		// Adds different report types to combo box.
		for (String date : reportTypes) {
			dateTypeSearch.addItem(date);
		}

		dateTypeSearch.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {
				int reportTypeIndex = dateTypeSearch.getSelectedIndex();
				// prints out users choice of report.
				reportType = reportTypes[reportTypeIndex];
				System.out.println(reportTypes[reportTypeIndex]);

			}
		});
		
		//Creates the date picker
		UtilDateModel startModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.month", "Month");
		p.put("text.today", "Today");
		p.put("text.year", "Year");
		JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, p);
		JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel,
				new DateComponentFormatter());
		actionPanel.add(startDatePicker);
		startDatePicker.setBounds(341, 40, 137, 26);
		
		startDatePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//returns the chosen date.
				selectedStartDate = (Date) startDatePicker.getModel()
						.getValue();
				System.out.println(selectedStartDate);
			}
		});

		UtilDateModel endModel = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.year", "Year");
		p2.put("text.month", "Month");
		p2.put("text.today", "Today");
		JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, p2);
		JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel, new DateComponentFormatter());
		endDatePicker.setBounds(496, 40, 137, 26);
		actionPanel.add(endDatePicker);

		endDatePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedEndDate = (Date) endDatePicker.getModel().getValue();
				System.out.println(selectedEndDate);
			}
		});

		saveReport = new JButton("Save Report");
		saveReport.setBounds(648, 40, 137, 26);
		actionPanel.add(saveReport);

		saveReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						".csv", "Report Type");
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
						".pdf", "Report Type");
				fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
				fc.addChoosableFileFilter(filter);
				fc.addChoosableFileFilter(filter2);
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println(fc.getFileFilter().getDescription());

					if (fc.getFileFilter().getDescription().equals(".csv")) {
						File yourFolder = fc.getSelectedFile();
						System.out.println(yourFolder);
						String numOfLogins = "";
						System.out.println("Im in");
						File pathToCSV = new File(yourFolder.getAbsolutePath()
								+ File.separator + "Fernando'sReport_csv.csv");

						ReportMakerCSV csv = new ReportMakerCSV(pathToCSV);
						SimpleDateFormat str = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = new Date[5];
						for (int i = 0; i < d.length; i++) {
							d[i] = new Date();
						}
						
						String[] s = new String[5];
						for (int i = 0; i < s.length; i++) {
							s[i] = str.format(d[i]);
						}
						numOfLogins = Integer.toString(s.length);
						csv.addHeader("Name,times Present,Dates present");
						csv.CreateCSVFile(student, numOfLogins, s);

					} else {
						File yourFolder = fc.getSelectedFile();
						File pathToPDF = new File(yourFolder.getAbsolutePath()
								+ File.separator + "Fernando'sReport.pdf");
						try {
							
							Date[] d2 = new Date[20];
							for (int i = 0; i < d2.length; i++) {
								d2[i] = new Date();
							}
								String timesPresent = Integer.toString(d2.length);
							
							ReportMakerPDF pdf = new ReportMakerPDF(pathToPDF);
							pdf.addMettaData("Report Title", "Report Subject", "Juan Zepeda");
							pdf.addHeader("Report Title", student, reportType, "John Smith");
							pdf.addStudent(student, "KNumber", timesPresent, d2);
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});

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
