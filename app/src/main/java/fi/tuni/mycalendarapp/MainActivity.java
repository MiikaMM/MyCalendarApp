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

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
	private final String TAG = "MunTagi";

	private String[] monthNames;
	private String[] weekDayNames;

	private Calendar currentTime;

	private int currentDisplayedYear;
	private int currentDisplayedMonth;
	private int currentDisplayedDate;
	private int currentDisplayedDay;

	private int numberOfDitM;
	private int firstDotM;

	private TextView yearDisplay;
	private TextView monthDisplay;
	private TextView dateDisplay;
	private TextView dayDisplay;

	private int janFirstDoW;

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

		/*Calendar c = currentTime;
		c.set(Calendar.DAY_OF_MONTH, 1);
		firstDotM = c.get(Calendar.DAY_OF_WEEK) - 1;*/

		updateMonthData();

//		Log.d(TAG, "The year is: " + currentDisplayedYear);
//		Log.d(TAG, "The month is: " + monthNames[currentDisplayedMonth-1]);
//		Log.d(TAG, "The day of the month is: " + currentDisplayedDate);
//		Log.d(TAG, "The current weekday is: " + weekDayNames[currentDisplayedDay]); // -1
		updateCurrentTimeDisplay(currentDisplayedMonth, currentDisplayedYear);
	}

	public void updateMonthData() {
		Calendar c = Calendar.getInstance();
		c.set(currentDisplayedYear, currentDisplayedMonth - 1, currentDisplayedDate);

		numberOfDitM = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		c.set(Calendar.DAY_OF_MONTH, 1);
		firstDotM = c.get(Calendar.DAY_OF_WEEK) - 1;

//		Log.d(TAG, "The month is: " + monthNames[currentDisplayedMonth-1]);
//		Log.d(TAG, "The number of days in the month is: " + numberOfDitM);
//		Log.d(TAG, "The first day of the month is: " + weekDayNames[firstDotM] + ", (" + firstDotM + ")");

		updateCalendarGrid();
	}

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

	public void dayClicked(View v) {
		PopupMenu popup = new PopupMenu(MainActivity.this, v);
		popup.setOnMenuItemClickListener(MainActivity.this);
		popup.inflate(R.menu.menu_day_popup);
		popup.show();
	}

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

	public void monthScrollClicked(View v) {
		Log.d(TAG, "monthScrollClicked");

		if(v.getId() == R.id.prev_button) {
			//Log.d(TAG, "monthScrollClicked, monthPrev");

			if(currentDisplayedMonth > 1) {
				//Log.d(TAG, "monthScrollClicked, monthPrev, if-check passed");

				updateCurrentTimeDisplay(currentDisplayedMonth - 1, currentDisplayedYear);
			} else if(currentDisplayedMonth == 1) {
				updateCurrentTimeDisplay(12, currentDisplayedYear - 1);
			}
		}
		if(v.getId() == R.id.next_button) {
			//Log.d(TAG, "monthScrollClicked, monthNext");

			if(currentDisplayedMonth < 12) {
				//Log.d(TAG, "monthScrollClicked, monthNext, if-check passed");

				updateCurrentTimeDisplay(currentDisplayedMonth + 1, currentDisplayedYear);
			} else if(currentDisplayedMonth == 12) {
				updateCurrentTimeDisplay(1, currentDisplayedYear + 1);
			}
		}
	}

	public void updateCurrentTimeDisplay(int month, int year) {
		Log.d(TAG, "updateCurrentTimeDisplay");

		if(month >= 1 && month <= 12) {
			currentDisplayedMonth = month;
		}

		if(year >= 1 && year <= 9999) {
			currentDisplayedYear = year;
		}

		//Log.d(TAG, "UpdateMonthDisplay: currentDisplayedMonth: " + currentDisplayedMonth);
		//Log.d(TAG, "UpdateMonthDisplay: to be monthText: " + monthNames[currentDisplayedMonth-1]);

		updateMonthData();

		String yearString = Integer.toString(currentDisplayedYear);
		yearDisplay.setText(yearString);

		String monthString = monthNames[currentDisplayedMonth-1];
		monthDisplay.setText(monthString);

		String ordinalInd = MyUtil.ordinal(currentDisplayedDate);
		String dateString = currentDisplayedDate + ordinalInd;
		dateDisplay.setText(dateString);

		String dayString = weekDayNames[currentDisplayedDay];	// -1
		dayDisplay.setText(dayString);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();

		switch (item.getItemId()) {
			case R.id.addnote:
				// TBA
				return true;
			case R.id.addtask:
				// TBA
				return true;
			case R.id.viewinfo:
				// TBA
				return true;
			default:
				return false;
		}
	}
}
