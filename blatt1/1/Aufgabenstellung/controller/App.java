package controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * @author Nicolas Neubauer
 * 
 *         Die App-Klasse initiiert die Applikation mit einem JFrame. Diesem
 *         wird die View des ersten (und hier einzigen) Controllers hinzugefügt.
 * 
 */
public class App {

	/**
	 * Startet die Applikation
	 * 
	 * @param args
	 *            werden nicht genutzt.
	 */
	public static void main(String[] args) {

		JFrame frame = new JFrame("MVC-Beispiel");

		ZaehlerController c = new ZaehlerController();

		frame.add(c.getView(), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

}
