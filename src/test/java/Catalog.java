import base.IndirectObject;
import models.Pages;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Catalog extends IndirectObject {

    static models.Catalog model;
    static Pages model2;

    @Override
    public void additionalSetup() {
        try {
            model = new models.Catalog(pdfModel);
            model2 = new Pages(pdfModel);
            model.writeCatalogAttributes();
            model.writeToPdf();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void succeedingTest() throws Exception {
        assertEquals("%PDF-1.7\n" +
                        "1 0 obj\n" +
                        "<<\n" +
                        "/Type /Catalog\n" +
                        "/Pages 2 0 R\n" +
                        ">>\n" +
                        "endobj\n",
                baosToString(pdfModel.getOutputStream()));
    }

}
