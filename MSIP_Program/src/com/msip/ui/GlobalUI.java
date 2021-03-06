package com.msip.ui;

import com.msip.db.Global;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.BarRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

public class GlobalUI {
	// Strings for Card layout
	public static final String LoginPanel = "LoginPanel";
	public static final String WelcomePanel = "WelcomePanel";
	public static final String AdminToolsPanel = "AdminToolsPanel";
	public static final String StudentSurveyPanel = "StudentSurveyPanel";
	public static final String BlankPanel = "BlankPanel";
	public static final String NotificationCard = "NotificationCard";
	public static final String newStudentMessagePanel = "newStudentMessagePanel";
	public static final String TimePanel = "Time/Date";

	//Strings for ReportPanel
	public static final String allStudents = "All Student's";

	// Strings for the QuestionnairePanel
	public static final String submitButtonText = "Submit";
	public static final String clearButtonText = "Clear";
	public static final String completeFields = "Please Complete all the Fields.";
	public static final String questionLength = "The Question Has to be At Least 4 Characters or More.";

	// Colors and Borders
	public static final Color GLOBAL_BACKGROUND_COLOR = new Color(243, 243, 243);
	public static final Color whiteColor = new Color(255, 255, 255);
	public static final Color redColor = new Color(244, 67, 54);
	public static final Color blueColor = new Color(107, 143, 194);
	public static final Color darkBlueColor = new Color(95, 124, 176);
	public static final Color strongBlue = new Color(53, 65, 136);
	public static final Color BARCHARTCOLOR = new Color(65, 91, 149);
	public static final Border redBorder = BorderFactory.createMatteBorder(2,
			2, 2, 2, redColor);
	public static final Color blackColor = Color.BLACK;
	public static final Border blackBorder = BorderFactory.createMatteBorder(1,
			1, 1, 1, blackColor);
	public static final Border dblueBorder = BorderFactory.createMatteBorder(1,
			1, 1, 1, darkBlueColor);

	// Minimum Value for the Question
	public static final int minQuestionLength = 4;

	//
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;

	// Messages for the WelcomePanel
	public static final String adminPassError = "Password or K# is incorrect.";
	public static final String help = "K# is Incorrect.  Try Again.";
	public static final String loginSuccess = "You Have Logged In";
	public static final String errorMessage = "KNumber is Incorrect." + '\n'
			+ "See a MESA Advisor to Register.";
	public static final String InsertAdminPassMessage = "Enter your Password.";

	// RadioButton Values for SurveyPanel

	public static final int responseOne = 1;
	public static final int responseTwo = 2;
	public static final int responseThree = 3;
	public static final int responseFour = 4;
	public static final int responseFive = 5;
	// To Clear:
	public static final String CLEAR = "";

	// Integers for MSIP
	public static final int kNumMax = 8;
	public static final int kNumLength = 0;

	// Student/Admin Table Constant
	public static final int numOfColumnsInAdmin = 3;
	public static final int numOfColumnsInStudent = 5;
	public static final int columnIndexLastName = 0;
	public static final int columnIndexFirstName = 1;
	public static final int columnIndexKNumber = 2;
	public static final int columnIndexMajor = 3;
	public static final int columnIndexParticipation = 4;
	public static final String columnNameFirstName = "First Name";
	public static final String columnNameLastName = "Last Name";
	public static final String columnNameKNumber = "K-Number";
	public static final String columnNameMajor = "Major";
	public static final String columnNameParticipation = "Participation";

	// Strings for Tab Layout
	public static final String StudentTab = "Students";
	public static final String AdminTab = "Administrators";
	public static final String ReportTab = "Reports";
	public static final String NotificationsPanel = "Notifications";
	public static final String QuestionnairePanel = "Questionnaire";

	// Path File for MESA Logo
	public static final String MESAURL = "MESA.png";
	public static final String YELLOWSTARURL = "yellowStar.png";

	// Integers for Student Admin Decision
	public static final int STUDENT = 0;
	public static final int ADMIN = 1;
	public static final int NEITHER = 2;

	//Magic Number for not truncating the StudentSurveyPanel Labels
	public static final int truncateLimit = 15;
	public static final int textQuestionLimit = 200;

	// Button properties
	public static final Dimension ButtonDimenesions = new Dimension(100, 30);

	// Label Properties
	public static final Font LableFont = new Font("Segoe UI Light", 0, 16);
	public static final Font welcomeLabelFont = new Font("Segoe UI", 1, 60);
	public static final Font adminErrorFont = new Font("Segoe UI Bold", 0, 12);
	public static final Font characterCountFont = new Font("Segoe UI Bold", 0, 16);
	public static final float NOALIGNMENT = -777777;

