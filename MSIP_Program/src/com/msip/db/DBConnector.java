package com.msip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	static Connection myConnection;

	/**
	 * Connects to the SQL database and creates the login table as soon as the
	 * database object has been created and executed.
	 */
	public DBConnector() {
		try {
			myConnection = (Connection) DriverManager.getConnection(Global.URL,
					Global.USERNAME, Global.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("\n" + "You did not Sign in correctly or you aren't using 'MesaDB' database" + "\n");
		}
	}


	public static void main(String[] args) throws SQLException{
		new DBConnector();
		AdminTable admin = new AdminTable();
		admin.deleteAll();
		admin.add(11111111, "Juan", "Zepeda", "1");
		admin.add(22222222, "Cynthia", "Barajas", "2");
		//admin.getAll();

		
		StudentTable student = new StudentTable();
		student.deleteAll();
		student.add(33333333, "Fernando", "Estevez", "Computer Engineering");
		student.add(44444444, "Christian", "Martinez", "Computer Science");
		student.add(55555555, "Celina", "Lazaro", "Computer Engineering");
		student.add(66666666, "Jorge", "Pantaleon", "Electrcal Engineering");
		student.add(77777777, "Daryl","Delgado", "Electrical Engineering");
		//student.getAll();

	
		
		LoginTable login = new LoginTable();
		// Still working on creating a months worth of data.
		String dateInString = "2016-01-25";
		
		
		
		


//		login.add(33333333);
//		login.add(11111111);
//		login.add(22222222);
//		login.add(44444444);
//		login.add(55555555);
//		login.add(66666666);
//		login.add(77777777);
		login.getParticipation(33333333);
		//login.addUsingDates(33333333, dateInString);

		
	
}

}
