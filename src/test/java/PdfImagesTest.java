import helperclasses.PdfOperations;
import models.ImageModel;
import models.PdfModel;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PdfImagesTest extends PdfOperations {


    static PdfModel pdfModel;
    static ImageModel imageModel;



    @BeforeAll
    static void createContentModel() {
        pdfModel = new PdfModel();
        imageModel = new ImageModel(pdfModel);
        imageModel.drawImage();
        try {
            imageModel.writeInstructions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    @Order(2)
    void succeedingTest() {
        try {
            imageModel.writeStream(pdfModel.getOutputStream());
        } catch (IOException e) {}
        assertEquals(baosToString(pdfModel.getOutputStream()),
        "%PDF-1.7\n1 0 obj\n" + ""
        );
    }
}
