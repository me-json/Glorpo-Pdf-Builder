package models;



import java.io.IOException;



@SuppressWarnings("unused")
public abstract class IndirectObject extends Object{




    private final int objectNumber;
    private final int startingOffset;
    private int size;
    private int objectVersion = 0;



    public IndirectObject(PdfModel pdfModel) {
        super(pdfModel);
        this.objectNumber = pdfModel.provideObjectNumber();
        pdfModel.registerObject(this);
        this.startingOffset = pdfModel.getCurrentSize();
    }



    //Writes entire object, object syntax (5 0 R obj .... endobj)
    void writeToPdf() throws IOException {}


    public void calculateSize() {
        size = pdfModel.getCurrentSize() - startingOffset;
    }



    public int getSize() {
        return size;
    }



    public String getObjectNumber() {
        return String.valueOf(objectNumber);
    }

    public String getObjectVersion() {
        return String.valueOf(objectVersion);
    }

    public void incrementVersion() {
        objectVersion++;
    }

    public void setVersion(int version) {
        objectVersion = version;
    }
}