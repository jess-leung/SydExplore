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
import android.graphics.drawable.Drawable;
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
				holder.reviewCategory = (ImageView) view.findViewById(R.id.reviewCategory);
				//holder.reviewCategory = (TextView) view.findViewById(R.id.reviewCategory);
				
				view.setTag(holder);
			}else{
				holder = (ViewHolder) view.getTag();
			}
	  
		  Review rev = reviewList.get(position);
		  holder.reviewTitle.setText(rev.getReviewTitle());
		  holder.reviewerName.setText(rev.getReviewerName());
		  //holder.reviewCategory.setText(rev.getReviewCategory());
		  
		  String category = rev.getReviewCategory();
		  String image = "";
		  Log.d("CATEGORYREVIEW",category);
		  
		  if (category.equals("Adventurous")){
			  image = "trekking"; 
		  } else if (category.equals("Social")){
			  image="party1";
		  } else if (category.equals("Cultural")){
			  image="greek1";
		  } else if (category.equals("Historical")){
			  image="time12";
		  } else if (category.equals("Education")){
			  image="books30";
		  } else if (category.equals("Hungry")){
			  image="plate7";
		  } else if (category.equals("Natural")){
			  image="tree101";
		  } else if (category.equals("Lazy")){
			  image="man271";
		  } else if (category.equals("Luxurious")){
			  image="banknotes";
		  }
		  
		  		  
		  String uri = "@drawable/"+image; 
		  int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
		  Drawable res = context.getResources().getDrawable(imageResource);
		  holder.reviewCategory.setImageDrawable(res);

		  return view;
		}
		
		
		 static class ViewHolder {
			TextView reviewTitle;
			TextView reviewerName;
			ImageView reviewCategory;
			 }
		 


	
	
	
	
	
	
}
