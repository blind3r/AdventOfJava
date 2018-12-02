package aoc.day2;

public class Day2 {

    final static String FILE_NAME = "inputs/input_day2.txt";

    public void solve() {
        Part1 part1 = new Part1();
        part1.solve(FILE_NAME);

        Part2 part2 = new Part2();
        part2.solve(FILE_NAME);
    }

}
