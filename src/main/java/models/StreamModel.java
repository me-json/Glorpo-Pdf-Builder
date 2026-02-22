package models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

public class StreamModel extends Object{


    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private int streamSize;
    //counter indicates if -1 should be added to stream size
    private int counter = 0;

    public StreamModel(PdfModel pdfModel) {
        super(pdfModel);
    }


    public int getStreamSize() {
        streamSize = stream.size();
        if (counter > 1) {
            streamSize--;
        }
        return streamSize;
    }

    public ByteArrayOutputStream getStream() {
        return stream;
    }

    public void drawImage(
            UUID id,
            double a, double b, double c,
            double d, double e, double f) throws IOException {
        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, e, f, id.toString());
        stream.write(drawingInstruction.getBytes(StandardCharsets.US_ASCII));
        counter++;
    }
    // Draw images based on 4 value transformation matrix
    public void drawImage(
            UUID id,
            double a, double b, double c,
            double d) throws IOException {

        String drawingInstruction = String.format(
                "q\n%.0f %.0f %.0f %.0f cm\n/%s Do\nQ\n",
                a, b, c, d, id.toString());
        stream.write(drawingInstruction.getBytes(StandardCharsets.US_ASCII));
        counter++;
    }


}
