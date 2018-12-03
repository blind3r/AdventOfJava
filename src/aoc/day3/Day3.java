package aoc.day3;

import java.awt.Rectangle;
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

	static int[][] matrix = new int[1000][1000];

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
			List<Integer> slots = new ArrayList<>();
			Matcher numberMatcher = numberPattern.matcher(str);
			while (numberMatcher.find()) {
				slots.add(Integer.parseInt(numberMatcher.group()));
			}
			for (int i = slots.get(1); i < slots.get(1) + slots.get(3); i++) {
				for (int j = slots.get(2); j < slots.get(2) + slots.get(4); j++) {
					matrix[i][j] += 1;
				}
			}
		}
		int overlaps = 0;
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				if (matrix[i][j] > 1) {
					overlaps++;
				}
			}
		}
		return overlaps;
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
	
	@SuppressWarnings("serial")
	class Claim extends Rectangle{
		
		int id;

		public Claim(String claimString) {
			super();
			
			final int idIndex = claimString.indexOf('#');
			final int atIndex = claimString.indexOf('@');
			final int colonIndex = claimString.indexOf(':');

			id = Integer.valueOf(claimString.substring(idIndex + 1, atIndex - 1));

			final String[] offsets = claimString.substring(atIndex + 2, colonIndex).split(",");
			x = Integer.valueOf(offsets[0]);
			y = Integer.valueOf(offsets[1]);

			final String[] sizes = claimString.substring(colonIndex + 2).split("x");
			width = Integer.valueOf(sizes[0]);
			height = Integer.valueOf(sizes[1]);		
		}
	}
}
