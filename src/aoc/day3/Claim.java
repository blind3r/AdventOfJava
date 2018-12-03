package aoc.day3;

import java.awt.Rectangle;

@SuppressWarnings("serial")
public class Claim extends Rectangle{

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

	@Override
	public String toString() {
		return "#" + id + " @ " + x + "," + y + ": " + width + "x" + height;
	}
}
