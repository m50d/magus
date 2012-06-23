package net.homelinux.md401.magus;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;

public class AddToMylistService extends IntentService {
	private final FileHandler handler = new FileHandler();
	private static final int SCIENTIST = 7;
	public AddToMylistService(){
		super("AddFilesToMylist");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		UsernamePasswordFile param = (UsernamePasswordFile) intent.getSerializableExtra("UsernamePasswordFile");
//		NotificationManager notificationService = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification.Builder(this).setContentTitle("Hashing File").getNotification();
		startForeground(SCIENTIST, notification);
		String s;
		try {
			handler.addFile(param.username, param.password, true, param.file);
			s = "Added " + param.file.getName() + " to mylist as watched.";
		} catch (FailureException e) {
			s = e.detail;
		}
	}

}
