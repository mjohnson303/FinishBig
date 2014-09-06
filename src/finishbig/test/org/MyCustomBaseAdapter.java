package finishbig.test.org;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyCustomBaseAdapter extends BaseAdapter {
	private static ArrayList<SearchResults> searchArrayList;

	private LayoutInflater mInflater;

	public MyCustomBaseAdapter(Context context, ArrayList<SearchResults> results) {
		searchArrayList = results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return searchArrayList.size();
	}

	public Object getItem(int position) {
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.custom_row_view, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(R.id.name);
			holder.txtTime = (TextView) convertView.findViewById(R.id.time);
			holder.txtLength = (TextView) convertView.findViewById(R.id.length);
			holder.txtDate = (TextView) convertView.findViewById(R.id.date);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtName.setText(searchArrayList.get(position).getName());
		holder.txtTime.setText(searchArrayList.get(position).getTime());
		holder.txtLength.setText(searchArrayList.get(position).getLength());
		holder.txtDate.setText(searchArrayList.get(position).getDate());

		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		TextView txtTime;
		TextView txtLength;
		TextView txtDate;
	}
}