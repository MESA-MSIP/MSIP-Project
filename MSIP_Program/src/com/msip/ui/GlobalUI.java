package com.msip.ui;

import java.awt.Dimension;
import java.awt.Font;

public class GlobalUI {
	// Strings for Card layout
	public static final String LoginPanel = "LoginPanel";
	public static final String AdminToolsPanel = "AdminToolsPanel";

	//
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;

	// Integers for MSIP
	public static final int kNumMax = 8;
	public static final int kNumLength = 0;

	// Student Table Constant
	public static final int numOfColumnsInStudent = 4;
	public static final int columnIndexFirstName = 0;
	public static final int columnIndexLastName = 1;
	public static final int columnIndexKNumber = 2;
	public static final int columnIndexMajor = 3;
	public static final String columnNameFirstName = "First Name";
	public static final String columnNameLastName = "Last Name";
	public static final String columnNameKNumber = "K-Number";
	public static final String columnNameMajor = "Major";

	// Strings for Tab Layout
	public static final String StudentTab = "Students";
	public static final String AdminTab = "Admins";
	public static final String ReportTab = "Reports";
	public static final String NotificationsPanel = "Notifications";
	public static final String QuestionnairePanel = "Questionnaire";

	//Path File for MESA Logo
	public static final String MESAURL = "MESA.png";
	
	//Integers for Student Admin Decision
	public static final int STUDENT = 0;
	public static final int ADMIN = 1;
	public static final int NEITHER = 2;
	
	//Button properties
	public static final Dimension ButtonDimenesions = new Dimension(100, 30);
	
	//Label Properties
	public static final Font LableFont = new Font("Tahoma", Font.BOLD, 13);
	
	//Label Text Field
	public static final Font TextFieldFont = new Font("Tahoma", Font.BOLD, 13);
	
	//General Strings
	public static final String ADDSTUDENT = "Add Student to Database";
	public static final String MODIFYSTUDENT = "Edit Student Information";
	public static final String SELECTASTUDENT = "Please Select a student";
	
	//Magic Number
	public static final int TWO_THOUSAND_MILLI_SECONDS = 2000;

	
	
}