package fi.tuni.mycalendarapp;

public class MyUtil {
	public static String ordinal(int i) {
		int mod100 = i % 100;
		int mod10 = i % 10;
		if(mod10 == 1 && mod100 != 11) {
			return "st";
		} else if(mod10 == 2 && mod100 != 12) {
			return "nd";
		} else if(mod10 == 3 && mod100 != 13) {
			return "rd";
		} else {
			return "th";
		}
	}
}
