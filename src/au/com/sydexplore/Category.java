package au.com.sydexplore; 

public class Category {
	// name of attraction 
    public String name;
    
    // url of image thumbnail
    public String icon;
    
    
    /** 
     * Constructor for normal attraction 
     * @param name
     * @param location
     */
    public Category(String name, String icon) {
       this.name = name;
       this.icon = icon;
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
}