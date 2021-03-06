/*
 * Paintroid: An image manipulation application for Android.
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.paintroid.iotasks;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.catrobat.paintroid.FileIO;

import java.io.IOException;
import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;

public class SaveImageAsync extends AsyncTask<Void, Void, Uri> {
	private static final String TAG = SaveImageAsync.class.getSimpleName();
	private WeakReference<SaveImageCallback> callbackRef;
	private int requestCode;
	private Uri uri;
	private boolean saveAsCopy;
	private Bitmap bitmap;

	public SaveImageAsync(SaveImageCallback activity, int requestCode, Bitmap bitmap, @Nullable Uri uri, boolean saveAsCopy) {
		this.callbackRef = new WeakReference<>(activity);
		this.requestCode = requestCode;
		this.uri = uri;
		this.saveAsCopy = saveAsCopy;
		this.bitmap = bitmap;
	}

	@Override
	protected void onPreExecute() {
		SaveImageCallback callback = callbackRef.get();
		if (callback == null || callback.isFinishing()) {
			cancel(false);
		} else {
			callback.onSaveImagePreExecute(requestCode);
		}
	}

	@Override
	protected Uri doInBackground(Void... voids) {
		SaveImageCallback callback = callbackRef.get();
		if (callback != null && !callback.isFinishing()) {
			try {
				if (uri != null) {
					return FileIO.saveBitmapToUri(uri, callback.getContentResolver(), bitmap);
				} else {
					String fileName = FileIO.getDefaultFileName();
					return FileIO.saveBitmapToFile(fileName, bitmap, callback.getContentResolver());
				}
			} catch (IOException e) {
				Log.d(TAG, "Can't save image file", e);
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Uri uri) {
		SaveImageCallback callback = callbackRef.get();
		if (callback != null && !callback.isFinishing()) {
			callback.onSaveImagePostExecute(requestCode, uri, saveAsCopy);
		}
	}

	public interface SaveImageCallback {
		void onSaveImagePreExecute(int requestCode);
		void onSaveImagePostExecute(int requestCode, Uri uri, boolean saveAsCopy);
		ContentResolver getContentResolver();
		boolean isFinishing();
	}
}
