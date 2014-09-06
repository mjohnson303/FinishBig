package finishbig.test.org;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cloudmine.api.CMAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;

public class CompareList extends ListActivity {


	/** Called when the activity is first created. */
	String username = "Congratulations YOU WON";
	List<Model> list;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list);
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			username = extras.getString("value1");
		ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(this,getModel());
		setListAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.compare_menu, menu);
	    return true;
	}

	private List<Model> getModel() {
		list = new ArrayList<Model>();
		CMAdapter cmadapter = new CMAdapter("42c5b99912b04f5e8c254e6a9e02878f","22851dfa56b84a3e81e6b48e90b7e4b3");
		JSONObject objects = cmadapter.getValues();
		String tempName, id;
		Iterator<String> note_ids = objects.keys();
		while (note_ids.hasNext()) {
			id = note_ids.next();
			try {
				tempName = objects.getJSONObject(id).getString("username");
				if (tempName.equals(username)) {
					list.add(get(objects.getJSONObject(id).getString("race")));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Initially select one of the items
		list.get(0).setSelected(true);
		return list;
	}

	private Model get(String s) {
		return new Model(s);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.compare_items:
	        	String s1 = "meh";
	        	String s2="";
	        	for(int i=0; i<list.size(); i++){
	        		if(list.get(i).isSelected()){
	        			if(s1.equals("meh"))
	        				s1=list.get(i).getName();
	        			else
	        				s2=list.get(i).getName();
	        		}
	        	}
	        	Bundle b = new Bundle(); b.putString("s1",s1); b.putString("s2",s2);
	        	Intent i = new Intent(this, CompareView.class);
	        	i.putExtras(b);
	        	startActivity(i);
	        	return true;
	    }
	    return false;
	}

}