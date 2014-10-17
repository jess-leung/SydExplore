package au.com.sydexplore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

public class ViewCategoryList extends Activity {
    
	// Listview for this activity 
	ListView listview; 
	
	// Attractions adapter
	ArrayAdapter<String> attractionsAdapter;

	// No attractions text view 
	TextView noAttractions; 
	
	// JSON variables 
	static InputStream is = null;
	static String jsonin = "";
	
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        // Set content view 
        setContentView(R.layout.activity_view_category);
     	
        // reference the "listview" variable to the id-"listview" in the layout
     	listview = (ListView) findViewById(R.id.attractionsList);
     	
	     	// JSON Object parsing 
	     	JSONObject obj;
	     	JSONArray data = null; 
	     	
	     	// Flag for location 
	     	boolean hasLocation = false; 
	     	
			try {
				// Load the JSON string 
				obj = new JSONObject(ViewCategory.jsonString);
				data = obj.getJSONArray("attractions");
				final int n = data.length();
				
				ViewCategory.attractionsArray = new ArrayList<Attraction>();
				
				LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
				
				// getting GPS status
	            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

	            // getting network status
	            boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	            
	            Log.i("isGPSEnabled",String.valueOf(isGPSEnabled));
	            Log.i("isNetwork",String.valueOf(isNetworkEnabled));
	            
	            Location location = null;
	            		
	            if (isGPSEnabled){
	            	location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	            }
	            
	            if(isNetworkEnabled){
	            	location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	            }
				
				double longitude = 0.0; 
				double latitude = 0.0; 
				
				// Get current location 
				if (location != null){
					longitude = location.getLongitude();
					latitude = location.getLatitude();
					hasLocation = true;
				}
				
				// If we successfully load the data from the JSON 
				// then add to the array 
				for (int i = 0; i < n; ++i) {
					final JSONObject attraction = data.getJSONObject(i);
					Attraction newAttraction = new Attraction(attraction);
					if (hasLocation){
						Location locationA = new Location("point A");
						locationA.setLatitude(latitude);
						locationA.setLongitude(longitude);
						Location locationB = new Location("point B");
						locationB.setLatitude(newAttraction.latitude);
						locationB.setLongitude(newAttraction.longitude);
						float distance = locationA.distanceTo(locationB);
						newAttraction.currentDist=distance;
					}
					ViewCategory.attractionsArray.add(newAttraction);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
     	
		// Check that there are actually attractions for this category 
		if(ViewCategory.attractionsArray.size()>0){
			if (hasLocation==true){
				Collections.sort(ViewCategory.attractionsArray, new Comparator<Attraction>() {
			    public int compare(Attraction a1, Attraction a2) {
			        return Double.compare(a1.currentDist,a2.currentDist);
			    }
				});
			}
			else{
				Collections.sort(ViewCategory.attractionsArray, new Comparator<Attraction>() {
				    public int compare(Attraction a1, Attraction a2) {
				        return a1.name.compareTo(a2.name);
				    }
				});
			}
			
			
			// Initialize array adapter for categories 
			attractionsAdapter = new AttractionAdapter(this,ViewCategory.attractionsArray,ViewCategory.primaryColor,ViewCategory.secondaryColor);
        
			// Attach the adapter to a ListView
			listview.setAdapter(attractionsAdapter);
     	
			setupListViewListener();
     	
			// Create global configuration and initialize ImageLoader with this config
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
			ImageLoader.getInstance().init(config);	
		}
		// Else, display a message that there are no attractions in this category
		else{
			noAttractions = (TextView) findViewById(R.id.noAttractions);
			listview.setEmptyView(noAttractions);
		}
	}
    
	private void setupListViewListener() { 
		
		listview.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView <? > parent, View view, int position, long id) { 
				
				//Get the attraction that was clicked on
				Attraction attractionClickedOn = ViewCategory.attractionsArray.get(position);
				
				//Get the attraction name
				String attractionName = attractionClickedOn.getName();
				
				//get review details from the database
				try{
					sendJson(attractionName);
				}
					catch (InterruptedException e) {
						e.printStackTrace();
				}
				
				//start a new intent for the attraction information
				Intent intent = new Intent(ViewCategoryList.this, ViewAttractionInfo.class);
		        
				if (intent != null) {
					//add the attraction information to the attraction information intent
					intent.putExtra("attractionName", attractionClickedOn.getName());
					intent.putExtra("location", attractionClickedOn.getLocation());
					intent.putExtra("openingHours", attractionClickedOn.getOpeninghours());
					intent.putExtra("URL", attractionClickedOn.getURL());
					intent.putExtra("description", attractionClickedOn.getDescription());
					intent.putExtra("image", attractionClickedOn.getImageURL());
					intent.putExtra("jsonString",jsonin);
					startActivity(intent);	
				}	 
			} 
		});
	}
		
		
		/** 
	     * Send JSON to backend 
	     * @param category
	     * @throws InterruptedException 
	     */
		private void sendJson(final String attractionName) throws InterruptedException {
			Thread t = new Thread() {

				public void run() {
					HttpClient client = new DefaultHttpClient();
					HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
					
					//HttpResponse response;
		            JSONObject json = new JSONObject();

		            try {
		            	HttpPost post = new HttpPost("http://lit-cove-9769.herokuapp.com/attractions/getReviewDetails/");
		            	// construct JSON output 
		            	json.put("attraction_name", attractionName);
		            	Log.d("JSON", json.toString());
		            	StringEntity se = new StringEntity(json.toString());  
		            	se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		            	post.setEntity(se);
		            	HttpResponse httpResponse = client.execute(post);
		    			HttpEntity httpEntity = httpResponse.getEntity();
		    			is = httpEntity.getContent();    
		            	Log.d("JSON", "Got content from entity");
	        
		            } catch(Exception e) {
		            	e.printStackTrace();
		            	Log.d("JSON", "Caught Exception");
		            }
		            
		            try {
		    			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		    			StringBuilder sb = new StringBuilder();
		    			String line = null;
		    			while ((line = reader.readLine()) != null) {
		    				sb.append(line + "\n");
		    			}
		    			is.close();
		    			jsonin = sb.toString();
		    		} catch (Exception e) {
		    			Log.d("JSON", "Caught Exception");
		    			Log.e("Buffer Error", "Error converting result " + e.toString());
		    		}
		    		Log.d("JSON", "Json String returned");
				}
			};
			t.start();
			t.join();
		}
}
