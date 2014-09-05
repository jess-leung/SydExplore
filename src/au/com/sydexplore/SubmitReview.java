package au.com.sydexplore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class SubmitReview extends Activity {
	public int position=0;
	EditText etItem; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//populate the screen using the layout
		setContentView(R.layout.activity_submit_review);
		
		//Get the data from the main screen
		// String editItem = getIntent().getStringExtra("item");
		// position = getIntent().getIntExtra("position",-1);
		
		// show original content in the text field
		// etItem = (EditText)findViewById(R.id.etEditItem);
		// etItem.setText(editItem);
	}

	public void onSubmit(View v) {

	} 
}
