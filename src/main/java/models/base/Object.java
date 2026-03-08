package models.base;

import models.Pdf;

import java.util.UUID;

public abstract class Object {
    protected final Pdf pdfModel;
    private final UUID id = UUID.randomUUID();

    public Object(Pdf pdfModel) {
        this.pdfModel = pdfModel;
    }


    public UUID getId() {
        return id;
    }

}
