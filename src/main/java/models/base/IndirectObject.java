package models.base;



import models.Pdf;

import java.io.IOException;



@SuppressWarnings("unused")
public abstract class IndirectObject extends Object{




    private final int objectNumber;
    private final int startingOffset;
    private int size;
    private int generationNumber = 0;
    private boolean inUse = false;



    public IndirectObject(Pdf pdfModel) {
        super(pdfModel);
        this.objectNumber = pdfModel.provideObjectNumber();
        pdfModel.registerObject(this);
        this.startingOffset = pdfModel.getCurrentSize();
        markInUse();
    }



    //Writes entire object, object syntax (5 0 R obj .... endobj)
    public void writeToPdf() throws IOException {}


    public String returnObjectHeader() {
        return getObjectNumber() + " " + getGenerationNumber() + " obj\n";
    }

    public String returnObjectReference() {
        return getObjectNumber() + " " + getGenerationNumber() + " " + (isInUse() ? "R" : "F");
    }

    public void calculateSize() {
        size = pdfModel.getCurrentSize() - startingOffset;
    }



    public int getSize() {
        return size;
    }



    public String getObjectNumber() {
        return String.valueOf(objectNumber);
    }

    public String getGenerationNumber() {
        return String.valueOf(generationNumber);
    }

    public int getStartingOffset() {
        return startingOffset;
    }

    //Object version needs to be changed to generation number
    public void incrementGeneration() {
        generationNumber++;
    }

    public void setGenerationNumber(int generation) {
        generationNumber = generation;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void markInUse() {
        inUse = true;
    }

    public void markNotInUse() {
        inUse = false;
    }

    public int getGeneration() {
        return generationNumber;
    }


}