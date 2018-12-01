package aoc.day1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
*You notice that the device repeats the same frequency change list over and over. To calibrate the device, you need to
 * find the first frequency it reaches twice.
 *
 * For example, using the same list of changes above, the device would loop as follows:
 *
 * Current frequency  0, change of +1; resulting frequency  1.
 * Current frequency  1, change of -2; resulting frequency -1.
 * Current frequency -1, change of +3; resulting frequency  2.
 * Current frequency  2, change of +1; resulting frequency  3.
 * (At this point, the device continues from the start of the list.)
 * Current frequency  3, change of +1; resulting frequency  4.
 * Current frequency  4, change of -2; resulting frequency  2, which has already been seen.
 * In this example, the first frequency reached twice is 2. Note that your device might need to repeat its list of
 * frequency changes many times before a duplicate frequency is found, and that duplicates might be found while in the
 * middle of processing the list.
 *
 * Here are other examples:
 *
 * +1, -1 first reaches 0 twice.
 * +3, +3, +4, -2, -4 first reaches 10 twice.
 * -6, +3, +8, +5, -6 first reaches 5 twice.
 * +7, +7, -2, -7, -4 first reaches 14 twice.
 * What is the first frequency your device reaches twice?
 */

public class Part2 {

    int frequency = 0;

    List<Integer> frequencies = new ArrayList<>();

    public Integer solve(){
        String fileName = "inputs/input_day1.txt" ;
        int loops = 1000;

        do {
            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNext()) {
                    frequency += Integer.valueOf(scanner.nextLine());

                    if (!frequencies.contains(frequency)) {
                        frequencies.add(frequency);
                    } else {
                        return frequency;
                    }
                }
                loops --;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(loops > 0);

        return null;
    }
}
