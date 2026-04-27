import base.CreateBarcode;
import models.*;
import models.base.DictionaryObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class CreatePdf {


    public static void createPdf() {
        try {




            Pdf pdfModel = new Pdf();









            //Creates pages
            Pages pages = new Pages(pdfModel);
            Font font = new Font(pdfModel);
            font.writeFont("/Font", "/Type1", "/Helvetica");
            font.writeToPdf();
            DictionaryObject object = new DictionaryObject(pdfModel);
            object.writeDictionaryEntry("/F1", font.returnObjectReference());
            for(int i=1500; i>1074; i--) {
                //Writes Image
                //Creates content model
                UUID uuid = UUID.randomUUID();
                Content contentModel = new Content(pdfModel);
                contentModel.drawImage(uuid, 130, 0, 0, 60, 73, 6);
                contentModel.writeText(String.valueOf(i), String.valueOf(i), 12, 30, "/F1", 24);
                contentModel.writeToPdf();
                Image image = new Image(pdfModel);
                image.writeImageAttributes(650, 300, "/DeviceGray", 1);
                byte[] bytes = CreateBarcode.getCompressedBarcode("I00" + String.valueOf(i));
                image.writeImageStream(bytes.length, "/FlateDecode", bytes);
                image.writeToPdf();


                //Writes page
                Page page1 = new Page(pdfModel);

                page1.writeResource("/Font", object.returnDictionary());
                page1.writeContents(contentModel);
                page1.setMediaBox(0, 0, 216, 72);
                page1.writeImage(image, uuid.toString());
                page1.writeToPdf();


            }

            //Writes pages
            pages.writeToPdf();

            //Writes catalog
            Catalog catalog  = new Catalog(pdfModel);
            catalog.writeToPdf();

            //Writes xref table
            pdfModel.writeXrefTable();

            //Writes trailer
            pdfModel.writeTrailer(catalog);









            String baos = baosToString(pdfModel.getOutputStream());
            //String baos = "";
            //System.out.println(baos);

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
