package com.msip.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

public class SurveyTable {
	private String dateString = null;

	

	/**
	 * Creates the Survey table if it doesn't all ready exist.
	 */
	public SurveyTable() {
		try {
			PreparedStatement createTable = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("CREATE TABLE IF NOT EXISTS Survey(ID INT AUTO_INCREMENT, Question VARCHAR(400), StartDate DATE NOT NULL,"
							+ " Response1 INT, Response2 INT, Response3 INT, Response4 INT, Response5 INT, PRIMARY KEY(ID))");
			createTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds the question to the Survey table.
	 * 
	 * @param question
	 */
	public void addQuestion(String question, Date startDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 dateString = formatter.format(startDate);
		try {	
			PreparedStatement addQuestion = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("INSERT INTO Survey VALUE(NULL, '"
							+ question + "', '" + dateString
							+ "' , 0, 0, 0, 0, 0);");
		addQuestion.executeUpdate();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * returns the last inserted ID.
	 * @return
	 */
	public int getID(){
		ArrayList<Integer> id = new ArrayList<Integer>();
		try {
			PreparedStatement myID = (PreparedStatement) DBConnector.myConnection.prepareStatement("SELECT ID FROM SURVEY;");
			ResultSet rs = myID.executeQuery();
			while(rs.next()){
				id.add(rs.getInt("ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id.get(id.size() -1);
	}
	
	/**
	 * Adds the last question result to the survey table.
	 */
	public void addResults(int result) {
		// TODO get add results to work.

		
		try {
			
			if (result == 1) {//when user chooses a rating of 1 it will update the response1 column
				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response1= Response1 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();
				
			} else if (result == 2) { //when user chooses a rating of 2 it will update the response2 column
				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response2= Response2 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();

			} else if (result == 3) {//when user chooses a rating of 3 it will update the response3 column

				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response3= Response3 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();
			} else if (result == 4) {//when user chooses a rating of 4 it will update the response4 column

				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response4= Response4 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();
				
			} else {//when user chooses a rating of 5 it will update the response5 column
				PreparedStatement insert = (PreparedStatement) DBConnector.myConnection
						.prepareStatement("UPDATE Survey SET Response5= Response5 + 1 WHERE ID='"+getID()+"';");
				insert.executeUpdate();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Removes the survey question based on the date.
	 * 
	 * @param endDate
	 */
	public void removeFromSurveyTable(Date endDate) {
		try {
			PreparedStatement delete = (PreparedStatement) DBConnector.myConnection
					.prepareStatement("DELETE FROM Survey WHERE Date ='"
							+ endDate + "';");
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Clears the survey Table.
	 */
	public void removeAll(){
		//TODO Reset the ID to start from 0.
		try {
			PreparedStatement removeAll = (PreparedStatement) DBConnector.myConnection.prepareStatement("DELETE FROM Survey");
			removeAll.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
