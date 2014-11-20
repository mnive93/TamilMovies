package com.tamil.tamilmovies;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.tamil.tamilmovies.R;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ImagePagerAdapter extends PagerAdapter  {
ArrayList<String>movies;
ArrayList<String>rating;
ArrayList<String>images;
ArrayList<String>id;
ArrayList<String>hero;
ArrayList<String>syno;
Context context;
ViewPager viewPager;
ImageLoader imageLoader;
public ImagePagerAdapter()

{
	
}
public ImagePagerAdapter(Context context,ArrayList<String>movies)
{
	this.context = context;
	this.movies = movies;
	this.rating = new ArrayList<String>();
	this.images = new ArrayList<String>();
	id = new ArrayList<String>();
	syno = new ArrayList<String>();
	hero = new ArrayList<String>();
	imageLoader = ImageLoader.getInstance();
	
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return movies.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		 return view == object;
	}
	 @Override
	    public int getItemPosition(Object object) {
	    	   return POSITION_NONE;
	    	}
	 public DisplayImageOptions loadOptions()
		{
			return new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.ic_launcher)
			.resetViewBeforeLoading(true)
			.cacheOnDisk(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true)
			.displayer(new FadeInBitmapDisplayer(100))
			.build();
		}
	class ViewHolder
	{
		TextView name;
		ImageView poster;
		TextView cast;
		TextView rate;
		TextView synopsis;
		ProgressBar bar;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
	    return movies.get(position);
	}
	 @Override
	    public Object instantiateItem(View collection, int position) {
		 ViewHolder holder;
		 viewPager = (ViewPager)collection;
		 LayoutInflater inflater = (LayoutInflater) collection.getContext()
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		 View view;
				
					view = inflater.inflate(R.layout.gridview_items,null);
					holder = new ViewHolder();
					holder.name = (TextView)view.findViewById(R.id.name);
					holder.cast = (TextView)view.findViewById(R.id.hero);
					holder.rate = (TextView)view.findViewById(R.id.rate);
					holder.synopsis = (TextView)view.findViewById(R.id.synopsis);
					holder.poster = (ImageView)view.findViewById(R.id.poster);
					holder.bar = (ProgressBar)view.findViewById(R.id.progressBar1);
					
					view.setTag(holder);
					view.setId(position);
				
				
				try
				{
				if(!rating.isEmpty() && (position < (rating.size())))
				{
					holder.rate.setText(rating.get(position)+"/100");
					holder.name.setText(movies.get(position));
					holder.cast.setText(hero.get(position));
					holder.synopsis.setText(syno.get(position));
					holder.bar.setVisibility(View.GONE);
					imageLoader.displayImage(images.get(position), holder.poster);
					
				}
				else
				{
					new loadData().execute(movies.get(position));
				}
				}
				catch(Exception e)
				{
					
				}
		 viewPager.addView(view,0);
		 return view;
	 }
	 @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	    	
	      ((ViewPager) container).removeView((View)object);
	        
	    	}
	 class loadData extends AsyncTask<String, Void, String> {
		  private final ProgressDialog dialog = new ProgressDialog(context);

		  @Override
		  protected void onPreExecute()
		  {
			 // this.dialog.setMessage("Loading...");
			 // this.dialog.show();
		  }
		    @Override
		    protected String doInBackground(String... params) {
		    	String result = "hey";
	            try
	            {
		        
		     // Create a new HttpClient and Post Header
		       
		        String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=93wruq8zcav6dpepds3cnxmu&q="+URLEncoder.encode(params[0],"UTF-8")+"&page_limit=1";
		        HttpClient httpclient = new DefaultHttpClient();
	            HttpResponse response;
	          
	                // make a HTTP request
	            	  Log.d("here","before");
	            	response = httpclient.execute(new HttpGet(url));
	            	Log.d("here","after");
	                StatusLine statusLine = response.getStatusLine();
	                Log.d("here",statusLine.toString());
	                if (statusLine.getStatusCode() == HttpStatus.SC_OK)
	                {
	                    // request successful - read the response and close the connection
	                    ByteArrayOutputStream out = new ByteArrayOutputStream();
	                    response.getEntity().writeTo(out);
	                    out.close();
	                    result = out.toString();
	                    return result;
	                }
	                else
	                {
	                    // request failed - close the connection
	                    response.getEntity().getContent().close();
	                    throw new IOException(statusLine.getReasonPhrase());
	                }
	            }
		 
	            catch (Exception e)
	            {
	               e.printStackTrace();
	            }
		 
		        return result;
		    }
		 
		    @Override
		    protected void onPostExecute(String result) {
		    	/**if (this.dialog.isShowing()) {
		            this.dialog.dismiss();
		         }**/
		            try {
		                JSONObject json = new JSONObject(result);
		                JSONArray jba = json.getJSONArray("movies");
		                JSONObject jb = jba.getJSONObject(0);
		                String id1 = jb.getString("id");
		                String title = jb.getString("title");
		                Log.d("here",title);
		                if(!id.contains(id1))
		                {
		                	id.add(id1);
		                	String sy = jb.getString("synopsis");
		                	if(!sy.equals(""))
		                	{
		                	syno.add(sy);
		                	}
		                	else
		                	{
		                		syno.add("No summary provided.");
		                	}
		                	 String rate = jb.getJSONObject("ratings").getString("audience_score");
				                rating.add(rate);
				                JSONObject im = jb.getJSONObject("posters");
				                String image_url = im.getString("detailed");
				                image_url = image_url.replace("_tmb", "_det");
				                images.add(image_url);
				                JSONArray cast = jb.getJSONArray("abridged_cast");
				                int i;
				                String heroes="";
				                for(i=0;i<cast.length();i++)
				                {
				                	heroes = heroes+cast.getJSONObject(i).getString("name");
				                	heroes = heroes+" ,";
				                }
				                hero.add(heroes);
				               /* View view = viewPager.findFocus();
				                ViewHolder holder = (ViewHolder)view.getTag();
				                holder.name.setText(title);
				                holder.hero.setText(rate);*/
				                notifyDataSetChanged();
				               
				                
		                }
		               
		               
		            } catch (Exception e){
		                e.printStackTrace();
		            
		        }
		    }	
	 }
}

