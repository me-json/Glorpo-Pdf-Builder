import base.IndirectObjectTest;
import models.Content;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.UUID;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class XrefTableTest extends IndirectObjectTest {

    static Content model;
    static Content model2;
    static UUID uuid;

    @Override
    public void additionalSetup() {
        uuid = UUID.randomUUID();
        try {
            //This has highlighted an issue, offset calculation will be incorrect if indirect object creation is not blocking
            model = new Content(pdfModel);
            model.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model.writeToPdf();
            model2 = new Content(pdfModel);
            model2.drawImage(uuid, 132, 0, 0, 132, 45, 140);
            model2.writeToPdf();
            pdfModel.writeXrefTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void succeedingTest() throws IOException {
        assertEquals("%PDF-1.7\n" +
                "1 0 obj\n" +
                "<<\n" +
                "/Length 67\n" +
                ">>\n" +
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
                "<<\n" +
                "/Length 67\n" +
                ">>\n" +
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
                "0 3\n" +
                "0000000000 65535 f\n" +
                "0000000009 00000 n\n" +
                "0000000125 00000 n\n", baosToString(pdfModel.getOutputStream()));
    }


}