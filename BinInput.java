import java.io.*;

public class BinInput {
    public static void binToText(String inpath, String outpath) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(inpath));
             FileWriter out = new FileWriter(outpath)) {

            BinHeader header = BinHeader.readFrom(in);
            BinKonwerter.ToText(in, out, header);
        }
    }
}
