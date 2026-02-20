package models;

import java.util.UUID;

public abstract class Object {
    protected final PdfModel pdfModel;
    private final UUID id = UUID.randomUUID();

    public Object(PdfModel pdfModel) {
        this.pdfModel = pdfModel;
    }


    public UUID getId() {
        return id;
    }

}
