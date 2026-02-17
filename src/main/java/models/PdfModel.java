package models;

import helperclasses.Id;
import helperclasses.PdfOperations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

//this should not be a record, but the inputs should be final
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class PdfModel extends PdfOperations {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final String currentStage = "";
    private int currentOffset = 0;
    private int currentObjectNumber;
    private double version;

    // Indirect Object Abstract Class map for fast retrieval all Indirect Objects
    private final HashMap<Id, IndirectObject> indirectObjects = new HashMap<>();

    //Different constructors could create different pdf versions
    public PdfModel() {
        try {
            writeBytes(outputStream, "%PDF-1.7\n");
            currentOffset += outputStream.size();
        } catch (IOException e) {}
    }

    public void writeIndirectObject(IndirectObject indirectObject) {
        indirectObjects.put(indirectObject.getId(), indirectObject);
    }

    public int provideObjectNumber() {
        return ++currentObjectNumber;
    }

    public int getCurrentOffset() {
        return currentOffset;
    }

    public void addCurrentOffsett(int length) {
        currentOffset += length;
    }

    public void setCurrentOffset(int length) {
        currentOffset = length;
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }


}
