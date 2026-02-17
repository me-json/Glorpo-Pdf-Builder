package models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageModel extends IndirectObject {

//    <</Type /XObject
//      /Subtype /Image
//      /Width 256
//      /Height 256
//      /ColorSpace /DeviceGray
//      /BitsPerComponent 8
//      /Length 83183
//      /Filter /ASCII85Decode
//    >>



    private final PdfModel pdfModel;

    //Direct Object only for key, value can be indirect object
    private final Map<String, String> map = new HashMap<>();

    public ImageModel(PdfModel pdfModel, String width, String height, String colorSpace, String bitPerComponent, String Length, String Filter) {
        super(pdfModel);
        this.pdfModel = pdfModel;
        map.put("/Type", "/XObject");
        map.put("/Subtype", "Image");
        map.put("/Width", width);
        map.put("/Height", height);
        map.put("/ColorSpace", colorSpace);
        map.put("/BitPerComponent", bitPerComponent);
        map.put("/Length", Length);
        map.put("/Filter", Filter);
    }



    public void drawImage() {}



    @Override
    public void writeDictionary(ByteArrayOutputStream out) throws IOException {

    }

    @Override
    public void writeStream(ByteArrayOutputStream out) throws IOException {}


}
