package au.com.sydexplore;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
     	listview = (ListView) findViewById(R.id.reviewList);
     	
     	// Get the data from the main screen
        String jsonString = getIntent().getStringExtra("jsonString");
        jsonString = "{ reviews: "+jsonString+" }";
        
        // Construct the array containing Attractions
     	reviewsArray = new ArrayList<Review>();
     	
     	// JSON Object parsing 
     	JSONObject obj;
     	JSONArray data = null; 
     	
		try {
			// Load the JSON string 
			obj = new JSONObject(jsonString);
			data = obj.getJSONArray("reviews");
			final int n = data.length();
			
			// If we successfully load the data from the JSON 
			// then add to the array 
			for (int i = 0; i < n; ++i) {
				final JSONObject review = data.getJSONObject(i);
				reviewsArray.add(new Review(review));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// Initialize array adapter for reviews
        reviewsAdapter = new ReviewAdapter(this,reviewsArray);
        
        // Attach the adapter to a ListView
     	listview.setAdapter(reviewsAdapter);
		
				
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
        	startActivity(intent);
        }
    }
}