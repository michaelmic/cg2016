package view;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * 
 * @author Nicolas Neubauer
 *
 * View-Klasse für das MVC-Beispiel.
 *
 */

@SuppressWarnings("serial")
public class ZaehlerView extends JPanel {

	// View-Components
	private JButton up;
	private JButton down;
	private JLabel result;
	private JSlider slider;
	private JTextField eingabe;

	/**
	 * Legt die View an.
	 */
	
	public ZaehlerView() {
		super();

		//eigenes Layout setzen
		this.setLayout(new GridLayout(0,1));
		
		//View-Componenten initalisieren
		up = new JButton("Erhöhen");
	    down = new JButton("Verringern");
	    result = new JLabel("", JLabel.CENTER);
	    slider = new JSlider();
		eingabe = new JTextField();
		
	    //Schriftart des JLabels
	    Font font = new Font("SansSerif", Font.BOLD, 30);
	    result.setFont(font);
	    
	    //hinzufügen der View-Components
		this.add(up);
		this.add(result);
		this.add(slider);
		this.add(down);
		this.add(eingabe);
	}

	
	/* -------- Getter-Methoden -------- */
	
	/**
	 * @return the up
	 */
	public JButton getUp() {
		return up;
	}

	/**
	 * @return the down
	 */
	public JButton getDown() {
		return down;
	}

	/**
	 * @return the result
	 */
	public JLabel getResult() {
		return result;
	}

	/**
	 * @return the slider
	 */
	public JSlider getSlider() {
		return slider;
	}

	/**
	 * @return the eingabe
	 */
	public JTextField getEingabe() {
		return eingabe;
	}
	
	
}
