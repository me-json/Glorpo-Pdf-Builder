import helperclasses.PdfOperations;
import models.ContentModel;
import models.PdfModel;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PdfContentsTest extends PdfOperations {


    static ContentModel model;
    static PdfModel pdfModel;



    @BeforeAll
    static void createContentModel() {
        pdfModel = new PdfModel();
        model = new ContentModel(pdfModel);
        model.addChunk(model.drawImage("Im1", 132, 0, 0, 132, 45, 140));
        model.addChunk(model.drawImage("Im1", 132, 0, 0, 132, 45, 140));
        try {
            model.writeInstructions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    @Order(2)
    void succeedingTest() {
        try {
            model.writeStream(pdfModel.getOutputStream());
        } catch (IOException e) {}
        assertEquals(baosToString(pdfModel.getOutputStream()), "%PDF-1.7\n1 0 obj\n" +
                "<< /Length 67 >>\n" +
                "stream\n" +
                "q\n" +
                "132 0 0 132 45 140 cm\n" +
                "/Im1 Do\n" +
                "Q\n" +
                "q\n" +
                "132 0 0 132 45 140 cm\n" +
                "/Im1 Do\n" +
                "Q\n" +
                "endstream\n" +
                "endobj\n");
    }
}
