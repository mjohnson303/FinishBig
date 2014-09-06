package finishbig.test.org;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Account extends Activity {
	TextView t, t1;
	String username="yo";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.finish_big);*/
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.finish_big);
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			username = extras.getString("value1");
		
		Shader textShader = new LinearGradient(0, 0, 0, 50, new int[] {
				Color.argb(100, 144, 144, 144), Color.BLACK }, new float[] { 0,
				1 }, TileMode.CLAMP); // From white to #C0C0C0
		
		t=(TextView)findViewById(R.id.accountName); 
	    t.setText("Account Name: "+username);
	    t.getPaint().setShader(textShader);
	}
}
