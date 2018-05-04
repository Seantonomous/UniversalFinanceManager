package ufm.universalfinancemanager.addeditreminder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by simranjeetkaur on 03/05/18.
 */
public class NotifyReminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "UFM Reminder", Toast.LENGTH_LONG).show();
       // Vibrator v = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
      //  v.vibrate(10000);
    }
}
