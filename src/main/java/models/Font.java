package models;

import models.base.DictionaryObject;
import models.base.IndirectObject;

import java.io.IOException;

public class Font extends IndirectObject {

    private final DictionaryObject dictionary;

    public Font(Pdf model) {
        super(model);
        dictionary = new DictionaryObject(model);
    }

    public void writeToPdf() throws IOException {
        String objectLine = getObjectNumber() + " " + getGenerationNumber() + " obj\n";
        pdfModel.writeString(objectLine);
        pdfModel.writeString(dictionary.returnDictionary());
        pdfModel.writeString("\nendobj\n");
        calculateSize();
    }

    public void writeFont(String font, String subType, String baseFont) {
        dictionary.writeDictionaryEntry("/Font", font);
        dictionary.writeDictionaryEntry("/Subtype", subType);
        dictionary.writeDictionaryEntry("/BaseFont", baseFont);
    }

}
