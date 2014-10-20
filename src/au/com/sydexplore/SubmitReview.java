package au.com.sydexplore;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitReview extends Activity {
	// EditText variables which refer to the text input fields 
	TextView submitReviewTitle;
	EditText editReviewTitle; 
	EditText editReviewBody;
	EditText editUser; 
	RatingBar editRating; 
	String attraction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// populate the screen using the layout
		setContentView(R.layout.activity_submit_review);

		//set the back button in the action bar
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// get the input fields 
		submitReviewTitle = (TextView) findViewById(R.id.submitReviewTitle);
		editReviewTitle = (EditText) findViewById(R.id.editReviewTitle);
		editReviewBody = (EditText) findViewById(R.id.editReviewBody);
		editUser = (EditText) findViewById(R.id.editUsername);
		editRating = (RatingBar) findViewById(R.id.ratingBar1);
		attraction = getIntent().getStringExtra("attractionName");
		String titleOfActivity = "Reviewing "+attraction;

		// Set the review title 
		submitReviewTitle.setText(titleOfActivity);
		editReviewBody.setMovementMethod(new ScrollingMovementMethod());

		// Find the root view
		View root = editReviewTitle.getRootView();

		// Set the color
		root.setBackgroundColor(getResources().getColor(R.drawable.grey));	   
	}

	/**
	 * On submit of review, send data to heroku
	 * @param v
	 */
	public void onSubmit(View v) {
		// Get the values from the fields 
		String username = editUser.getText().toString();
		String reviewText = editReviewBody.getText().toString(); 
		String reviewTitle = editReviewTitle.getText().toString(); 
		float rating = editRating.getRating();

		// Basic validation checking for empty fields 
		if (username.equals("") || reviewText.equals("") || reviewTitle.equals("")){
			String emptyFields="";
			if (reviewTitle.equals("")){
				emptyFields+="Review Title ";
			}
			if (username.equals("")){
				emptyFields+="Username ";
			}
			if (reviewText.equals("")){
				emptyFields+="Review ";
			}
			// Alert user of invalid fields
			Toast.makeText(this, emptyFields+"fields cannot be empty.", Toast.LENGTH_SHORT).show(); 
			return; 
		}

		// Otherwise, can send the data to DB 
		sendJson(username,reviewTitle,reviewText,rating);

		// Construct new intent to send data back 
		Intent data = new Intent();

		// Activity finished OK, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	} 

	/**
	 * Cancel functionality - take user back to previous activity 
	 */
	public void onCancel(View v){
		finish(); 
	}

	/**
	 * Send JSON to the heroku postgres DB. 
	 * @param username
	 * @param reviewTitle
	 * @param reviewText
	 * @param rating
	 */
	private void sendJson(final String username, final String reviewTitle, final String reviewText, final Float rating) {
		Thread t = new Thread() {

			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit

				//HttpResponse response;
				JSONObject json = new JSONObject();

				try {
					HttpPost post = new HttpPost("http://lit-cove-9769.herokuapp.com/attractions/postReview/");
					// construct JSON output 
					json.put("reviewTitle", reviewTitle);
					json.put("reviewText", reviewText);
					json.put("reviewer", username);
					json.put("rating", rating);
					json.put("attraction", attraction);
					StringEntity se = new StringEntity(json.toString());  
					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
					post.setEntity(se);
					client.execute(post);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();      
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem menu){
		super.onOptionsItemSelected(menu);

		//back arrow
		onBackPressed();

		String category="";
		switch(menu.getItemId()){
		case R.id.All:
			displayAttractionPageFromSubmitReview("All");
			return true;
		case R.id.Adventurous:
			displayAttractionPageFromSubmitReview("Adventurous");
			return true;
		case R.id.Cultural:
			displayAttractionPageFromSubmitReview("Cultural");
			return true;
		case R.id.Education:
			displayAttractionPageFromSubmitReview("Education");
			return true;
		case R.id.Fun:
			displayAttractionPageFromSubmitReview("Fun");
			return true;
		case R.id.Historical:
			displayAttractionPageFromSubmitReview("Historical");
			return true;
		case R.id.Hungry:
			displayAttractionPageFromSubmitReview("Hungry");
			return true;
		case R.id.Lazy:
			displayAttractionPageFromSubmitReview("Lazy");
			return true;
		case R.id.Luxurious:
			displayAttractionPageFromSubmitReview("Luxurious");
			return true;
		case R.id.Natural:
			displayAttractionPageFromSubmitReview("Natural");
			return true;
		case R.id.Social:
			displayAttractionPageFromSubmitReview("Social");
			return true;

		}
		return false;
	}

	public void displayAttractionPageFromSubmitReview(String categoryClickedOn){
		try {
			MainActivity.sendJson(categoryClickedOn);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Create new intent 
		Intent intent = new Intent(SubmitReview.this, ViewCategory.class); 
		if (intent != null) { 
			// put "extras" into the bundle for access in the edit activity
			intent.putExtra("jsonString",MainActivity.jsonin); 

			int i;
			int secondaryColor = 0;

			// Check if category is all 
			if (categoryClickedOn.equals("All")){
				MainActivity.oldColor = MainActivity.allCategory;
				secondaryColor = MainActivity.secAllCategory;
			} // otherwise, construct category array
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater menuinf = getMenuInflater();
		menuinf.inflate(R.menu.menu, menu);
		return true;
	}
}
