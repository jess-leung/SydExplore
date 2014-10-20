package au.com.sydexplore; 

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AttractionAdapter extends ArrayAdapter {

	// list of attractions 
	private List<Attraction> attractionList;
	private Context context;
	
	// ImageLoader options
	DisplayImageOptions options;
	
	// Colours 
	private int primaryColor;
	private int secondaryColor;

	/**
	 * Constructor for attraction adapter
	 * @param context
	 * @param attList
	 * @param primaryColor
	 * @param secondaryColor
	 */
	public AttractionAdapter(Context context, List attList,int primaryColor,int secondaryColor) {
		super(context, R.layout.activity_attraction_row, attList);
		this.attractionList = attList;
		this.context = context;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;

		// Have not set view yet 
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.activity_attraction_row, null, false);
			holder = new ViewHolder();
			holder.attractionName = (TextView) view.findViewById(R.id.name);
			holder.location = (TextView) view.findViewById(R.id.location);
			holder.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
			holder.rect = (ImageView) view.findViewById(R.id.rectimage);
			holder.distance = (TextView) view.findViewById(R.id.distance);
			view.setTag(holder);
		}else{ // View already set, so just grab it 
			holder = (ViewHolder) view.getTag();
		}

		// Get attraction 
		Attraction att = attractionList.get(position);
		holder.attractionName.setText(att.getName());
		holder.location.setText(att.getLocation());
		// Check that distance is there 
		if(att.currentDist!=0){
			int d = (int) att.currentDist/1000;
			holder.distance.setText(String.valueOf(d)+"km");
		}
		// Display attraction humbnail 
		ImageLoader.getInstance().displayImage("https://sydexplore-attractions.s3.amazonaws.com"+att.getThumbnailUrl(), holder.thumbnail, options);
		// Set even strip rectangle color 
		if(position%2==0){
			holder.rect.setBackgroundResource(primaryColor);
		}
		else{ // Set odd strip rectangle color 
			holder.rect.setBackgroundResource(secondaryColor);
		}
		return view;
	}

	/**
	 * View holder constructor
	 *
	 */
	static class ViewHolder {
		TextView attractionName;
		TextView location;
		TextView distance;
		ImageView thumbnail; 
		ImageView rect;
	}
}