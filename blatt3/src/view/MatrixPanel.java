package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Matrix;

/**
 * @author Nicolas Neubauer
 * 
 */
@SuppressWarnings("serial")
public class MatrixPanel extends JPanel {

	// TextFields
	private JTextField[][] textFields;

	// MatrixList
	private JList<Matrix> matrixList;

	// Buttons
	private JButton addButton;
	private JButton clearButton;

	/**
	 * Erzeugt ein neues Panel mit der Möglichkeit zur Matrizeneingabe und
	 * Listendarstellung.
	 */
	public MatrixPanel() {
		this.setLayout(new BorderLayout());

		// Create Label
		JLabel l = new JLabel("Matrix-Stack");
		this.add(l, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		// Create 3x3 TextFields in new JPanel
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(3, 3));

		textFields = new JTextField[3][3];

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				textFields[row][col] = new JTextField();
				fieldPanel.add(textFields[row][col]);
			}
		}

		centerPanel.add(fieldPanel, BorderLayout.NORTH);

		// Matrix List
		matrixList = new JList<>();
		matrixList.setPreferredSize(new Dimension(300, 0));
		matrixList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		centerPanel.add(matrixList, BorderLayout.CENTER);

		this.add(centerPanel, BorderLayout.CENTER);

		// Buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());

		addButton = new JButton("Hinzufuegen");
		clearButton = new JButton("Leeren");

		buttonPanel.add(addButton, BorderLayout.WEST);
		buttonPanel.add(clearButton, BorderLayout.EAST);

		this.add(buttonPanel, BorderLayout.SOUTH);

	}

	/**
	 * @return the textFields
	 */
	public JTextField[][] getTextFields() {
		return textFields;
	}

	/**
	 * @return the matrixList
	 */
	public JList<Matrix> getMatrixList() {
		return matrixList;
	}

	/**
	 * @return the addButton
	 */
	public JButton getAddButton() {
		return addButton;
	}

	/**
	 * @return the clearButton
	 */
	public JButton getClearButton() {
		return clearButton;
	}

}
