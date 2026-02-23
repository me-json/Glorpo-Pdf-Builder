package models;

import java.io.IOException;


@SuppressWarnings({"unused", "FieldMayBeFinal"})
public class ImageModel extends IndirectObject {


    //This entire class could be done inside StreamObject class...
    //Maybe it could extend StreamObject
    private final DictionaryModel dictionary;
    private final StreamModel stream;




    public ImageModel(PdfModel model) {
        super(model);
        dictionary = new DictionaryModel(pdfModel);
        stream = new StreamModel(pdfModel);
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


    public DictionaryModel getDictionary() {
        return dictionary;
    }

    public StreamModel getStreamModel() {
        return stream;
    }

}
