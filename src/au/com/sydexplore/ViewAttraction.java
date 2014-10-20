package au.com.sydexplore;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import au.com.sydexplore.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ViewAttraction extends Activity {

	// Request code for editting item 
	public final int EDIT_ITEM_REQUEST_CODE = 1243123;

	// Image display options
	DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_attraction);
	}

	/** 
	 * Open up submit review activity 
	 * @param view
	 */
	public void submitReview(View view) 
	{
		Intent intent = new Intent(ViewAttraction.this, SubmitReview.class);
		startActivity(intent);
	}

	/**
	 * Open up view attraction informaiton activity
	 * @param view
	 */
	public void viewAttractionInfo(View view)
	{
		Intent intent = new Intent(ViewAttraction.this, ViewAttractionInfo.class);
		startActivity(intent);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		// Check if the user came from submitting a review
		if (requestCode == EDIT_ITEM_REQUEST_CODE) { 
			if (resultCode == RESULT_OK) { 
				// Display message for user 
				Toast.makeText(this,"Review submitted",Toast.LENGTH_SHORT).show(); 
			} 
		} 
	}
}
