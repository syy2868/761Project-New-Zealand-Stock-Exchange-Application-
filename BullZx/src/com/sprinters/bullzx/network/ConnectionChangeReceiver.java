/**
 * 
 */
package com.sprinters.bullzx.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.widget.Toast;

/**
 * @author Chid
 *
 */
public class ConnectionChangeReceiver extends BroadcastReceiver
{
  @Override
  public void onReceive( Context context, Intent intent )
  {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(     ConnectivityManager.TYPE_MOBILE );
    if ( activeNetInfo != null )
    {
      Toast.makeText( context, "Active Network Type : " + activeNetInfo.getTypeName(), Toast.LENGTH_SHORT ).show();
    }else{
    	final Toast tag = Toast.makeText(context, "Network Disconnected : Enable Data/Wifi to Continue",Toast.LENGTH_SHORT);

    	tag.show();

    	new CountDownTimer(9000, 1000)
    	{

    	    public void onTick(long millisUntilFinished) {tag.show();}
    	    public void onFinish() {tag.show();}

    	}.start();
      
    }
    
  }
}