import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.JOptionPane;

public class AnimatedDFS {
    private char[][] maze;
    private List<Point> steps; // Lista kroków

    public AnimatedDFS(char[][] maze) {
        this.maze = maze;
        this.steps = new ArrayList<>();
    }

    public List<Point> solve(int startX, int startY) {
        if (dfs(startX, startY)) {
            return steps;
        }
        JOptionPane.showMessageDialog(null, "Brak rozwiązania dla podanego labiryntu.");
        return Collections.emptyList();
    }

    private boolean dfs(int x, int y) {
        Point currentPoint = new Point(x, y);
        char currentChar = maze[x][y];
        if (currentChar == 'K') {
            steps.add(currentPoint); // Dodaj punkt końcowy
            return true; // Znaleziono wyjście
        }
        if (currentChar == 'X' || currentChar == '1') {
            return false; // Ściana lub odwiedzone pole
        }
        maze[x][y] = '1'; // Oznacz pole jako odwiedzone
        steps.add(currentPoint); // Dodaj aktualny punkt do listy kroków

        // Sprawdź sąsiednie pola
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // Góra, Prawo, Dół, Lewo
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (isValid(newX, newY) && maze[newX][newY] != '1') {
                if (dfs(newX, newY)) { // Rekurencyjne przeszukiwanie
                    return true;
                }
            }
        }
        steps.add(currentPoint); // Dodaj punkt powrotu
        return false; // Brak wyjścia z labiryntu
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length; // Sprawdź czy współrzędne są poprawne
    }
}
