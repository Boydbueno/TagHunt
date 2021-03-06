package boyd.bueno.taghunt;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import boyd.bueno.taghunt.adapters.EventAdapter;
import boyd.bueno.taghunt.entities.TagEvent;

/**
 * Background task for reading the data.
 */
class NfcReaderTask extends AsyncTask<Tag, Void, String> {

    private ArrayList<TagEvent> events;
    private EventAdapter eventAdapter;

    public NfcReaderTask(ArrayList<TagEvent> events, EventAdapter eventAdapter) {
        this.events = events;
        this.eventAdapter = eventAdapter;
    }

    @Override
    protected String doInBackground(Tag... params) {
        Tag tag = params[0];

        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            return null;
        }

        NdefMessage ndefMessage = ndef.getCachedNdefMessage();

        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records) {
            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                try {
                    return readText(ndefRecord);
                } catch (UnsupportedEncodingException e) {
                    Log.e("test", "Unsupported Encoding", e);
                }
            }
        }

        return null;
    }

    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        // Get the Text
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    @Override
    protected void onPostExecute(String result) {
        int id = 0;
        try {
            id = new JSONObject(result).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result != null) {
            // Todo: Actually not the responsibility of this class
            events.add(new TagEvent("You scanned tag with id " + id + "!"));
            eventAdapter.notifyDataSetChanged();
        }
    }
}