
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.io.IOException;

public class Hangman extends ConsoleProgram {

	private HangmanCanvas canvas;
	private HangmanLexicon lexicon;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private static int N = 8; //number of tries player has
	private String word;
	private String hyphens;
	private String input;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
		lexicon = new HangmanLexicon();
		hyphens = "";
		word = "";
		input = "";
	}

	public void run() {
		// randomly chooses a word from given file
		int x = rgen.nextInt(0, lexicon.getWordCount() - 1); 
		word = lexicon.getWord(x);
		canvas.displayWord(hyphens);
		canvas.drawGallow();
		opening(word);
		inputLetter(word);
	}

	private void opening(String word2) {
		println("Welcome to Hangman!");
		//changes letters with hyphens
		for (int i = 0; i < word.length(); i++) {
			hyphens += "-";
		}
		//adds hyphens and letters on canvas
		canvas.displayWord(hyphens);

		println("The word now looks like this: " + hyphens);
		println("You have " + N + " guesses left.");
	}

	// player inputs letters until they win or lose
	private void inputLetter(String word2) {
		while (N > 0) {
			input = readLine("Your guess: ");
			input = input.toUpperCase(); 
			if (correct(input, word)) {
				correctLetter(input, word);
				canvas.displayWord(hyphens);
				if (winGame(hyphens)) {
					println("You guessed the word: " + word);
					println("You win.");
					break;
				}
			}
			if (wrong(input, word)) {
				wrongLetter(input, word);
				canvas.noteIncorrectGuess(input, N);
			}
			if (notLetter(input)) {
				wrongSymbol();
			}
		}
	}

	private void correctLetter(String input, String word2) {
		println("That guess is correct.");
		for (int i = 0; i < word.length(); i++) {
			//writes correct letters instead of hyphens
			if (input.charAt(0) == word.charAt(i)) {
				hyphens = hyphens.substring(0, i) + word.charAt(i) + hyphens.substring(i + 1);
			}
		}
		// until player guesses full word
		if (!winGame(hyphens)) {
			println("The word now looks like this: " + hyphens);
			println("You have " + N + " guesses left.");
		}
	}

	private void wrongLetter(String input, String word2) {
		N--; 
		// lose game
		if (N == 0) {
			println("You're completely hung.");
			println("The word was: " + word);
			println("You lose.");
		} else {
			println("There are no " + input + "\'s in the word.");
			println("The word now looks like this: " + hyphens);
			println("You have " + N + " guesses left.");
		}
	}

	private void wrongSymbol() {
		println("You must enter a letter.");
		inputLetter(word);
	}

	//checks if word contains given letter
	private boolean correct(String input, String word2) {
		return input.length() == 1 && word.contains(input);
	}

	private boolean wrong(String input, String word2) {
		return input.length() == 1 && !correct(input, word) && Character.isLetter(input.charAt(0));
	}

	private boolean notLetter(String input) {
		return !Character.isLetter(input.charAt(0));
	}

	// tells if full word is guessed or not
	private boolean winGame(String hyphens2) {
		return !hyphens.contains("-");
	}
}
