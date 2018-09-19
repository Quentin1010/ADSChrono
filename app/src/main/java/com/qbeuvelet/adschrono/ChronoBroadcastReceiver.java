package com.qbeuvelet.adschrono;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.TextView;

public class ChronoBroadcastReceiver extends BroadcastReceiver {
	
	public static final String BROADCAST_INTENT = "com.broadcast.chrono.update";
	private TextView textViewToUpdate;
	
	public ChronoBroadcastReceiver(TextView textView)
	{
		textViewToUpdate = textView;
	}
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		textViewToUpdate.append(String.valueOf(SystemClock.uptimeMillis()));
		textViewToUpdate.append("\n");
	}
}
