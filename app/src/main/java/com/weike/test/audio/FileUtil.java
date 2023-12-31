/*
 * Copyright 2018 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weike.test.audio;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUtil {

	private static final String LOG_TAG = "---FileUtil---";

	/** The default buffer size ({@value}) to use for
	 * {@link #copyLarge(InputStream, OutputStream)} */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/** Represents the end-of-file (or stream).*/
	public static final int EOF = -1;


	private FileUtil() {
	}

	public static File getPrivateMusicStorageDir(Context context, String albumName) {
		Log.d(LOG_TAG, "getPrivateMusicStorageDir: ----" + context);
		File file = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
		if (file != null) {
			File f = new File(file, albumName);
			if (!f.exists() && !f.mkdirs()) {
				Log.e(LOG_TAG, "Directory not created");
			} else {
				return f;
			}
		}
		return null;
	}
}
