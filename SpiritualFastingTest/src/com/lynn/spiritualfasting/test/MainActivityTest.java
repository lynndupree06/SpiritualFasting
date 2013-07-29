package com.lynn.spiritualfasting.test;

import com.lynn.mobile.spiritualfasting.CreateFastActivity;
import com.lynn.mobile.spiritualfasting.MainActivity;
import com.lynn.mobile.spiritualfasting.fragments.AboutFragment;
import com.lynn.mobile.spiritualfasting.fragments.HomeFragment;
import com.lynn.mobile.spiritualfasting.fragments.TypesofFastsListFragment;
import com.lynn.mobile.spiritualfasting.fragments.YourFastsListFragment;
import com.lynn.spiritualfasting.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity fragmentActivity;
	private FragmentManager fragmentManager;
	private int fragmentContainer;

	public MainActivityTest() {
		super("com.lynn.spiritualfasting", MainActivity.class);
	}  

	protected void setUp() throws Exception {
		super.setUp(); 
		fragmentActivity = getActivity();
		fragmentManager = fragmentActivity.getSupportFragmentManager();
		fragmentContainer = R.id.fragment_container;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFragmentManager() {
	    assertNotNull(fragmentManager);
	}
	
	public void testInitialFragment() {
		assertNotNull(fragmentManager.findFragmentById(R.id.home_container));
	}
	
	public void testFragmentAttachment() {
		HomeFragment homeFragment = new HomeFragment();
		fragmentManager.beginTransaction().add(fragmentContainer, homeFragment).commit();
		assertTrue(homeFragment instanceof HomeFragment);
		
		AboutFragment aboutFragment = new AboutFragment();
		fragmentManager.beginTransaction().add(fragmentContainer, aboutFragment).commit();
		assertTrue(aboutFragment instanceof AboutFragment);
		
		TypesofFastsListFragment typesFragment = new TypesofFastsListFragment();
		fragmentManager.beginTransaction().add(fragmentContainer, typesFragment).commit();
		assertTrue(typesFragment instanceof TypesofFastsListFragment);
		
		YourFastsListFragment yourFragment = new YourFastsListFragment();
		fragmentManager.beginTransaction().add(fragmentContainer, yourFragment).commit();
		assertTrue(yourFragment instanceof YourFastsListFragment);
	}
	
	public void testSlidingMenu() {
	}
	
	public void testHomeFragment() {
		HomeFragment homeFragment = new HomeFragment();
		assertNotNull(homeFragment);
	}
	
	@UiThreadTest
	public void testHomeFragmentLayout() {
		HomeFragment homeFragment = new HomeFragment();
		fragmentManager.beginTransaction().add(fragmentContainer, homeFragment).commit();
		
		Button startButton = (Button) fragmentActivity.findViewById(R.id.start_fast_button);
		assertTrue(startButton.performClick());
		
		Fragment createFastFragment = fragmentManager.findFragmentById(R.id.create_fragment);
		assertTrue(createFastFragment instanceof CreateFastActivity);
	}
	
	public void testAboutFragment() {
		AboutFragment aboutFragment = new AboutFragment();
		assertNotNull(aboutFragment);
	}

	public void testTypesofFastsFragment() {
		TypesofFastsListFragment typesFragment = new TypesofFastsListFragment();
		assertNotNull(typesFragment);
	}

	public void testYourFastsFragment() {
		YourFastsListFragment yourFragment = new YourFastsListFragment();
		assertNotNull(yourFragment);
	}
}
