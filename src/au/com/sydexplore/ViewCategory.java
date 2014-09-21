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

import au.com.sydexplore.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ViewCategory extends Activity {
    
	// Listview for this activity 
	ListView listview; 
	
	// Attractions array
	ArrayList<Attraction> attractionsArray; 
	
	// Attractions adapter
	ArrayAdapter<String> attractionsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        // Set content view 
        setContentView(R.layout.activity_view_category);
 
        // reference the "listview" variable to the id-"listview" in the layout
     	listview = (ListView) findViewById(R.id.attractionsList);
     	
     	// Get the data from the main screen
        String jsonString = getIntent().getStringExtra("jsonString");
        jsonString = "{ attractions: "+jsonString+" }";
        
        // Construct the array containing Attractions
     	attractionsArray = new ArrayList<Attraction>();
     	
     	// JSON Object parsing 
     	JSONObject obj;
     	JSONArray data = null; 
     	
		try {
			// Load the JSON string 
			obj = new JSONObject(jsonString);
			data = obj.getJSONArray("attractions");
			final int n = data.length();
			
			// If we successfully load the data from the JSON 
			// then add to the array 
			for (int i = 0; i < n; ++i) {
				final JSONObject attraction = data.getJSONObject(i);
				attractionsArray.add(new Attraction(attraction));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// Initialize array adapter for categories 
        attractionsAdapter = new AttractionAdapter(this,attractionsArray);
        
        // Attach the adapter to a ListView
     	listview.setAdapter(attractionsAdapter);
     	
    }
}
