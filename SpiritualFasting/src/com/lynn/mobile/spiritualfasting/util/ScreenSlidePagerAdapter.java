package com.lynn.mobile.spiritualfasting.util;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import com.lynn.mobile.spiritualfasting.fragments.YourFastDetailFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

	private List<SherlockFragment> fragments;

	public ScreenSlidePagerAdapter(FragmentManager fm, List<SherlockFragment> fragments) {
    	super(fm);
    	this.fragments = fragments;
	}
    
	@Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    
    @Override
    public int getItemPosition(Object object) {
    	YourFastDetailFragment fragment = (YourFastDetailFragment)object;
		return fragment.getDay() - 1;
    }
}
