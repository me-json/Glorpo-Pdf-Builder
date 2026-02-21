package models;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StreamModel extends Object{

    ByteArrayOutputStream instructionStream = new ByteArrayOutputStream();
    private ArrayList<String> instructions = new ArrayList<>();
    private int streamSize;


    //flush commands write any literal instructions to the baos
    //any direct read bytes don't need to follow this process
    //instruction arraylist -> flush to bytes
    //byte[] or baos input -> directly to baos
    public void writeInstruction(){
        for (String instruction : instructions) {
            instructionStream.write(instruction.getBytes(StandardCharsets.US_ASCII));
        }

    }
    //

    public int getStreamSize() {
        return streamSize;
    }

    public byte[] getByteArray() {
        return instructionStream.toByteArray();
    }

}
