package mk.ukim.finki.rmandroid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mk.ukim.finki.rmandroid.adapters.GroupAdapter;
import mk.ukim.finki.rmandroid.database.GetGroupsFromDB;
import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.webservicecomunication.GetDataTask;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class StartActivity extends Activity {

	private static final long ONE_DAY = 24 * 60 * 60 * 1000;
	private PullToRefreshListView lvGropus;
	private List<Group> group_data;
	private GroupAdapter adapter;
	private GetGroupsFromDB task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		try {
		    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

		    startActivityForResult(intent, 0);

		} catch (Exception e) {

		    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
		    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
		    startActivity(marketIntent);

		}
		
		group_data = new ArrayList<Group>();

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
				getDataFromService();
			}
		});
	}

	void getDataFromService() {
		IntentFilter filter = new IntentFilter(
				GetDataTask.ITEMS_DOWNLOADED_ACTION);
		registerReceiver(new OnDownloadRefreshReceiver(), filter);
		startService(new Intent(StartActivity.this, DownloadService.class));
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
			break;
		case R.id.callWaiter:
			Intent i = new Intent(StartActivity.this, CallWaiterActivity.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	class OnDownloadRefreshReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			task = new GetGroupsFromDB(StartActivity.this, adapter);
			task.execute();

			// Call onRefreshComplete when the list has been refreshed.
			lvGropus.onRefreshComplete();

			StartActivity.this.unregisterReceiver(this);

		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {           
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	        	String serviceURL = data.getStringExtra("SCAN_RESULT");
	    		SharedPreferences sharedPref = getSharedPreferences("RMSharedPref",
	    				MODE_PRIVATE);
	        	SharedPreferences.Editor prefEditor = sharedPref.edit();
	    		long lastUpdateTime = sharedPref.getLong("lastUpdate", 0);
	    		String lastServiceURL = sharedPref.getString("serviceURL", "");
	    		long currentTime = new Date().getTime();
	    		
				prefEditor.putString("serviceURL", serviceURL);
				prefEditor.commit();
	            Toast.makeText(StartActivity.this,data.getStringExtra("SCAN_RESULT"), Toast.LENGTH_LONG).show();
	    		if (!serviceURL.equals(lastServiceURL) || currentTime - lastUpdateTime >= ONE_DAY) {
	    			Toast.makeText(this, "Downloading from Server...",
	    					Toast.LENGTH_LONG).show();
	    			getDataFromService();
	    		} else {
	    			Toast.makeText(this, "Downloading from Database...",
	    					Toast.LENGTH_LONG).show();
	    			task = new GetGroupsFromDB(this, adapter);
	    			task.execute();
	    		}
	        }
	        if(resultCode == RESULT_CANCELED){
	        	Toast.makeText(this, "Downloading from Database...",
    					Toast.LENGTH_LONG).show();
    			task = new GetGroupsFromDB(this, adapter);
    			task.execute();
	        }
	    }
	}
}
