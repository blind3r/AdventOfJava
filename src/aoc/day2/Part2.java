package aoc.day2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Confident that your list of box IDs is complete, you're ready to find the boxes full of prototype fabric.
 * <p>
 * The boxes will have IDs which differ by exactly one character at the same position in both strings. For example,
 * given the following box IDs:
 * <p>
 * abcde
 * fghij
 * klmno
 * pqrst
 * fguij
 * axcye
 * wvxyz
 * The IDs abcde and axcye are close, but they differ by two characters (the second and fourth). However, the IDs fghij
 * and fguij differ by exactly one character, the third (h and u). Those must be the correct boxes.
 * <p>
 * What letters are common between the two correct box IDs? (In the example above, this is found by removing the
 * differing character from either ID, producing fgij.)
 */

public class Part2 {

    List<String> allWords = new ArrayList<>();

    public void solve(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {

            while (scanner.hasNext()) {
                allWords.add(scanner.nextLine());
            }

            for (int i = 0; i < allWords.size(); i++) {
                compareToRemaining(allWords.get(i), i, allWords);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void compareToRemaining(String word, int i, List<String> allWords) {
        for (int j = i; j < allWords.size(); j++) {
            compareWordToWord(word, allWords.get(j));
        }
    }

    private void compareWordToWord(String word1, String word2) {
        String solution;
        int countDifferent = 0;
        int differencePosition = -1;

        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                countDifferent++;
                differencePosition = i;
            }
        }
        if (countDifferent == 1) {
            solution = buildCorrectId(word1, differencePosition);
            System.out.println("The solution to part2 is: " + solution);
        }
    }


    private String buildCorrectId(String word, int position) {
        String solution = "";
        for (int i = 0; i < word.length(); i++) {
            if (i != position) {
                solution += word.charAt(i);
            }
        }
        return solution;
    }
}
