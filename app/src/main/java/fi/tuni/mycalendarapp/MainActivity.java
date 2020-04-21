package fi.tuni.mycalendarapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

/**
 * Calendar Application
 * MainActivity is where the application's main view
 * is, as well as the logic behind the calendar.
 *
 * @author 	Miika Minkkinen
 * @version 1.0
 * @since 	2020-04-21
 *
 */
public class MainActivity extends AppCompatActivity {
	private final String TAG = "MunTagi";

	// String Arrays for containing the names for
	// months and weekdays.
	private String[] monthNames;
	private String[] weekDayNames;

	// java.util.Calendar, used for accessing
	// current time and logic of a calendar.
	private Calendar currentTime;

	// Storing what time information is
	// displayed currently on the app.
	private int currentDisplayedYear;
	private int currentDisplayedMonth;
	private int currentDisplayedDate;
	private int currentDisplayedDay;

	// Number of days in the month.
	private int numberOfDitM;
	// First day of the month.
	private int firstDotM;

	// TextView's for accessing the layout.
	private TextView yearDisplay;
	private TextView monthDisplay;
	private TextView dateDisplay;
	private TextView dayDisplay;


	/**
	 * Inside "onCreate()" many of the fields are initialized
	 * and at the end "toToday()" is called.
	 *
	 * {inheritdoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(TAG, "test");

		monthNames = new String[] {"January", "February", "March", "April",
				"May", "June", "July", "August",
				"September", "October", "November", "December"};

		weekDayNames = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday",
				"Thursday", "Friday", "Saturday"};

		yearDisplay = findViewById(R.id.toolbar_display_year);
		monthDisplay = findViewById(R.id.toolbar_display_month);
		dateDisplay = findViewById(R.id.toolbar_display_date);
		dayDisplay = findViewById(R.id.toolbar_display_day);

		Button toTodayButton = findViewById(R.id.toolbar_display_todayButton);
		toTodayButton.setOnClickListener(v -> toToday());

		toToday();

	}

	/**
	 * Sets the calendar display back to the current year and month.
	 *
	 * Calls "updateMonthData()" and "updateCurrentTimeDisplay()" at the end.
	 */
	public void toToday() {
		currentTime = Calendar.getInstance();
		currentTime.setFirstDayOfWeek(Calendar.MONDAY);

		currentDisplayedYear = currentTime.get(Calendar.YEAR);
		Log.d(TAG, "Calendar.YEAR = " + currentTime.get(Calendar.YEAR));

		currentDisplayedMonth = currentTime.get(Calendar.MONTH) + 1;
		Log.d(TAG, "Calendar.MONTH = " + currentTime.get(Calendar.MONTH));

		currentDisplayedDate = currentTime.get(Calendar.DAY_OF_MONTH);
		Log.d(TAG, "Calendar.DAY_OF_MONTH = " + currentTime.get(Calendar.DAY_OF_MONTH));

		currentDisplayedDay = currentTime.get(Calendar.DAY_OF_WEEK) - 1;
		Log.d(TAG, "Calendar.DAY_OF_WEEK = " + currentTime.get(Calendar.DAY_OF_WEEK));

		numberOfDitM = currentTime.getActualMaximum(Calendar.DAY_OF_MONTH);

		updateMonthData();

		updateCurrentTimeDisplay(currentDisplayedMonth, currentDisplayedYear);
	}

	/**
	 * Updates the information to
	 * correspond to the month being displayed.
	 * For example how many days there are in the month,
	 * and what weekday the first day of the month is.
	 *
	 * Calls "updateCalendarGrid()" at the end.
	 */
	public void updateMonthData() {
		Calendar c = Calendar.getInstance();
		c.set(currentDisplayedYear, currentDisplayedMonth - 1, currentDisplayedDate);

		numberOfDitM = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		c.set(Calendar.DAY_OF_MONTH, 1);
		firstDotM = c.get(Calendar.DAY_OF_WEEK) - 1;

		updateCalendarGrid();
	}

