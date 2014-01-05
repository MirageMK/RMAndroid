package mk.ukim.finki.rmandroid;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.rmandroid.database.RMDao;
import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.model.Item;
import mk.ukim.finki.rmandroid.utils.DrawableManager;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	//private static GetItemsFromDB task;
	//private static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//context = this;

		dm = new DrawableManager();

		RMDao mDao = new RMDao(this);
		mDao.open();
		group_data = mDao.getAllGroups();
		mDao.close();

		// Show the Up button in the action bar.
		setupActionBar();

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
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
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
		case R.id.callWaiter:
			Intent i = new Intent(MainActivity.this, CallWaiterActivity.class);
			startActivity(i);
			break;
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
		private transient DrawableManager dm;
		private Group group;
		//private String[] values;
		private ArrayAdapter<String> adapter;
		private List<Item> item_data;
		private ListView listView;
		private ImageView imgGroup;
		//private TextView groupSubtitle;

		public SectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			ArrayList<String> values = new ArrayList<String>();
			// values = new String[]{};

			adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
					android.R.layout.simple_list_item_1, values);

			dm = (DrawableManager) getArguments().getSerializable(
					"DrawableManager");
			group = (Group) getArguments().getSerializable("Group");

			/*task = new GetItemsFromDB(context, adapter);
			task.execute(group.getKey());*/
			RMDao mDao = new RMDao(getActivity());
			mDao.open();
			item_data = mDao.getAllItems(group.getKey());
			mDao.close();

			for (Item i : item_data)
				adapter.add(i.getTitle());
			
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			listView = (ListView) rootView.findViewById(R.id.lvItems);
			imgGroup = (ImageView) rootView.findViewById(R.id.imgGroup);
			// groupSubtitle = (TextView)
			// rootView.findViewById(R.id.groupSubtitle);

			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent i = new Intent(getActivity(), ItemActivity.class);
					i.putExtra("Item", item_data.get(position));
					startActivity(i);
					
				}
			});
			dm.fetchDrawableOnThread(group.getBackgroundImage(), imgGroup);
			// groupSubtitle.setText(group.getSubtitle());
			return rootView;
		}
	}

}
