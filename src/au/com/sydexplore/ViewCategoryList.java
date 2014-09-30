package au.com.sydexplore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import com.google.android.gms.maps.GoogleMap;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ViewCategoryList extends Activity {
    
	// Listview for this activity 
	ListView listview; 
	
	// Attractions adapter
	ArrayAdapter<String> attractionsAdapter;

	static InputStream is = null;
	static String jsonin = "";
	
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
	     	
			try {
				// Load the JSON string 
				obj = new JSONObject(ViewCategory.jsonString);
				data = obj.getJSONArray("attractions");
				final int n = data.length();
				
				ViewCategory.attractionsArray = new ArrayList<Attraction>();
				
				// If we successfully load the data from the JSON 
				// then add to the array 
				for (int i = 0; i < n; ++i) {
					final JSONObject attraction = data.getJSONObject(i);
					ViewCategory.attractionsArray.add(new Attraction(attraction));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
     	
		
		// Initialize array adapter for categories 
     	Log.i("ATT",ViewCategory.attractionsArray.toString());
        attractionsAdapter = new AttractionAdapter(this,ViewCategory.attractionsArray);
        
        // Attach the adapter to a ListView
     	listview.setAdapter(attractionsAdapter);
     	
     	setupListViewListener();
     	
     	// Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);	
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
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				
				//Log.d("jsonString",jsonin);
				
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
