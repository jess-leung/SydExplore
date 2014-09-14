package au.com.sydexplore;

import java.util.ArrayList;

import au.com.sydexplore.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	// List view for categories 
	ListView categoriesList; 
	// Array list containing the categories 
	ArrayList<String> categoryArray; 
	// ArrayAdapter 
	ArrayAdapter<String> categoriesAdapter;
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
}
