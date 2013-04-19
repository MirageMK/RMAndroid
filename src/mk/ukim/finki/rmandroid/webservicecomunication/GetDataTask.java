package mk.ukim.finki.rmandroid.webservicecomunication;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import mk.ukim.finki.rmandroid.database.RMDao;
import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.utils.RestClient;
import mk.ukim.finki.rmandroid.utils.RestClient.RequestMethod;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetDataTask extends AsyncTask<String, Void, ArrayList<Group>> {

	public static final String ITEMS_DOWNLOADED_ACTION = "mk.ukim.finki.rmandroid.ITEMS_DOWNLOADED_ACTION";
	private Exception exception = null;
	protected Context context;

	public GetDataTask(Context context) {
		this.context = context;
	}

	@Override
	protected ArrayList<Group> doInBackground(String... params) {
		RestClient client = new RestClient(params[0]);
		// http://rmservice.apphb.com/Service1.svc/
		// Simulates a background job.
		ArrayList<Group> temp_data = new ArrayList<Group>();
		try {
			client.Execute(RequestMethod.GET);
			String response = client.getResponse();

			JSONObject json = new JSONObject(response);
			JSONArray jsonItems = json.getJSONArray("getAllGroupsResult");

			for (int i = 0; i < jsonItems.length(); i++) {
				JSONObject jObj = (JSONObject) jsonItems.get(i);
				Group group = new Group();
				group.setID(jObj.getInt("ID"));
				group.setBackgroundImage(jObj.getString("backgroundImage"));
				group.setDescription(jObj.getString("description"));
				group.setKey(jObj.getString("key"));
				group.setSubtitle(jObj.getString("subtitle"));
				group.setTitle(jObj.getString("title"));
				temp_data.add(group);
			}

		} catch (Exception e) {
			exception = e;
		}
		return temp_data;
	}

	@Override
	protected void onPostExecute(ArrayList<Group> result) {
		super.onPostExecute(result);
		if (exception != null) {
			Toast.makeText(context, "Error: " + exception.getMessage(),
					Toast.LENGTH_LONG).show();
		} else {

			RMDao dao = new RMDao(context);
			dao.open();

			dao.delete();
			for (Group group : result) {
				dao.insert(group);
			}
			dao.close();

			SharedPreferences sharedPref = context.getSharedPreferences(
					"RMSharedPref", android.content.Context.MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = sharedPref.edit();
			prefEditor.putLong("lastUpdate", new Date().getTime());
			prefEditor.commit();

			Intent intent = new Intent(ITEMS_DOWNLOADED_ACTION);
			context.sendBroadcast(intent);

		}
	}
}