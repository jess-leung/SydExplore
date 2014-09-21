package au.com.sydexplore; 

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
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
			holder.attractionName = (TextView) view.findViewById(R.id.name);
			holder.location = (TextView) view.findViewById(R.id.location);
			holder.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
  
	  Attraction att = attractionList.get(position);
//	  holder.rightIcon.setImageDrawable(feed.getUserIcon());
	  holder.attractionName.setText(att.getName());
	  holder.location.setText(att.getLocation());
	  Log.i("ATT",att.getName());
	  if (holder.thumbnail != null) {
			new ImageDownloaderTask(holder.thumbnail).execute("https://sydexplore-attractions.s3.amazonaws.com"+att.getThumbnailUrl());
	  }

	  return view;
	}

	 static class ViewHolder {
	//	  ImageView rightIcon;
		  TextView attractionName;
		  TextView location;
		  ImageView thumbnail; 
	 }
}