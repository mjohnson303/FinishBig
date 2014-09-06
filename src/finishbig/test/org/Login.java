package finishbig.test.org;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import cloudmine.api.CMAdapter;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
	EditText field1, field2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		field1 = (EditText) findViewById(R.id.editEmail);
		field2 = (EditText) findViewById(R.id.editUsername);

		View continueButton = findViewById(R.id.doneLogin_button);
		continueButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.doneLogin_button:
			storeData();
			Intent i = new Intent(this, FinishBig.class);
			Bundle b = new Bundle(); b.putString("value1", field2.getText().toString()); i.putExtras(b);
			//i.putExtra("value1", field2.getText().toString());
			startActivity(i);
			break;
		}
	}

	public void storeData() {
		CMAdapter cmadapter = new CMAdapter("42c5b99912b04f5e8c254e6a9e02878f",
				"22851dfa56b84a3e81e6b48e90b7e4b3");
		// String[] keys = null;
		JSONObject objects = cmadapter.getValues();
		String tempName;
		boolean contains = false;
		Iterator<String> note_ids = objects.keys();
		while (note_ids.hasNext()) {
			String id = note_ids.next();
			try {
				tempName = objects.getJSONObject(id).getString("username");
				if (objects.getJSONObject(id).getString("username").equals(field2.getText().toString())){
					contains=true;
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.d("", "3");
		if (!contains) {
			ContentValues cv = new ContentValues(8);
			//cv.put("email", field1.getText().toString());
			cv.put("username", field2.getText().toString());
			cv.put("length", "");
			cv.put("date", "");
			cv.put("loc", "");
			cv.put("placed", "");
			cv.put("race", "");
			cv.put("time", "");
			String key = System.currentTimeMillis() + "";
			String new_key = cmadapter.updateValue(key, cv);
			Toast toast = Toast.makeText(getApplicationContext(),
					"Account Created", Toast.LENGTH_SHORT);
			toast.show();
		}

	}
}
