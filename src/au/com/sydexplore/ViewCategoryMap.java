package au.com.sydexplore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
		
		static InputStream is = null;
		static String jsonin = "";
		
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

			googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				    @Override
				    public void onInfoWindowClick(Marker marker) {
				    	Log.i("Map Marker",marker.getTitle());
				    	String thisMarkerTItle = marker.getTitle();
				    	for (Attraction att:ViewCategory.attractionsArray){
				    		if (att.getName().equals(thisMarkerTItle)){
				    			Attraction attractionClickedOn = att;
				    			//get review details from the database
								try{
									sendJson(thisMarkerTItle);
								}
									catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
				    			//start a new intent for the attraction information
								Intent intent = new Intent(ViewCategoryMap.this, ViewAttractionInfo.class);
								if (intent != null) {
									//add the attraction information to the attraction information intent
									intent.putExtra("attractionName", attractionClickedOn.getName());
									intent.putExtra("location", attractionClickedOn.getLocation());
									intent.putExtra("openingHours", attractionClickedOn.getOpeninghours());
									intent.putExtra("URL", attractionClickedOn.getURL());
									intent.putExtra("description", attractionClickedOn.getDescription());
									intent.putExtra("image", attractionClickedOn.getImageURL());
									intent.putExtra("jsonString",jsonin);
									startActivity(intent);	
								}
				    		}
				    	}
//				    	Intent in=new Intent(getApplicationContext(),first.class);
//				    	in.putExtra("driverid", marker.getSnippet());
//				    	startActivity(in);  
				    }
			});         
			
			// create markers
			for(Attraction att:ViewCategory.attractionsArray){
				double attLat = att.latitude;
				double attLon = att.longitude;
				Log.i("ATT",String.valueOf(attLat));
				MarkerOptions marker = new MarkerOptions().position(new LatLng(attLat, attLon)).title(att.getName());
				// adding marker
				googleMap.addMarker(marker).showInfoWindow();
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
	     * Send JSON to backend 
	     * @param category
	     * @throws InterruptedException 
	     */
		private void sendJson(final String attractionName) throws InterruptedException {
			Thread t = new Thread() {

				public void run() {
					HttpClient client = new DefaultHttpClient();
					HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
					
					//HttpResponse response;
		            JSONObject json = new JSONObject();

		            try {
		            	HttpPost post = new HttpPost("http://lit-cove-9769.herokuapp.com/attractions/getReviewDetails/");
		            	// construct JSON output 
		            	json.put("attraction_name", attractionName);
		            	Log.d("JSON", json.toString());
		            	StringEntity se = new StringEntity(json.toString());  
		            	se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		            	post.setEntity(se);
		            	HttpResponse httpResponse = client.execute(post);
		    			HttpEntity httpEntity = httpResponse.getEntity();
		    			is = httpEntity.getContent();    
		            	Log.d("JSON", "Got content from entity");
	        
		            } catch(Exception e) {
		            	e.printStackTrace();
		            	Log.d("JSON", "Caught Exception");
		            }
		            
		            try {
		    			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		    			StringBuilder sb = new StringBuilder();
		    			String line = null;
		    			while ((line = reader.readLine()) != null) {
		    				sb.append(line + "\n");
		    			}
		    			is.close();
		    			jsonin = sb.toString();
		    		} catch (Exception e) {
		    			Log.d("JSON", "Caught Exception");
		    			Log.e("Buffer Error", "Error converting result " + e.toString());
		    		}
		    		Log.d("JSON", "Json String returned");
				}
			};
			t.start();
			t.join();
		}
	   
		
	}