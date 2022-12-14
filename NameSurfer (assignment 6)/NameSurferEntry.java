
/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears in the data
	 * file. Each line begins with the name, which is followed by integers giving
	 * the rank of that name for each decade.
	 */
	public NameSurferEntry(String line) {
		int startIndex = line.indexOf(" ");
		rankings = line.substring(startIndex + 1); // String of ranks of all decades
		name = line.substring(0, startIndex);
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular decade. The decade
	 * value is an integer indicating how many decades have passed since the first
	 * year in the database, which is given by the constant START_DECADE. If a name
	 * does not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		String[] ranksArr = rankings.split(" "); // elements of this array are ranks as Strings
		ranksInt = new int[NDECADES]; // array of ints
		for (int i = 0; i < NDECADES; i++) {
			ranksInt[i] = Integer.parseInt(ranksArr[i]); // each rank is a different int element of ranksInt array
		}
		return ranksInt[decade];
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a NameSurferEntry.
	 */
	public String toString() {
		return ("\"" + name + " [" + rankings + "] \"");
	}

	private String name;
	private String rankings;
	private int[] ranksInt;

}
