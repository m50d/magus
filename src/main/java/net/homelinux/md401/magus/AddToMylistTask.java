package net.homelinux.md401.magus;

import android.os.AsyncTask;

public class AddToMylistTask extends AsyncTask<UsernamePasswordFile, Void, Void> {
	private final FileHandler handler;
	public AddToMylistTask(final FileHandler handler){
		this.handler = handler;
	}

	@Override
	protected Void doInBackground(UsernamePasswordFile... params) {
		UsernamePasswordFile param = params[0];
		handler.addFile(param.username, param.password, true, param.file);
		return null;
	}

}
