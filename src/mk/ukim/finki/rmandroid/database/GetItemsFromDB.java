package mk.ukim.finki.rmandroid.database;

import java.util.List;

import mk.ukim.finki.rmandroid.model.Item;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

public class GetItemsFromDB extends AsyncTask<String, Void, List<Item>> {

	protected Context context;
	protected RMDao mDao;
	protected ArrayAdapter<String> adapter;

	public GetItemsFromDB(Context context, ArrayAdapter<String> adapter) {
		this.context = context;
		this.mDao = new RMDao(context);
		this.adapter = adapter;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDao.open();
	}

	@Override
	protected List<Item> doInBackground(String... params) {
		// Simulates a background job.
		return mDao.getAllItems(params[0]);
	}

	@Override
	protected void onPostExecute(List<Item> result) {
		super.onPostExecute(result);
		mDao.close();
		adapter.clear();
		adapter.setNotifyOnChange(true);
		for (Item i : result)
			adapter.add(i.getTitle());
	}

}
