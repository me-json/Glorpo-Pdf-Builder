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

    public void writeString(String string) throws IOException {
        stream.write(string.getBytes(StandardCharsets.UTF_8));
        counter++;
    }




}
