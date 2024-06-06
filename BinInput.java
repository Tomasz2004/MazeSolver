import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class BinInput {
    public static void binToText(String binaryFilePath, String txtFilePath) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(binaryFilePath));
             FileWriter out = new FileWriter(txtFilePath)) {

            BinHeader header = new BinHeader(in);
            BinKonwerter.toTxt(in, out, header);
        }
    }
}
