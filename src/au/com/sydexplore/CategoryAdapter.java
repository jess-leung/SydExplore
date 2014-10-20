package au.com.sydexplore; 

import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends ArrayAdapter {

	// List of categories
	private List<Category> categoriesList;
	private Context context;

	/**
	 * Constructor for category adapter 
	 * @param context
	 * @param catList
	 */
	public CategoryAdapter(Context context, List catList) {
		super(context, R.layout.activity_category_row, catList);
		this.categoriesList = catList;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		// View not set yet 
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.activity_category_row, null, false);
			holder = new ViewHolder();
			holder.categoryName = (TextView) view.findViewById(R.id.categoryName);
			holder.icon = (ImageView) view.findViewById(R.id.categoryIcon);
			view.setTag(holder);
		}else{ // View set, so just grab it 
			holder = (ViewHolder) view.getTag();
		}
		
		// Get category 
		Category cat = categoriesList.get(position);
		holder.categoryName.setText(cat.getName());
		
		// If there is an icon, set it
		if (holder.icon != null) {
			// get icon 
			String uri = "@drawable/"+cat.getIcon(); 
			int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
			Drawable res = context.getResources().getDrawable(imageResource);
			
			// Set images and colour of category 
			holder.icon.setImageDrawable(res);
			holder.icon.setBackgroundResource(cat.getColor());
			holder.categoryName.setBackgroundResource(cat.getColor());
		}
		return view;
	}

	class ViewHolder {
		TextView categoryName; 
		ImageView icon;
	}
}