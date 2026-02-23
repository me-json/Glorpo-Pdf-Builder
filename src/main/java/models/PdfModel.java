package models;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;


//PdfModel is used to write objects on demand to the baos, retrieve object properties from hash map

@SuppressWarnings({"unused", "FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
public class PdfModel {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private int currentObjectNumber = 0;
    private int currentXrefNumber = 0;
    private int currentGenerationNumber = 0;
    private final LinkedHashMap<UUID, Object> objects = new LinkedHashMap<>();



    public PdfModel() throws IOException {
        outputStream.write("%PDF-1.7\n".getBytes(StandardCharsets.US_ASCII));
    }

    public void writeByteArray(ByteArrayOutputStream byteArray) throws IOException {
        byteArray.writeTo(outputStream);
    }
    public void writeString(String string) throws IOException {
        outputStream.write(string.getBytes(StandardCharsets.US_ASCII));
    }
    public int getCurrentSize() {
        return outputStream.size();
    }

    public int provideObjectNumber() {
        return ++currentObjectNumber;
    }

    public void registerObject(Object object) {
        objects.put(object.getId(), object);
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public void writeXrefTable() throws IOException {
        outputStream.write("xref\n".getBytes(StandardCharsets.US_ASCII));
        outputStream.write(String.valueOf(currentXrefNumber).getBytes(StandardCharsets.US_ASCII));
        outputStream.write(" ".getBytes(StandardCharsets.US_ASCII));
        outputStream.write(String.valueOf(objects.size()).getBytes(StandardCharsets.US_ASCII));
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
        return String.format("%010d %05d %s \n", objectOffset, Integer.parseInt(generationNumber), inUse ? "n" : "f");
    }







}
