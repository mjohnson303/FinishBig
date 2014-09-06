package finishbig.test.org;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;

public class MoreScreen extends Activity{
	String race1, race2;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.compare_more);

		Bundle extras = getIntent().getExtras();
		if (extras != null){
			race1 = extras.getString("s1");
			race2=extras.getString("s2");
		}
		else{
			race1="";
			race2="";
		}
	}
}