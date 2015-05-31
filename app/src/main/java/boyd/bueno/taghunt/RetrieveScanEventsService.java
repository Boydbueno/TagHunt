package boyd.bueno.taghunt;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
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
        int min = 0;
        int max = 100;

        Random r = new Random();
        int roll = r.nextInt(max - min + 1) + min;

        if (roll >= 30) {
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("A friend found a tag!")
                    .setContentText("Boyd Bueno de Mesquita has found tag with id 12!");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());

            sendLocalBroadcast("Boyd Bueno de Mesquita has found tag with id 12!");
        }

        return Service.START_NOT_STICKY;
    }

    private void sendLocalBroadcast(String event){
        Intent intent = new Intent("newTagEvent");
        intent.putExtra("event", event);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}