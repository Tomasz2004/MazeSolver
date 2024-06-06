import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.SwingWorker;

public class Rysowanie extends JPanel {
    private ArrayList<ArrayList<Character>> maze;
    private List<Point> solutionPath;
    private boolean[][] visited;
    private int w; // liczba wierszy
    private int k; // liczba kolumn
    private int komorkax;
    private int komorkay;
    private List<Point> animationPath;
    private int animationIndex = 0;
    private volatile boolean isAnimating = false;
    private int speed = 100;
    public int czyKoniec = 0;

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
        stopAnimation();
        resetSolution();
        repaint();
    }

    public void stopAnimation() {
        isAnimating = false;
    }

    public void setCzyKoniec(int value) {
        this.czyKoniec = value;
    }

    public int getCzyKoniec() {
        return this.czyKoniec;
    }

    public void animateDFS(List<Point> path) {
        this.animationPath = path;
        this.animationIndex = 0;
        this.isAnimating = true;
        setCzyKoniec(0);
        if (w*k>100){
            speed=50;
            if (w*k>2500){
                speed=30;
                if (w*k>10000){
                    speed=15;
                    if (w*k>1000000){
                        speed=5;
                    }
                }
            }
        }

        SwingWorker<Void, Point> worker = new SwingWorker<Void, Point>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (Point point : animationPath) {
                    if (!isAnimating) {
                        break;
                    }
                    publish(point); //Przekaż punkt do metody process
                    Thread.sleep(speed); // Przerwa między krokami
                }
                return null;
            }

            @Override
            protected void process(List<Point> chunks) {    //chunks to liczba punktów opublikowanych przez publish(point)
                for (Point point : chunks) {
                    if (!isAnimating) {
                        break;
                    }
                    animationIndex++;
                    repaint();
                }
            }
            @Override
            protected void done() {
                setCzyKoniec(1); // Ustawienie zmiennej na 1 po zakończeniu animacji
            }
        };
        worker.execute();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (maze == null) {
            return;
        }

        // Rysowanie labiryntu
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

        // Rysowanie odwiedzonych pól
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

        // Rysowanie animacji DFS
        if (animationPath != null && isAnimating) {
            g2d.setColor(Color.BLUE);
            for (int i = 0; i < animationIndex; i++) {
                Point p = animationPath.get(i);
                g2d.fillRect(p.y * komorkax * 3, p.x * komorkay * 3, komorkax * 3, komorkay * 3);
            }
        }

        // Rysowanie ścieżki rozwiązania
        if (solutionPath != null) {
            g2d.setColor(Color.YELLOW);
            for (Point p : solutionPath) {
                g2d.fillRect(p.y * komorkax * 3, p.x * komorkay * 3, komorkax * 3, komorkay * 3);
            }
        }
    }

    public int getKomorkax() {
        return komorkax;
    }

    public int getKomorkay() {
        return komorkay;
    }

    public int getStartX() {
        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(i).size(); j++) {
                if (maze.get(i).get(j) == 'P') {
                    return i; // Zwróć współrzędną X punktu startowego
                }
            }
        }
        return -1; // Jeśli punkt startowy nie został znaleziony, zwróć -1
    }

    public int getStartY() {
        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(i).size(); j++) {
                if (maze.get(i).get(j) == 'P') {
                    return j; // Zwróć współrzędną Y punktu startowego
                }
            }
        }
        return -1; // Jeśli punkt startowy nie został znaleziony, zwróć -1
    }


    public ArrayList<ArrayList<Character>> getMaze() {
        return maze;
    }

    public char[][] getMazeDFS() {
        char[][] mazeArray = new char[maze.size()][];
        for (int i = 0; i < maze.size(); i++) {
            ArrayList<Character> row = maze.get(i);
            mazeArray[i] = new char[row.size()];
            for (int j = 0; j < row.size(); j++) {
                mazeArray[i][j] = row.get(j);
            }
        }
        return mazeArray;
    }
}