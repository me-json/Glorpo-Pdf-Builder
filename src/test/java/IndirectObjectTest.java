import models.Pdf;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IndirectObjectTest {

    static Pdf pdfModel;

    @BeforeAll
    void setup() throws Exception {
        pdfModel = new Pdf();
        additionalSetup();
    }

    void additionalSetup() {
        System.out.println("No Additional Setup Implemented");
    }

    String baosToString(ByteArrayOutputStream baos) {
        return baos.toString(StandardCharsets.UTF_8);
    }

}
