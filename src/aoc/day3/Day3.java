package aoc.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

	final static String FILE_NAME = "inputs/input_day3.txt";

	static int[][] size = new int[1000][1000];

	public void solve() throws IOException {

		List<String> inputString = new ArrayList<>();

		Stream<String> stream = Files.lines(Paths.get(FILE_NAME));
		inputString = stream.collect(Collectors.toList());
		stream.close();

		System.out.println("How many square inches of fabric are within two or more claims? " + partOne(inputString));
		System.out.println("What is the ID of the only claim that doesn't overlap? " + partTwo(inputString));
	}

	private int partOne(List<String> inputString) {
		Pattern numberPattern = Pattern.compile("\\d+");
		for (String str : inputString) {
			List<Integer> numbers = new ArrayList<>();
			Matcher numberMatcher = numberPattern.matcher(str);
			while (numberMatcher.find()) {
				numbers.add(Integer.parseInt(numberMatcher.group()));
			}
			for (int i = numbers.get(1); i < numbers.get(1) + numbers.get(3); i++) {
				for (int j = numbers.get(2); j < numbers.get(2) + numbers.get(4); j++) {
					size[i][j] += 1;
				}
			}
		}
		int duplicates = 0;
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				if (size[i][j] > 1) {
					duplicates++;
				}
			}
		}
		return duplicates;
	}

	public Integer partTwo(List<String> inputString) throws IOException {
		List<Claim> claims = new ArrayList<>();

		for (String string : inputString) {
			claims.add(new Claim(string));
		}

		for (Claim claim : claims) {
			List<Claim> remainingClaims = new ArrayList<>();
			remainingClaims.addAll(claims);
			remainingClaims.remove(claim);
			boolean foundIntersections = false;

			for (Claim claimToken : remainingClaims) {
				if (claim.intersects(claimToken)) {
					foundIntersections = true;
				}
			}
			if (!foundIntersections) {
				return claim.id;
			}
		}

		return null;
	}
}
