package mk.ukim.finki.rmandroid;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.rmandroid.adapters.GroupAdapter;
import mk.ukim.finki.rmandroid.database.RMDao;
import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.webservicecomunication.GetDataTask;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class StartActivity extends Activity {

	private PullToRefreshListView lvGropus;
	ArrayList<Group> group_data;
	GroupAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		group_data = new ArrayList<Group>();
/*		group_data
				.add(new Group(
						0,
						"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Angus-Bacon-Cheese.png",
						"Delicious, freshly made, and oh-so-satisfying. From the Big Mac to our Premium Grilled Chicken Club to our classic Cheeseburger, McDonald’s sandwiches make the meal.",
						"group1", "You so want one.", "Burgers & Sandwiches"));
		group_data
				.add(new Group(
						1,
						"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Spicy-Chicken-McBites-Regular-Size.png",
						"From our ever-popular Chicken McNuggets to our fabulously fresh salads, chicken from McDonald’s is a delicious choice.",
						"group2", "Juicy, tender and irresistible.", "Chicken"));
		group_data
				.add(new Group(
						2,
						"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Fruit-Maple-Oatmeal-.png",
						"With so many options, mornings have never been tastier. From wholesome choices like oatmeal and the Egg McMuffin to the savory Sausage Biscuit to the sweet McGriddles sandwich, you'll find exactly what you need to start your morning off just right.",
						"group3", "Wake up to deliciousness", "Breakfast"));
		group_data
				.add(new Group(
						3,
						"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Premium-Bacon-Ranch-Salad-without-chicken.png",
						"Can a salad be classy? We say yes. Add a little panache to your day with select mixed greens, elegant toppings, and choices galore.",
						"group4", "Yummy, fresh, freedom in a bowl.", "Salads"));
		group_data
				.add(new Group(
						4,
						"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Small-French-Fries.png",
						"Whether you need a little pick-me-up to get you through the afternoon or a companion for your favorite sandwich, we’ve got you covered.",
						"group5", "Delight your tastebuds, any time.",
						"Snacks & Sides"));
		group_data
				.add(new Group(
						5,
						"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-1-Low-Fat-Milk-Jug.png",
						"No meal is complete without a drink! From Diet Coke® to low-fat milk to fresh-brewed, hot coffee, we’ve got the perfect companion for your favorite menu items.",
						"group6", "The refreshing meal companion.", "Beverages"));*/

		RMDao mDao = new RMDao(this);
		mDao.open();
		List<Group> group_data = mDao.getAllItems();
		//adapter.setData(result);
		mDao.close();
		
		adapter = new GroupAdapter(this, R.layout.listview_item_row_group,
				group_data);

		lvGropus = (PullToRefreshListView) findViewById(R.id.lvGroups);
		lvGropus.setAdapter(adapter);
		lvGropus.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(StartActivity.this, MainActivity.class);
				i.putExtra("position", position - 1);
				startActivity(i);
			}
		});
		lvGropus.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// Do work to refresh the list here.
				IntentFilter filter = new IntentFilter(
						GetDataTask.ITEMS_DOWNLOADED_ACTION);
				registerReceiver(new OnDownloadRefreshReceiver(), filter);
				startService(new Intent(StartActivity.this,
						DownloadService.class));

				// new GetDataTask(StartActivity.this).execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.language:
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setClassName("com.android.settings",
					"com.android.settings.LanguageSettings");
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	class OnDownloadRefreshReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			RMDao mDao = new RMDao(context);
			mDao.open();
			List<Group> result = mDao.getAllItems();
			adapter.clear();
			adapter.setData(result);
			mDao.close();

			// Call onRefreshComplete when the list has been refreshed.
			lvGropus.onRefreshComplete();

			StartActivity.this.unregisterReceiver(this);

		}
	}
}
