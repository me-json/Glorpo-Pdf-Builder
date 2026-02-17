package models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class ContentModel extends IndirectObject {


    //This entire class could be done inside StreamObject class...
    //Maybe it could extend StreamObject



    public ContentModel(PdfModel model) {
        super(model);

    }

    @Override
    public void writeDictionary(ByteArrayOutputStream out) throws IOException {}


    @Override
    public void writeStream(ByteArrayOutputStream out) throws IOException {
        for (String instruction : streamChunks) {
            writeBytes(stream, instruction);
        }
        writeBytes(out, String.valueOf(this.getObjectNumber()));
        writeBytes(out, " ");
        writeBytes(out, String.valueOf(this.getObjectVersion()));
        writeBytes(out, " obj\n<< /Length ");
        writeBytes(out, String.valueOf(stream.size()-1));
        writeBytes(out, " >>\nstream\n");
        for (String instruction : streamChunks) {
            writeBytes(out, instruction);
        }
        writeBytes(out, "endstream\nendobj\n");
    }






    // Draw images based on 6 value transformation matrix
    public String drawImage(
            String id,
            double a, double b, double c,
            double d, double e, double f) {
        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, e, f, id);
        addSize(drawingInstruction.length());
        return drawingInstruction;
    }

    // Draw images based on 4 value transformation matrix
    public String drawImage(
            String id,
            double a, double b, double c,
            double d) {

        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, id);
        addSize(drawingInstruction.length());
        return drawingInstruction;
    }


}
