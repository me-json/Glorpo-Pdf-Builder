import base.IndirectObjectTest;
import models.Image;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImageTest extends IndirectObjectTest {

    static Image model;

    @Override
    public void additionalSetup() {
        try {
            model = new Image(pdfModel);
            model.writeImageAttributes(1, 1, "DeviceGray", 1);
            byte[] data = new byte[] {
                    (byte)0x78, (byte)0x01, (byte)0x63, (byte)0x60,
                    (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02,
                    (byte)0x00, (byte)0x01
            };
            model.writeImageStream(10, "/FlateDecode", data);
            model.writeToPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    @Order(1)
    void succeedingTest() {
        assertEquals("", baosToString(pdfModel.getOutputStream()));
    }

}
