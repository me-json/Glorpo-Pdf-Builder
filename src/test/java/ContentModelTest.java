import models.ContentModel;
import models.PdfModel;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContentModelTest {

    static PdfModel pdfModel;
    static ContentModel model;
    static UUID uuid;

    @BeforeAll
    static void setup() throws IOException {
        pdfModel = new PdfModel();
        uuid = UUID.randomUUID();
        try {
            model = new ContentModel(pdfModel);
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

    private String baosToString(ByteArrayOutputStream baos) {
        return baos.toString(StandardCharsets.UTF_8);
    }
}