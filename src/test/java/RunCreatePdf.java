public class RunCreatePdf {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        for(int i = 0; i<1; i++) {
            CreatePdf.createPdf();
        }
        System.out.println(System.currentTimeMillis());
    }
}
//87 for 150k no save -> 1724/sec
//3.35 for 5000 with save -> 1492/sec
//31.853 for 50000 with save -> 1570
//3.1 for 5000 with no save -> 1612
//29 sec for 50000 with no save -> e1724/sec