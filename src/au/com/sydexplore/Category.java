package au.com.sydexplore; 

import au.com.sydexplore.R.drawable;

public class Category {
	// name of attraction 
    public String name;
    
    // url of image thumbnail
    public String icon;
    
    // color of category
    public int color; 
    
    // secondary color of category 
    public int secondaryColor;
    
    /** 
     * Constructor for normal attraction 
     * @param name
     * @param location
     */
    public Category(String name, String icon,int sunsetorange, int secondaryCol) {
       this.name = name;
       this.icon = icon;
       this.color = sunsetorange;
       this.secondaryColor = secondaryCol;
    }
    
    /**
     * Return name of category
     * @return
     */
    public String getName(){
    	return name; 
    }
       
    /**
     * Return icon
     * @return
     */
    public String getIcon(){
    	return icon;
    }
    
    /**
     * Return color
     */
    public int getColor(){
    	return color;
    }
}