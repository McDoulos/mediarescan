package mcdoulos.android.mediarescan;

import java.io.File;

import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.util.Log;

public class MediaScannerWrapper implements MediaScannerConnectionClient {
	private static final String TAG = "MediaScannerWrapper";
	private MediaScannerConnection mConnection;
	private String[] pathFiles;
	private boolean recurseDirs = false;

	// paths - the locations to scan;
	private MediaScannerWrapper(android.content.Context ctx, String[] paths) {
		pathFiles = paths;
		mConnection = new MediaScannerConnection(ctx, this);
	}

	public void scan() {
		mConnection.connect();
	}

	@Override
	// start the scan when scanner is ready
	public void onMediaScannerConnected() {
		android.util.Log.i(TAG, "Initiated media scanner");
		for (String path : pathFiles) {
			File file = new File(path);
			scanFile(file);
		}
	}

	private void scanFile(File file) {
		if (file.isFile())
			mConnection.scanFile(file.getAbsolutePath(), null);
		else if (file.isDirectory())
			scanDirectory(file, recurseDirs);
	}

	private void scanDirectory(File dir, boolean recurse) {
		for (File file : dir.listFiles()) {
			if (file.isFile())
				mConnection.scanFile(file.getAbsolutePath(), null);
			else if (file.isDirectory() && recurse)
				scanDirectory(file, recurse);
		}
	}

	@Override
	public void onScanCompleted(String path, android.net.Uri uri) {
		if (uri == null)
			Log.w(TAG, "Failed to scan file: " + path);
		else
			Log.i(TAG, "Scanned media file: " + path);
	}

	public static void ScanFiles(android.content.Context ctx, String[] files) {
		MediaScannerWrapper mw = new MediaScannerWrapper(ctx, files);
		mw.scan();
	}

	public static void ScanFiles(android.content.Context ctx, String path) {
		File dir = new File(path);
		if (!dir.isDirectory())
			Log.e(TAG, "");
	}
}
