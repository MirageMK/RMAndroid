package mk.ukim.finki.rmandroid;

import mk.ukim.finki.rmandroid.webservicecomunication.GetDataTask;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class DownloadService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences sharedPref = getSharedPreferences("RMSharedPref",
				MODE_PRIVATE);
		
		String lastServiceURL = sharedPref.getString("serviceURL", "");
		
		GetDataTask task = new GetDataTask(this);
		task.execute(lastServiceURL+"/getAllGroups",
				lastServiceURL+"/getAllItems");

		return super.onStartCommand(intent, flags, startId);
	}
}
