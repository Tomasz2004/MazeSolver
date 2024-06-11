import java.awt.Point;
import java.util.*;
import javax.swing.JOptionPane;

public class BFS {
    private char[][] maze;
    private boolean[][] visited;
    private Point start;
    private Point end;
    private ArrayList<Point> path;

    public BFS(ArrayList<ArrayList<Character>> maze) {
        int w = maze.size();
        int k = maze.get(0).size();
        this.maze = new char[w][k];
        this.visited = new boolean[w][k];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < k; j++) {
                this.maze[i][j] = maze.get(i).get(j);
                if (maze.get(i).get(j) == 'P') {
                    start = new Point(i, j);
                } else if (maze.get(i).get(j) == 'K') {
                    end = new Point(i, j);
                }
            }
        }
    }

    public List<Point> solve() {
        Queue<Point> queue = new LinkedList<>();
        HashMap<Point, Point> kroki = new HashMap<>();
        queue.add(start);
        visited[start.x][start.y] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(end)) {
                return reconstructPath(kroki);
            }

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (isValidMove(newX, newY)) {
                    visited[newX][newY] = true;
                    Point next = new Point(newX, newY);
                    queue.add(next);
                    kroki.put(next, current);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Brak rozwiązania dla podanego labiryntu.");
        return Collections.emptyList();
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length &&
                !visited[x][y] && maze[x][y] != 'X';
    }

    private List<Point> reconstructPath(HashMap<Point, Point> kroki) {
        List<Point> path = new LinkedList<>();
        Point step = end;
        while (step != null) {
            path.add(0, step);
            step = kroki.get(step);
        }
        return path;
    }

    public boolean[][] getVisited() {
        return visited;
    }
}
