package au.com.sydexplore; 

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AttractionAdapter extends ArrayAdapter {

	private List<Attraction> attractionList;
	private Context context;

	public AttractionAdapter(Context context, List attList) {
		super(context, R.layout.activity_attraction_row, attList);
		this.attractionList = attList;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.activity_attraction_row, null, false);
			holder = new ViewHolder();
//			holder.leftIcon = (ImageView) view.findViewById(R.id.leftIcon);
			holder.attractionName = (TextView) view.findViewById(R.id.name);
			holder.location = (TextView) view.findViewById(R.id.location);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
  
	  Attraction att = attractionList.get(position);
//	  holder.rightIcon.setImageDrawable(feed.getUserIcon());
	  holder.attractionName.setText(att.getName());
	  holder.location.setText(att.getLocation());

	  return view;
	}

	 static class ViewHolder {
//	  ImageView rightIcon;
	  TextView attractionName;
	  TextView location;
	 }
}