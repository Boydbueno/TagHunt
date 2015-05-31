package boyd.bueno.taghunt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import boyd.bueno.taghunt.R;
import boyd.bueno.taghunt.entities.TagEvent;

public class EventAdapter extends ArrayAdapter<TagEvent> {

    public EventAdapter(Context context, ArrayList<TagEvent> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TagEvent event = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }

        TextView eventView = (TextView) convertView.findViewById(R.id.event);

        eventView.setText(event.toString());

        return convertView;
    }

}
