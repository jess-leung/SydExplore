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
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
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
	
	//TextView for no reviews
	TextView noReviews;
	
	//Fragmemt for reiew
	FragmentManager fragmentmanager;
	FragmentTransaction fragmenttransaction;

	public final int SUBMIT_OK = 124567;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//set the layout
		setContentView(R.layout.activity_view_attraction_info);
		
		//set the back button in the action bar
		getActionBar().setDisplayHomeAsUpEnabled(true);

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
		if (openingHours.equals("")){
			ImageView imgViewHours = (ImageView) findViewById(R.id.openingHoursIcon);
			imgViewHours.setVisibility(View.GONE);
			textviewopeninghours.setVisibility(View.GONE);
		}
		else{
			textviewopeninghours.setText(openingHours);
		}
		
		//display URL of the attraction
		TextView textviewurl = (TextView) findViewById(R.id.URL);
		if (URL.equals("")){
			ImageView imgViewUrl = (ImageView) findViewById(R.id.urlIcon);
			imgViewUrl.setVisibility(View.GONE);
			textviewurl.setVisibility(View.GONE);
		}
		else{
			textviewurl.setText(URL);
		}
		
		
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
     	float average = 0;
		try {
			// Load the JSON string 
			obj = new JSONObject(jsonString);
			data = obj.getJSONArray("reviews");
			Log.d("data",data.toString());
			final int n = data.length();
			float rating = 0;
			// If we successfully load the data from the JSON 
			// then add to the array 
			for (int i = 0; i < n; ++i) {
				final JSONObject review = data.getJSONObject(i);
				reviewsArray.add(new Review(review));
				Review rev = reviewsArray.get(i);
				String avg = rev.getReviewRating();
				rating = rating + Float.parseFloat(avg);
				
			}
			average = rating/n;
			//Log.d("Review Array", reviewsArray.get(0).getReviewerName());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set the rating bar for the average rating of reviews
		RatingBar starRating = (RatingBar)findViewById(R.id.average);
		starRating.setRating(average);
		
		//Check that there are reviews for this attraction
		if (reviewsArray.size()>0){
		
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
		else{
			noReviews = (TextView) findViewById(R.id.noReviews);
			noReviews.setText("No reviews for this attraction");
		}

	}
	
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu){
	    	MenuInflater menuinf = getMenuInflater();
	    	menuinf.inflate(R.menu.menu, menu);
	    	
	    	return true;
	    }
	
	public void closeFragment(View v){
		
		fragmentmanager.popBackStack();
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
	
	
    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
    	super.onOptionsItemSelected(menu);
    	
    	//back arrow
    	onBackPressed();
    	
    	String category="";
    	switch(menu.getItemId()){
    		case R.id.All:
    			displayAttractionPageFromAttractionInfo("All");
    			return true;
    		case R.id.Adventurous:
    			displayAttractionPageFromAttractionInfo("Adventurous");
    			return true;
    		case R.id.Cultural:
    			displayAttractionPageFromAttractionInfo("Cultural");
    			return true;
    		case R.id.Education:
    			displayAttractionPageFromAttractionInfo("Education");
    			return true;
    		case R.id.Fun:
    			displayAttractionPageFromAttractionInfo("Fun");
    			return true;
    		case R.id.Historical:
    			displayAttractionPageFromAttractionInfo("Historical");
    			return true;
    		case R.id.Hungry:
    			displayAttractionPageFromAttractionInfo("Hungry");
    			return true;
    		case R.id.Lazy:
    			displayAttractionPageFromAttractionInfo("Lazy");
    			return true;
    		case R.id.Luxurious:
    			displayAttractionPageFromAttractionInfo("Luxurious");
    			return true;
    		case R.id.Natural:
    			displayAttractionPageFromAttractionInfo("Natural");
    			return true;
    		case R.id.Social:
    			displayAttractionPageFromAttractionInfo("Social");
    			return true;

    	}
    	return false;
    	

    }
    
    
    
    
   public void displayAttractionPageFromAttractionInfo(String categoryClickedOn){
	   
	   try {
			MainActivity.sendJson(categoryClickedOn);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
		Intent intent = new Intent(ViewAttractionInfo.this, ViewCategory.class); 
		if (intent != null) { 
			// put "extras" into the bundle for access in the edit activity
			intent.putExtra("jsonString",MainActivity.jsonin); 
			
			int i;
			int secondaryColor = 0;
			
			if (categoryClickedOn.equals("All")){
				MainActivity.oldColor = MainActivity.allCategory;
				secondaryColor = MainActivity.secAllCategory;
			}
			else {
			for (i=0; i<MainActivity.categoryArray.size(); i++){
				Category cat = MainActivity.categoryArray.get(i);
				if (cat.getName().equals(categoryClickedOn)){
					MainActivity.oldColor = cat.getColor();
					secondaryColor = cat.secondaryColor;
				}
			}
			}
			intent.putExtra("primaryColor", MainActivity.oldColor);
			intent.putExtra("secondaryColor", secondaryColor);
			// brings up the second activity
			startActivity(intent); 
		} 
   	
   }
	
	
	
	
}