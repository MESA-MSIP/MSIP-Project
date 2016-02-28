package com.msip.db;

import java.sql.SQLException;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

public class SurveyTable {
	
	/**
	 * Creates the Survey table if it doesn't all ready exist.
	 */
	public SurveyTable(){
		try {
			PreparedStatement createTable = (PreparedStatement) 
					DBConnector.myConnection.prepareStatement("CREATE TABLE IF NOT EXISTS Survey(ID INT AUTO_INCREMENT, Question VARCHAR(400), StartDate DATE NOT NULL,"
							+ " Response1 INT NUll, Response2 INT NUll, Response3 INT Null, Response4 INT NUll, Response5 INT Null, PRIMARY KEY(ID))");
			createTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds the question to the Survey table.
	 * @param question
	 */
	public void addQuestion(String question){
		try {
			PreparedStatement addQuestion = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO Survey VALUE(NULL, '" + question
							+ "', NOW(), NULL, NULL, NULL, NULL, NULL);");
			addQuestion.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds the question result to the survey table.
	 */
	public void addResults(){
		//TODO are the results scaled from one to five.
		//TODO will the table keep track of the number of students who have participated in the question.
	}
	/**
	 * Removes the survey question based on the date.
	 * @param endDate
	 */
	public void removeFromSurveyTable(Date endDate){
		try {
			PreparedStatement delete = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("DELETE FROM Survey WHERE Date ='"
							+ endDate + "';");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
