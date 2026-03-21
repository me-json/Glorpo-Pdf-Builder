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

            UUID uuid = UUID.randomUUID();


            //Creates content model
            Content contentModel = new Content(pdfModel);
            contentModel.drawImage(uuid, 130, 0, 0, 60, 43, 6);
            contentModel.writeToPdf();





            //Creates pages
            Pages pages = new Pages(pdfModel);



            for(int i=700; i<702; i++) {
                //Writes Image
                Image image = new Image(pdfModel);
                image.writeImageAttributes(650, 300, "/DeviceGray", 1);
                byte[] bytes = CreateBarcode.getCompressedBarcode("C000" + String.valueOf(i));
                image.writeImageStream(bytes.length, "/FlateDecode", bytes);
                image.writeToPdf();


                //Writes page
                Page page1 = new Page(pdfModel);
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
