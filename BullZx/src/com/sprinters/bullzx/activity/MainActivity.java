package com.sprinters.bullzx.activity;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.sprinters.bullzx.network.ConnectionDetector;
import com.sprinters.bullzx.R;
import com.sprinters.bullzx.activity.*;
import com.sprinters.bullzx.model.Stock;
import com.sprinters.bullzx.model.WatchList;
import com.sprinters.bullzx.utils.MyApp;
import com.sprinters.bullzx.utils.PullStockParser;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private MyApp application;
	ConnectionDetector cd;
	private Menu mymenu;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Add our menu
		
		super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.watchlist_menu, menu);
         
        // We should save our menu so we can use it to reset our updater.
        mymenu = menu;
		return true;
	}


	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // action with ID action_refresh was selected
	    case R.id.action_refresh:
	      Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
	          .show();
	      break;
	    // action with ID action_settings was selected
	    case R.id.action_settings:
	      Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
	          .show();
	      break;
	    default:
	      break;
	    }

	    return true;
	  } 
	// flag for Internet connection status
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// creating connection detector class instance
		cd = new ConnectionDetector(getApplicationContext());
		//checkIfInternetConnectionAvailable();
		
		// get Internet status
		isInternetPresent = cd.isConnectingToInternet();
		System.out.println("isInternetPresent : "+isInternetPresent);
		// check for Internet status
		if (!isInternetPresent) {
			// Internet connection is not present
			// Ask user to connect to Internet
			try {
				showAlertDialog(MainActivity.this, "No Internet Connection",
						"Please Enable Data/Wifi to use the application.", false);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
// METHOD 1		
		
         /****** Create Thread that will sleep for 2 seconds *************/  		
		Thread background = new Thread() {
			public void run() {
				
				try {					
					
					// Thread will sleep for 2 seconds
					sleep(2*1000);
					
					application = (MyApp)getApplication();					
					
					// After 2 seconds redirect to another intent
				    Intent i=new Intent(getBaseContext(),MyWatchListActivity.class);// Passing intent to watchlist activity

				    startActivity(i);
					
					//Remove activity
					finish();
					
				} catch (Exception e) {
				
				}
			}
			
		};
		
		// start thread
		background.start();	
		
	}	

	/**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - failure (used to set icon)
	 * @throws InterruptedException 
     * */
    @SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) throws InterruptedException {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
        Thread.sleep(2000);
    }

	
	@Override
    protected void onDestroy() {
        super.onDestroy();        
    }
}
