import models.ContentIndirectObject;
import models.Pdf;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContentModelTest extends IndirectObjectTest {


    static ContentIndirectObject model;
    static UUID uuid;

    @Override
    void additionalSetup() {
        uuid = UUID.randomUUID();
        try {
            model = new ContentIndirectObject(pdfModel);
            model.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model.writeToPdf();
            System.out.println(model.getDictionary().returnDictionary());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void succeedingTest() {
        assertEquals(baosToString(pdfModel.getOutputStream()), "%PDF-1.7\n1 0 obj\n" +
                "<< /Length 133 >>\n" +
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
                "endobj\n");
    }


}