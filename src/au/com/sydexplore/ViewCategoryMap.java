package au.com.sydexplore;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCategoryMap extends Activity implements GooglePlayServicesClient.ConnectionCallbacks, 
	GooglePlayServicesClient.OnConnectionFailedListener{

		// Google Map
	    private GoogleMap googleMap;
	    
	    // Location client 
		LocationClient mLocationClient;
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_view_category_map);

			 // Connect the location client to start receiving updates
		    mLocationClient = new LocationClient(this, this, this);
		    
		    try {
	            // Loading map
	            initilizeMap();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		protected void onStart() {
		    super.onStart();
		    // Connect the client.
		    mLocationClient.connect();
		}

		protected void onStop() {
		    // Disconnecting the client invalidates it.
		    mLocationClient.disconnect();
		    super.onStop();
		}
		
		@Override
		public void onConnectionFailed(ConnectionResult arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onConnected(Bundle arg0) {
			// Get current location 
			Location mCurrentLocation = mLocationClient.getLastLocation();
			double lat = mCurrentLocation.getLatitude();
			double lon = mCurrentLocation.getLongitude();
			
			//update google map, move it to current location
			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat,lon)).zoom(10).build();
			googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

			// create markers
			for(Attraction att:ViewCategory.attractionsArray){
				double attLat = att.latitude;
				double attLon = att.longitude;
				Log.i("ATT",String.valueOf(attLat));
				MarkerOptions marker = new MarkerOptions().position(new LatLng(attLat, attLon)).title(att.getName());
				// adding marker
				googleMap.addMarker(marker);
			}
		}
		
		@Override
		public void onDisconnected() {		
		}
		
		public void onLocationChanged(Location location) {
		    // Report to the UI that the location was updated
		   
		}

		 /**
	     * function to load map. If map is not created it will create it for you
	     * */
	    private void initilizeMap() {
	        if (googleMap == null) {
	            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
	                    R.id.map)).getMap();
	 
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }
	    }
	    
	    @Override
	    protected void onResume() {
	        super.onResume();
	        initilizeMap();
	    }
	    
	    /**
	     * Go back to the list view 
	     * @param v
	     */
	    public void initializeList(View v){
	    	Intent intent = new Intent(ViewCategoryMap.this, ViewCategoryList.class);
	    	
	    	startActivity(intent);	
	    }
		
	}