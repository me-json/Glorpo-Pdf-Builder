package base;

import models.Pdf;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IndirectObject {

    public static Pdf pdfModel;

    @BeforeAll
    void setup() throws Exception {
        pdfModel = new Pdf();
        additionalSetup();
    }

    public void additionalSetup() {
        System.out.println("No Additional Setup Implemented");
    }

    public String baosToString(ByteArrayOutputStream baos) {
        return baos.toString(StandardCharsets.UTF_8);
    }

}
