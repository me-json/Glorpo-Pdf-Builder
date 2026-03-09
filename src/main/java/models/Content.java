package models;

import models.base.DictionaryObject;
import models.base.IndirectObject;
import models.base.StreamObject;

import java.io.IOException;
import java.util.UUID;


@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class Content extends IndirectObject {


    //This entire class could be done inside StreamObject class...
    //Maybe it could extend StreamObject
    private final DictionaryObject dictionary;
    private final StreamObject stream;




    public Content(Pdf model) {
        super(model);
        dictionary = new DictionaryObject(pdfModel);
        stream = new StreamObject(pdfModel);
    }



    @Override
    public void writeToPdf() throws IOException {
        String objectLine = getObjectNumber() + " " + getGenerationNumber() + " obj\n";
        pdfModel.writeString(objectLine);
        dictionary.writeDictionaryEntry("/Length", String.valueOf(stream.getStreamSize()-1));
        pdfModel.writeString(dictionary.returnDictionary());
        pdfModel.writeString("stream\n");
        pdfModel.writeByteArray(stream.getStream());
        pdfModel.writeString("endstream\nendobj\n");
        calculateSize();
    }

    public void drawImage(
            UUID id,
            double a, double b, double c,
            double d, double e, double f) throws IOException {
        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, e, f, id.toString());
        stream.writeString(drawingInstruction);
    }
    // Draw images based on 4 value transformation matrix
    public void drawImage(
            UUID id,
            double a, double b, double c,
            double d) throws IOException {

        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, id.toString());
        stream.writeString(drawingInstruction);
    }


    public DictionaryObject getDictionary() {
        return dictionary;
    }

    public StreamObject getStreamModel() {
        return stream;
    }

}
