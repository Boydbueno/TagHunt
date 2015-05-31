package boyd.bueno.taghunt;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import boyd.bueno.taghunt.entities.TagEvent;

public class RetrieveScanEventsService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Todo: Do a volley GET request to check if there are any new tag scans


        // Will fake this with a 30% chance of a new event
        Random r = new Random();
        int roll = r.nextInt(100);

        if (roll >= 0) {
            Intent contentTntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, contentTntent, 0);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            boolean actionBarNotification = prefs.getBoolean("checkbox_notifications_action_bar_preference", true);
            boolean vibrationNotification = prefs.getBoolean("checkbox_notifications_vibrate_preference", true);

            if (actionBarNotification) {
                Notification.Builder builder = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("A friend found a tag!")
                        .setContentText("Boyd Bueno de Mesquita has found tag with id 12!")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());
            }

            if (vibrationNotification) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                long[] pattern = {0, 300, 300, 300};

                v.vibrate(pattern, -1);
            }



            int randomId = r.nextInt(20);

            sendLocalBroadcast("Boyd Bueno de Mesquita has found tag with id " + randomId + "!");
        }

        return Service.START_NOT_STICKY;
    }

    private void sendLocalBroadcast(String event){
        Intent intent = new Intent("newTagEvent");
        intent.putExtra("event", event);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
