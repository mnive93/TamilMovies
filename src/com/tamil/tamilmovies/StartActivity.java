package com.tamil.tamilmovies;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.CountDownTimer;

public class StartActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.start_activity);
	    final long length_in_milliseconds = 5000;
	    CountDownTimer countDownTimer = new CountDownTimer(2000,1000) {
	       
	        @Override
	        public void onTick(long millisUntilFinished) 
	        {
	        
	    }

	        @Override
	        public void onFinish() {
	           Intent myintent = new Intent(StartActivity.this,MainActivity.class);
	           startActivity(myintent);
	        finishscreen();   
	        }
	    }.start();
	}
	public void finishscreen()
	{ 
		this.finish();
		
	}
}
