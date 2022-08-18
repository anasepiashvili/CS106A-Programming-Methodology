
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		update();
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		graphEntry.clear();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		graphEntry.add(entry);
	}

	// draws lines between gridlines according to statistics
	public void addGraphics(NameSurferEntry entry) {
		for (int i = 0; i < NDECADES - 1; i++) {
			int x1 = i * getWidth() / NDECADES;
			int x2 = x1 + getWidth() / NDECADES;

			if (entry.getRank(i) == 0) {
				int y1 = getHeight() - GRAPH_MARGIN_SIZE;

				if (entry.getRank(i + 1) == 0) {
					int y2 = getHeight() - GRAPH_MARGIN_SIZE;
					statistics = new GLine(x1, y1, x2, y2);
				} else {
					int y2 = GRAPH_MARGIN_SIZE
							+ entry.getRank(i + 1) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
					statistics = new GLine(x1, y1, x2, y2);
				}

			} else {
				int y1 = GRAPH_MARGIN_SIZE + entry.getRank(i) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;

				if (entry.getRank(i + 1) == 0) {
					int y2 = getHeight() - GRAPH_MARGIN_SIZE;
					statistics = new GLine(x1, y1, x2, y2);
				} else {
					int y2 = GRAPH_MARGIN_SIZE
							+ entry.getRank(i + 1) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
					statistics = new GLine(x1, y1, x2, y2);
				}
			}
			add(statistics);
		}
	}

	// adds label that shows name and ranking
	public void addLabel(NameSurferEntry entry) {
		String name = entry.getName();
		for (int i = 0; i < NDECADES; i++) {
			int x = i * getWidth() / 11 + getWidth() / 100;

			if (entry.getRank(i) == 0) {
				int y = getHeight() - GRAPH_MARGIN_SIZE;
				nameLabel = new GLabel(name + " *", x, y);

			} else {
				int y = GRAPH_MARGIN_SIZE + entry.getRank(i) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
				nameLabel = new GLabel(name + " " + entry.getRank(i), x, y);
			}
			add(nameLabel);
		}
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		addBackground();
		for (int i = 0; i < graphEntry.size(); i++) {
			NameSurferEntry nse = graphEntry.get(i); // gets entries saved in graphEntry ArrayList
			// add objects on canvas
			addGraphics(nse);
			addLabel(nse);
		}
	}

	// draws gridlines and writes years
	private void addBackground() {

		for (int i = 0; i < 12; i++) {
			int x = i * getWidth() / NDECADES;
			int y1 = 0;
			int y2 = getHeight();
			GLine vertLines = new GLine(x, y1, x, y2);
			add(vertLines);

			int age = START_DECADE + i * 10;
			String s = Integer.toString(age);
			GLabel decade = new GLabel(s);
			add(decade, x + getWidth() / 150, getHeight() - GRAPH_MARGIN_SIZE + decade.getAscent());
		}

		// two horizontal lines of margins
		GLine topLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine bottomLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);

		add(topLine);
		add(bottomLine);
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	private GLine statistics;
	private GLabel nameLabel;
	private ArrayList<NameSurferEntry> graphEntry = new ArrayList<NameSurferEntry>();
}
