package base;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;




import java.util.zip.Deflater;

public class CreateQrCode {

    public static byte[] getCompressedBarcode(String data) {
        try {
            BitMatrix m = new MultiFormatWriter()
                    .encode(data, BarcodeFormat.QR_CODE, 300, 300);

            int w = m.getWidth(), h = m.getHeight(), row = (w + 7) / 8;
            byte[] out = new byte[row * h];

            for (int y = 0; y < h; y++)
                for (int x = 0; x < w; x++)
                    if (m.get(x, y))
                        out[y * row + x / 8] |= (1 << (7 - (x % 8)));

            for (int i = 0; i < out.length; i++) {
                out[i] = (byte) ~out[i];
            }

            Deflater deflater = new Deflater(Deflater.BEST_SPEED); // default compression level
            deflater.setInput(out);
            deflater.finish();

            byte[] buffer = new byte[out.length];
            int compressedSize = deflater.deflate(buffer);

            byte[] output = new byte[compressedSize];
            System.arraycopy(buffer, 0, output, 0, compressedSize);

            return output;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
