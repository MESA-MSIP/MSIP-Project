/**
 * 
 */
package com.msip.ui;

import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.msip.manager.MISPCore;

/**
 * @author Celina
 *
 */
public class GraphReport extends ApplicationFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int[] arrayAnswers = new int[5];
	static MISPCore manager;

	/**
	 * Default constructor.
	 *
	 * @param title
	 *            the frame title.
	 */
	public GraphReport(String title) {
		super(title);
		setContentPane(createPiePanel());
	}

	//
	// private int[] analyzeAnswerData(ArrayList<Integer> results) {
	// int[] arrayAnswers = new int[5];
	// for (int a = 0; a < arrayAnswers.length; a++) {
	// arrayAnswers[a] = 0;
	// }
	// for (Integer arrA : results) {
	// int answers = getAnswers(arrA.getResults());
	// // arrayAnswers[answers] = arrayAnswers[answers] + 1;
	// System.out.println(answers);
	// }
	// return arrayAnswers;
	//
	// }

	// private int[] analyzeQuestionnData(ArrayList<Integer> results) {
	// int[] arrayQuestions = new int[5];
	// for (int a = 0; a < arrayQuestions.length; a++) {
	// arrayQuestions[a] = 0;
	// }
	// for (Integer arrQ : results) {
	// int questions = getQuestion(arrQ.getResults());
	// // arrayQuestions[questions] = arrayQuestions[question] + 1;
	// System.out.println(questions);
	// }
	// return arrayQuestions;

	// }

	/**
	 * Creates a sample dataset for questionnaire (Hard-coded)
	 * 
	 * @return A sample dataset.
	 */
	private static PieDataset createDataset() {
		DefaultPieDataset datasetQ = new DefaultPieDataset();
		// QuestionnairePanel ssp = new QuestionnairePanel(null);
		// QuestionnairePanel ansQ = new QuestionnairePanel(manager);
		manager = new MISPCore();
		String[] quest = { "Result1", "Result2", "Result3", "Result4",
				"Result5" };
		for (int a = 0; a < 4; a++) {

			datasetQ.setValue(quest[a], manager.getResults().get(a));
			// datasetQ.setValue("Somewhat available", new Double(9.0));
			// datasetQ.setValue("Available ", new Double(11.0));
			// datasetQ.setValue("We have tutors?", new Double(1.0));

		}
		return datasetQ;
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return A chart.
	 */
	private static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart("Questionnaire", // chart
																		// title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");

		plot.setCircular(false);
		plot.setLabelGap(0.02);
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
				"{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat(
						"0%"));
		plot.setLabelGenerator(gen);

		return chart;

	}

	/**
	 * Creates a panel
	 * 
	 * @return A panel.
	 */
	public static JPanel createPiePanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	private void getAnswers() {
		QuestionnairePanel ansQ = new QuestionnairePanel(null);
		// int[] arrayAnswers = new int[5];
		for (int a = 0; a < arrayAnswers.length; a++) {
			arrayAnswers[a] = ansQ.getResult().get(a);
		}

		// ansQ.getResult();

	}

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args
	 *            ignored.
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// StudentSurveyPanel ssp = new StudentSurveyPanel(manager);

		GraphReport pi = new GraphReport("Questionnaire");
		pi.pack();
		RefineryUtilities.centerFrameOnScreen(pi);
		pi.setVisible(true);

	}

}
