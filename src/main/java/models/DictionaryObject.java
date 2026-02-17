package models;

//a direct object

import java.util.HashMap;
import java.util.Map;

public class DictionaryObject {

    //Direct Object only for key, value can be indirect object
    //Hash map could be a LinkedHashMap
    private final Map<String, String> map = new HashMap<>();

    public DictionaryObject() {

    }

    public void writeDictionaryEntry(String key, String value) {
        map.put(key, value);
    }

    public String returnDictionary() {
        StringBuilder dictionary = new StringBuilder();
        dictionary.append("<<");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            dictionary.append(entry.getKey() + " " + entry.getValue());
        }
        dictionary.append(">>");
        return dictionary.toString();
    }


}
