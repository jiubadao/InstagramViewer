package com.codepath.instagramviewer;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;


public class PhotosActivity extends Activity {
	
	public static final String INSTAGRAM_FETCH_ENDPOINT_URL = "https://api.instagram.com/v1/media/popular?client_id=";
	public static final String CLIENT_ID = "549e2c1771f84accaeaa27e4707ae3ec"; 
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotoAdapter aPhotos;
	private PullToRefreshListView lvPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        fetchPopularPhotos();
    }


    private void fetchPopularPhotos() {
    	photos = new ArrayList<InstagramPhoto>(); 
    	
    	// Create adapter and bind it to the data from list
    	aPhotos = new InstagramPhotoAdapter(this, photos);
    	
    	// Populate the data in the ListView
    	lvPhotos = (PullToRefreshListView)findViewById(R.id.lvPhotos);
    	// Set a listener to be invoked when the list should be refreshed.
        lvPhotos.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call listView.onRefreshComplete() when
                // once the network request has completed successfully.
                refreshList();
//                aPhotos.notifyDataSetChanged();
                
				lvPhotos.postDelayed(new Runnable() {
				
									
									@Override
									public void run() {
										lvPhotos.onRefreshComplete();
									}
								}, 3000);
                
                
            }
        });
    	
    	// Set adapter to the ListView
    	lvPhotos.setAdapter(aPhotos);
    	refreshList();    	
	}
    
    
    private void refreshList(){

    	// { "data" => [x] => "images" => "standard_resolution" => "url" }
		
    	// Setup popular url endpoint
    	String popularPhotosURL = INSTAGRAM_FETCH_ENDPOINT_URL + CLIENT_ID;
    	
    	// Network Client
    	AsyncHttpClient httpClient = new AsyncHttpClient();
    	httpClient.get(popularPhotosURL, new JsonHttpResponseHandler(){
    		// Handle callback response
    		@Override
    		public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
    			
    			JSONArray photosJSON = null;
    			try{
    				photos.clear();
    				photosJSON = response.getJSONArray("data");
    				for(int count = 0; count < photosJSON.length(); count++){
    					JSONObject photoJSON = photosJSON.getJSONObject(count);
    					if (photoJSON != null){
	    					InstagramPhoto photo = new InstagramPhoto();
	    					if (photoJSON.has("user") && photoJSON.getJSONObject("user") != null){
	    						photo.setUsername(photoJSON.getJSONObject("user").getString("username"));
	    						if (photoJSON.getJSONObject("user").has("profile_picture") 
		    							&& photoJSON.getJSONObject("user").getString("profile_picture") != null){
		    						photo.setUserProfilePicUrl(photoJSON.getJSONObject("user").getString("profile_picture"));
		    					}
	    					}	    					
	    					if (photoJSON.get("caption") != null
	    							&& photoJSON.get("caption") instanceof org.json.JSONObject ){
	    						photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
	    					}
	    					
	    					// get image related info
	    					photo.setCreatedTime(photoJSON.getLong("created_time"));
	    					photo.setImageUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
	    					photo.setImageHeight(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
	    					
	    					// Formatting the likesCount in Numeric readable format delimited with comma for thousands mark
	    					int likesCount = photoJSON.getJSONObject("likes").getInt("count");
	    					NumberFormat formatter = NumberFormat.getCurrencyInstance();
	    					String formattedLikesCount = formatter.format(likesCount);
	    					photo.setLikesCount(formattedLikesCount.substring(1, formattedLikesCount.indexOf(".")));
	    					
	    					// get comments - last & second-last
	    					if (photoJSON.get("comments") != null 
	    							&& photoJSON.getJSONObject("comments").get("data") != null){
	    						JSONArray commentsArray = photoJSON.getJSONObject("comments").getJSONArray("data");
	    						
	    						// last comment
	    						if(commentsArray.get(commentsArray.length() - 1) != null){
	    							JSONObject lastComment = commentsArray.getJSONObject(commentsArray.length() - 1);
	    							photo.setLastCommentUsername(lastComment.getJSONObject("from").getString("username"));
	    							photo.setLastComment(lastComment.getString("text"));
	    						}
	    						
	    						// second last comment
	    						if(commentsArray.get(commentsArray.length() - 2) != null){
	    							JSONObject lastComment = commentsArray.getJSONObject(commentsArray.length() - 2);
	    							photo.setSecondLastCommentUsername(lastComment.getJSONObject("from").getString("username"));
	    							photo.setSecondLastComment(lastComment.getString("text"));
	    						}
	    						
	    						
	    					}
	    					
	    					
	    					photos.add(photo);
	    				}
    				}
    				aPhotos.notifyDataSetChanged();
    			}
    			catch(JSONException exception){
    				exception.printStackTrace();
    			}
    			
    		}
    		
    		@Override
    		public void onFailure(int statusCode, Header[] headers,
    				Throwable throwable, JSONObject errorResponse) {
    			super.onFailure(statusCode, headers, throwable, errorResponse);
    		}
    	}); 
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
  
}
