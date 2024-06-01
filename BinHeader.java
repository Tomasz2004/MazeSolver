import java.io.DataInputStream;
import java.io.IOException;

public class BinHeader {
    private int fileId;
    private int escape;
    private short columns;
    private short lines;
    private short entryX;
    private short entryY;
    private short exitX;
    private short exitY;
    private int reserved1;
    private int reserved2;
    private int reserved3;
    private int counter;
    private int solutionOffset;
    private int separator;
    private int wall;
    private int path;

    public static BinHeader readFrom(DataInputStream in) throws IOException {
        BinHeader header = new BinHeader();
        header.fileId = Integer.reverseBytes(in.readInt());
        header.escape = in.read();
        header.columns = Short.reverseBytes(in.readShort());
        header.lines = Short.reverseBytes(in.readShort());
        header.entryX = Short.reverseBytes(in.readShort());
        header.entryY = Short.reverseBytes(in.readShort());
        header.exitX = Short.reverseBytes(in.readShort());
        header.exitY = Short.reverseBytes(in.readShort());
        header.reserved1 = Integer.reverseBytes(in.readInt());
        header.reserved2 = Integer.reverseBytes(in.readInt());
        header.reserved3 = Integer.reverseBytes(in.readInt());
        header.counter = Integer.reverseBytes(in.readInt());
        header.solutionOffset = Integer.reverseBytes(in.readInt());
        header.separator = in.read();
        header.wall = in.read();
        header.path = in.read();
        return header;
    }

    public int getColumns() {
        return columns; }
    public int getLines() {
        return lines; }
    public short getEntryX() {
        return entryX; }
    public short getEntryY() {
        return entryY; }
    public short getExitX() {
        return exitX; }
    public short getExitY() {
        return exitY; }
    public int getWall() {
        return wall; }
    public int getPath() {
        return path; }
}
