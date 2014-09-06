package finishbig.test.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class CompareView extends Activity{
	String race1, race2;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.compare_view);
		
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.more_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.compare_items:
	        	Intent i = new Intent(this, MoreScreen.class);
	        	startActivity(i);
	        	return true;
	    }
	    return false;
	}
}
