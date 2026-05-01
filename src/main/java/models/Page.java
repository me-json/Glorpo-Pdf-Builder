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
        StringBuilder builder = new StringBuilder();
        builder.append("/");
        builder.append(reference);
        builder.append(" ");
        builder.append(image.returnObjectReference());
        builder.append("\n");
        System.out.println(builder.toString());
        writeResource("XObject\n<<\n", builder.toString());
    }

    public void writeResource(String key, String value) {
        if(resources.containsKey("/" + key)) {
            resources.appendValue("/" + key, value);
            System.out.println("entry1");
        } else {
            resources.writeDictionaryEntry("/" + key, value);
        }
    }

    public void writeResource(String key, DictionaryObject resource) {
        if(resources.containsKey("/" + key)) {
            resources.appendValue("/" + key, resource.returnDictionary());
        } else {
            resources.writeDictionaryEntry("/" + key, resource.returnDictionary());
        }
    }

    public void writeToPdf() throws IOException {
        pdfModel.writeString(this.returnObjectHeader());
        dictionary.writeDictionaryEntry("/Resources", resources);
        //dictionary.writeDictionaryEntry("/Contents", contents);
        pdfModel.writeString(dictionary.returnDictionary());
        pdfModel.writeString(">>endobj\n");
        calculateSize();
    }

}
