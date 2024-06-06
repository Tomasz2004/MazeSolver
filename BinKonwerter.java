import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class BinKonwerter {
    public static void toTxt(DataInputStream in, FileWriter out, BinHeader header) throws IOException {
        for(int i = 0; i < header.getLines(); i++) {
            if(i != 0) out.write('\n');
            for(int j = 0; j < header.getColumns(); j++) {
                char znak;
                int sep;
                int value;
                int count;
                int ile;

                sep = in.read();
                value = in.read();

                if(value == header.getWall()) {
                    znak = 'X';
                } else if(value == header.getPath()) {
                    znak = ' ';
                } else {
                    znak = '?';
                }

                count = in.read();
                ile = count + 1;

                j--;
                for(int ilepom = 0; ilepom < ile; ilepom++) {
                    j++;
                    if(j == header.getColumns()) {              //Przeniesienie się do następnego wiersza w przypadku dojścia do ostatniego elementu wiersza
                        j = 0;
                        i++;
                    }

                    if(i == header.getEntryY() - 1 && j == header.getEntryX() - 1) {
                        out.write('P');
                    } else if(i == header.getExitY() - 1 && j == header.getExitX() - 1) {
                        out.write('K');
                    } else {
                        out.write(znak);
                    }
                }
            }
        }
    }
}
