import java.awt.Point;
import java.util.*;

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
        HashMap<Point, Point> sasiedzi = new HashMap<>();
        queue.add(start);
        visited[start.x][start.y] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(end)) {
                return sciezka(sasiedzi);
            }

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (ValidMove(newX, newY)) {
                    visited[newX][newY] = true;
                    Point next = new Point(newX, newY);
                    queue.add(next);
                    sasiedzi.put(next, current);
                }
            }
        }
        return Collections.emptyList(); //pusta lista, jeżeli nie ma rozwiązania
    }

    private boolean ValidMove(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length &&
                !visited[x][y] && maze[x][y] != 'X';
    }

    private List<Point> sciezka(HashMap<Point, Point> sasiedzi) {
        List<Point> path = new LinkedList<>();
        Point step = end;
        while (step != null) {
            path.add(0, step);
            step = sasiedzi.get(step);
        }
        return path;
    }

    public boolean[][] getVisited() {
        return visited;
    }
}
