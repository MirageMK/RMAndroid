package mk.ukim.finki.rmandroid;

import mk.ukim.finki.rmandroid.webservicecomunication.GetDataTask;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		GetDataTask task = new GetDataTask(this);
		task.execute("http://rmservice.apphb.com/Service1.svc/getAllGroups");

		return super.onStartCommand(intent, flags, startId);
	}
}
