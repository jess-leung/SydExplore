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


public class ReviewAdapter extends ArrayAdapter{

		private List<Review> reviewList;
		private Context context;

		public ReviewAdapter(Context context, List reviewList) {
			super(context, R.layout.activity_review_row, reviewList);
			this.reviewList = reviewList;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = null;
			if (view == null) {
				view = LayoutInflater.from(context).inflate(R.layout.activity_review_row, null, false);
				holder = new ViewHolder();
				holder.reviewTitle = (TextView) view.findViewById(R.id.reviewTitle);
				holder.reviewerName = (TextView) view.findViewById(R.id.reviewerName);
				view.setTag(holder);
			}else{
				holder = (ViewHolder) view.getTag();
			}
	  
		  Review rev = reviewList.get(position);
		  holder.reviewTitle.setText(rev.getReviewTitle());
		  holder.reviewerName.setText(rev.getReviewerName());


		  return view;
		}
		
		
		 static class ViewHolder {
			TextView reviewTitle;
			TextView reviewerName;
			 }
		 


	
	
	
	
	
	
}
