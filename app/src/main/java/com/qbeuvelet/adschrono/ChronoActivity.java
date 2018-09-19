package com.qbeuvelet.adschrono;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ChronoActivity extends AppCompatActivity {
	
	private static final String tagFirstFragment  = "firstFragment";
	private static final String tagSecondFragment = "secondFragment";
	
	private ChronoBroadcastReceiver bReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chrono);
		
		NavigationView       navigationView = findViewById(R.id.navigationView);
		FloatingActionButton fab            = navigationView.findViewById(R.id.fabChrono);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent();
				intent.setAction(ChronoBroadcastReceiver.BROADCAST_INTENT);
				sendBroadcast(intent);
			}
		});
		
		TextView textView = navigationView.findViewById(R.id.textChrono);
		
		bReceiver = new ChronoBroadcastReceiver(textView);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ChronoBroadcastReceiver.BROADCAST_INTENT);
		registerReceiver(bReceiver, intentFilter);
		
		if (getSupportFragmentManager().findFragmentByTag(tagFirstFragment) == null)
		{
			getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerFirstHalf, FragmentChrono.newInstance(tagFirstFragment), tagFirstFragment).commit();
			getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerSecondHalf, FragmentChrono.newInstance(tagSecondFragment), tagSecondFragment).commit();
		}
	}
	
	@Override
	protected void onDestroy()
	{
		unregisterReceiver(bReceiver);
		super.onDestroy();
	}
}
