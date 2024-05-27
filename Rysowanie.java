import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class Rysowanie extends JPanel {

    private ArrayList<ArrayList<Character>> maze;
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
            if (800 / k >= 1){
                komorkax = 800/ k;
                komorkay = 800 / w;
            }
            else {
                komorkax = 1;
                komorkay = 1;
            }
            setPreferredSize(new Dimension(k * komorkax, w * komorkay)); // Dynamiczne ustawienie preferowanego rozmiaru
            revalidate(); // Aktualizacja panelu
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
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
                g2d.fillRect(kol * komorkax, wiersz * komorkay, komorkax, komorkay);
            }
        }
    }
}
