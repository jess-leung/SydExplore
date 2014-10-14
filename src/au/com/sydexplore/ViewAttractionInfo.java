package au.com.sydexplore;

import java.util.ArrayList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import au.com.sydexplore.CategoryAdapter.ViewHolder;
import au.com.sydexplore.ReviewAdapter.ViewHold;

public class ViewAttractionInfo extends Activity {
	
	//name of attraction
	public String name;
	
	//opening hours of attraction
	public String openingHours;
	
	//description of attraction
	public String description;
	
	//website URL of attraction
	public String URL;
	
	//location of attraction
	public String location;
	
	//image of attraction
	public String image;
	
	// Listview for this activity 
	ListView listview; 
	
	// Attractions array
	ArrayList<Review> reviewsArray; 
	
	// Attractions adapter
	ArrayAdapter<String> reviewsAdapter;
	
	// ImageLoader options
	DisplayImageOptions options;
	
	//Fragmemt for reiew
	FragmentManager fragmentmanager;
	FragmentTransaction fragmenttransaction;

	public final int SUBMIT_OK = 124567;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//set the layout
		setContentView(R.layout.activity_view_attraction_info);

		// Get the data from the list of attractions screen
        name = getIntent().getStringExtra("attractionName");
		location = getIntent().getStringExtra("location");
		openingHours = getIntent().getStringExtra("openingHours");
		description = getIntent().getStringExtra("description");
		URL = getIntent().getStringExtra("URL");
		image = getIntent().getStringExtra("image");
				
		// Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);	
		
        options = new DisplayImageOptions.Builder()
        		.cacheInMemory(true)
        		.cacheOnDisk(true)
        		.build();
        		
        //display the image
        ImageView imageviewname = (ImageView) findViewById(R.id.image);
        ImageLoader.getInstance().displayImage("https://sydexplore-attractions.s3.amazonaws.com"+image, imageviewname, options);
        
        //display the attraction name
        TextView textviewname = (TextView) findViewById(R.id.name);
        textviewname.setText(name);

		//display the location of the attraction
		TextView textviewlocation = (TextView) findViewById(R.id.location);
		textviewlocation.setText(location);
		
		//display attraction opening hours
		TextView textviewopeninghours = (TextView) findViewById(R.id.openingHours);
		textviewopeninghours.setText(openingHours);
		
		//display URL of the attraction
		TextView textviewurl = (TextView) findViewById(R.id.URL);
		textviewurl.setText(URL);
		
		//display the description of the attraction
		TextView textviewdescription = (TextView) findViewById(R.id.description);
		textviewdescription.setText(description);
		
		// reference the "listview" variable to the id-"listview" in the layout
		LinearLayout listview = (LinearLayout) findViewById(R.id.reviewList);
     	
     	// Get the data from the main screen
        String jsonString = getIntent().getStringExtra("jsonString");
        jsonString = "{ reviews: "+jsonString+" }";
        
        Log.d("JSON STRING",jsonString);
        
        // Construct the array containing Attractions
     	reviewsArray = new ArrayList<Review>();
     	
     	// JSON Object parsing 
     	JSONObject obj;
     	JSONArray data = null; 
     	
