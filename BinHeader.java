import java.io.DataInputStream;
import java.io.IOException;

public class BinHeader {
    private int id;
    private int escape;
    private short columns;
    private short lines;
    private short entryX;
    private short entryY;
    private short exitX;
    private short exitY;
    private int reservedOne;
    private int reservedTwo;
    private int reservedThree;
    private int counter;
    private int solutionOffset;
    private int separator;
    private int wall;
    private int path;

    public BinHeader(DataInputStream in) throws IOException {
        this.id = Integer.reverseBytes(in.readInt());
        this.escape = in.read();
        this.columns = Short.reverseBytes(in.readShort());
        this.lines = Short.reverseBytes(in.readShort());
        this.entryX = Short.reverseBytes(in.readShort());
        this.entryY = Short.reverseBytes(in.readShort());
        this.exitX = Short.reverseBytes(in.readShort());
        this.exitY = Short.reverseBytes(in.readShort());
        this.reservedOne = Integer.reverseBytes(in.readInt());
        this.reservedTwo = Integer.reverseBytes(in.readInt());
        this.reservedThree = Integer.reverseBytes(in.readInt());
        this.counter = Integer.reverseBytes(in.readInt());
        this.solutionOffset = Integer.reverseBytes(in.readInt());
        this.separator = in.read();
        this.wall = in.read();
        this.path = in.read();
    }

    public short getColumns() {
        return columns;
    }

    public short getLines() {
        return lines;
    }

    public short getEntryX() {
        return entryX;
    }

    public short getEntryY() {
        return entryY;
    }

    public short getExitX() {
        return exitX;
    }

    public short getExitY() {
        return exitY;
    }

    public int getWall() {
        return wall;
    }

    public int getPath() {
        return path;
    }
}
