/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ZaehlerModel;
import view.ZaehlerView;

/**
 * @author Nicolas Neubauer
 * 
 *         Der Controller, der die ZaehlerView nutzt und verwaltet, so dass sich
 *         das ZaehlerModel ensprechend ändert oder dessen Änderungen an die
 *         ZaehlerView propagiert werden. (Noch zu implementieren)
 * 
 */
public class ZaehlerController {

	// Model und View
	private ZaehlerView view;
	private ZaehlerModel model;
	
 /**
 *  Hier soll die View und das Model angelegt werden. Außerdem müssen die
 *  View-Components mit über entsprechende Listener mit Funktionalität versehen werden. 
 */
	public ZaehlerController() {

		// Lege View an
		view = new ZaehlerView();
		// Lege Model an
		model = new ZaehlerModel(50, 0, 100);

		// Erstelle ActionListener
		// Hier ein Beispiel für den Erhoehe-Button, der das Model verändert,
		// jedoch die View nicht ändert. Dies muss noch mittels des Observer/Observable
		// Patterns implementiert werden. Außerdem müssen natürlich weitere Listener
		// für die anderen Aktionen implementiert werden. (Siehe Aufgabentext)
		ActionListener a = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Button
				if (e.getSource() == view.getUp()) {
						model.setWert(model.getWert() + 1);
				}
			}
		};

		// Hänge die Listener an die View-Componenten
		view.getUp().addActionListener(a);

	}

	/**
	 * Gibt die View zurück (JPanel)
	 * 
	 * @return the view
	 */
	public ZaehlerView getView() {
		return view;
	}

}
