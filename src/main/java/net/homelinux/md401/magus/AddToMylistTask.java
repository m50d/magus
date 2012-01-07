package net.homelinux.md401.magus;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AddToMylistTask extends AsyncTask<UsernamePasswordFile, Void, String> {
	private final FileHandler handler;
	private final Context context;
	public AddToMylistTask(final FileHandler handler, Context context){
		this.handler = handler;
		this.context = context;
	}

	@Override
	protected String doInBackground(UsernamePasswordFile... params) {
		UsernamePasswordFile param = params[0];
		try {
			handler.addFile(param.username, param.password, true, param.file);
		} catch (FailureException e) {
			return e.detail;
		}
		return  "Added " + param.file.getName() + " to mylist as watched.";
	}
	
	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(context,result, Toast.LENGTH_SHORT).show();
	}

}
