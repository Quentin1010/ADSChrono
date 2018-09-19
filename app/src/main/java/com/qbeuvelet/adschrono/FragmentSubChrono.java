package com.qbeuvelet.adschrono;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FragmentSubChrono extends FragmentChrono {
	
	private int               idChildContainer;
	private BroadcastReceiver childBroadcastReceiver;
	
	private String savedChildTextviewContent = null;
	
	public static FragmentSubChrono newInstance()
	{
		Bundle            args     = new Bundle();
		FragmentSubChrono fragment = new FragmentSubChrono();
		args.putInt(EXTRA_BACKGROUND_COLOR, R.color.backgroundOrange);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onDestroyView()
	{
		getActivity().unregisterReceiver(childBroadcastReceiver);
		super.onDestroyView();
	}
	
	@Override
	protected void addChild(View fragmentView, int idContainer, String tag)
	{
		idChildContainer = idContainer;
		FrameLayout childContainer = fragmentView.findViewById(idChildContainer);
		childContainer.setBackgroundResource(R.color.backgroundBlue);
		
		View child = getLayoutInflater().inflate(R.layout.content_chrono, null);
		childContainer.addView(child);
		
		FloatingActionButton fab = child.findViewById(R.id.fabChrono);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent();
				intent.setAction(ChronoBroadcastReceiver.BROADCAST_INTENT);
				getActivity().sendBroadcast(intent);
			}
		});
		
		TextView textView = child.findViewById(R.id.textChrono);
		if (savedChildTextviewContent != null)
		{
			textView.setText(savedChildTextviewContent);
		}
		
		childBroadcastReceiver = new ChronoBroadcastReceiver(textView);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ChronoBroadcastReceiver.BROADCAST_INTENT);
		getActivity().registerReceiver(childBroadcastReceiver, intentFilter);
	}
	
}