		try {
			// Load the JSON string 
			obj = new JSONObject(jsonString);
			data = obj.getJSONArray("reviews");
			Log.d("data",data.toString());
			final int n = data.length();
			
			// If we successfully load the data from the JSON 
			// then add to the array 
			for (int i = 0; i < n; ++i) {
				final JSONObject review = data.getJSONObject(i);
				reviewsArray.add(new Review(review));
				
			}
			
			//Log.d("Review Array", reviewsArray.get(0).getReviewerName());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		Log.d("Testing","TESTING");
		//Log.d("First review",reviewsArray);
		
		// Initialize array adapter for reviews
        reviewsAdapter = new ReviewAdapter(this,reviewsArray);
        
        // Attach the adapter to a ListView
        final int adapterCount = reviewsAdapter.getCount();

        for (int i = 0; i < adapterCount; i++) {
          View item = reviewsAdapter.getView(i, null, null);
          listview.addView(item);
  		  //set up the fragments for displaying reviews
          item.setOnClickListener(new View.OnClickListener(){
  	        @Override
  	        public void onClick(View v){  	
  	        	ViewHold holder = (ViewHold) v.getTag();
  	        	int holderPosition = holder.positionHolder;
  	        	//Get the review that was clicked on
  				Review reviewClickedOn = reviewsArray.get(holderPosition);
  								
  				fragmentmanager = getFragmentManager();
  				fragmenttransaction = fragmentmanager.beginTransaction();
  				FragmentReview fragmentreview = new FragmentReview();
  				
  				//send the details of the review clicked to the ReviewFragment
  				final Bundle bundle = new Bundle();
  				bundle.putString("reviewTitle",reviewClickedOn.getReviewTitle());
  				bundle.putString("reviewText",reviewClickedOn.getReviewText());
  				bundle.putString("reviewRating",reviewClickedOn.getReviewRating());
  				bundle.putString("reviewerName",reviewClickedOn.getReviewerName());
  				fragmentreview.setArguments(bundle);
  				
  				fragmenttransaction.add(R.id.fragment, fragmentreview);
  				fragmenttransaction.addToBackStack("review");
  				fragmenttransaction.commit();
  	        }
          });
        }
		

	}


//	private void setupListViewListener() { 
//		listview.setOnClickListener(new View.OnClickListener(){
//	        @Override
//	        public void onClick(View v){  	
//	        	Log.i("LISTVIEW", "TESTING LBAH BLAH BLAH");
//	        	ReviewAdapter.ViewHolder holder = (ReviewAdapter.ViewHolder) v.getTag();
//	        	int holderPosition = holder.positionHolder;
////	        	Log.i("POS",String.valueOf(holderPosition));
//	        	//Get the review that was clicked on
//				Review reviewClickedOn = reviewsArray.get(holderPosition);
//								
//				fragmentmanager = getFragmentManager();
//				fragmenttransaction = fragmentmanager.beginTransaction();
//				FragmentReview fragmentreview = new FragmentReview();
//				
//				//send the details of the review clicked to the ReviewFragment
//				final Bundle bundle = new Bundle();
//				bundle.putString("reviewTitle",reviewClickedOn.getReviewTitle());
//				bundle.putString("reviewText",reviewClickedOn.getReviewText());
//				bundle.putString("reviewRating",reviewClickedOn.getReviewRating());
//				bundle.putString("reviewerName",reviewClickedOn.getReviewerName());
//				fragmentreview.setArguments(bundle);
//				
//				fragmenttransaction.add(R.id.fragment, fragmentreview);
//				fragmenttransaction.addToBackStack("review");
//				fragmenttransaction.commit();
//	        }
//	    });
//	}
		
//		listview.setOnItemClickListener(new OnItemClickListener() { 
//			@Override
//			public void onItemClick(AdapterView <? > parent, View view, int position, long id) { 
//				
//				//Get the review that was clicked on
//				Review reviewClickedOn = reviewsArray.get(position);
//								
//				fragmentmanager = getFragmentManager();
//				fragmenttransaction = fragmentmanager.beginTransaction();
//				FragmentReview fragmentreview = new FragmentReview();
//				
//				//send the details of the review clicked to the ReviewFragment
//				final Bundle bundle = new Bundle();
//				bundle.putString("reviewTitle",reviewClickedOn.getReviewTitle());
//				bundle.putString("reviewText",reviewClickedOn.getReviewText());
//				bundle.putString("reviewRating",reviewClickedOn.getReviewRating());
//				bundle.putString("reviewerName",reviewClickedOn.getReviewerName());
//				fragmentreview.setArguments(bundle);
//				
//				fragmenttransaction.add(R.id.fragment, fragmentreview);
//				fragmenttransaction.addToBackStack("review");
//				fragmenttransaction.commit();
//				
//			} 
//		}); 
		
	
	
	
	public void closeFragment(View v){
		
		fragmentmanager.popBackStack();
	}

	


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Retrieves the information about the selected attraction from the database
	 */
	public void getAttractionInformation()
	{
    	//dummy attraction name
    	String attractionName = "Sydney Opera House";
    	Log.d(attractionName, attractionName);
    	
	}
	
    /** 
     * Open up submit review activity 
     * @param view
     */
    public void submitReview(View view) 
    {
        Intent intent = new Intent(ViewAttractionInfo.this, SubmitReview.class);
        if(intent!=null){
        	intent.putExtra("attractionName", name);
        	startActivityForResult(intent,SUBMIT_OK);
        }
    }
    
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		if (requestCode == SUBMIT_OK) { 
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "Review submitted for moderation", Toast.LENGTH_SHORT).show(); 
			}
		}
	}
}