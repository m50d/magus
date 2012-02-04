package net.homelinux.md401.magus;

import android.app.IntentService;
import android.content.Intent;

public class AddToMylistService extends IntentService {
	private final FileHandler handler;
	public AddToMylistService(final FileHandler handler){
		super("AddFilesToMylist");
		this.handler = handler;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		UsernamePasswordFile param = (UsernamePasswordFile) intent.getSerializableExtra("UsernamePasswordFile");
		String s;
		try {
			handler.addFile(param.username, param.password, true, param.file);
			s = "Added " + param.file.getName() + " to mylist as watched.";
		} catch (FailureException e) {
			s = e.detail;
		}
	}

}
