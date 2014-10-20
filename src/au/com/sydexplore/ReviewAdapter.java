package au.com.sydexplore;

import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewAdapter extends ArrayAdapter{

	// List of reviews 
	private List<Review> reviewList;
	private Context context;

	/**
	 * Constructor for review adapter 
	 * @param context
	 * @param reviewList
	 */
	public ReviewAdapter(Context context, List reviewList) {
		super(context, R.layout.activity_review_row, reviewList);
		this.reviewList = reviewList;
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHold holder = null;
		// Check if view is null and then set stuff 
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.activity_review_row, null, false);
			holder = new ViewHold();
			holder.reviewTitle = (TextView) view.findViewById(R.id.reviewTitle);
			holder.reviewerName = (TextView) view.findViewById(R.id.reviewerName);
			holder.reviewCategory = (ImageView) view.findViewById(R.id.reviewCategory);
			holder.positionHolder = position;
			view.setTag(holder);
		}else{ // view already set, grab it 
			holder = (ViewHold) view.getTag();
		}

		// Grab the review
		Review rev = reviewList.get(position);
		
		// Construct the title and set it etc. Do this for other components too 
		String title = "''" + rev.getReviewTitle() + "''";
		holder.reviewTitle.setText(title);
		String reviewer = rev.getReviewerName();
		holder.reviewerName.setText(reviewer);

		String rating = rev.getReviewRating();
		Float stars = Float.parseFloat(rating);
		RatingBar starRating = (RatingBar)view.findViewById(R.id.reviewRating);
		starRating.setRating(stars);

		String category = rev.getReviewCategory();
		String image = "";
		int colour = 65;

		// Set different images and colours depending on the review category
		if (category.equals("Adventurous")){
			image = "trekking";
			colour=R.drawable.sunsetOrange;	
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_red));
		} else if (category.equals("Social")){
			image="party1";
			colour=R.drawable.ripelemon;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_ripelemon));
		} else if (category.equals("Cultural")){
			image="greek1";
			colour=R.drawable.jade;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_jade));
		} else if (category.equals("Historical")){
			image="time12";
			colour=R.drawable.crusta;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_crusta));
		} else if (category.equals("Education")){
			image="books30";
			colour=R.drawable.jacksonspurple;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_jacksonspurple));
		} else if (category.equals("Hungry")){
			image="plate7";
			colour=R.drawable.california;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_california));
		} else if (category.equals("Natural")){
			image="tree101";
			colour=R.drawable.mountainmeadow;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_mountainmeadow));
		} else if (category.equals("Lazy")){
			image="man271";
			colour=R.drawable.curiousblue;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_curiousblue));
		} else if (category.equals("Luxurious")){
			image="banknotes";
			colour=R.drawable.rebeccapurple;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_rebeccapurple));
		} else if (category.equals("Fun")){
			image="star83";
			colour=R.drawable.not_so_electric_blue;
			holder.reviewCategory.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_notsoelectricblue));
		}

		// Get image resource and set it 
		String uri = "@drawable/"+image; 
		int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
		Drawable res = context.getResources().getDrawable(imageResource);
		holder.reviewCategory.setImageDrawable(res);
		
		return view;
	}

	public class ViewHold {
		public RatingBar reviewRating;
		TextView reviewTitle;
		TextView reviewerName;
		ImageView reviewCategory;
		int positionHolder;
	}
}
