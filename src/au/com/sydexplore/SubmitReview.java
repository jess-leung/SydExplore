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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;


public class SubmitReview extends Activity {
	// EditText variables which refer to the text input fields 
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
		
		// get the input fields 
		editReviewTitle = (EditText) findViewById(R.id.editReviewTitle);
		editReviewBody = (EditText) findViewById(R.id.editReviewBody);
		editUser = (EditText) findViewById(R.id.editUsername);
		editRating = (RatingBar) findViewById(R.id.ratingBar1);
		attraction = "Sydney Opera House"; // TODO: get this dynamically 
	}

	public void onSubmit(View v) {
		String username = editUser.getText().toString();
		String reviewText = editReviewBody.getText().toString(); 
		String reviewTitle = editReviewTitle.getText().toString(); 
		float rating = editRating.getRating();
		sendJson(username,reviewTitle,reviewText,rating);
	} 
	
	private void sendJson(final String username, final String reviewTitle, final String reviewText, final Float rating) {
		Thread t = new Thread() {

			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
				
				//HttpResponse response;
	            JSONObject json = new JSONObject();

	            try {
	            	Log.d("JSON", "HERE");
	            	HttpPost post = new HttpPost("http://lit-cove-9769.herokuapp.com/attractions/postReview/");
	            	// construct JSON output 
	            	json.put("reviewTitle", reviewTitle);
	            	json.put("reviewText", reviewText);
	            	json.put("reviewer", username);
	            	json.put("rating", rating);
	            	json.put("attraction", attraction);
	            	Log.d("JSON", json.toString());
	            	StringEntity se = new StringEntity(json.toString());  
	            	se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	            	post.setEntity(se);
	            	//HttpResponse response = 
	            	client.execute(post);
	            	Log.d("JSON", "JSON file posted");
        
	            } catch(Exception e) {
	            	e.printStackTrace();
	            	Log.d("JSON", "Caught Exception");
	            	Log.d("JSON", "Cannot Establish Connection");
	            }
			}
		};
		t.start();      
	}
}
