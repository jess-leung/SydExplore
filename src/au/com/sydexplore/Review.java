package au.com.sydexplore;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Review {

	//title of the review
	public String reviewTitle;

	//review text 
	public String reviewText; 

	//review rating
	public String reviewRating;

	//reviewer name
	public String reviewerName;

	//review category
	public String reviewCategory;

	/** 
	 * Constructor for normal review 
	 * @param review title
	 * @param review text
	 * @param review rating
	 */
	public Review(String reviewerName, String reviewerText, String reviewRating, String reviewTitle, String reviewCategory) {
		this.reviewTitle = reviewTitle;
		this.reviewText = reviewText;
		this.reviewRating = reviewRating;
		this.reviewerName = reviewerName;
		this.reviewCategory = reviewCategory;
	}

	/**
	 * Constructor for creating reviews from JSON object 
	 * @param object
	 */
	public Review(JSONObject object){
		try {
			this.reviewerName = object.getString("reviewer_name");
			this.reviewText = object.getString("review_text");
			this.reviewRating = object.getString("review_rating");
			this.reviewTitle = object.getString("review_title");
			this.reviewCategory = object.getString("review_category");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor for creating reviews from JSONArray of json objects 
	 * @param jsonObjects
	 * @return
	 */
	public static ArrayList<Review> fromJson(JSONArray jsonObjects) {
		ArrayList<Review> reviews = new ArrayList<Review>();
		for (int i = 0; i < jsonObjects.length(); i++) {
			try {
				reviews.add(new Review(jsonObjects.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return reviews;
	}

	/**
	 * Return name of review 
	 * @return
	 */
	public String getReviewTitle(){
		return reviewTitle; 
	}

	/**
	 * Return review text
	 * @return
	 */
	public String getReviewText(){
		return reviewText;
	}

	/**
	 * Return review rating
	 * @return
	 */
	public String getReviewRating(){
		return reviewRating;
	}

	/**
	 * Return reviewer name
	 * @return
	 */
	public String getReviewerName(){
		return reviewerName;
	}

	/**
	 * Return review category
	 * @return
	 */
	public String getReviewCategory(){
		return reviewCategory;
	}
}
