package com.codepath.instagramviewer;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {
	
	private static String PRIMARY_FONT = "Roboto-Regular.ttf"; // font string name
	private static String SECONDARY_FONT = "SourceSansPro-Regular.otf"; // font string name
	private static String SECONDARY_FONT_BOLD = "SourceSansPro-Semibold.otf"; // font string name
	private final Context context; // final Context variable to allow access to ViewHolder inner class
	
	// View lookup cache
    private static class ViewHolder {
    	RoundedImageView ivUserProfilePic;
		TextView tvUsername;
		TextView tvCaption;
		ImageView ivPhoto;
		TextView tvLikesCount;
		TextView tvLastComment;
		TextView tvLastCommentUsername;
		TextView tvSecondLastComment;
		TextView tvSecondLastCommentUsername;
    }
	
    public InstagramPhotoAdapter(Context context, List<InstagramPhoto> photos){
		super(context, R.layout.item_photos, photos);
		this.context=context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// get Item from getItem method of ArrayAdapter class
		final InstagramPhoto photo = getItem(position);
		
		// Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
		
		if (convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photos, parent, false);
			viewHolder.ivUserProfilePic = (RoundedImageView) convertView.findViewById(R.id.ivUserProfilePic);
			viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
			viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
			viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
			viewHolder.tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
			viewHolder.tvLastCommentUsername = (TextView) convertView.findViewById(R.id.tvLastCommentUsername);
			viewHolder.tvLastComment = (TextView) convertView.findViewById(R.id.tvLastComment);
			viewHolder.tvSecondLastCommentUsername = (TextView) convertView.findViewById(R.id.tvSecondLastCommentUsername);
			viewHolder.tvSecondLastComment = (TextView) convertView.findViewById(R.id.tvSecondLastComment);

			// set the fonts
			applySecondaryFont(viewHolder.tvUsername);
			applyPrimaryFont(viewHolder.tvCaption);
			applySecondaryFont(viewHolder.tvLikesCount);	
			applySecondaryFont(viewHolder.tvLastCommentUsername);
			applyPrimaryFont(viewHolder.tvLastComment);
			applySecondaryFont(viewHolder.tvSecondLastCommentUsername);
			applyPrimaryFont(viewHolder.tvSecondLastComment);
			
			convertView.setTag(viewHolder);
		}
		else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
		
		// setting the photo props to all the view elements
		viewHolder.tvUsername.setText(photo.getUsername());
		if (photo.getCaption() == null || photo.getCaption().equals("")){
			viewHolder.tvCaption.setVisibility(View.GONE);
		}
		else{
			viewHolder.tvCaption.setText(photo.getCaption());
		}
		viewHolder.tvLikesCount.setText(photo.getLikesCount() + " likes");
		viewHolder.ivPhoto.getLayoutParams().height = photo.getImageHeight();
		viewHolder.ivPhoto.setImageResource(0);
		
		
		// setting the last comments to the viewHolder elements if its not empty
		if (photo.getLastComment() != null && photo.getLastCommentUsername() != null){
			viewHolder.tvLastCommentUsername.setText(photo.getLastCommentUsername());
			viewHolder.tvLastComment.setText(photo.getLastComment());			
		}
		else{
			viewHolder.tvLastCommentUsername.setVisibility(View.GONE);
			viewHolder.tvLastComment.setVisibility(View.GONE);
		}
		
		if (photo.getSecondLastComment() != null && photo.getSecondLastCommentUsername() != null){
			viewHolder.tvSecondLastCommentUsername.setText(photo.getSecondLastCommentUsername());
			viewHolder.tvSecondLastComment.setText(photo.getSecondLastComment());			
		}
		else{
			viewHolder.tvSecondLastCommentUsername.setVisibility(View.GONE);
			viewHolder.tvSecondLastComment.setVisibility(View.GONE);
		}
		
		// used picasso lib to load the image from the specified url and set it to the imageView
		Picasso.with(getContext()).load(photo.getUserProfilePicUrl()).into(viewHolder.ivUserProfilePic);
		Picasso.with(getContext()).load(photo.getImageUrl()).into(viewHolder.ivPhoto);
		
		return convertView;
	}
	
	  // Method to set the font 
    public void applyPrimaryFont(TextView textView) {
        Typeface typeface = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/"
                + SECONDARY_FONT);
        textView.setTypeface(typeface);
    }
	
    // Method to set the font 
    public void applySecondaryFont(TextView textView) {
    	Typeface typeface = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/"
    			+ SECONDARY_FONT_BOLD);
    	textView.setTypeface(typeface);
    }
    
}
