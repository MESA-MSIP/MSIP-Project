package com.msip.db;

import com.msip.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
	
	
	public Student() {
	
		try {
			PreparedStatement createTable = DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Student(Knumber INT NOT NULL, FirstName VARCHAR(35) NOT NULL,"
							+ "LastName VARCHAR(35) NOT NULL, Major VARCHAR(35) NOT NULL, PRIMARY KEY(Knumber))");
			createTable.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Adds student to the student database.
	 * 
	 * @param Knumber
	 * @param firstName
	 * @param lastName
	 */
	public static void add(int Knumber, String firstName, String lastName,
			String Major) {
		try {
			PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO Student VALUE('" + Knumber
							+ "',  '" + firstName + "','" + lastName + "', '"
							+ Major + "');");
			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes student from student database.
	 * 
	 * @param Knumber
	 */
	public static void remove(int Knumber) {
		try {
			PreparedStatement delete = DBConnector.myConnection
					.prepareStatement("DELETE FROM Student WHERE Knumber='"
							+ Knumber + "';");
			delete.executeUpdate();
		} catch (SQLException e) {

		}
	}

	/**
	 * Updates any student changes.
	 * 
	 * @param Knumber
	 *            Major
	 * @return student
	 */
	public Student modify(int Knumber, String Major) {
		Student student = null;
		try {
			PreparedStatement updateTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("UPDATE student SET major='" + Major
							+ "' WHERE Knumber='" + Knumber + "';");
			updateTable.executeUpdate();

			PreparedStatement studentInfo = DBConnector.myConnection
					.prepareStatement("SELECT * FROM student WHERE Knumber='"
							+ Knumber + "';");
			ResultSet rs = studentInfo.executeQuery();
			String s = "";
			
			while (rs.next()) {
				s = rs.getString(Knumber);
				s += rs.getNString("major");
				System.out.println(s);
				student = new Student(rs.getNString("FirstName"), rs.getNString("LastName"), rs.getNString("Knumber"), rs.getString("Major"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;

	}

}
