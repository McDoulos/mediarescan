package mcdoulos.android.mediarescan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initiate Scan
		if (InitiateRescanOld(this)) { // Make Toast
			Toast toast = Toast.makeText(this.getApplicationContext(), R.string.info_text, Toast.LENGTH_SHORT);
			toast.show();
		}

		// Go Home
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);

		finish();
	}

	private static boolean InitiateRescanOld(Activity a) {
		if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			Toast toast = Toast.makeText(a, R.string.error_nosd, Toast.LENGTH_LONG);
			toast.show();
			return false;
		}

		a.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, android.net.Uri.parse("file://" + android.os.Environment.getExternalStorageDirectory())));
		return true;
	}
}
