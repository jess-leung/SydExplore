package au.com.sydexplore;

import java.io.Serializable;
import java.util.ArrayList;

public class AttractionDataWrapper implements Serializable {

   private ArrayList<Attraction> attractions;

   public AttractionDataWrapper(ArrayList<Attraction> data) {
      this.attractions = data;
   }

   public ArrayList<Attraction> getAttractions() {
      return this.attractions;
   }

}