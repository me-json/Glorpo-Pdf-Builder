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


    public void drawImage(String id,
                         double a, double b, double c,
                         double d, double e, double f) throws IOException {
        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, e, f, id);
        stream.writeString(drawingInstruction);

    }
    public void drawImage(
            String id,
            double a, double b, double c,
            double d) throws IOException {

        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, id);
        stream.writeString(drawingInstruction);
    }

    public void drawImage(UUID id,
            double a, double b, double c,
            double d, double e, double f) throws IOException {
            drawImage(id.toString(), a, b, c, d, e, f);
    }
    // Draw images based on 4 value transformation matrix
    public void drawImage(
            UUID id,
            double a, double b, double c,
            double d) throws IOException {
            drawImage(id.toString(), a, b, c, d);
    }

    public void writeText(String text, String name, int x, int y, String fontId, int fontSize) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("Bt");
        builder.append(fontId + " " + String.valueOf(fontSize) + " " + "Tf"); //Font & size
        builder.append(String.valueOf(x) + " " + String.valueOf(y) + " " + "Td");
        builder.append("(" + text + ") "Tj");
        builder.append(Et");
        String drawingInstructions = builder.toString();
        stream.writeString(drawingInstructions);
    }



    public DictionaryObject getDictionary() {
        return dictionary;
    }

    public StreamObject getStreamModel() {
        return stream;
    }

}
