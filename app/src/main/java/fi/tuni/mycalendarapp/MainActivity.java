package fi.tuni.mycalendarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
	private final String TAG = "MunTagi";
	private int calendarYear = 2020;
	private String[] monthNames;
	private int[] numOfDaysInMonth;
	private int currentMonth;
	private TextView monthDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(TAG, "test");

		Button btn = findViewById(R.id.testbutton);
		monthDisplay = findViewById(R.id.monthText);

		monthNames = new String[] {"January", "February", "March", "April",
									"May", "June", "July", "August",
									"September", "October", "November", "December"};

		updateMonthDisplay(1);

	}

	public void dayClicked(View v) {
		PopupMenu popup = new PopupMenu(MainActivity.this, v);
		popup.setOnMenuItemClickListener(MainActivity.this);
		popup.inflate(R.menu.menu_day_popup);
		popup.show();
	}

	public void monthScrollClicked(View v) {
		Log.d(TAG, "monthScrollClicked");

		if(v.getId() == R.id.monthPrev) {
			Log.d(TAG, "monthScrollClicked, monthPrev");

			if(currentMonth > 1) {
				Log.d(TAG, "monthScrollClicked, monthPrev, if-check passed");

				updateMonthDisplay(currentMonth - 1);
			}
		}
		if(v.getId() == R.id.monthNext) {
			Log.d(TAG, "monthScrollClicked, monthNext");

			if(currentMonth < 12) {
				Log.d(TAG, "monthScrollClicked, monthNext, if-check passed");

				updateMonthDisplay(currentMonth + 1);
			}
		}
	}

	public void updateMonthDisplay(int month) {
		Log.d(TAG, "updateMonthDisplay");

		if(month >= 1 && month <= 12) {
			Log.d(TAG, "updateMonthDisplay, if-check passed");

			currentMonth = month;
		}

		Log.d(TAG, "UpdateMonthDisplay: currentMonth: " + currentMonth);
		Log.d(TAG, "UpdateMonthDisplay: to be monthText: " + monthNames[currentMonth-1]);
		monthDisplay.setText(monthNames[currentMonth-1]);
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
