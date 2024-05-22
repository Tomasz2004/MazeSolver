import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MazeDrawer extends JPanel {
    private char[][] maze;
    private int cellSize = 20;

    public MazeDrawer() {
    }

    public void loadMaze(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int rows = 0;
            int cols = 0;

            // First, count the number of rows and columns
            while ((line = reader.readLine()) != null) {
                rows++;
                cols = line.length();
            }

            // Allocate the maze array
            maze = new char[rows][cols];

            // Reset reader to the beginning of the file
            reader.close();
            BufferedReader reader2 = new BufferedReader(new FileReader(filename));
            int currentRow = 0;

            // Read the maze into the array
            while ((line = reader2.readLine()) != null) {
                maze[currentRow] = line.toCharArray();
                currentRow++;
            }

            reader2.close();
            revalidate();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (maze == null) {
            return;
        }

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                char cell = maze[row][col];
                switch (cell) {
                    case 'X':
                        g.setColor(Color.BLACK);
                        break;
                    case ' ':
                        g.setColor(Color.WHITE);
                        break;
                    case 'P':
                        g.setColor(Color.GREEN);
                        break;
                    case 'K':
                        g.setColor(Color.RED);
                        break;
                }
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (maze != null) {
            return new Dimension(maze[0].length * cellSize, maze.length * cellSize);
        }
        return super.getPreferredSize();
    }
}
