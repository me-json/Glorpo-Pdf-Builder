package models;




import models.base.DictionaryObject;
import models.base.IndirectObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.UUID;


//Pdf is used to write objects on demand to the baos, retrieve object properties from hash map

@SuppressWarnings({"unused", "FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
public class Pdf {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private int currentObjectNumber = 0;
    private int currentXrefNumber = 0;
    private int currentGenerationNumber = 0;
    private final LinkedHashMap<UUID, models.base.Object> objects = new LinkedHashMap<>();
    private int xrefOffset;
    private Pages pages;
    private Catalog catalog;


    public Pdf() throws IOException {
        outputStream.write("%PDF-1.7\n".getBytes(StandardCharsets.US_ASCII));
        outputStream.write("%\u00E2\u00E3\u00CF\u00D3\n".getBytes(StandardCharsets.ISO_8859_1));
    }
    //This method can also be added to IndirectObject (must get os stream with pdfModel.getOutputStream())
    public void writeByteArray(ByteArrayOutputStream byteArray) throws IOException {
        byteArray.writeTo(outputStream);
    }
    //This method can also be added to IndirectObject (must get os stream with pdfModel.getOutputStream())
    public void writeString(String string) throws IOException {
        outputStream.write(string.getBytes(StandardCharsets.US_ASCII));
    }
    public int getCurrentSize() {
        return outputStream.size();
    }

    public int provideObjectNumber() {
        return ++currentObjectNumber;
    }

    public void registerObject(models.base.Object object) {
        objects.put(object.getId(), object);
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }


    public void writePagesIndirectObject(Pages pages) {
        this.pages = pages;
    }
    public Pages getPagesIndirectObject() {
        return pages;
    }

    public void writeCatalogIndirectObject(Catalog catalog) {
        this.catalog = catalog;
    }
    public Catalog getCatalogIndirectObject() {
        return catalog;
    }

    //This can be refactored to use writeString method
    public void writeXrefTable() throws IOException {
        xrefOffset = outputStream.size();
        outputStream.write("xref\n".getBytes(StandardCharsets.US_ASCII));
        outputStream.write(String.valueOf(currentXrefNumber).getBytes(StandardCharsets.US_ASCII));
        outputStream.write(" ".getBytes(StandardCharsets.US_ASCII));
        outputStream.write(String.valueOf(objects.size()+1).getBytes(StandardCharsets.US_ASCII));
        outputStream.write("\n".getBytes(StandardCharsets.US_ASCII));
        outputStream.write("0000000000 65535 f\n".getBytes(StandardCharsets.US_ASCII));
        int i = 0;
        for (Object value : objects.values()) {
            if (value instanceof IndirectObject) {
                if (i >= currentXrefNumber) {
                    outputStream.write(writeXrefLine((IndirectObject) value).getBytes(StandardCharsets.US_ASCII));
                }
            }
            if (i >= objects.size()) break;
            i++;
        }
    }

    public String writeXrefLine(IndirectObject indirectObject) throws IOException {
        int objectOffset = indirectObject.getStartingOffset();
        String generationNumber = indirectObject.getGenerationNumber();
        boolean inUse = indirectObject.isInUse();
        return String.format("%010d %05d %s\n", objectOffset, Integer.parseInt(generationNumber), inUse ? "n" : "f");
    }

    public void writeTrailer(Catalog catalog) throws IOException {
        outputStream.write("trailer\n".getBytes(StandardCharsets.US_ASCII));
        DictionaryObject trailerDictionary = new DictionaryObject(this);
        trailerDictionary.writeDictionaryEntry("/Size",String.valueOf(objects.size()+1));
        trailerDictionary.writeDictionaryEntry("/Root", catalog.returnObjectReference());
        trailerDictionary.writeDictionaryEntry("/ID", "[<1234567890ABCDEF1234567890ABCDEF> <1234567890ABCDEF1234567890ABCDEF>]");
        outputStream.write(trailerDictionary.returnDictionary().getBytes(StandardCharsets.US_ASCII));
        outputStream.write("startxref\n".getBytes(StandardCharsets.US_ASCII));
        outputStream.write((String.valueOf(xrefOffset) + "\n").getBytes(StandardCharsets.US_ASCII));
        outputStream.write("%%EOF".getBytes(StandardCharsets.US_ASCII));
    }






}
