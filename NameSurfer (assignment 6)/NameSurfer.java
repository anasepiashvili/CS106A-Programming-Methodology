
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {

		nameField = new JTextField(10);
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");

		// add interactors on south border
		add(new JLabel("Name "), SOUTH);
		add(nameField, SOUTH);
		add(graphButton, SOUTH);
		add(clearButton, SOUTH);

		nameField.addActionListener(this);
		addActionListeners();

		graph = new NameSurferGraph();
		add(graph);

		data = new NameSurferDataBase(NAMES_DATA_FILE);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nameField || e.getSource() == graphButton) {
			String name = nameField.getText(); // typed text
			entry = data.findEntry(name); // finds entry for given name
			if (entry != null) {
				graph.addEntry(entry);
				graph.update(); // updates canvas
			}
		}
		if (e.getSource() == clearButton) {
			graph.clear(); // clears canvas
			graph.update();
		}
	}

	// Private instance variables
	private NameSurferDataBase data;;
	private NameSurferGraph graph;
	private NameSurferEntry entry;
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
}
