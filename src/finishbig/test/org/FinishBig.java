package finishbig.test.org;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import cloudmine.api.CMAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FinishBig extends Activity {
	String username = "Congratulations YOU WON!";
	//private String username;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.finish_big);
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			username = extras.getString("value1");
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new TileAdapter(this));

		Object[] arr = getData();

		Shader textShader = new LinearGradient(0, 0, 0, 50, new int[] {
				Color.argb(100, 144, 144, 144), Color.BLACK }, new float[] { 0,
				1 }, TileMode.CLAMP); // From white to #C0C0C0

		TextView raceHeader = (TextView) findViewById(R.id.home_screen_header1);
		raceHeader.setPadding(40, 30, 0, 0);
		raceHeader.getPaint().setShader(textShader);
		raceHeader.setGravity(Gravity.LEFT);
		raceHeader.setText("Races: " + arr[0] + "");

		TextView milesHeader = (TextView) findViewById(R.id.home_screen_header2);
		milesHeader.setPadding(40, 7, 0, 0);
		milesHeader.getPaint().setShader(textShader);
		milesHeader.setGravity(Gravity.LEFT);
		milesHeader.setText("Miles: " + arr[1] + "");

		TextView timeHeader = (TextView) findViewById(R.id.home_screen_header3);
		timeHeader.setPadding(40, 7, 0, 0);
		timeHeader.getPaint().setShader(textShader);
		timeHeader.setGravity(Gravity.LEFT);
		timeHeader.setText("Total Time: " + arr[2]);

		gridview.setOnItemClickListener(new ListClick());
	}

	public Object[] getData() {
		Object[] arr = new Object[3];
		CMAdapter cmadapter = new CMAdapter("42c5b99912b04f5e8c254e6a9e02878f",
				"22851dfa56b84a3e81e6b48e90b7e4b3");
		// String[] keys = null;
		JSONObject objects = cmadapter.getValues();
		int races = 0, totalTime = 0; 
		double totalMiles = 0.0;
		String tempName;
		Iterator<String> note_ids = objects.keys();
		while (note_ids.hasNext()) {
			String id = note_ids.next();
			try {
				tempName = objects.getJSONObject(id).getString("username");
				if (tempName!=null && !tempName.equals("") && tempName.equals(username)){
					if(objects.getJSONObject(id).getString("race") != ""){
						races++;
						totalTime += Integer.parseInt(objects.getJSONObject(id)
								.getString("time"));
						totalMiles += Integer.parseInt(objects.getJSONObject(id)
								.getString("length"));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String timeInMin;
		if(totalTime % 60 < 10)
			timeInMin = "" + totalTime / 60 + ".0" + totalTime % 60;
		else
			timeInMin = "" + totalTime / 60 + "." + totalTime % 60;
		arr[0] = races;
		arr[1] = totalMiles;
		arr[2] = timeInMin;
		return arr;
	}

	private class ListClick implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				Intent i = new Intent(getApplicationContext(), Add.class);
				Bundle b = new Bundle(); b.putString("value1", username); i.putExtras(b);
				//i.putExtra("value1", username);
				startActivity(i);
				break;
			case 1: 
				Intent i2 = new Intent(getApplicationContext(),LView.class); 
				Bundle bb = new Bundle(); bb.putString("value1", username); i2.putExtras(bb);
				startActivity(i2); 
				break;
			case 2:
				//stats
				break;
			case 3:
				//favorites
				break;
			case 4:
				//settings
				break;
			case 5:
				Intent i3 = new Intent(getApplicationContext(), Account.class);
				Bundle b2 = new Bundle(); b2.putString("value1", username); i3.putExtras(b2);
				startActivity(i3);
				break;
			}
		}
	}
}