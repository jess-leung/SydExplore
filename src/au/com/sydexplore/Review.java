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
	    
	    //review date
	    public String reviewDate;
	    
	    /** 
	     * Constructor for normal review 
	     * @param review title
	     * @param review text
	     * @param review rating
	     */
	    public Review(String reviewText, String reviewerName, String reviewTitle, String reviewRating, String reviewDate) {
	       this.reviewTitle = reviewTitle;
	       this.reviewText = reviewText;
	       this.reviewRating = reviewRating;
	       this.reviewerName = reviewerName;
	       this.reviewDate = reviewDate;

	    }
	    
	    /**
	     * Constructor for creating reviews from JSON object 
	     * @param object
	     */
	    public Review(JSONObject object){
	        try {
	            this.reviewTitle = object.getString("reviewTitle");
	            this.reviewText = object.getString("reviewText");
	            this.reviewRating = object.getString("reviewRating");
	            this.reviewerName = object.getString("reviewerName");
	            this.reviewDate = object.getString("reviewDate");
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
	     * Return review date
	     * @return
	     */
	    public String getReviewDate(){
	    	return reviewDate;
	    }
	
	
}
