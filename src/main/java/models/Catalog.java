package models;

import models.base.DictionaryObject;
import models.base.IndirectObject;

import java.io.IOException;

public class Catalog extends IndirectObject {


    private final DictionaryObject dictionary;

    public Catalog(Pdf pdfModel) {
        super(pdfModel);
        pdfModel.writeCatalogIndirectObject(this);
        dictionary = new DictionaryObject(pdfModel);
    }

    public void writeToPdf() throws IOException {
        pdfModel.writeString(this.returnObjectHeader());
        pdfModel.writeString(dictionary.returnDictionary());
        pdfModel.writeString("endobj\n");
        calculateSize();
    }

    public void writeCatalogAttributes() {
        dictionary.writeDictionaryEntry("/Type", "/Catalog");
        dictionary.writeDictionaryEntry("/Pages", pdfModel.getPagesIndirectObject().returnObjectReference());
    }

}
