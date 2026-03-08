package models.base;


import models.Pdf;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

//For now this will contain no key /Name validation and no value validation
//It does not contain anything to track the length of the object
//The indirect object should keep track of object length
//It can gather offset by getting size before and after writing an
//entire indirect object
@SuppressWarnings("unused")
public class DictionaryObject extends Object {
    private final LinkedHashMap<String, String> map = new LinkedHashMap<>();
    private final UUID Id = UUID.randomUUID();

    public DictionaryObject(Pdf pdfModel) {
        super(pdfModel);
        pdfModel.registerObject(this);
    }


    public void writeDictionaryEntry(String key, String value) {
        map.put(key, value);
    }

    public String returnDictionary() {
        StringBuilder dictionary = new StringBuilder();
        dictionary.append("<<\n");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            dictionary.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }
        dictionary.append(">>\n");
        return dictionary.toString();
    }




    public void writePair(String key, String value) {
        map.put(key, value);
    }

    public void writeDictionary() throws IOException {
        pdfModel.writeString("<<\n");
        for (String key : map.keySet()) {
            String s = key + " " + map.get(key) + "\n";
            pdfModel.writeString(s);
        }
        pdfModel.writeString(">>\n");
    }


}
