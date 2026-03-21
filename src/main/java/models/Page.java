package models;

import models.base.DictionaryObject;
import models.base.IndirectObject;

import java.io.IOException;

public class Page extends IndirectObject {

    private final DictionaryObject dictionary;

    private final DictionaryObject resources;
    private final DictionaryObject contents;


    public Page(Pdf model) {
        super(model);
        model.getPagesIndirectObject().registerPage(this);
        dictionary = new DictionaryObject(model);
        dictionary.writeDictionaryEntry("/Type", "/Page");
        dictionary.writeDictionaryEntry("/Parent", model.getPagesIndirectObject().returnObjectReference());

        resources = new DictionaryObject(model);
        contents = new DictionaryObject(model);

    }

    public void setMediaBox(int x1, int y1, int x2, int y2) {
        String value = "[" + x1 + " "  + y1 + " " + x2 + " " + y2 + "]";
        dictionary.writeDictionaryEntry("/MediaBox", value);
    }
    //Here we have a content model that is an indirect object
    public void writeContents(Content model) {
        String value = model.getObjectNumber() + " " + model.getGenerationNumber() + " " + (model.isInUse() ? "R" : "F");
        dictionary.writeDictionaryEntry("/Contents", value);
    }
    //But here we have an XObject that is a direct object, it can be direct or indirect
    ///XObject << /Im0 22 0 R >> or /XObject 15 0 R
    public void writeImage(Image image, String reference) {

        DictionaryObject dictionary = new DictionaryObject(pdfModel);
        dictionary.writeDictionaryEntry("/" + reference, image.returnObjectReference());
        writeResource("XObject", dictionary);
    }

    public void writeResource(String key, String value) {
        resources.writeDictionaryEntry(key, value);
    }

    public void writeResource(String key, DictionaryObject resource) {
        resources.writeDictionaryEntry("/" + key , resource.returnDictionary());
    }

    public void writeToPdf() throws IOException {
        pdfModel.writeString(this.returnObjectHeader());
        dictionary.writeDictionaryEntry("/Resources", resources);
        System.out.println(resources.returnDictionary());
        //dictionary.writeDictionaryEntry("/Contents", contents);
        pdfModel.writeString(dictionary.returnDictionary());
        pdfModel.writeString("endobj\n");
        calculateSize();
    }

}
