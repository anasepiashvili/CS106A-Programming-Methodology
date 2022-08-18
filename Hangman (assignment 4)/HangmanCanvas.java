
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Font;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	private GLabel hiddenWord;
	private GLabel wrongInput;
	private static int counter = 0;
	private static int y = 0;

	public void drawGallow() {
		drawScaffold();
		drawBeam();
		drawRope();
	}

	private void drawScaffold() {
		int x = getWidth() / 2 - BEAM_LENGTH;
		int y1 = getHeight() / 2 - 2 * HEAD_RADIUS - BODY_LENGTH / 2 - ROPE_LENGTH - MOVE;
		int y2 = y1 + SCAFFOLD_HEIGHT;

		GLine scaffold = new GLine(x, y1, x, y2);
		add(scaffold);
	}

	private void drawBeam() {
		int x1 = getWidth() / 2 - BEAM_LENGTH;
		int x2 = getWidth() / 2;
		int y = getHeight() / 2 - 2 * HEAD_RADIUS - BODY_LENGTH / 2 - ROPE_LENGTH - MOVE;

		GLine beam = new GLine(x1, y, x2, y);
		add(beam);
	}

	private void drawRope() {
		int x = getWidth() / 2;
		int y1 = getHeight() / 2 - 2 * HEAD_RADIUS - BODY_LENGTH / 2 - ROPE_LENGTH - MOVE;
		int y2 = y1 + ROPE_LENGTH;

		GLine rope = new GLine(x, y1, x, y2);
		add(rope);
	}

	private void drawHead() {
		int x = getWidth() / 2 - HEAD_RADIUS;
		int y = getHeight() / 2 - BODY_LENGTH / 2 - HEAD_RADIUS * 2 - MOVE;

		GOval head = new GOval(HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		add(head, x, y);
	}

	private void drawBody() {
		int x = getWidth() / 2;
		int y1 = getHeight() / 2 - BODY_LENGTH / 2 - MOVE;
		int y2 = getHeight() / 2 + BODY_LENGTH / 2 - MOVE;

		GLine body = new GLine(x, y1, x, y2);
		add(body);
	}

	private void drawLeftHand() {
		int x1 = getWidth() / 2 - UPPER_ARM_LENGTH;
		int x2 = getWidth() / 2;
		int y1 = getHeight() / 2 - BODY_LENGTH / 2 + ARM_OFFSET_FROM_HEAD - MOVE;
		int y2 = y1 + LOWER_ARM_LENGTH;

		GLine upperHand = new GLine(x1, y1, x2, y1);
		add(upperHand);

		GLine lowerHand = new GLine(x1, y1, x1, y2);
		add(lowerHand);
	}

	private void drawRightHand() {
		int x1 = getWidth() / 2;
		int x2 = getWidth() / 2 + UPPER_ARM_LENGTH;
		int y1 = getHeight() / 2 - BODY_LENGTH / 2 + ARM_OFFSET_FROM_HEAD - MOVE;
		int y2 = y1 + LOWER_ARM_LENGTH;

		GLine upperHand1 = new GLine(x1, y1, x2, y1);
		add(upperHand1);

		GLine lowerHand1 = new GLine(x2, y1, x2, y2);
		add(lowerHand1);
	}

	private void drawLeftLeg() {
		int x1 = getWidth() / 2 - HIP_WIDTH;
		int x2 = getWidth() / 2;
		int y1 = getHeight() / 2 + BODY_LENGTH / 2 - MOVE;
		int y2 = y1 + LEG_LENGTH;

		GLine hip = new GLine(x1, y1, x2, y1);
		add(hip);

		GLine leg = new GLine(x1, y1, x1, y2);
		add(leg);
	}

	private void drawRightLeg() {
		int x1 = getWidth() / 2;
		int x2 = getWidth() / 2 + HIP_WIDTH;
		int y1 = getHeight() / 2 + BODY_LENGTH / 2 - MOVE;
		int y2 = y1 + LEG_LENGTH;

		GLine hip1 = new GLine(x1, y1, x2, y1);
		add(hip1);

		GLine leg1 = new GLine(x2, y1, x2, y2);
		add(leg1);
	}

	private void drawLeftFoot() {
		int x1 = getWidth() / 2 - HIP_WIDTH - FOOT_LENGTH;
		int x2 = getWidth() / 2 - HIP_WIDTH;
		int y = getHeight() / 2 + BODY_LENGTH / 2 + LEG_LENGTH - MOVE;

		GLine foot = new GLine(x1, y, x2, y);
		add(foot);
	}

	private void drawRightFoot() {
		int x1 = getWidth() / 2 + HIP_WIDTH;
		int x2 = getWidth() / 2 + HIP_WIDTH + FOOT_LENGTH;
		int y = getHeight() / 2 + BODY_LENGTH / 2 + LEG_LENGTH - MOVE;

		GLine foot1 = new GLine(x1, y, x2, y);
		add(foot1);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		counter++;
		if (counter == 1) {
			hiddenWord = new GLabel(word);
			hiddenWord.setFont("calibri-35");
			add(hiddenWord, 50, 400);
		} else {
			remove(hiddenWord);
			hiddenWord = new GLabel(word);
			hiddenWord.setFont("calibri-35");
			add(hiddenWord, 50, 400);
		}
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(String input, int N) {
		y += 18;
		wrongInput = new GLabel(input);
		wrongInput.setFont("calibri-20");
		add(wrongInput, 40 + y, 430);
		if (N == 7) {
			drawHead();
		}
		if (N == 6) {
			drawBody();
		}
		if (N == 5) {
			drawLeftHand();
		}
		if (N == 4) {
			drawRightHand();
		}
		if (N == 3) {
			drawLeftLeg();
		}
		if (N == 2) {
			drawRightLeg();
		}
		if (N == 1) {
			drawLeftFoot();
		}
		if (N == 0) {
			drawRightFoot();
		}
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int MOVE = 65;

}
