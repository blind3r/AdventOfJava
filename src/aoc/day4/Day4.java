package aoc.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

	final static String FILE_NAME = "inputs/input_day4.txt";

	static final String START = "begins shift";
	static final String SLEEPS = "falls asleep";
	static final String WAKES = "wakes up";

	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static final SimpleDateFormat DF_NO_TIME = new SimpleDateFormat("yyyyMMdd");

	Map<Integer, Guard> guards = new HashMap<>();

	public void solve() {
		Stream<String> stream;
		List<String> inputString = null;
		try {
			stream = Files.lines(Paths.get(FILE_NAME));
			inputString = stream.collect(Collectors.toList());
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Map<String, Shift> shifts = parse(inputString);
			fillGuardsInfo(shifts);

			int maxSleepMinutes = 0;
			int sleepyGuard = 0;
			for (Guard guard : guards.values()) {

				if (guard.sleepMinutesCount > maxSleepMinutes) {
					maxSleepMinutes = guard.sleepMinutesCount;
					sleepyGuard = guard.number;
				}
				System.out.println(guard);
			}
			List<Shift> sleepyShifts = new ArrayList<>();
			for (Shift shift : shifts.values()) {
				//System.out.println(shift.toString());
				if (shift.guardNumber == sleepyGuard) {
					sleepyShifts.add(shift);
				}
			}
			int[] sumMinutesAsleep = new int[60];
			for (Shift shift : sleepyShifts) {
				for (int i = 0; i < shift.minutesAsleep.length; i++) {
					sumMinutesAsleep[i] += shift.minutesAsleep[i];
				}
			}
			int minuteMostSleep = -1;
			int minuteNumber = -1;
			for (int i = 0; i < sumMinutesAsleep.length; i++) {
				if (sumMinutesAsleep[i] > minuteMostSleep) {
					minuteMostSleep = sumMinutesAsleep[i];
					minuteNumber = i;
				}
			}
			System.out.println("What is the ID of the guard you chose multiplied by the minute you chose?: "
					+ (minuteNumber + 1) * sleepyGuard);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	void fillGuardsInfo(Map<String, Shift> shifts) {
		Collection<Shift> shiftList = shifts.values();
		Guard guard;

		for (Shift shift : shiftList) {
			if (guards.containsKey(shift.guardNumber)) {
				guard = guards.get(shift.guardNumber);
			} else {
				guard = new Guard();
				guard.number = shift.guardNumber;
				guards.put(shift.guardNumber, guard);
			}

			guard.sleepMinutesCount = shift.fillMinutesSleeping();
		}
	}

	Map<String, Shift> parse(List<String> inputString) throws ParseException {
		Map<String, Shift> shifts = new HashMap<>();
		Shift shift;
		Date date;
		String dateKey;

		for (String string : inputString) {
			String[] strings = string.split("]");
			date = DATE_FORMAT.parse(strings[0].substring(1));

			dateKey = DF_NO_TIME.format(date);
			if (shifts.containsKey(dateKey)) {
				shift = shifts.get(dateKey);
			} else {
				shift = new Shift(date);
				shifts.put(dateKey, shift);
			}
			shift.parseEvent(strings[1], date);
		}
		return shifts;
	}

	class Guard {
		int number;
		int sleepMinutesCount = 0;

		public String toString() {
			return "guard #" + number + " sleepMinutesCount:" + sleepMinutesCount;
		}
	}

	class Shift {
		Date date;

		int guardNumber;

		int[] minutesAsleep = new int[60];

		List<Integer> sleep = new ArrayList<>();
		List<Integer> awake = new ArrayList<>();

		public String toString() {
			String string = date + " guard:" + guardNumber + " minutes:\n";
			
			for (int i = 0; i < minutesAsleep.length; i++) {
				string += "," + minutesAsleep[i];
			}
			
			return string;
		}

		public Shift(Date date) {
			this.date = roundDate(date);
		}

		public int fillMinutesSleeping() {
			Collections.sort(sleep);
			Collections.sort(awake);
			int sleepCounter = 0;
			Arrays.fill(minutesAsleep, 0);

			for (int i = 0; i < sleep.size(); i++) {
				for (int j = sleep.get(i); j < awake.get(i); j++) {
					minutesAsleep[j]++;
					sleepCounter++;
				}
			}
			return sleepCounter;
		}

		Date roundDate(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.HOUR) != 0) {
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			return cal.getTime();
		}

		void parseEvent(String event, Date dateInput) {
			Date date = roundDate(dateInput);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			if (event.endsWith(SLEEPS)) {
				sleep.add(cal.get(Calendar.MINUTE));
			} else if (event.endsWith(WAKES)) {
				awake.add(cal.get(Calendar.MINUTE));
			} else {
				guardNumber = Integer.valueOf(event.replaceAll("[^0-9]", ""));
			}
		}
	}
}
