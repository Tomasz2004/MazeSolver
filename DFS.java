import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.JOptionPane;

public class DFS {
    private char[][] maze;
    private boolean[][] visited;

    public DFS(char[][] maze) {
        this.maze = maze;
        this.visited = new boolean[maze.length][maze[0].length];
    }

    public List<Point> solve(int startX, int startY) {
        Stack<Point> stack = new Stack<>();
        Map<Point, Point> kroki = new HashMap<>();

        stack.push(new Point(startX, startY));

        while (!stack.isEmpty()) {
            Point current = stack.pop();
            int x = current.x;
            int y = current.y;

            if (maze[x][y] == 'K') {
                return PathOnList(kroki, new Point(startX, startY), current);
            }

            if (!visited[x][y]) {
                visited[x][y] = true;

                int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
                for (int[] dir : directions) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];

                    if (isValid(newX, newY) && !visited[newX][newY] && maze[newX][newY] != 'X') {
                        stack.push(new Point(newX, newY));
                        kroki.put(new Point(newX, newY), current);
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Brak rozwiązania dla podanego labiryntu.");
        return Collections.emptyList(); // Jeśli nie znaleziono rozwiązania, zwraca pustą listę
    }

    private List<Point> PathOnList(Map<Point, Point> kroki, Point start, Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;

        while (!current.equals(start)) {
            path.add(current);
            current = kroki.get(current);
        }
        path.add(start);
        Collections.reverse(path); //Odwracamy bo szliśmy od końca
        return path;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length;
    }

    public boolean[][] getVisited() {
        return visited;
    }
}