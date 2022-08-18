
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {

	private ArrayList<String> list;

	// This is the HangmanLexicon constructor
	public HangmanLexicon() {
		BufferedReader hl = null;
		list = new ArrayList<String>();
		try {

			hl = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
				String newLine = hl.readLine();
				if (newLine != null) {
					list.add(newLine);
				} else {
					break;
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// rest of HangmanLexicon class...

	/**
	 * Returns the number of words in the lexicon.
	 */
	public int getWordCount() {
		return list.size();
	}

	/**
	 * Returns the word at the specified index.
	 * 
	 */
	public String getWord(int index) {
		return list.get(index);
	}
}
