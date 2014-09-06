package finishbig.test.org;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import cloudmine.api.CMAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class LView extends Activity{
	String username = "Congratulations YOU WON!";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list2);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
			username = extras.getString("value1");
		
		ArrayList<SearchResults> searchResults = GetSearchResults();

		final ListView lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));

		lv1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
				Object o = lv1.getItemAtPosition(position);
				SearchResults fullObject = (SearchResults)o;
				Bundle b = new Bundle(); b.putString("value1",username); b.putString("value2", fullObject.getName());
	        	Intent i = new Intent(getApplicationContext(), RaceDetail.class);
	        	i.putExtras(b);
	        	startActivity(i);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.compare_menu, menu);
	    return true;
	}

	private ArrayList<SearchResults> GetSearchResults(){
		ArrayList<SearchResults> results = new ArrayList<SearchResults>();
		CMAdapter cmadapter = new CMAdapter("42c5b99912b04f5e8c254e6a9e02878f","22851dfa56b84a3e81e6b48e90b7e4b3");
		// String[] keys = null;
		JSONObject objects = cmadapter.getValues();
		String tempName, id;
		SearchResults sr1 = new SearchResults();
		Iterator<String> note_ids = objects.keys();
		while (note_ids.hasNext()) {
			id = note_ids.next();
			try {
				tempName = objects.getJSONObject(id).getString("username");
				if(tempName.equals(username)) {
					sr1=new SearchResults();
					sr1.setName(objects.getJSONObject(id).getString("race"));
					sr1.setTime("Time: "+objects.getJSONObject(id).getString("time")+"s");
					sr1.setLength("Length: "+objects.getJSONObject(id).getString("length")+"m");
					sr1.setDate("Date: "+objects.getJSONObject(id).getString("date"));
					results.add(sr1);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return results;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.compare_items:
	        	Bundle b = new Bundle(); b.putString("value1",username);
	        	Intent i = new Intent(this, CompareList.class);
	        	i.putExtras(b);
	        	startActivity(i);
	        	return true;
	    }
	    return false;
	}
}
