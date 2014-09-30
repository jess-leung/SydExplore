package au.com.sydexplore; 

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Attraction implements Serializable {
	// name of attraction 
    public String name;
    
    // location of attraction 
    public String location; 
    
    // url of image thumbnail
    public String thumbnailUrl;
    
    //opening hours of attraction
    public String openingHours;
    
    //description of attraction
    public String description;
    
    //URL of the attraction
    public String URL;
    
    //url of large image
    public String image;
    
    // long and lat 
    public double longitude; 
    public double latitude;
    
    /** 
     * Constructor for normal attraction 
     * @param name
     * @param location
     */
    public Attraction(String name, String location, String thumbnailurl, String openingHours, String description, String URL, String image) {
       this.name = name;
       this.location = location;
       this.thumbnailUrl = thumbnailurl;
       this.openingHours = openingHours;
       this.description = description;
       this.URL = URL;
       this.image = image;
    }
    
    /**
     * Constructor for creating attraction from JSON object 
     * @param object
     */
    public Attraction(JSONObject object){
        try {
            this.name = object.getString("name");
            this.location = object.getString("location");
            this.latitude = Double.parseDouble(object.getString("latitude"));
            this.longitude = Double.parseDouble(object.getString("longitude"));
            this.thumbnailUrl = object.getString("thumbnail");
            this.openingHours = object.getString("opening_hours");
            this.description = object.getString("description");
            this.URL = object.getString("url");
            this.image = object.getString("image");
       } catch (JSONException e) {
            e.printStackTrace();
       }
    }
    
    /**
     * Constructor for creating attraction from JSONArray of json objects 
     * @param jsonObjects
     * @return
     */
    public static ArrayList<Attraction> fromJson(JSONArray jsonObjects) {
           ArrayList<Attraction> attractions = new ArrayList<Attraction>();
           for (int i = 0; i < jsonObjects.length(); i++) {
               try {
                  attractions.add(new Attraction(jsonObjects.getJSONObject(i)));
               } catch (JSONException e) {
                  e.printStackTrace();
               }
          }
          return attractions;
    }
    
    /**
     * Return name of attraction 
     * @return
     */
    public String getName(){
    	return name; 
    }
    
    /**
     * Return location of attraction 
     * @return
     */
    public String getLocation(){
    	return location;
    }
    
    /**
     * Return url of thumbnail
     * @return
     */
    public String getThumbnailUrl(){
    	return thumbnailUrl;
    }
    
    public String getDescription(){
    	return description;
    }
    
    public String getOpeninghours(){
    	return openingHours;
    }
    
    public String getURL(){
    	return URL;
    }
    
    public String getImageURL(){
    	return image;
    }
}