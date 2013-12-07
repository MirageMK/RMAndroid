package mk.ukim.finki.rmandroid.webservicecomunication;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import mk.ukim.finki.rmandroid.database.RMDao;
import mk.ukim.finki.rmandroid.model.Group;
import mk.ukim.finki.rmandroid.model.Item;
import mk.ukim.finki.rmandroid.utils.RestClient;
import mk.ukim.finki.rmandroid.utils.RestClient.RequestMethod;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetDataTask extends AsyncTask<String, Void, ArrayList<Item>> {

	public static final String ITEMS_DOWNLOADED_ACTION = "mk.ukim.finki.rmandroid.ITEMS_DOWNLOADED_ACTION";
	private Exception exception = null;
	protected Context context;
	ArrayList<Group> groups;

	public GetDataTask(Context context) {
		this.context = context;
		this.groups = new ArrayList<Group>();
	}

	@Override
	protected ArrayList<Item> doInBackground(String... params) {
		RestClient client = new RestClient(params[1]);
		// http://rmservice.apphb.com/Service1.svc/
		// Simulates a background job.
		ArrayList<Item> items = new ArrayList<Item>();
		try {
			client.Execute(RequestMethod.GET);
			String response = client.getResponse();

			JSONObject json = new JSONObject(response);
			JSONArray jsonItems = json.getJSONArray("getAllItemsResult");

			for (int i = 0; i < jsonItems.length(); i++) {
				JSONObject jObj = (JSONObject) jsonItems.get(i);
				Item item = new Item();
				item.setID(jObj.getInt("ID"));
				item.setBackgroundImage(jObj.getString("backgroundImage"));
				item.setContent(jObj.getString("content"));
				item.setDescription(jObj.getString("description"));
				item.setGroupKey(jObj.getJSONObject("group").getString("key"));
				item.setSubtitle(jObj.getString("subtitle"));
				item.setTitle(jObj.getString("title"));
				items.add(item);
			}

			/* GET GROUPS */
			client = new RestClient(params[0]);
			client.Execute(RequestMethod.GET);
			response = client.getResponse();

			json = new JSONObject(response);
			jsonItems = json.getJSONArray("getAllGroupsResult");

			for (int i = 0; i < jsonItems.length(); i++) {
				JSONObject jObj = (JSONObject) jsonItems.get(i);
				Group group = new Group();
				group.setID(jObj.getInt("ID"));
				group.setBackgroundImage(jObj.getString("backgroundImage"));
				group.setDescription(jObj.getString("description"));
				group.setKey(jObj.getString("key"));
				group.setSubtitle(jObj.getString("subtitle"));
				group.setTitle(jObj.getString("title"));
				groups.add(group);
			}

		} catch (Exception e) {
			exception = e;
		}
		return items;
	}

	@Override
	protected void onPostExecute(ArrayList<Item> result) {
		super.onPostExecute(result);
		if (exception != null) {
			Toast.makeText(context, "Error: " + exception.getMessage(),
					Toast.LENGTH_LONG).show();
		} else {

			RMDao dao = new RMDao(context);
			dao.open();

			dao.deleteGroup();
			for (Group group : groups) {
				dao.insertGroup(group);
			}
			
			dao.deleteItem();
			for (Item item : result) {
				dao.insertItem(item);
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