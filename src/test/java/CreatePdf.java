import models.*;
import models.base.DictionaryObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class CreatePdf {


    public static void main(String[] args) {
        try {
            Pdf pdfModel = new Pdf();


            Image image = new Image(pdfModel);
            image.writeImageAttributes(1, 1, "/DeviceGray", 1);
            byte[] data = new byte[]{
                    (byte)0x78, (byte)0x01,
                    (byte)0x63, (byte)0x60,
                    (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02,
                    (byte)0x00, (byte)0x01
            };
            image.writeImageStream(10, "/FlateDecode", data);
            image.writeToPdf();

            UUID uuid = UUID.randomUUID();

            Content contentModel = new Content(pdfModel);
            contentModel.drawImage(uuid, 132, 0, 0, 132, 45, 140);

            DictionaryObject dictionary = new DictionaryObject(pdfModel);
            dictionary.writeDictionaryEntry("/" + uuid.toString(), image.returnObjectReference());
            contentModel.writeToPdf();


            Pages pages = new Pages(pdfModel);
            Page page = new Page(pdfModel);
            page.writeContents(contentModel);
            page.setMediaBox(0, 0, 612, 792);
            page.writeXObject(dictionary);
            page.writeToPdf();
            pages.writeToPdf();


            Catalog catalog  = new Catalog(pdfModel);
            catalog.writeToPdf();

            pdfModel.writeXrefTable();
            pdfModel.writeTrailer(catalog);









            String baos = baosToString(pdfModel.getOutputStream());
            System.out.println(baos);
            try (FileOutputStream fos = new FileOutputStream("test.pdf")) {
                pdfModel.getOutputStream().writeTo(fos);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public static String baosToString(ByteArrayOutputStream baos) {
        return baos.toString(StandardCharsets.UTF_8);
    }





}
