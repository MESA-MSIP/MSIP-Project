package com.msip.ui;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.itextpdf.text.DocumentException;
import com.msip.manager.MISPCore;
import com.msip.model.Student;
import com.msip.external.ReportMakerCSV;
import com.msip.external.ReportMakerPDF;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ReportPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	// Drop downs
	private ComboBox<String> CBoxStudentSearch;
	private ComboBox<String> CBoxReportTypeSearch;
	// Action Button
	private JButton saveReportButton;
	private JPanel centerPanel = new JPanel();
	private JFXPanel actionPanel;
	// Text labels
	private JLabel lblChooseAStudent;
	private JLabel lblReportType;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	// List of all students
	private ArrayList<Student> listOfStudents = new ArrayList<Student>();
	private String[] reportTypes = {"Hours", "Days", "Weeks", "Months"};
	private String student = "";
	private String reportType = "";
	private Date selectedStartDate = null;
	private Date selectedEndDate = null;
	private Date tempStartDate = null;
	private Date tempEndDate = null;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component verticalStrut;
	private int studentKnumber;
	private GeneralGraph graph = new GeneralGraph("");
	private ReportPanel panel;
	//Date Picker - Calendars
	private DatePicker dBegin;
	private DatePicker dEnd;
	private DirectoryChooser dc;
	private AdminToolsPanel adminToolsPanel;
	//Used to place JavaFX objects
	private GridPane tPane;
	File selectedFolder;
	RadioButton rbPDF;
	RadioButton rbCSV;
	ToggleGroup tGroup;

	public ReportPanel(MISPCore msipCore, AdminToolsPanel adminToolsPanel) {

		this.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);

		new JFXPanel();

		//Tile pane for Date Pickers & Dropdowns
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tPane = new GridPane();
				tPane.setLayoutX(22);
				tPane.setLayoutY(19);
				tPane.setHgap(18);
				tPane.setStyle("-fx-background-color: transparent");
			}
		});

		this.setManager(msipCore);
		this.setAdminToolsPanel(adminToolsPanel);
		panel = this;
		setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		setLayout(new BorderLayout(0, 0));

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				actionPanel = new JFXPanel();
				actionPanel.setPreferredSize(new Dimension(100, 80));
				add(actionPanel, BorderLayout.NORTH);
				actionPanel.setLayout(null);
				actionPanel.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
			}
		});

		/////////////
		//DROPDOWNS//
		/////////////

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				//Instantiates and Adds Students to Dropdown
				CBoxStudentSearch = new ComboBox<String>(FXCollections.observableArrayList(returnStudents(listOfStudents)));
				CBoxStudentSearch.setPrefSize(137, GlobalUI.TEXTBOXHEIGHT);

				// Adds "all students" to combo box, Sets it as default
				CBoxStudentSearch.getItems().add(0, "All Students");
				studentKnumber = 0;
				student = "All Students";
				CBoxStudentSearch.getSelectionModel().selectFirst();

				//Listener For When the User selects a different Student
				CBoxStudentSearch.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
							int comboBoxIndex = CBoxStudentSearch.getSelectionModel().getSelectedIndex();
							if (comboBoxIndex == 0) { //ALL STUDENTS
								student = "All Student's";
								studentKnumber = 0;
								updateGraph();
								System.out.println("You chose all students");
							} else { //SPECIFIC STUDENT
								// Gets the index of the combo box and subtracts it by one
								// to choose the same person from the student list
								try{
									student = listOfStudents.get(comboBoxIndex - 1)
											.getFullName();
									studentKnumber = listOfStudents.get(comboBoxIndex - 1)
											.getkNumber();
									updateGraph();}
								catch(IndexOutOfBoundsException e){
									System.out.print("");
								}
							}
						}
				);

				//Instantiates and Adds Report Types to Dropdown
				CBoxReportTypeSearch = new ComboBox<String>(FXCollections.observableArrayList(reportTypes));
				CBoxReportTypeSearch.setPrefSize(137, GlobalUI.TEXTBOXHEIGHT);
				reportType = "Hours";
				CBoxReportTypeSearch.getSelectionModel().select(0);
				//Listener For When the User selects a different Report Type
				CBoxReportTypeSearch.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
							int reportTypeIndex = CBoxReportTypeSearch.getSelectionModel().getSelectedIndex();
							reportType = reportTypes[reportTypeIndex];
							updateGraph();
						}
				);
			}
		});


		////////////////
		//DATE PICKERS//
		////////////////

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				//Begin Date
				dBegin = new DatePicker();
				dBegin.setPrefSize(137, GlobalUI.TEXTBOXHEIGHT);
				dBegin.setStyle("-fx-font-size: 0.65em;");

				//Sets dBegin Default Date to a month before today's date
				String lastMonthString = ZonedDateTime.now().minusMonths(1)
						.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				dBegin.setValue(LocalDate.parse(lastMonthString, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				selectedStartDate = java.sql.Date.valueOf(dBegin.getValue());
				tempStartDate = selectedStartDate;

				//When Date is Changed
				dBegin.valueProperty().addListener(new ChangeListener<LocalDate>() {
					public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
						if (java.sql.Date.valueOf(newValue) == null || java.sql.Date.valueOf(newValue).after(selectedEndDate)) {
							getAdminToolsPanel().setStatusMsg(
									"Please set a correct start date.");
							dBegin.setStyle("-fx-border-color: #ff0000;" + "-fx-font-size: 0.65em;" + "-fx-focus-color: #ff0000;");
							saveReportButton.setEnabled(false);
							selectedStartDate = java.sql.Date.valueOf(oldValue);
							tempStartDate = java.sql.Date.valueOf(newValue);
						} else {
							dBegin.setStyle("-fx-font-size: 0.65em;");
							dEnd.setStyle("-fx-font-size: 0.65em;");
							saveReportButton.setEnabled(true);
							selectedStartDate = java.sql.Date.valueOf(newValue);
							selectedEndDate = tempEndDate;
							updateGraph();
						}
					}
				});


				//End Date
				dEnd = new DatePicker();
				dEnd.setPrefSize(137, GlobalUI.TEXTBOXHEIGHT);
				dEnd.setStyle("-fx-font-size: 0.65em;");

				//Sets dEnd Default Date to today's date
				String thisMonthString = ZonedDateTime.now()
						.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				dEnd.setValue(LocalDate.parse(thisMonthString, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				selectedEndDate = java.sql.Date.valueOf(dEnd.getValue());
				tempEndDate = selectedEndDate;

				//When Date is Changed
				dEnd.valueProperty().addListener(new ChangeListener<LocalDate>() {
					public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
						if (java.sql.Date.valueOf(newValue) == null || java.sql.Date.valueOf(newValue).before(selectedStartDate)) {
							getAdminToolsPanel().setStatusMsg(
									"Please set a correct start date.");
							dEnd.setStyle("-fx-border-color: #ff0000;" + "-fx-font-size: 0.65em;" + "-fx-focus-color: #ff0000;");
							saveReportButton.setEnabled(false);
							selectedEndDate = java.sql.Date.valueOf(oldValue);
							tempEndDate = java.sql.Date.valueOf(newValue);
						} else {
							dBegin.setStyle("-fx-font-size: 0.65em;");
							dEnd.setStyle("-fx-font-size: 0.65em;");
							saveReportButton.setEnabled(true);
							selectedEndDate = java.sql.Date.valueOf(newValue);
							selectedStartDate = tempStartDate;
							updateGraph();
						}
					}
				});
			}
		});

		//Save Report Button
		saveReportButton = new JButton("Save Report");
		saveReportButton.setBounds(638, 40, 130, GlobalUI.BUTTONHEIGHT);
		GlobalUI.formatButtonAdmin(saveReportButton, 130, GlobalUI.GlobalFont);
		saveReportButton.addActionListener(this);

		//Placing JavaFX objects
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				//Save Report File Type TickMarks
				//Extra Spaces Used for Formatting
				rbPDF = new RadioButton("pdf       ");
				rbCSV = new RadioButton("csv");
				tGroup = new ToggleGroup();
				rbPDF.setToggleGroup(tGroup);
				rbCSV.setToggleGroup(tGroup);
				rbPDF.setSelected(true);
				HBox hb = new HBox();
				hb.getChildren().add(rbPDF);
				hb.getChildren().add(rbCSV);

				//Placing JavaFX objects
				tPane.add(CBoxStudentSearch, 0, 1);
				tPane.add(CBoxReportTypeSearch, 1, 1);
				tPane.add(dBegin, 2, 1);
				tPane.add(dEnd, 3, 1);
				tPane.add(hb, 4, 0);

				Scene fxScene = new Scene(tPane, GlobalUI.FX_COLOR);
				actionPanel.setScene(fxScene);

				//Labels Above Dropdowns
				lblChooseAStudent = GlobalUI.reportPanelLabelFormat("Choose a Student(s):", Component.CENTER_ALIGNMENT, 20, 5, 155, GlobalUI.LABELHEIGHT);
				actionPanel.add(lblChooseAStudent);
				lblReportType = GlobalUI.reportPanelLabelFormat("Report Type:", Component.CENTER_ALIGNMENT, 208, 5, 93, GlobalUI.LABELHEIGHT);
				actionPanel.add(lblReportType);
				lblStartDate = GlobalUI.reportPanelLabelFormat("Start Date:", Component.CENTER_ALIGNMENT, 366, 5, 76, GlobalUI.LABELHEIGHT);
				actionPanel.add(lblStartDate);
				lblEndDate = GlobalUI.reportPanelLabelFormat("End Date:", GlobalUI.NOALIGNMENT, 520, 5, 76, GlobalUI.LABELHEIGHT);
				actionPanel.add(lblEndDate);
				actionPanel.add(saveReportButton);

			}
		});

		//Panel behind
		centerPanel.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		add(centerPanel, BorderLayout.CENTER);

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
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						updateGraph();
						updateStudentCBox();
					}
				});
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
	 * @param manager the manager to set
	 */
	public void setManager(MISPCore manager) {
		this.manager = manager;
	}

	/**
	 * @param adminToolsPanel the adminToolsPanel to set
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

	private void saveToCSV(File selFolder) {
		//Checks to see if the cancel button was clicked
		if(selFolder == null)
			return;

		// Formats a date to Year-month-day
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "Report_" + reportType + "_" + student + "_"
				+ str.format(selectedStartDate) + "_"
				+ str.format(selectedEndDate) + ".csv";

		File pathToCSV = new File(selFolder.getAbsolutePath() + File.separator
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

	private void saveToPDF(File selFolder) {
		//Checks to see if the cancel button was clicked
		if(selFolder == null)
			return;

		// formats a date object
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd");
		// default file name format.
		String fileName = "Report_" + reportType + "_" + student + "_"
				+ str.format(selectedStartDate) + "_"
				+ str.format(selectedEndDate) + ".pdf";

		File pathToPDF = new File(selFolder.getAbsolutePath() + File.separator
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

	/**
	 * Updates Graph
	 */
	public void updateGraph() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ArrayList<Date> dates = manager.getStudentDataRange(studentKnumber,
						selectedStartDate, selectedEndDate);
				graph.createGraph(CBoxReportTypeSearch.getSelectionModel().getSelectedIndex(), dates);
				BorderLayout layout = (BorderLayout) panel.getLayout();
				panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
				add(graph.getGraph(), BorderLayout.CENTER);
				revalidate();
			}
		});
	}

	/**
	 * Updates Student Drop Down Object
	 */
	private void updateStudentCBox() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (listOfStudents.size() != manager.getStudents().size()) {
					listOfStudents = manager.getStudents();
					Collections.sort(listOfStudents);
					CBoxStudentSearch.setItems(FXCollections.observableArrayList(returnStudents(listOfStudents)));
					CBoxStudentSearch.getItems().add(0, "All Students");
				}
			}
		});
	}

	/**
	 * Converts Student Arraylist to a String ArrayList using their fullName
	 * @param students
	 * @return ArrayList of Students as Strings
	 */
	public ArrayList<String> returnStudents(ArrayList<Student> students){
		ArrayList<String> ret = new ArrayList<>();
		for(Student stud: students)
			ret.add(stud.getFullName());
		return ret;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
	    //Used for when the Save Report Button is clicked
		if (e.getSource().equals(saveReportButton)) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					int flag = 0;
                    //Attempts to set Initial Location to MesaReports directory
					File defaultLoc = defaultLoc = new File(System.getProperty("file.separator")
                            , "MesaReports");
					dc = new DirectoryChooser();
                    //Checks to see if it was a valid path
                    if(defaultLoc.exists())
                        dc.setInitialDirectory(defaultLoc);
                    else
                        System.out.println("COuld Not locate Default Path: MesaReports");
                    //Displays pop-up window
                    selectedFolder = dc.showDialog(new Stage());
					//Checks to see which RadioButton is Selected
					if(tGroup.getSelectedToggle() == rbPDF)
						saveToPDF(selectedFolder);
					else
						saveToCSV(selectedFolder);
				}
			});
		}

	}
}

