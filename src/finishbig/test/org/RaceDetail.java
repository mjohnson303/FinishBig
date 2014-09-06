package finishbig.test.org;

import java.text.DecimalFormat;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import cloudmine.api.CMAdapter;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

public class RaceDetail extends Activity {
	String username="Congratulations YOU WON!";
	String race="race";
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.race_detail);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			username = extras.getString("value1");
			race=extras.getString("value2");
		}
		
		Shader textShader = new LinearGradient(0, 0, 0, 50, new int[] {
				Color.argb(100, 144, 144, 144), Color.BLACK }, new float[] { 0,
				1 }, TileMode.CLAMP); // From white to #C0C0C0		
		
		TextView raceName = (TextView) findViewById(R.id.raceName);
		TextView time = (TextView) findViewById(R.id.time);
		TextView length = (TextView) findViewById(R.id.length);
		TextView pace = (TextView) findViewById(R.id.pace);
		
		Object[] arr = getData();
		
		raceName.getPaint().setShader(textShader);
		raceName.setGravity(Gravity.LEFT);
		raceName.setText(race);
		time.getPaint().setShader(textShader);
		time.setGravity(Gravity.LEFT);
		time.setText("Length: "+arr[0]+" m");
		length.getPaint().setShader(textShader);
		length.setGravity(Gravity.LEFT);
		length.setText("Time: "+arr[1]+"");
		pace.getPaint().setShader(textShader);
		pace.setGravity(Gravity.LEFT);
		DecimalFormat d1=new DecimalFormat("0.00");
        pace.setText("Pace: "+d1.format(((Integer)arr[2])/((Double)arr[0]))+"/m");
		
	}
	
	public Object[] getData() {
		Object[] arr = new Object[3];
		CMAdapter cmadapter = new CMAdapter("42c5b99912b04f5e8c254e6a9e02878f",
				"22851dfa56b84a3e81e6b48e90b7e4b3");
		// String[] keys = null;
		JSONObject objects = cmadapter.getValues();
		int totalTime = 0;
		double totalMiles = 0;
		String tempName;
		String raceName;
		Iterator<String> note_ids = objects.keys();
		while (note_ids.hasNext()) {
			String id = note_ids.next();
			try {
				tempName = objects.getJSONObject(id).getString("username");
				raceName= objects.getJSONObject(id).getString("race");
				if (tempName!=null && !tempName.equals("") && tempName.equals(username)
						&& !raceName.equals("") && raceName.equals(race)) {
						totalTime += Integer.parseInt(objects.getJSONObject(id)
								.getString("time"));
						totalMiles += Integer.parseInt(objects.getJSONObject(id)
								.getString("length"));
						break;
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String timeInMin;
		arr[2]=totalTime;
		if(totalTime%60<0)
			timeInMin="" + totalTime / 60 + ":0" + totalTime % 60;
		else
			timeInMin = "" + totalTime / 60 + ":" + totalTime % 60;
		arr[0] = totalMiles;
		arr[1] = timeInMin;
		return arr;
	}
}
