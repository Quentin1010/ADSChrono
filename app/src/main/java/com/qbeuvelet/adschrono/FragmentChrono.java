package com.qbeuvelet.adschrono;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentChrono extends Fragment {
	
	protected static final String EXTRA_BACKGROUND_COLOR = "extraBackgroundColor";
	protected static final String EXTRA_TAG              = "extraTag";
	
	private ChronoBroadcastReceiver bReceiver;
	private TextView                textView;
	public boolean test = false;
	
	public static FragmentChrono newInstance(String tag)
	{
		FragmentChrono fragment = new FragmentChrono();
		
		Bundle args = new Bundle();
		args.putInt(EXTRA_BACKGROUND_COLOR, R.color.backgroundPurple);
		args.putString(EXTRA_TAG, tag);
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View fragmentView = inflater.inflate(R.layout.content_chrono, container, false);
		fragmentView.setBackgroundResource(getArguments().getInt(EXTRA_BACKGROUND_COLOR));
		
		FloatingActionButton fab = fragmentView.findViewById(R.id.fabChrono);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent();
				intent.setAction(ChronoBroadcastReceiver.BROADCAST_INTENT);
				getActivity().sendBroadcast(intent);
			}
		});
		
		textView = fragmentView.findViewById(R.id.textChrono);
		
		bReceiver = new ChronoBroadcastReceiver(textView);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ChronoBroadcastReceiver.BROADCAST_INTENT);
		getActivity().registerReceiver(bReceiver, intentFilter);
		
		int idFragmentContainer = createChildContainer(fragmentView);
		addChild(fragmentView, idFragmentContainer, getArguments().getString(EXTRA_TAG) + "sub");
		
		return fragmentView;
	}
	
	protected void addChild(View fragmentView, int idContainer, String tag)
	{
		getActivity().getSupportFragmentManager().beginTransaction().add(idContainer, FragmentSubChrono.newInstance(), tag).commit();
	}
	
	protected int createChildContainer(View fragmentView)
	{
		FrameLayout               fragmentContainer = new FrameLayout(getContext());
		LinearLayout.LayoutParams layoutParams      = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
		
		int id = View.generateViewId();
		fragmentContainer.setId(id);
		
		LinearLayout layoutParent = fragmentView.findViewById(R.id.layoutContentParent);
		layoutParent.addView(fragmentContainer, layoutParams);
		
		return id;
	}
	
	@Override
	public void onDestroyView()
	{
		getActivity().unregisterReceiver(bReceiver);
		super.onDestroyView();
	}
}
