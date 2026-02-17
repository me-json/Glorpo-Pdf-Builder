package models;

import helperclasses.Id;
import helperclasses.PdfOperations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


@SuppressWarnings("unused")
public abstract class IndirectObject extends PdfOperations {


    //Master pdfModel object, each object has a unique id
    private final PdfModel pdfModel;
    private final Id id;


    //Size of entire object, used to calculate xref table
    private int size = 0;
    //Necessary indirect object values
    private final int objectNumber;
    private int objectVersion = 0;



    //Data structures for building the contents of a stream, and for writing the stream itself
    protected ArrayList<String> streamChunks = new ArrayList<>();
    protected ByteArrayOutputStream stream = new ByteArrayOutputStream();



    //Constructor links to master pdfmodel, gets objectnumber from pdfmodel,
    //has the pdfmodel assign it an addressable id, and registers the class
    //reference and id number with the pdfmodel master class through the
    //indirectObject hash map
    public IndirectObject(PdfModel pdfModel) {
        this.pdfModel = pdfModel;
        this.objectNumber = pdfModel.provideObjectNumber();
        this.id = new Id();
        pdfModel.writeIndirectObject(this);
    }


    //Writes the dictionary data structure to the pdfmodel baos
    public abstract void writeDictionary(ByteArrayOutputStream out) throws IOException;
    //Writes the stream data structure to the pdfmodel baos
    public abstract void writeStream(ByteArrayOutputStream out) throws IOException;

    //Writes instructions to the main pdfmodel baos
    //#### THIS CAN BE REPLACED WITH WRITEDICTIONARY & WRITESTREAM
    //#### MAYBE DICTIONARY OBJECT AND STREAM CAN BE ENTIRE MODEL CLASSES
    public void writeInstructions() throws IOException {
        for (String instruction : streamChunks) {
            //updateOffset();
            int currentSize = pdfModel.getCurrentOffset();
            int newSize = pdfModel.getOutputStream().size();
            size = newSize - currentSize;
            pdfModel.setCurrentOffset(newSize);
        }
    }
    //Writes instruction "chunk" to the instructions data structure
    public void addChunk(String chunk) {
        streamChunks.add(chunk);
    }

    //Updates main offset and logs length of existing object
    public void updateOffset() {

    }

    public Id getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public void addSize(int length) {
        size += length;
    }


    public int getObjectNumber() {
        return objectNumber;
    }

    public int getObjectVersion() {
        return objectVersion;
    }

    public void incrementVersion() {
        objectVersion++;
    }

    public void setVersion(int version) {
        objectVersion = version;
    }
}