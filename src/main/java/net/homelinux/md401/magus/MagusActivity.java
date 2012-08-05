/*
 * Copyright (C) 2008 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.homelinux.md401.magus;

import java.io.File;

import org.openintents.intents.FileManagerIntents;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MagusActivity extends Activity {
	protected static final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 1;
	private TextView username;
	private TextView password;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        username.setText(preferences.getString("username", ""));
        password.setText(preferences.getString("password", ""));
    }

	public void onClickOpenFile(final View view) {
		openFile();
	}

    public void openFile() {
		final Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);
		intent.setData(Uri.fromFile(new File("")));
		intent.putExtra(FileManagerIntents.EXTRA_TITLE, "Choose file to add to mylist");
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT, "Add to mylist");
		try {
			startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
		} catch (final ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, "No filemanager installed. Please install OI File Manager from market",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_PICK_FILE_OR_DIRECTORY) {
			if (resultCode == RESULT_OK && data != null) {
				// obtain the filename
				final Uri fileUri = data.getData();
				if (fileUri != null) {
					final String filePath = fileUri.getPath();
					File file = new File(filePath).getAbsoluteFile();
					UsernamePasswordFile usernamePasswordFile = new UsernamePasswordFile(username.getText().toString(), password.getText().toString(), file);
					Intent intent = new Intent(this, AddToMylistService.class);
					intent.putExtra(UsernamePasswordFile.USERNAME_PASSWORD_FILE, usernamePasswordFile);
					startService(intent);
				}
			}
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.commit();
	}
}