package au.com.sydexplore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//set the layout
		setContentView(R.layout.activity_view_attraction_info);
	
		// Get the data from the list of attractions screen
        name = getIntent().getStringExtra("attractionName");
		location = getIntent().getStringExtra("location");
		
		//put the data into the variables
		openingHours = "9:00am - 5:00pm";
		description = "Testing dummy data";
		URL = "www.google.com";
		
		
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
	
	
}
