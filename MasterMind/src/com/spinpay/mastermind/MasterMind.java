package com.spinpay.mastermind;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * <p>
 * Master Mind Game
 * </p>
 *
 * @author <a href="mailto:willian963@gmail.com">Willian Ribeiro</a>
 *
 */

public class MasterMind {

	public static void main(String[] args) throws IOException {
		solutionPattern();
	}

	/**
	 * Game init
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	static void solutionPattern() throws IOException {
		boolean continueGame = false;
		while (!continueGame) {
			int patternLength = 3;// generateRandomNumber();
			int numberGuess = 3;// generateRandomNumber();
			int pattern[] = generatePattern(patternLength);
			int guess[] = new int[patternLength];

			System.out.println("Pattern length is " + patternLength + ". Maximum number of guesses is " + numberGuess);
			int correct = 0;
			int correctPossition = 0;
			HashMap<Integer, Integer> mapCorrect = new HashMap<Integer, Integer>();
			Scanner input = new Scanner(System.in);
			boolean validateFlag = false;

			if (null != pattern) {
				for (int guessLoop = 1; guessLoop <= numberGuess; guessLoop++) {
					while (!validateFlag) {
						try {
							System.out.print("Please enter an guess: ");
							String inputGuess = input.nextLine();
							validateFlag = validateCriteria(pattern, guess, numberGuess, inputGuess);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}

					for (int i = 0; i < pattern.length; i++) {
						for (int j = 0; j < guess.length; j++) {
							if (pattern[i] == guess[j] && i == j) {
								correctPossition++;
							}
							if (pattern[i] == guess[j] && !mapCorrect.containsKey(pattern[i])) {
								mapCorrect.put(pattern[i], pattern[i]);
								correct++;
							}

						}
					}
					if (isCodeBreaker(pattern, correct, correctPossition, guessLoop, numberGuess)) {
						break;
					} else {
						correct = 0;
						correctPossition = 0;
						mapCorrect = new HashMap<Integer, Integer>();
						validateFlag = false;
					}
				}

			}
			System.out.println("\n");
			if (!shouldContinueGame()) {
				continueGame = true;
				System.out.println("\n");
				System.out.println("Thank you for playing Master Mind :) ...");
				System.out.println("@WA RIBEIRO SERVICOS EM INFORMATICA LTDA");
			}
			clearScreen();
			System.out.println("\n");
		}
	}

	/**
	 * Clear Screen
	 * @throws IOException
	 */
	public static void clearScreen() throws IOException {

		try {
			if (System.getProperty("os.name").startsWith("Window")) {
				Runtime.getRuntime().exec("cls");
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (final Exception e) {

		}
	}

	/**
	 * Method to generate random number.
	 * @return
	 */
	static int generateRandomNumber() {
		int maxDigitPattern = 6;
		boolean ext = false;
		int result = 0;
		while (!ext) {
			Random rand = new Random();
			int value = rand.nextInt(maxDigitPattern);
			if (value != 0) {
				result = value;
				ext = true;
			}
		}
		return result;
	}

	/**
	 * Method to generate pattern.
	 * @param number
	 * @return
	 */
	static int[] generatePattern(int number) {
		int pattern[] = new int[number];
		int maxDigitPattern = 6;
		HashMap<Integer, Integer> mapPattern = new HashMap<Integer, Integer>();

		for (int i = 0; i < number; i++) {
			boolean ext = false;
			while (!ext) {
				Random rand = new Random();
				int value = rand.nextInt(maxDigitPattern);
				if (!mapPattern.containsKey(value) && value != 0) {
					mapPattern.put(value, value);
					pattern[i] = value;
					ext = true;
				}
			}
		}
		return pattern;
	}

	/**
	 * Method with all validates.
	 * @param pattern
	 * @param guess
	 * @param numberGuess
	 * @param inputGuess
	 * @return
	 */
	static boolean validateCriteria(int[] pattern, int[] guess, int numberGuess, String inputGuess) {
		validateGuessInput(pattern, guess, inputGuess);
		validateLength(pattern, guess, inputGuess);
		return true;
	}

	/**
	 * Validate Input
	 * @param pattern
	 * @param guess
	 * @param inputGuess
	 * @return
	 */
	static boolean validateGuessInput(int[] pattern, int[] guess, String inputGuess) {
		boolean result = false;
		if (pattern.length == inputGuess.length()) {
			for (int i = 0; i <= inputGuess.length(); i++) {
				if (i + 1 <= inputGuess.length()) {
					Integer value = new Integer(inputGuess.substring(i, i + 1));
					if (value > 0 && value <= 6) {
						guess[i] = value;
					} else {
						throw new RuntimeException("Error: Guess number out of range! Number: " + value);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Validate pattern length
	 * @param pattern
	 * @param guess
	 * @param inputGuess
	 * @return
	 */
	static boolean validateLength(int[] pattern, int[] guess, String inputGuess) {
		boolean result = false;
		if (null != guess && pattern.length == inputGuess.length()) {
			result = true;
		} else {
			throw new RuntimeException("Error: Invalid Guess Length! The length should be " + pattern.length);
		}
		return result;
	}

	/**
	 * Validate if the Code was broken.
	 * @param pattern
	 * @param correct
	 * @param correctPossition
	 * @param numberLoop
	 * @param numberGuess
	 * @return
	 */
	static boolean isCodeBreaker(int[] pattern, int correct, int correctPossition, int numberLoop, int numberGuess) {
		boolean result = false;
		if (pattern.length == correctPossition && correct == correctPossition) {
			System.out.println(
					"You broke the code in " + numberLoop + " guesses!" + " Pattern: " + printPattern(pattern) + ".");
			result = true;
		} else {
			System.out.println(correct + " correct, " + correctPossition + " correct position");
			if (numberGuess == numberLoop) {
				System.out.println("You were unable to break the code in " + numberLoop + " guesses. Code pattern is: "
						+ printPattern(pattern) + ".");
			}
		}
		return result;
	}
	
	/**
	 * Method print the pattern
	 * @param pattern
	 * @return
	 */
	static String printPattern(int[] pattern) {
		String result = "";
		for (int i = 0; i < pattern.length; i++) {
			result = result.concat(new Integer(pattern[i]).toString());
		}
		return result;
	}

	/**
	 * Method to validate if player will be continue playing
	 * @return
	 */
	@SuppressWarnings("resource")
	static boolean shouldContinueGame() {
		boolean sContinue = false;
		boolean result = false;
		while (!sContinue) {
			Scanner input = new Scanner(System.in);
			System.out.print("Would you like to continue playing: Y or N ");
			String inputGuess = input.nextLine();

			if (inputGuess.toUpperCase().equals("Y")) {
				result = true;
				sContinue = true;
			}
			if (inputGuess.toUpperCase().equals("N")) {
				result = false;
				sContinue = true;
			}
			if (!inputGuess.toUpperCase().equals("Y") && !inputGuess.toUpperCase().equals("N")) {
				System.out.println("The answer should be Y or N. ");
			}
		}
		return result;
	}
}