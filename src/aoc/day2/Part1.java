package aoc.day2;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * To make sure you didn't miss any, you scan the likely candidate boxes again, counting the number that have an ID
 * containing exactly two of any letter and then separately counting those with exactly three of any letter. You can
 * multiply those two counts together to get a rudimentary checksum and compare it to what your device predicts.
 *
 * For example, if you see the following box IDs:
 *
 * abcdef contains no letters that appear exactly two or three times.
 * bababc contains two a and three b, so it counts for both.
 * abbcde contains two b, but no letter appears exactly three times.
 * abcccd contains three c, but no letter appears exactly two times.
 * aabcdd contains two a and two d, but it only counts once.
 * abcdee contains two e.
 * ababab contains three a and three b, but it only counts once.
 * Of these box IDs, four of them contain a letter which appears exactly twice, and three of them contain a letter
 * which appears exactly three times. Multiplying these together produces a checksum of 4 * 3 = 12.
 *
 * What is the checksum for your list of box IDs?
 */

public class Part1 {

    int countAppearsTwice = 0;
    int countAppearsThrice = 0;

    public int solve(String fileName){
        HashMap<Character, Integer> charCountMap ;

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()){
                charCountMap =  countChars(scanner.nextLine());

                incrementTwiceAndThrice(charCountMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        int checksum = countAppearsTwice * countAppearsThrice;
        System.out.println("The checksum is: "+checksum);

        return checksum;
    }

    private void incrementTwiceAndThrice(HashMap<Character, Integer> charCountMap) {
        int count;
        Set<Character> chars = charCountMap.keySet();
        boolean foundTwice = false, foundThrice = false;

        for (char c : chars) {
            count = charCountMap.get(c);

            if(!foundTwice && count == 2){
                countAppearsTwice ++;
                foundTwice = true;
            } else if(!foundThrice &&count == 3){
                countAppearsThrice ++;
                foundThrice = true;
            }
        }
    }

    private  HashMap<Character, Integer> countChars(String line) {
        HashMap<Character, Integer> charCountMap  = new HashMap<>();

        for (char c : line.toCharArray()) {
            if (charCountMap.containsKey(c)) {
                charCountMap.put(c, charCountMap.get(c) + 1);
            } else {
                charCountMap.put(c, 1);
            }
        }
        return charCountMap;
    }

}
