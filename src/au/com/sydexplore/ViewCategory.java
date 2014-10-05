package au.com.sydexplore; 

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
}