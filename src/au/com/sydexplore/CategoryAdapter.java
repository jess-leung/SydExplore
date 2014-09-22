package au.com.sydexplore; 

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends ArrayAdapter {

	private List<Category> categoriesList;
	private Context context;

	public CategoryAdapter(Context context, List catList) {
		super(context, R.layout.activity_category_row, catList);
		this.categoriesList = catList;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.activity_category_row, null, false);
			holder = new ViewHolder();
			holder.categoryName = (TextView) view.findViewById(R.id.categoryName);
			holder.icon = (ImageView) view.findViewById(R.id.categoryIcon);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
  
	  Category cat = categoriesList.get(position);
	  holder.categoryName.setText(cat.getName());
	  Log.i("CATEGORY",cat.getName());
	  if (holder.icon != null) {
		  String uri = "@drawable/"+cat.getIcon(); //+".png";
		  Log.i("CAT",uri);
		  int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
		  Drawable res = context.getResources().getDrawable(imageResource);
		  holder.icon.setImageDrawable(res);
	  }
	  return view;
	}

	 static class ViewHolder {
	//	  ImageView rightIcon;
		  TextView categoryName; 
		  ImageView icon; 
	 }
}