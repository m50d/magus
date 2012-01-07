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
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MagusActivity extends Activity {
	protected static final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 1;
	protected static final int REQUEST_CODE_GET_CONTENT = 2;

	protected EditText editText;
	protected TextView textView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        editText = (EditText) findViewById(R.id.file_path);
        textView = (TextView) findViewById(R.id.info);
    }

	public void onClickOpenFile(final View view) {
		openFile();
	}

	public void onClickGetContent(final View view) {
		getContent();
	}

    /**
     * Opens the file manager to select a file to open.
     */
    public void openFile() {
		final String fileName = editText.getText().toString();

		final Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);

		// Construct URI from file name.
		final File file = new File(fileName);
		intent.setData(Uri.fromFile(file));

		// Set fancy title and button (optional)
		intent.putExtra(FileManagerIntents.EXTRA_TITLE, "Choose file to add to mylist");
		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT, "Add to mylist");

		try {
			startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
		} catch (final ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, "No filemanager installed. Please install OI File Manager from market",
					Toast.LENGTH_SHORT).show();
		}
	}

    /**
     * Use GET_CONTENT to open a file.
     */
    public void getContent() {

		final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(intent, REQUEST_CODE_GET_CONTENT);
		} catch (final ActivityNotFoundException e) {
			// No compatible file manager was found.
			Toast.makeText(this, "No compatible file manager found",
					Toast.LENGTH_SHORT).show();
		}
    }

    /**
     * This is called after the file manager finished.
     */
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		textView.setText("");

		switch (requestCode) {
		case REQUEST_CODE_PICK_FILE_OR_DIRECTORY:
			if (resultCode == RESULT_OK && data != null) {
				// obtain the filename
				final Uri fileUri = data.getData();
				if (fileUri != null) {
					final String filePath = fileUri.getPath();
					if (filePath != null)
						editText.setText(filePath);
				}
			}
			break;
		case REQUEST_CODE_GET_CONTENT:
			if (resultCode == RESULT_OK && data != null) {
				String filePath = null;
				long fileSize = 0;
				String displayName = null;
				final Uri uri = data.getData();
				final Cursor c = getContentResolver().query(uri, new String[] {MediaStore.MediaColumns.DATA,
					MediaStore.MediaColumns.MIME_TYPE,
					MediaStore.MediaColumns.DISPLAY_NAME,
					MediaStore.MediaColumns.SIZE
				}, null, null, null);
				if (c != null && c.moveToFirst()) {
					final int id = c.getColumnIndex(MediaColumns.DATA);
					if (id != -1)
						filePath = c.getString(id);
					displayName = c.getString(2);
					fileSize = c.getLong(3);
				}
				if (filePath != null)
					editText.setText(filePath);
//					textView.setText(strFileSize);
			}
		}
	}
}