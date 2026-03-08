import models.ContentIndirectObject;
import models.Pdf;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class XrefTableTest extends IndirectObjectTest {

    static ContentIndirectObject model;
    static ContentIndirectObject model2;
    static UUID uuid;

    @Override
    void additionalSetup() {
        uuid = UUID.randomUUID();
        try {
            //This has highlighted an issue, offset calculation will be incorrect if indirect object creation is not blocking
            model = new ContentIndirectObject(pdfModel);
            model.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model.writeToPdf();
            model2 = new ContentIndirectObject(pdfModel);
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
        assertEquals(baosToString(pdfModel.getOutputStream()), "%PDF-1.7\n" +
                "1 0 obj\n" +
                "<< /Length 67 >>\n" +
                "stream\n" +
                "q\n" +
                "132 0 0 132 45 140 cm\n" +
                "/" +
                uuid +
                " Do\n" +
                "Q\n" +
                "endstream\n" +
                "endobj\n" +
                "2 0 obj\n" +
                "<< /Length 67 >>\n" +
                "stream\n" +
                "q\n" +
                "132 0 0 132 45 140 cm\n" +
                "/" +
                uuid +
                " Do\n" +
                "Q\n" +
                "endstream\n" +
                "endobj\n" +
                "xref\n" +
                "0 4\n" +
                "0000000000 65535 f\n" +
                "0000000009 00000 f \n" +
                "0000000125 00000 f \n");
    }


}