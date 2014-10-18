package au.com.sydexplore; 

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.LocationClient;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class ViewCategory extends TabActivity {
	
	// Attractions array
	public static ArrayList<Attraction> attractionsArray; 	
	public static String jsonString = "";
	
	// Colors
	public static int primaryColor;
	public static int secondaryColor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_tab);
        
		//set the back button in the action bar
		getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Get JSON string from intent data 
        jsonString = getIntent().getStringExtra("jsonString");
        jsonString = "{ attractions: "+jsonString+" }";
        
        // Get colors
        primaryColor = getIntent().getIntExtra("primaryColor",R.drawable.electric_blue);
        secondaryColor = getIntent().getIntExtra("secondaryColor", R.drawable.not_so_electric_blue);
        
        TabHost tabHost = getTabHost();
         
        // Tab for Photos
        TabSpec listspec = tabHost.newTabSpec("List");
        // setting Title and Icon for the Tab
        listspec.setIndicator("List");
        Intent listIntent = new Intent(this, ViewCategoryList.class);
        listspec.setContent(listIntent);
         
        // Tab for Songs
        TabSpec mapspec = tabHost.newTabSpec("Map");        
        mapspec.setIndicator("Map");
        Intent mapIntent = new Intent(this, ViewCategoryMap.class);
        mapspec.setContent(mapIntent);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(listspec); // Adding list tab
        tabHost.addTab(mapspec); // Adding map tab
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater menuinf = getMenuInflater();
    	menuinf.inflate(R.menu.menu, menu);
    	//onBackPressed();
    	return true;
    }
    
  
	
    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
    	super.onOptionsItemSelected(menu);
    	
    	//back arrow
    	onBackPressed();
    	
    	String category="";
    	switch(menu.getItemId()){
    		case R.id.All:
    			displayAttractionPageFromCategory("All");
    			return true;
    		case R.id.Adventurous:
    			displayAttractionPageFromCategory("Adventurous");
    			return true;
    		case R.id.Cultural:
    			displayAttractionPageFromCategory("Cultural");
    			return true;
    		case R.id.Education:
    			displayAttractionPageFromCategory("Education");
    			return true;
    		case R.id.Fun:
    			displayAttractionPageFromCategory("Fun");
    			return true;
    		case R.id.Historical:
    			displayAttractionPageFromCategory("Historical");
    			return true;
    		case R.id.Hungry:
    			displayAttractionPageFromCategory("Hungry");
    			return true;
    		case R.id.Lazy:
    			displayAttractionPageFromCategory("Lazy");
    			return true;
    		case R.id.Luxurious:
    			displayAttractionPageFromCategory("Luxurious");
    			return true;
    		case R.id.Natural:
    			displayAttractionPageFromCategory("Natural");
    			return true;
    		case R.id.Social:
    			displayAttractionPageFromCategory("Social");
    			return true;

    	}
    	return false;
    	

    }
    
    
    
    
   public void displayAttractionPageFromCategory(String categoryClickedOn){
	   
	   try {
			MainActivity.sendJson(categoryClickedOn);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
		Intent intent = new Intent(ViewCategory.this, ViewCategory.class); 
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
   
    
    
	
	
