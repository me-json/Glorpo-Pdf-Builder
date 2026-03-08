package models;

import models.base.DictionaryObject;
import models.base.IndirectObject;

import java.io.IOException;

public class PageIndirectObject extends IndirectObject {

    private final DictionaryObject dictionary;



    public PageIndirectObject(Pdf model, PagesIndirectObject pagesModel) {
        super(model);
        pagesModel.registerPage(this);
        dictionary = new DictionaryObject(model);
        dictionary.writeDictionaryEntry("/Type", "/PageIndirectObject");
    }

    public void setMediaBox(int x1, int y1, int x2, int y2) {
        String value = "[" + x1 + " "  + y1 + " " + x2 + " " + y2 + "]";
        dictionary.writeDictionaryEntry("/MediaBox", value);
    }
    //Here we have a content model that is an indirect object
    public void writeContents(ContentIndirectObject model) {
        String value = model.getObjectNumber() + " " + model.getGenerationNumber() + " " + (model.isInUse() ? "R" : "F");
        dictionary.writeDictionaryEntry("/Contents", value);
    }
    //But here we have an XObject that is a direct object, it can be direct or indirect
    ///XObject << /Im0 22 0 R >> or /XObject 15 0 R
    public void writeXObject(DictionaryObject xObject) {
        String value = xObject.returnDictionary();
        dictionary.writeDictionaryEntry("/XObject", value);
    }

    public void writeToPdf() throws IOException {
        String objectLine = getObjectNumber() + " " + getGenerationNumber() + " obj\n";
        pdfModel.writeString(objectLine);
    }

}
