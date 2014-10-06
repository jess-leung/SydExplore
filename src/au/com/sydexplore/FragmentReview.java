package au.com.sydexplore;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentReview extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View v = inflater.inflate(R.layout.fragment_review, container, false);
	
		//get the review details		
		Bundle reviewDetails = getArguments();

		
		if (reviewDetails!=null){
			String reviewTitle = reviewDetails.getString("reviewTitle");
			String reviewText = reviewDetails.getString("reviewText");
			String reviewRating = reviewDetails.getString("reviewRating");
			String reviewerName = reviewDetails.getString("reviewerName");

			TextView title = (TextView)v.findViewById(R.id.reviewTitle);
			title.setText(reviewTitle);
			
			TextView text = (TextView)v.findViewById(R.id.reviewText);
			text.setText(reviewText);
			
			TextView rating = (TextView)v.findViewById(R.id.reviewRating);
			rating.setText(reviewRating);
			
			TextView reviewer = (TextView)v.findViewById(R.id.reviewerName);
			reviewer.setText(reviewerName);
		}
		
		return v;

	}
	
	


}
