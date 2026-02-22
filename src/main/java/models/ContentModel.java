package models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;


@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class ContentModel extends IndirectObject {


    //This entire class could be done inside StreamObject class...
    //Maybe it could extend StreamObject
    private final DictionaryModel dictionary;
    private final StreamModel stream;




    public ContentModel(PdfModel model) {
        super(model);
        dictionary = new DictionaryModel(pdfModel);
        stream = new StreamModel(pdfModel);
    }



    @Override
    public void writeToPdf() throws IOException {
        String objectLine = getObjectNumber() + " " + getObjectVersion() + " obj\n";
        pdfModel.writeString(objectLine);
        dictionary.writeDictionaryEntry("Length", String.valueOf(stream.getStreamSize()));
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


    public DictionaryModel getDictionary() {
        return dictionary;
    }

    public StreamModel getStreamModel() {
        return stream;
    }

}
