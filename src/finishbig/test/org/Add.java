package finishbig.test.org;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import cloudmine.api.CMAdapter;

public class Add extends Activity implements OnClickListener {

	EditText raceName, time, length, date, location, placed;
	String username = "Congratulations YOU WON!";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
			username = extras.getString("value1");

		View continueButton = findViewById(R.id.doneData_button);
		continueButton.setOnClickListener(this);

		raceName = (EditText) findViewById(R.id.editName);
		time = (EditText) findViewById(R.id.editTime);
		length = (EditText) findViewById(R.id.editLength);
		date = (EditText) findViewById(R.id.editDate);
		location = (EditText) findViewById(R.id.editLocation);
		placed = (EditText) findViewById(R.id.editPlace);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.doneData_button:
			storeData();
			Intent i = new Intent(this, FinishBig.class);
			Bundle b = new Bundle(); b.putString("value1", username); i.putExtras(b);
			//i.putExtra("value1", username);
			startActivity(i);
			break;
		}
	}

	public void storeData() {
		ContentValues cv = new ContentValues(7);
		cv.put("username", username);
		cv.put("race", raceName.getText().toString());
		cv.put("time", time.getText().toString());
		cv.put("length", length.getText().toString());
		cv.put("date", date.getText().toString());
		cv.put("loc", location.getText().toString());
		cv.put("placed", placed.getText().toString());
		insert(cv);
		Toast toast = Toast.makeText(getApplicationContext(), "Data Stored",
				Toast.LENGTH_SHORT);
		toast.show();
	}

	public void insert(ContentValues initialValues) {
		// Validate the requested uri
		/**
		 * if (sUriMatcher.match(uri) != NOTES) { throw new
		 * IllegalArgumentException("Unknown URI " + uri); }
		 */

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		Long now = Long.valueOf(System.currentTimeMillis());

		// Make sure that the fields are all set
		if (values.containsKey("id") == false) {
			values.put("id", username);
		}
		if (values.containsKey("race") == false) {
			values.put("race", "");
		}
		if (values.containsKey("time") == false) {
			values.put("time", "");
		}
		if (values.containsKey("length") == false) {
			values.put("length", "");
		}
		if (values.containsKey("date") == false) {
			values.put("date", "");
		}
		if (values.containsKey("loc") == false) {
			values.put("loc", "");
		}
		if (values.containsKey("placed") == false) {
			values.put("placed", "");
		}

		CMAdapter cmadapter = new CMAdapter("42c5b99912b04f5e8c254e6a9e02878f",
				"22851dfa56b84a3e81e6b48e90b7e4b3");
		// for the moment, use time for the key
		String key = System.currentTimeMillis() + "";
		String new_key = cmadapter.updateValue(key, values);
	}
}
