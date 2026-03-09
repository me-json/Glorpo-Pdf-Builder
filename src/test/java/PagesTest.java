import base.IndirectObjectTest;
import models.Page;
import models.Pages;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PagesTest extends IndirectObjectTest {

    static Pages model;
    static Page model2;

    @Override
    public void additionalSetup() {
        try {
            model = new Pages(pdfModel);
            model2 = new Page(pdfModel);
            model.writeToPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Order(1)
    void succeedingTest() throws Exception {
        assertEquals(baosToString(pdfModel.getOutputStream()), "%PDF-1.7\n" +
                "1 0 obj\n" +
                "<<\n" +
                "/Type /Pages\n" +
                "/Count 1\n" +
                "/Kids 2 0 R\n" +
                ">>\n" +
                "endobj\n");
    }

}