	/**
	 * Goes through the calendar grid in the display
	 * and updates all of the day-TextViews with the new
	 * information gathered in "updateMonthData()"
	 *
	 * Calls "highlightCurrentDate()" at the end.
	 */
	public void updateCalendarGrid() {
		Log.d(TAG, "updateCalendarGrid()");

		boolean firstDaySet = false;
		int dayAt = 1;

		for(int w = 1; w <= 6; w++) {
			for(int d = 1; d <= 7; d++) {
				String stringID = "week" + w + "day" + d;
				int resID = getResources().getIdentifier(stringID, "id", getPackageName());
				TextView tv = findViewById(resID);
				tv.setBackgroundColor(Color.TRANSPARENT);
				tv.setTextColor(Color.BLACK);
				tv.setVisibility(TextView.INVISIBLE);

				if(firstDaySet && dayAt <= numberOfDitM) {
					if(dayAt < 10) {
						tv.setText("0" + dayAt);
						tv.setVisibility(TextView.VISIBLE);
						dayAt++;
					} else {
						tv.setText(Integer.toString(dayAt));
						tv.setVisibility(TextView.VISIBLE);
						dayAt++;
					}
				}

				if(w == 1 && d < 7) {
					if(d == firstDotM) {
						tv.setText("01");
						tv.setVisibility(TextView.VISIBLE);
						dayAt++;
						firstDaySet = true;
					}
				} else if(w == 1 && d == 7) {
					if(firstDotM == 0) {
						tv.setText("01");
						tv.setVisibility(TextView.VISIBLE);
						dayAt++;
						firstDaySet = true;
					}
				}
			}
		}

		highlightCurrentDate();
	}

	/**
	 * Highlights the date that is today,
	 * if the displayed month and year are current.
	 */
	public void highlightCurrentDate() {
		Log.d(TAG, "HIGHLIGHT: current displayed month and current month: " + (currentDisplayedMonth - 1) + ", " + currentTime.get(Calendar.MONTH));
		Log.d(TAG, "HIGHLIGHT: current displayed year and current year: " + currentDisplayedYear + ", " + currentTime.get(Calendar.YEAR));

		TextView tv;
		String today;
		if(currentDisplayedDate < 10) {
			today = "0" + currentDisplayedDate;
		} else {
			today = Integer.toString(currentDisplayedDate);
		}

		if((currentDisplayedMonth - 1) == currentTime.get(Calendar.MONTH) &&
				currentDisplayedYear == currentTime.get(Calendar.YEAR)) {
			for(int w = 1; w <= 6; w++) {
				for (int d = 1; d <= 7; d++) {
					String stringID = "week" + w + "day" + d;
					int resID = getResources().getIdentifier(stringID, "id", getPackageName());
					tv = findViewById(resID);

					if(tv.getText() == today) {
						tv.setTextColor(Color.WHITE);
						tv.setBackgroundColor(Color.rgb(113, 188, 120));
					}
				}
			}
		}


	}

	/**
	 * Called when either "previous" or "next" -buttons
	 * are pressed.
	 * Changes the displayed month to the next or previous month.
	 *
	 * @param v the View (Button in this case) that called the method.
	 */
	public void monthScrollClicked(View v) {
		Log.d(TAG, "monthScrollClicked");

		if(v.getId() == R.id.prev_button) {
			if(currentDisplayedMonth > 1) {

				updateCurrentTimeDisplay(currentDisplayedMonth - 1, currentDisplayedYear);
			} else if(currentDisplayedMonth == 1) {
				updateCurrentTimeDisplay(12, currentDisplayedYear - 1);
			}
		}
		if(v.getId() == R.id.next_button) {

			if(currentDisplayedMonth < 12) {
				updateCurrentTimeDisplay(currentDisplayedMonth + 1, currentDisplayedYear);
			} else if(currentDisplayedMonth == 12) {
				updateCurrentTimeDisplay(1, currentDisplayedYear + 1);
			}
		}
	}

	/**
	 * Updates the TextViews in the app display with the updated
	 * year, month, date or day values.
	 *
	 * Calls "updateMonthData()".
	 *
	 * @param month month to be changed as the new displayed one.
	 * @param year year to be changed as the new displayed one.
	 */
	public void updateCurrentTimeDisplay(int month, int year) {
		Log.d(TAG, "updateCurrentTimeDisplay");

		if(month >= 1 && month <= 12) {
			currentDisplayedMonth = month;
		}

		if(year >= 1 && year <= 9999) {
			currentDisplayedYear = year;
		}

		updateMonthData();

		String yearString = Integer.toString(currentDisplayedYear);
		yearDisplay.setText(yearString);

		String monthString = monthNames[currentDisplayedMonth-1];
		monthDisplay.setText(monthString);

		String ordinalInd = MyUtil.ordinal(currentDisplayedDate);
		String dateString = currentDisplayedDate + ordinalInd;
		dateDisplay.setText(dateString);

		String dayString = weekDayNames[currentDisplayedDay];
		dayDisplay.setText(dayString);
	}
}
