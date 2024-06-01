import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class BinKonwerter {
    public static void ToText(DataInputStream in, FileWriter out, BinHeader header) throws IOException {
        int columns = header.getColumns();
        int lines = header.getLines();
        short entryX = header.getEntryX();
        short entryY = header.getEntryY();
        short exitX = header.getExitX();
        short exitY = header.getExitY();
        int wall = header.getWall();
        int path = header.getPath();

        for (int i = 0; i < lines; i++) {
            if (i > 0) {
                out.write('\n');
            }
            for (int j = 0; j < columns; j++) {
                char znak;
                int sep; //słowa kodujące
                int value;
                int count;

                sep = in.read(); //separator
                value = in.read(); //value

                if (value == wall) {
                    znak = 'X';
                } else if (value == path) {
                    znak = ' ';
                } else {
                    znak = '?';
                }

                count = in.read() + 1;

                for (int ile = 0; ile < count; ile++) {
                    if (i == entryY - 1 && j == entryX - 1) {
                        out.write('P');
                    } else if (i == exitY - 1 && j == exitX - 1) {
                        out.write('K');
                    } else {
                        out.write(znak);
                    }
                }
            }
        }
    }
}
