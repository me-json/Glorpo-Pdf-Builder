package models;


//an indirect object that consists of a dictionary followed by a stream

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class StreamObject extends IndirectObject {

    private final PdfModel pdfModel;
    private final DictionaryObject dictionary;
    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    public StreamObject(PdfModel model) {
        super(model);
        this.pdfModel = model;
        dictionary = new DictionaryObject();
    }

    @Override
    public void writeStream() throws IOException {
        ByteArrayOutputStream pdf = pdfModel.getOutputStream();
        writeBytes(pdf, String.valueOf(getObjectNumber()));
        writeBytes(pdf, " ");
        writeBytes(pdf, String.valueOf(getObjectVersion()));
        dictionary.returnDictionary();
    }

}
