package mk.ukim.finki.rmandroid;

import mk.ukim.finki.rmandroid.model.Group;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
	Group group_data[];
	GroupAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		// Ova ke se polni preku web servis. Ova se samo dummy podatoci
		group_data = new Group[] {
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

		adapter = new GroupAdapter(this, R.layout.listview_item_row_group,
				group_data);

		lvGropus = (PullToRefreshListView) findViewById(R.id.lvGroups);
		lvGropus.setAdapter(adapter);
		lvGropus.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(StartActivity.this, MainActivity.class);
				i.putExtra("position", position-1);
				startActivity(i);
			}
		});
		lvGropus.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// Do work to refresh the list here.
				new GetDataTask().execute();
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

	private class GetDataTask extends AsyncTask<Void, Void, Group[]> {

		@Override
		protected Group[] doInBackground(Void... params) {
			// Simulates a background job.
			Group[] temp_data = new Group[] {
					new Group(
							0,
							"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Angus-Bacon-Cheese.png",
							"Delicious, freshly made, and oh-so-satisfying. From the Big Mac to our Premium Grilled Chicken Club to our classic Cheeseburger, McDonald’s sandwiches make the meal.",
							"group1", "You so want one.",
							"Burgers & Sandwiches"),
					new Group(
							1,
							"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Spicy-Chicken-McBites-Regular-Size.png",
							"From our ever-popular Chicken McNuggets to our fabulously fresh salads, chicken from McDonald’s is a delicious choice.",
							"group2", "Juicy, tender and irresistible.",
							"Chicken"),
					new Group(
							2,
							"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Fruit-Maple-Oatmeal-.png",
							"With so many options, mornings have never been tastier. From wholesome choices like oatmeal and the Egg McMuffin to the savory Sausage Biscuit to the sweet McGriddles sandwich, you'll find exactly what you need to start your morning off just right.",
							"group3", "Wake up to deliciousness", "Breakfast"),
					new Group(
							3,
							"http://www.mcdonalds.com/content/dam/McDonalds/item/mcdonalds-Premium-Bacon-Ranch-Salad-without-chicken.png",
							"Can a salad be classy? We say yes. Add a little panache to your day with select mixed greens, elegant toppings, and choices galore.",
							"group4", "Yummy, fresh, freedom in a bowl.",
							"Salads"),
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
							"group6", "The refreshing meal companion.",
							"Beverages") };
			try {
				Thread.sleep(4000);

			} catch (InterruptedException e) {
			}
			return temp_data;
		}

		@Override
		protected void onPostExecute(Group[] result) {
			adapter.setData(result);

			// Call onRefreshComplete when the list has been refreshed.
			lvGropus.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

}
