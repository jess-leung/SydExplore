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

public class MainActivity extends Activity {
	
	// List view for categories 
	ListView categoriesList; 
	// Array list containing the categories 
	ArrayList<String> categoryArray; 
	// ArrayAdapter 
	ArrayAdapter<String> categoriesAdapter;
	static InputStream is = null;
    static String jsonin = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set content view 
        setContentView(R.layout.activity_main);
        
        // reference the "listview" variable to the id-"listview" in the layout
     	categoriesList = (ListView) findViewById(R.id.categoriesList);
     		
        // Construct the data source
     	categoryArray = new ArrayList<String>();
     	
     	// Add in default categories 
     	categoryArray.add("Adventurous");
     	categoryArray.add("Social");
     	categoryArray.add("Cultural"); 
     	categoryArray.add("Historical"); 
     	categoryArray.add("Education");
     	categoryArray.add("Hungry"); 
     	categoryArray.add("Natural");
     	categoryArray.add("Lazy");
     	categoryArray.add("Luxurious"); 
     	
     	// Initialize array adapter for categories 
        categoriesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categoryArray);
        
        // Attach the adapter to a ListView
        categoriesList.setAdapter(categoriesAdapter);
        
        setupListViewListener(); 
    }
    
    /** 
     * Open up submit review activity 
     * @param view
     */
    public void submitReview(View view) 
    {
        Intent intent = new Intent(MainActivity.this, ViewAttraction.class);
        startActivity(intent);
    }
    
    
	private void setupListViewListener() { 
		categoriesList.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView <? > parent, View view, int position, long id) { 
				String categoryClickedOn = categoriesAdapter.getItem(position); //.getText(); 
				Log.i("MainActivity", "Clicked item " + position + ": " + categoryClickedOn); 
				sendJson(categoryClickedOn);
				
			} 
		}); 
	}
    
    /** 
     * Send JSON to backend 
     * @param category
     */
	private void sendJson(final String category) {
		Thread t = new Thread() {

			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
				
				//HttpResponse response;
	            JSONObject json = new JSONObject();

	            try {
	            	HttpPost post = new HttpPost("http://lit-cove-9769.herokuapp.com/attractions/getAttractions/");
	            	// construct JSON output 
	            	json.put("category_name", category);
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
	    			Log.d("JSON", jsonin);
	    		} catch (Exception e) {
	    			Log.d("JSON", "Caught Exception");
	    			Log.e("Buffer Error", "Error converting result " + e.toString());
	    		}
	    		Log.d("JSON", "Json String returned");
			}
		};
		t.start();      
	}
}
