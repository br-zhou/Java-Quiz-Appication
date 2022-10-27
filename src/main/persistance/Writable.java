package persistance;

import org.json.JSONObject;

// class implementation influenced by https://www.shorturl.at/shortener.php
public interface Writable {
    /*
     * EFFECTS: returns this in JSON format
     */
    public JSONObject toJson();
}
