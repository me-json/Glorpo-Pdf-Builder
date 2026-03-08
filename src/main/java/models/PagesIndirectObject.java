package models;

import models.base.DictionaryObject;
import models.base.IndirectObject;

import java.io.IOException;
import java.util.ArrayList;

public class PagesIndirectObject extends IndirectObject {
    private final ArrayList<PageIndirectObject> pages = new ArrayList<>();
    private final DictionaryObject dictionary;


    public PagesIndirectObject(Pdf model) {
        super(model);
        pdfModel.writePagesIndirectObject(this);
        dictionary = new DictionaryObject(model);
        dictionary.writeDictionaryEntry("/Type", "/PagesIndirectObject");
    }

    public void registerPage(PageIndirectObject page) {
        pages.add(page);
    }

    public void writeToPdf() throws IOException {
        String objectLine = getObjectNumber() + " " + getGenerationNumber() + " obj\n";
        pdfModel.writeString(objectLine);
    }

}
