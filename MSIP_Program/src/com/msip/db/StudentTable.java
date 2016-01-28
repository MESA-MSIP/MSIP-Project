package com.msip.db;

import com.msip.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentTable {

	/**
	 * Creates the StudentTable if it doesn't exist.
	 */
	public StudentTable() {
		try {
			PreparedStatement createTable = DBConnector.myConnection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS Student(Knumber INT NOT NULL, FirstName VARCHAR(35) NOT NULL,"
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
	 * @throws SQLException
	 */
	public void add(int Knumber, String firstName, String lastName, String Major) throws SQLException {
		PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
				.prepareStatement("INSERT INTO Student VALUE('" + Knumber + "',  '" + firstName + "','" + lastName
						+ "', '" + Major + "');");
		insert.executeUpdate();
	}

	/**
	 * Removes student from student database.
	 * 
	 * @param Knumber
	 * @throws SQLException
	 */
	public void remove(int Knumber) throws SQLException {
		PreparedStatement delete = DBConnector.myConnection
				.prepareStatement("DELETE FROM Student WHERE Knumber='" + Knumber + "';");
		delete.executeUpdate();
	}

	/**
	 * Deletes all rows from Student table.
	 */
	public void deleteAll() {
		try {
			PreparedStatement deletAll = DBConnector.myConnection.prepareStatement("DELETE FROM Student");
			deletAll.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates any student changes.
	 * 
	 * @param Knumber
	 *            Major
	 * @return student
	 * @throws SQLException
	 */
	public Student modify(int Knumber, String Major) throws SQLException {
		PreparedStatement updateTable = (PreparedStatement) DBConnector.myConnection
				.prepareStatement("UPDATE Student SET Major='" + Major + "' WHERE Knumber='" + Knumber + "';");
		updateTable.executeUpdate();
		return getInfo(Knumber);
	}

	/**
	 * Gets the current info of a Student.
	 * 
	 * @param Knumber
	 * @return Student
	 * @throws SQLException
	 */
	public Student getInfo(int Knumber) throws SQLException {
		Student student = null;
		PreparedStatement studentInfo;

		studentInfo = DBConnector.myConnection
				.prepareStatement("SELECT * FROM Student WHERE Knumber='" + Knumber + "';");

		ResultSet rs = studentInfo.executeQuery();
		while (rs.next()) {
			student = new Student(rs.getString("FirstName"), rs.getString("LastName"),
					Integer.parseInt(rs.getString("Knumber")), rs.getString("Major"));
		}
		return student;
	}

	/**
	 * Gets all the students and stores it in an ArrayList of students.
	 * 
	 * @return
	 */
	public ArrayList<Student> getAll() {
		ArrayList<Student> allStudents = new ArrayList<Student>();
		Student student = null;
		PreparedStatement studentInfo;
		try {
			studentInfo = DBConnector.myConnection.prepareStatement("SELECT * FROM Student;");

			ResultSet rs = studentInfo.executeQuery();
			while (rs.next()) {
				student = new Student(rs.getString("FirstName"), rs.getString("LastName"),
						Integer.parseInt(rs.getString("Knumber")), rs.getString("Major"));
				allStudents.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allStudents;

	}

}
