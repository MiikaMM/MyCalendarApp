package fi.tuni.mycalendarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
	private int calendarYear = 2020;
	private String[] monthNames;
	private int[] numOfDaysInMonth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = findViewById(R.id.testbutton);

	}

	public void dayClicked(View v) {
		PopupMenu popup = new PopupMenu(MainActivity.this, v);
		popup.setOnMenuItemClickListener(MainActivity.this);
		popup.inflate(R.menu.menu_day_popup);
		popup.show();
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
