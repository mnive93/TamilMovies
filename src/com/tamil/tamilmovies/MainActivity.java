package com.tamil.tamilmovies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tamil.tamilmovies.R;


import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

@SuppressLint("ClickableViewAccessibility")
public class MainActivity extends Activity {
ArrayList<String>movies;
ArrayList<String>rating;
ArrayList<String>images;
TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);
		
		setContentView(R.layout.activity_main);
		movies = new ArrayList<String>();
		loadMovies();
		final ViewPager viewPager = (ViewPager)findViewById(R.id.imageviewer);
		tv = (TextView)findViewById(R.id.tv);
		ImagePagerAdapter adapter = new ImagePagerAdapter(this.getApplicationContext(),movies);
		viewPager.setAdapter(adapter);
		viewPager.setOnTouchListener(new View.OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent e) {
		        // How far the user has to scroll before it locks the parent vertical scrolling.
		        final int margin = 10;
		        final int fragmentOffset = v.getScrollX() % v.getWidth();

		        if (fragmentOffset > margin && fragmentOffset < v.getWidth() - margin) {
		            viewPager.getParent().requestDisallowInterceptTouchEvent(true);
		        }
		        return false;
		    }

			
		});
		 tv.setText("1/"+String.valueOf(movies.size()));
    PageListener pageListener = new PageListener();
		viewPager.setOnPageChangeListener((OnPageChangeListener) pageListener);
		if(Build.VERSION.SDK_INT >= 11)
		{
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		}
	}
	private class PageListener extends SimpleOnPageChangeListener{	
		 public void onPageSelected(int position) {
			 tv.setText(String.valueOf(position+1)+"/"+String.valueOf(movies.size()));
		 }
		 
	}
public void loadMovies()
{
	Log.d("here","here");
	try
	{
	InputStream is = this.getAssets().open("movies.txt");
	
	StringBuilder text = new StringBuilder();

	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    String line;

	    while ((line = br.readLine()) != null) {
	    	movies.add(line);
	    }
	}
	catch (IOException e) {
	    //You'll need to add proper error handling here
	}

	
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
