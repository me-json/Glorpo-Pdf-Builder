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
    private final StreamModel streamModel;
    ByteArrayOutputStream instructionStream = new ByteArrayOutputStream();
    private ArrayList<String> instructions = new ArrayList<>();
    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    //counter indicates if -1 should be added to stream size
    private int counter = 0;

    public ContentModel(PdfModel model) {
        super(model);
        dictionary = new DictionaryModel(pdfModel);
    }



    @Override
    public void writeToPdf() throws IOException {
        for (String instruction : instructions) {
            instructionStream.write(instruction.getBytes(StandardCharsets.US_ASCII));
        }
        String objectLine = getObjectNumber() + " " + getObjectVersion() + " obj\n";
        pdfModel.writeString(objectLine);
        int streamSize = instructionStream.size();
        if (instructionStream.size() > 1) {
            streamSize--;
        }
        dictionary.writeDictionaryEntry("Length", String.valueOf(streamSize));
        pdfModel.writeString(dictionary.returnDictionary());
        pdfModel.writeByteArray(stream.toByteArray());
        pdfModel.writeString("stream\n");
        pdfModel.writeByteArray(instructionStream.toByteArray());
        pdfModel.writeString("endstream\nendobj\n");
        calculateSize();
    }






    // Draw images based on 6 value transformation matrix
    public void drawImage(
            UUID id,
            double a, double b, double c,
            double d, double e, double f) {
        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, e, f, id.toString());
        instructions.add(drawingInstruction);
        counter++;
    }

    // Draw images based on 4 value transformation matrix
    public void drawImage(
            UUID id,
            double a, double b, double c,
            double d) {

        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, id.toString());
        instructions.add(drawingInstruction);
        counter++;
    }

    public DictionaryModel getDictionary() {
        return dictionary;
    }

}
