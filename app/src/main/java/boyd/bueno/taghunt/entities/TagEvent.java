package boyd.bueno.taghunt.entities;

import com.orm.SugarRecord;

public class TagEvent extends SugarRecord<TagEvent> {
    String event;

    public TagEvent() {

    }

    public TagEvent(String event) {
        this.event = event;

        this.save();
    }

    @Override
    public String toString() {
        return event;
    }
}
