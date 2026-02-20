package models;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;


//PdfModel is used to write objects on demand to the baos, retrieve object properties from hash map

@SuppressWarnings({"unused", "FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
public class PdfModel {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private int currentObjectNumber = 0;
    private final HashMap<UUID, Object> objects = new HashMap<>();


    public PdfModel() throws IOException {
        outputStream.write("%PDF-1.7\n".getBytes(StandardCharsets.US_ASCII));
    }

    public void writeByteArray(byte[] byteArray) throws IOException {
        outputStream.write(byteArray);
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









}
