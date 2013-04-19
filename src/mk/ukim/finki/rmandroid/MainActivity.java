package mk.ukim.finki.rmandroid;

import java.util.List;

import mk.ukim.finki.rmandroid.database.RMDao;
import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.utils.DrawableManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	public DrawableManager dm;
	private List<Group> group_data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dm = new DrawableManager();
		
		RMDao mDao = new RMDao(this);
		mDao.open();
		group_data = mDao.getAllItems();
		mDao.close();

		// Show the Up button in the action bar.
		// getActionBar().setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), dm);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		Intent i = getIntent();
		mViewPager.setCurrentItem(i.getIntExtra("position", 0));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		DrawableManager dm;

		public SectionsPagerAdapter(FragmentManager fm, DrawableManager dm) {
			super(fm);
			this.dm = dm;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new SectionFragment();
			Bundle args = new Bundle();
			args.putSerializable("DrawableManager", dm);
			args.putSerializable("Group", group_data.get(position));
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return group_data.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return group_data.get(position).getTitle();
		}
	}

	public static class SectionFragment extends Fragment {
		DrawableManager dm;
		Group group;
		String[] values;
		ArrayAdapter<String> adapter;
		ListView listView;

		public SectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			values = new String[] { "Android", "iPhone", "WindowsMobile",
					"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
					"Linux", "OS/2" };
			adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
					android.R.layout.simple_list_item_1, values);
			dm = (DrawableManager) getArguments().getSerializable(
					"DrawableManager");
			group = (Group) getArguments().getSerializable("Group");

			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			ImageView imgGroup = (ImageView) rootView
					.findViewById(R.id.imgGroup);
			listView = (ListView) rootView.findViewById(R.id.lvItems);

			listView.setAdapter(adapter);
			dm.fetchDrawableOnThread(group.getBackgroundImage(), imgGroup);
			return rootView;
		}

	}

}
