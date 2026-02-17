package helperclasses;

import models.ContentModel;
import models.IndirectObject;
import models.PdfModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import java.util.UUID;


@SuppressWarnings("unused")
public abstract class PdfOperations {


    // start file, writes header
    public void writeFileHeader(PdfModel pdf) {

    }
    // writes transient data
    public void writeIndirectObject(PdfModel pdf, String objectType, Byte[] bytes) {

    }
    // page operations
    public void writeObjectToPage(PdfModel pdf, String pageId, IndirectObject object, ContentModel content) {

    }
    public void writeObjectToPage(UUID pdf, UUID pageId, UUID object, UUID content) {

    }
    // cross-reference table and writes trailer





    //Converts UTF-8 characters to US_ASCII, writing the converted bytes to the BAOS
    public static void writeBytes(OutputStream os, String name) throws IOException {

        if (name == null) return;
        byte[] bytes = name.getBytes(StandardCharsets.US_ASCII);
        IO.print(name.getBytes(StandardCharsets.US_ASCII));
        os.write(bytes);
    }

    public static int getSize(String name) {
        if (name == null) return 0;
        byte[] bytes = name.getBytes(StandardCharsets.US_ASCII);
        return bytes.length;
    }

    public static String writeObjectNumber(int objectNumber) {
        return objectNumber + " 0 obj\n";
    }

    public static byte[] getBytes(String name) {
        if (name == null) return null;
        return name.getBytes(StandardCharsets.US_ASCII);
    }


    public String baosToString(ByteArrayOutputStream baos) {
        return baos.toString(StandardCharsets.UTF_8);
    }


}
