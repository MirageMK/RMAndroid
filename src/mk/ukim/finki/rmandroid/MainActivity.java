package mk.ukim.finki.rmandroid;

import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.utility.DrawableManager;
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

	Group group_data[] = new Group[] {
			new Group(
					0,
					"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Angus-Bacon-Cheese.png",
					"Delicious, freshly made, and oh-so-satisfying. From the Big Mac to our Premium Grilled Chicken Club to our classic Cheeseburger, McDonald’s sandwiches make the meal.",
					"group1", "You so want one.", "Burgers & Sandwiches"),
			new Group(
					1,
					"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Spicy-Chicken-McBites-Regular-Size.png",
					"From our ever-popular Chicken McNuggets to our fabulously fresh salads, chicken from McDonald’s is a delicious choice.",
					"group2", "Juicy, tender and irresistible.", "Chicken"),
			new Group(
					2,
					"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Fruit-Maple-Oatmeal-.png",
					"With so many options, mornings have never been tastier. From wholesome choices like oatmeal and the Egg McMuffin to the savory Sausage Biscuit to the sweet McGriddles sandwich, you'll find exactly what you need to start your morning off just right.",
					"group3", "Wake up to deliciousness", "Breakfast"),
			new Group(
					3,
					"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Premium-Bacon-Ranch-Salad-without-chicken.png",
					"Can a salad be classy? We say yes. Add a little panache to your day with select mixed greens, elegant toppings, and choices galore.",
					"group4", "Yummy, fresh, freedom in a bowl.", "Salads"),
			new Group(
					4,
					"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Small-French-Fries.png",
					"Whether you need a little pick-me-up to get you through the afternoon or a companion for your favorite sandwich, we’ve got you covered.",
					"group5", "Delight your tastebuds, any time.",
					"Snacks & Sides"),
			new Group(
					5,
					"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-1-Low-Fat-Milk-Jug.png",
					"No meal is complete without a drink! From Diet Coke® to low-fat milk to fresh-brewed, hot coffee, we’ve got the perfect companion for your favorite menu items.",
					"group6", "The refreshing meal companion.", "Beverages") };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dm = new DrawableManager();

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
			args.putSerializable("Group", group_data[position]);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return group_data.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return group_data[position].getTitle();
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class SectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		DrawableManager dm;
		Group group;

		public SectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			dm = (DrawableManager) getArguments().getSerializable("DrawableManager");
			group = (Group) getArguments().getSerializable("Group");

			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			ImageView imgGroup = (ImageView) rootView
					.findViewById(R.id.imgGroup);
			ListView listView = (ListView) rootView.findViewById(R.id.lvItems);
			String[] values = new String[] { "Android", "iPhone",
					"WindowsMobile", "Blackberry", "WebOS", "Ubuntu",
					"Windows7", "Max OS X", "Linux", "OS/2" };
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity().getBaseContext(),
					android.R.layout.simple_list_item_1, values);
			listView.setAdapter(adapter);
			dm.fetchDrawableOnThread(group.getBackgroundImage(), imgGroup);
			return rootView;
		}
	}

}
