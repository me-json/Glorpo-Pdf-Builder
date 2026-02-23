import models.ContentModel;
import models.PdfModel;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class XrefTableTest {

    static PdfModel pdfModel;
    static ContentModel model;
    static ContentModel model2;
    static UUID uuid;

    @BeforeAll
    static void setup() throws IOException {
        pdfModel = new PdfModel();
        uuid = UUID.randomUUID();
        try {
            //This has highlighted an issue, offset calculation will be incorrect if indirect object creation is not blocking
            model = new ContentModel(pdfModel);
            model.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model.writeToPdf();
            model2 = new ContentModel(pdfModel);
            model2.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model2.writeToPdf();
            pdfModel.writeXrefTable();
            System.out.println(model.getDictionary().returnDictionary());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void succeedingTest() throws IOException {
        System.out.println(pdfModel.writeXrefLine(model));
        assertEquals(baosToString(pdfModel.getOutputStream()), "");
    }

    private String baosToString(ByteArrayOutputStream baos) {
        return baos.toString(StandardCharsets.UTF_8);
    }
}