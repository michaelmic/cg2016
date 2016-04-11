/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
				try {
					if (e.getSource() == view.getUp()) {
							model.setWert(model.getWert() + 1);
					}
					else if (e.getSource() == view.getDown()) {
							model.setWert(model.getWert() - 1);
					}
					else if (e.getSource() == view.getEingabe()) {
							model.setWert(view.getEingabe().getText());
					}
				} catch (IllegalArgumentException ex) {	}
			}
		};
		
		// Erstelle ChangeListener
		ChangeListener c = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				try {
					if (e.getSource() == view.getSlider()) {
						model.setWert(view.getSlider().getValue());
					}
				} catch (IllegalArgumentException ex) { }
			}
		};
		
		// Erstelle ItemListener
		ItemListener i = new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if ( e.getSource() == view.getCombobox() ) {
				System.out.println("bin in controller z.89");
					String selected = (String) view.getCombobox().getSelectedItem();
					try {
						UIManager.setLookAndFeel(selected);
						SwingUtilities.updateComponentTreeUI(view);
//						view.pack();
					} catch (Exception ex) {
						System.out.println("Exception!!");
					}
				}
			}
		};

		// Hänge die Listener an die View-Componenten
		view.getUp().addActionListener(a);
		view.getDown().addActionListener(a);
		view.getEingabe().addActionListener(a);
		view.getSlider().addChangeListener(c);
		view.getCombobox().addItemListener(i);
		
		// die View soll das Model beobachten
		model.addObserver(view);
		model.setWert(model.getWert()); // die update() von view wird aufgerufen 
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