	// Label Text Field
	public static final Font TextFieldFont = new Font("Segoe UI Light", 0, 16);
	public static final Font txtKNumberFont = new Font("Segoe UI Light", 0, 32);
	public static final Font TextFieldLabelFont = new Font("Segoe UI", 0, 24);


	// General Strings
	public static final String ADDSTUDENT = "Add Student to Database";
	public static final String MODIFYSTUDENT = "Edit Student Information";
	public static final String SELECTASTUDENT = "Please Select a student";
	public static final String SELECTANADMIN = "Please Select an Admin";
	public static final String ADDADMIN = "Add Admin to Database";
	public static final String PLEASE_SEE_DEVELOPER = "Please see developer. Something went wrong.. Oops!";
	public static final String MODIFY_ADMIN = "Edit Admin Information";;

	// Magic Number
	public static final int TWO_THOUSAND_MILLI_SECONDS = 2000;
	public static final int TEXTBOXHEIGHT = 45;
	public static final int LABELHEIGHT = 40;
	public static final int BUTTONHEIGHT = 40;
	public static final Font GlobalFont = new Font("Tahoma", Font.PLAIN, 15);
	public static final Font NotifictionFont = new Font("Tahoma", Font.PLAIN, 22);

	public static String[] getListMajors() {
		String[] majorList = { "Aerospace Engineering",
				"Agricultural Engineering", "Architectural Engineering",
				"Biology", "Biochemistry", "Bioengineering",
				"Biological Sciences", "Biomedical Engineering",
				"Chemical Engineering", "Chemistry", "Civil Engineering",
				"Computer Engineering", "Computer Science",
				"Electrical Engineering", "Engineering",
				"Environmental Engineering", "Environmental Science",
				"Food Science", "Geology", "Information Technology",
				"Industrial Engineering", "Marine Biology",
				"Materials Engineering", "Mathematics", "Mathematics Teaching",
				"Mechanical Engineering", "Pharmacy", "Physics", "PreDental",
				"PreMed", "Veterinary", "Zoology", "Other" };

		return majorList;
	}

	/**
	 * Method used to format the buttons used in the Admin Tools Page
	 * @param adminButton
	 * @param width
	 * @param fontUsed
	 */
	public static void formatButtonAdmin(JButton adminButton, int width, Font fontUsed){
		adminButton.setFont(fontUsed);
		adminButton.setForeground(Color.WHITE);
		adminButton.setPreferredSize(new Dimension(width, GlobalUI.BUTTONHEIGHT));
		adminButton.setBackground(GlobalUI.blueColor);
		adminButton.setBorder(GlobalUI.dblueBorder);
		adminButton.setRolloverEnabled(true);
		adminButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) { adminButton.setBackground(GlobalUI.darkBlueColor); }
			public void mouseExited(MouseEvent e) { adminButton.setBackground(GlobalUI.blueColor); }
		});
	}

	/**
	 * Method used to format the drop down labels used in the Report Panel
	 * @param text
	 * @param alignment
	 * @param xpos
	 * @param ypos
	 * @param width
	 * @param height
	 * @return JLabel
	 */
	public static JLabel reportPanelLabelFormat(String text, float alignment, int xpos, int ypos, int width, int height){
		JLabel currLabel = new JLabel(text);
		currLabel.setBounds(xpos, ypos, width, height);
		currLabel.setFont(GlobalUI.LableFont);
		if(alignment != NOALIGNMENT)
			currLabel.setAlignmentX(alignment);
		return currLabel;
	}

	/**
	 * Method used to format the Bar Chart found in "Reports" tab
	 * @param barChart
	 * @return ChartPanel
	 */
	public static ChartPanel formatBarChart(JFreeChart barChart){
		barChart.removeLegend();
		BarRenderer renderer = (BarRenderer) barChart.getCategoryPlot().getRenderer();
		renderer.setSeriesPaint(0, GlobalUI.BARCHARTCOLOR);
		barChart.getPlot().setBackgroundPaint(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		barChart.setBackgroundPaint(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		barChart.getPlot().setOutlinePaint(null);
		barChart.getCategoryPlot().setRangeGridlinePaint(new Color(41, 41, 41));
		ChartPanel chartPan = new ChartPanel(barChart);
		chartPan.setPreferredSize(new java.awt.Dimension(700, 250));
		return chartPan;
	}

}
