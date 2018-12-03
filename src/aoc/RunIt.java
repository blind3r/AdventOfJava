package aoc;
import java.io.IOException;

import aoc.day3.Day3;

public class RunIt {

    public static void main(String[] args) {
    	try {
//	        Day1 day1 = new Day1();
//	        day1.solve();
//	
//	        Day2 day2 = new Day2();
//	        day2.solve();
        
    		Day3 day3 = new Day3();
			day3.solve();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
