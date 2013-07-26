package com.lynn.spiritualfasting.util;

import com.lynn.spiritualfasting.fragments.YourFastDetailFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

	private int count;
	private Bundle bundle;

	public ScreenSlidePagerAdapter(FragmentManager fm, int count, Bundle bundle) {
    	super(fm);
    	this.count = count;
    	this.bundle = bundle;
	}
    
	@Override
    public Fragment getItem(int position) {
		position = (position == 0) ? 1 : position;
		bundle.putInt(Resources.DAY, position);
        return new YourFastDetailFragment().newInstance(bundle);
    }

    @Override
    public int getCount() {
        return count;
    }

}
