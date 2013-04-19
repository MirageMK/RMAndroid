package mk.ukim.finki.rmandroid.database;

import java.util.List;

import mk.ukim.finki.rmandroid.adapters.GroupAdapter;
import mk.ukim.finki.rmandroid.model.Group;
import android.content.Context;
import android.os.AsyncTask;

public class GetDataFromDB extends AsyncTask<Void, Void, List<Group>> {

	protected Context context;
	protected RMDao mDao;
	protected GroupAdapter adapter;

	public GetDataFromDB(Context context, GroupAdapter adapter) {
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
	protected List<Group> doInBackground(Void... params) {
		// Simulates a background job.
		return mDao.getAllItems();
	}

	@Override
	protected void onPostExecute(List<Group> result) {
		super.onPostExecute(result);
		mDao.close();
		adapter.clear();
		adapter.setData(result);
	}

}
