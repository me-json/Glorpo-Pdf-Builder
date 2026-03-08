package models;

import models.base.DictionaryObject;
import models.base.IndirectObject;

import java.io.IOException;
import java.util.ArrayList;

public class Pages extends IndirectObject {
    private final ArrayList<Page> pages = new ArrayList<>();
    private final DictionaryObject dictionary;


    public Pages(Pdf model) {
        super(model);
        pdfModel.writePagesIndirectObject(this);
        dictionary = new DictionaryObject(model);
        dictionary.writeDictionaryEntry("/Type", "/Pages");
    }

    public void registerPage(Page page) {
        pages.add(page);
    }

    public void writeToPdf() throws IOException {
        String objectLine = getObjectNumber() + " " + getGenerationNumber() + " obj\n";
        pdfModel.writeString(objectLine);
        writeKids();
        dictionary.writeDictionary();
        pdfModel.writeString("endobj\n");
    }

    public void writeKids() {
        //needs to print array
        //needs to print count (should be exact length of array)
        dictionary.writeDictionaryEntry("/Count", String.valueOf(pages.size()));
        StringBuilder something = new StringBuilder();
        for (Page page : pages) {
            something.append(page.returnObjectReference());
        }
        String result = something.toString();
        dictionary.writeDictionaryEntry("/Kids", result);
    }

}
