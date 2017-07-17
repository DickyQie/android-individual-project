package com.zhangqie.miyucalculator.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangqie.miyucalculator.R;


public class PreferenceItem extends LinearLayout {
	
	private LayoutInflater mLayoutInflater;
	private TextView mTitle;
	private TextView mWhole;
	private ImageView mInto;
	public PreferenceItem(Context context) {
		this(context, null);
	}
	public PreferenceItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init() {
		setOrientation(HORIZONTAL);
		mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLayoutInflater.inflate(R.layout.widget_preference_item, this, true);
		mTitle = (TextView) findViewById(R.id.my_item_title);
		mWhole = (TextView) findViewById(R.id.my_item_whole);
		mInto=(ImageView) findViewById(R.id.my_item_into);
	}
	public void setTitle(String title) {
		mTitle.setText(title);
	}
	public void setTitle(int title) {
		mTitle.setText(title);
	}
	public void setContent(String content) {
		mWhole.setText(content);
	}
	public void setContent(int content) {
		mWhole.setText(content);
	}
	public void setVisibilityInto(int view)
	{
		mInto.setVisibility(view);
	}
	public void setVisibilityContent(int view)
	{
		mWhole.setVisibility(view);
	}
	
}
