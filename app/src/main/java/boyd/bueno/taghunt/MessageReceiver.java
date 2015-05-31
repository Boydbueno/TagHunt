package boyd.bueno.taghunt;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import boyd.bueno.taghunt.adapters.EventAdapter;
import boyd.bueno.taghunt.entities.TagEvent;

public class MessageReceiver extends BroadcastReceiver {

    private ArrayList<TagEvent> events;
    private EventAdapter eventAdapter;

    public MessageReceiver(ArrayList<TagEvent> events, EventAdapter eventAdapter) {
        this.events = events;
        this.eventAdapter = eventAdapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test", "Message received!");
        String eventString = intent.getStringExtra("event");
        events.add(new TagEvent(eventString));
        eventAdapter.notifyDataSetChanged();
    }
}
