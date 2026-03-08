package models;

import models.base.DictionaryObject;
import models.base.IndirectObject;
import models.base.StreamObject;

import java.io.IOException;


@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class Image extends IndirectObject {


    //This entire class could be done inside StreamObject class...
    //Maybe it could extend StreamObject
    private final DictionaryObject dictionary;
    private final StreamObject stream;




    public Image(Pdf model) {
        super(model);
        dictionary = new DictionaryObject(pdfModel);
        stream = new StreamObject(pdfModel);
    }


    @Override
    public void writeToPdf() throws IOException {
        String objectLine = getObjectNumber() + " " + getGenerationNumber() + " obj\n";
        pdfModel.writeString(objectLine);
        dictionary.writeDictionaryEntry("Length", String.valueOf(stream.getStreamSize()));
        pdfModel.writeString(dictionary.returnDictionary());
        pdfModel.writeString("stream\n");
        pdfModel.writeByteArray(stream.getStream());
        pdfModel.writeString("endstream\nendobj\n");
        calculateSize();
    }


    public DictionaryObject getDictionary() {
        return dictionary;
    }

    public StreamObject getStreamModel() {
        return stream;
    }

    public void writeImageStream(int length, String filter, byte[] data) throws IOException {
        dictionary.writeDictionaryEntry("/Length", String.valueOf(length));
        dictionary.writeDictionaryEntry("/Filter", "/" + String.valueOf(filter));
        stream.writeByteArray(data);
    }

    public void writeImageAttributes(int width, int height, String colorSpace, int bitsPerComponent) {
        dictionary.writeDictionaryEntry("/Type", "/XObject");
        dictionary.writeDictionaryEntry("/Subtype", "/Image");
        dictionary.writeDictionaryEntry("/Width", "/" + String.valueOf(width));
        dictionary.writeDictionaryEntry("/Height", "/" + String.valueOf(height));
        dictionary.writeDictionaryEntry("", "");
        dictionary.writeDictionaryEntry("", "");
    }


}
