import base.IndirectObject;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Content extends IndirectObject {


    static models.Content model;
    static UUID uuid;

    @Override
    public void additionalSetup() {
        uuid = UUID.randomUUID();
        try {
            model = new models.Content(pdfModel);
            model.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model.writeToPdf();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void succeedingTest() {
        assertEquals("%PDF-1.7\n1 0 obj\n" +
                "<<\n/Length 133\n>>\n" +
                "stream\n" +
                "q\n" +
                "132 0 0 132 45 140 cm\n" +
                "/" + uuid + " Do\n" +
                "Q\n" +
                "q\n" +
                "132 0 0 132 45 140 cm\n" +
                "/" + uuid + " Do\n" +
                "Q\n" +
                "endstream\n" +
                "endobj\n", baosToString(pdfModel.getOutputStream()));
    }


}