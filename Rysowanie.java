import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Rysowanie extends JPanel {
    private ArrayList<ArrayList<Character>> maze;
    private List<Point> solutionPath;
    private boolean[][] visited;
    private int w; // liczba wierszy
    private int k; // liczba kolumn
    private int komorkax;
    private int komorkay;

    public void przekazaniepliku(String plik) {
        maze = new ArrayList<>();

        try {
            FileReader reader = new FileReader(plik);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ArrayList<Character> row = new ArrayList<>();
                for (char ch : line.toCharArray()) {
                    row.add(ch);
                }
                maze.add(row);
            }
            bufferedReader.close();
            w = maze.size();
            k = maze.get(0).size();
            if (256 / k >= 1) {
                komorkax = 256 / k;
                komorkay = 256 / w;
            } else {
                komorkax = 1;
                komorkay = 1;
            }
            setPreferredSize(new Dimension(k * komorkax * 3, w * komorkay * 3)); // Dynamiczne ustawienie preferowanego rozmiaru
            revalidate(); // Aktualizacja panelu
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        resetSolution();
        repaint();
    }

    public void setSolutionPath(List<Point> path) {
        this.solutionPath = path;
        repaint();
    }

    public void setVisited(boolean[][] visited) {
        this.visited = visited;
        repaint();
    }

    public void resetSolution() {
        this.solutionPath = null;
        this.visited = null;
    }

    public void clearPath() {
        resetSolution();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (maze == null) {
            return;
        }

        for (int wiersz = 0; wiersz < w; wiersz++) {
            for (int kol = 0; kol < k; kol++) {
                char znak = maze.get(wiersz).get(kol);
                switch (znak) {
                    case 'P':
                        g2d.setColor(Color.GREEN);
                        break;
                    case 'K':
                        g2d.setColor(Color.RED);
                        break;
                    case ' ':
                        g2d.setColor(Color.WHITE);
                        break;
                    case 'X':
                        g2d.setColor(Color.BLACK);
                        break;
                    default:
                        System.exit(1);
                        break;
                }
                g2d.fillRect(kol * komorkax * 3, wiersz * komorkay * 3, komorkax * 3, komorkay * 3);
            }
        }

        if (visited != null) {
            g2d.setColor(Color.gray);
            for (int i = 0; i < visited.length; i++) {
                for (int j = 0; j < visited[i].length; j++) {
                    if (visited[i][j]) {
                        g2d.fillRect(j * komorkax * 3, i * komorkay * 3, komorkax * 3, komorkay * 3);
                    }
                }
            }
        }

        if (solutionPath != null) {
            g2d.setColor(Color.YELLOW);
            for (Point p : solutionPath) {
                g2d.fillRect(p.y * komorkax * 3, p.x * komorkay * 3, komorkax * 3, komorkay * 3);
            }
        }
    }

    public ArrayList<ArrayList<Character>> getMaze() {
        return maze;
    }
}
