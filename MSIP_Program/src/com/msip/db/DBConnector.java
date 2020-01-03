package com.msip.db;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.msip.external.Utility;

public class DBConnector {
	//TODO DBConnector the driver for all Table instantiation.
	static Connection myConnection;

	/**
	 * Connects to the SQL database and creates the login table as soon as the
	 * database object has been created and executed.
	 */
	public DBConnector() {
		try {
			System.out.println("Connecting to MySQL...");
			myConnection = DriverManager.getConnection(Global.URL,
					Global.USERNAME, Global.PASSWORD);
			System.out.println("Successfully connected to MySQL");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("\n"+"Failed to connected to MySQL" +"\n");

			System.out.println("\n"
							+ "You did not Sign in correctly or you aren't using 'MesaDB' database"
							+ "\n");
		}
	}

	public static void main(String[] args) throws SQLException, ParseException,
			NoSuchAlgorithmException, IOException {
		new DBConnector();
		AdminTable admin = new AdminTable();
		admin.deleteAll();
		admin.add(11111111, "Juan", "Zepeda", Utility.getHashedPassword("1"));
		admin.add(22222222, "Cynthia", "Barajas",
				Utility.getHashedPassword("2"));
		admin.add(33333333, "Fernando", "Estevez",
				Utility.getHashedPassword("3"));

		StudentTable student = new StudentTable();
		student.deleteAll();
		student.add(33333333, "Fernando", "Estevez", "Computer Engineering");
		student.add(44444444, "Christian", "Martinez", "Computer Science");
		student.add(55555555, "Celina", "Lazaro", "Computer Engineering");
		student.add(66666666, "Jorge", "Pantaleon", "Electrcal Engineering");
		student.add(512840, "Daryl", "Delgado", "Electrical Engineering");



        LoginTable login = new LoginTable();
		login.deleteAll();
		//Utility.importLoginsFromCSVFile("F:\\Downloads\\Spring 2016.txt", login);
		//login.getParticipation(33333333);
	

//		 for (int i = 0; i < 500; i++) {
//		 login.addUsingDates(33333333, getRandomDate());
//		 }
//		
//		 for (int i = 0; i < 500; i++) {
//		 login.addUsingDates(44444444, getRandomDate());
//		 }
//		
//		 for (int i = 0; i < 5000; i++) {
//		 login.addUsingDates(55555555, getRandomDate());
//		 }
//		 for (int i = 0; i < 500; i++) {
//		 login.addUsingDates(66666666, getRandomDate());
//		 }
//		
//		 for (int i = 0; i < 500; i++) {
//		 login.addUsingDates(77777777, getRandomDate());
//		 }

		NotificationTable no = new NotificationTable();
		no.removeAll();
		SimpleDateFormat formatter = new SimpleDateFormat("MMM/dd/yyyy");
		String tomorrowsDateString = ZonedDateTime.now().plusDays(2)
				.format(DateTimeFormatter.ofPattern("MMM/dd/yyyy"));
		Date tomorrowsDate = null;
		try {
			tomorrowsDate = formatter.parse(tomorrowsDateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		//no.removeFromNotificationTable(new Date());
	 no.addToNotificationTable("MESA Sign-In Kiosk 1.0 presentation today @ 1:00 PM", new Date(), tomorrowsDate); 
//		 no.addToNotificationTable("Exam on wed", new Date(),
//						 getRandomDate());
//		 no.addToNotificationTable("Exam on thur", new Date(),
//				 getRandomDate());
//		 no.addToNotificationTable("Exam on friday", new Date(),
//				 getRandomDate());
//		 System.out.println(no.getAllNotification());

		SurveyTable s = new SurveyTable();
		//s.addQuestion("No new question", new Date());
		
		//s.removeFromSurveyTable(new Date());
		
//
//		for(int i = 0; i < 7 ; i++){
//			s.addResults(5);
//		}
//		for(int n = 0; n < 3; n++){
//			s.addResults(1);
//		}
//		for(int j = 0; j < 18; j++){
//			s.addResults(2);
//		}
//		for(int k = 0; k < 2; k++){
//			s.addResults(3);
//		}
//		for(int c = 0; c < 1; c++){
//			s.addResults(4);
//		}
		s.removeAll();


	}

	private static Timestamp getRandomDate() {
		long offset = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2016-12-01 00:00:00").getTime();
		long diff = end - offset + 1;
		return new Timestamp(offset + (long) (Math.random() * diff));
	}
}
