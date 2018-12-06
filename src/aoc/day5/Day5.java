package aoc.day5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * --- Day 5: Alchemical Reduction --- You've managed to sneak in to the
 * prototype suit manufacturing lab. The Elves are making decent progress, but
 * are still struggling with the suit's size reduction capabilities.
 * 
 * While the very latest in 1518 alchemical technology might have solved their
 * problem eventually, you can do better. You scan the chemical composition of
 * the suit's material and discover that it is formed by extremely long polymers
 * (one of which is available as your puzzle input).
 * 
 * The polymer is formed by smaller units which, when triggered, react with each
 * other such that two adjacent units of the same type and opposite polarity are
 * destroyed. Units' types are represented by letters; units' polarity is
 * represented by capitalization. For instance, r and R are units with the same
 * type but opposite polarity, whereas r and s are entirely different types and
 * do not react.
 * 
 * For example:
 * 
 * In aA, a and A react, leaving nothing behind. In abBA, bB destroys itself,
 * leaving aA. As above, this then destroys itself, leaving nothing. In abAB, no
 * two adjacent units are of the same type, and so nothing happens. In aabAAB,
 * even though aa and AA are of the same type, their polarities match, and so
 * nothing happens. Now, consider a larger example, dabAcCaCBAcCcaDA:
 * 
 * dabAcCaCBAcCcaDA The first 'cC' is removed. dabAaCBAcCcaDA This creates 'Aa',
 * which is removed. dabCBAcCcaDA Either 'cC' or 'Cc' are removed (the result is
 * the same). dabCBAcaDA No further actions can be taken. After all possible
 * reactions, the resulting polymer contains 10 units.
 * 
 * How many units remain after fully reacting the polymer you scanned?
 */

public class Day5 {

	public void solve() throws IOException {
		String polymer;
		polymer = generatePolymer(null);
		System.out.println(
				"Part 1, How many units remain after fully reacting the polymer you scanned?: " + polymer.length());

		String input = Files.readAllLines(new File("inputs/input_day5.txt").toPath()).get(0);
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		int smallerPolymerSize = Integer.MAX_VALUE;
		String reducedInput;
		for (int i = 0; i < alphabet.length; i++) {
			reducedInput = input.replaceAll(String.valueOf(alphabet[i]), "");
			reducedInput = reducedInput.replaceAll(String.valueOf(Character.toUpperCase(alphabet[i])), "");

			polymer = generatePolymer(reducedInput);
			if (polymer.length() < smallerPolymerSize) {
				smallerPolymerSize = polymer.length();
			}
		}
		System.out
				.println("Part 2, What is the length of the shortest polymer you can produce?: " + smallerPolymerSize);
	}

	public String generatePolymer(String input) throws IOException {
		if (input == null) {
			input = Files.readAllLines(new File("inputs/input_day5.txt").toPath()).get(0);
		}
		
		StringBuilder polymer = new StringBuilder(100000);
		char token, next = '.';
		boolean foundReactions = false;

		for (int i = 0; i < input.length() - 1; i++) {
			token = input.charAt(i);
			next = input.charAt(i + 1);

			// test for lower case or upper case being the same letter using XOR 32
			if ((token ^ next) == 32) {
				i++;
				foundReactions = true;
			} else {
				polymer.append(token);
			}
		}
		polymer.append(next);

		if (!foundReactions) {
			return polymer.toString();
		}

		return generatePolymer(polymer.toString());
	}
}
