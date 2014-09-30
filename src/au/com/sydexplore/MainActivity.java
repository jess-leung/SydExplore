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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends Activity {
	
	// List view for categories 
	GridView categoriesList; 
	// Array list containing the categories 
	ArrayList<Category> categoryArray; 
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
     	categoriesList = (GridView) findViewById(R.id.categoriesList);
     		
        // Construct the data source
     	categoryArray = new ArrayList<Category>();
     	
     	// Add in default categories 
     	categoryArray.add(new Category("Adventurous","trekking",R.drawable.sunsetOrange));
     	categoryArray.add(new Category("Social","party1",R.drawable.ripelemon));
     	categoryArray.add(new Category("Cultural","greek1",R.drawable.jade)); 
     	categoryArray.add(new Category("Historical","time12",R.drawable.crusta)); 
     	categoryArray.add(new Category("Education","books30",R.drawable.jacksonspurple));
     	categoryArray.add(new Category("Hungry","plate7",R.drawable.california)); 
     	categoryArray.add(new Category("Natural","tree101",R.drawable.mountainmeadow));
     	categoryArray.add(new Category("Lazy","man271",R.drawable.curiousblue));
     	categoryArray.add(new Category("Luxurious","banknotes",R.drawable.rebeccapurple)); 
     	
     	// Initialize array adapter for categories 
        categoriesAdapter = new CategoryAdapter(this,categoryArray);
        
        // Attach the adapter to a ListView
        categoriesList.setAdapter(categoriesAdapter);
        
        setupListViewListener(); 
    }
    
	private void setupListViewListener() { 
		categoriesList.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView <? > parent, View view, int position, long id) { 
				String categoryClickedOn = categoryArray.get(position).getName();
				Log.i("MainActivity", "Clicked item " + position + ": " + categoryClickedOn); 
				try {
					sendJson(categoryClickedOn);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(MainActivity.this, ViewCategory.class); 
				if (intent != null) { 
					// put "extras" into the bundle for access in the edit activity
					intent.putExtra("jsonString",jsonin); 
					// brings up the second activity
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
	private void sendJson(final String category) throws InterruptedException {
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
	    		} catch (Exception e) {
	    			Log.d("JSON", "Caught Exception");
	    			Log.e("Buffer Error", "Error converting result " + e.toString());
	    		}
	    		Log.d("JSON", jsonin);
			}
		};
		t.start();
		t.join();
	}
}
