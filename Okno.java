import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Okno extends JFrame implements ActionListener {

    private Rysowanie rysowaniePanel;
    private NewPK newPK;
    private DFS dfsSolver;

    JMenuBar menubar;
    JMenu labiryntMenu;
    JMenuItem txtItem;
    JMenuItem binItem;
    JMenuItem zapiszItem;
    JMenuItem wyjdzItem;
    JButton startend;
    JButton dfs;
    JButton animateddfs;
    JButton bfs;
    JButton clear;
    JScrollPane scrollPane;

    public Okno() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLayout(new BorderLayout());

        menubar = new JMenuBar();

        labiryntMenu = new JMenu("Labirynt");

        txtItem = new JMenuItem("Wczytaj z .txt");
        binItem = new JMenuItem("Wczytaj z .bin");
        zapiszItem = new JMenuItem("Zapisz labirynt");
        wyjdzItem = new JMenuItem("Wyjdź");

        txtItem.addActionListener(this);
        binItem.addActionListener(this);
        zapiszItem.addActionListener(this);
        wyjdzItem.addActionListener(this);

        labiryntMenu.setMnemonic(KeyEvent.VK_L); // ALT+L dla labiryntMenu

        txtItem.setMnemonic(KeyEvent.VK_T); // T dla pliku txt
        binItem.setMnemonic(KeyEvent.VK_B); // B dla pliku bin
        zapiszItem.setMnemonic(KeyEvent.VK_S); // S aby zapisać
        wyjdzItem.setMnemonic(KeyEvent.VK_E); // E aby wyjść

        labiryntMenu.add(txtItem);
        labiryntMenu.add(binItem);
        labiryntMenu.add(zapiszItem);
        labiryntMenu.add(wyjdzItem);
        menubar.add(labiryntMenu);
        this.setJMenuBar(menubar);
        zapiszItem.setVisible(false);

        rysowaniePanel = new Rysowanie();
        scrollPane = new JScrollPane(rysowaniePanel);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        startend = new JButton();
        dfs = new JButton();
        animateddfs = new JButton();
        bfs = new JButton();
        clear = new JButton();

        startend.addActionListener(this);
        startend.setText("Nowy punkt startowy i końcowy");
        startend.setFocusable(false);
        startend.setVisible(false);

        dfs.addActionListener(this);
        dfs.setText("DFS");
        dfs.setFocusable(false);
        dfs.setVisible(false);

        animateddfs.addActionListener(this);
        animateddfs.setText("Symulacja");
        animateddfs.setFocusable(false);
        animateddfs.setVisible(false);

        bfs.addActionListener(this);
        bfs.setText("BFS - najkrótsza ścieżka");
        bfs.setFocusable(false);
        bfs.setVisible(false);

        clear.addActionListener(this);
        clear.setText("Wyczyść labirynt");
        clear.setFocusable(false);
        clear.setVisible(false);

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(startend);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(dfs);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(animateddfs);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(bfs);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(clear);
        buttonPanel.add(Box.createHorizontalGlue());

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);

        newPK = new NewPK(rysowaniePanel);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == txtItem) {
            rysowaniePanel.clearPath(); // Przerywa animację i czyści ścieżkę animowaną
            JFileChooser txtchooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
            txtchooser.setFileFilter(filter);

            int odp = txtchooser.showOpenDialog(null);
            if (odp == JFileChooser.APPROVE_OPTION) {
                String maze = txtchooser.getSelectedFile().getAbsolutePath();
                rysowaniePanel.resetSolution(); // Resetuje rozwiązanie przed wczytaniem nowego labiryntu
                rysowaniePanel.przekazaniepliku(maze);
                zapiszItem.setVisible(true);
                startend.setVisible(true);
                dfs.setVisible(true);
                animateddfs.setVisible(true);
                bfs.setVisible(true);
                clear.setVisible(true);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        }
        if (e.getSource() == binItem) {
            rysowaniePanel.clearPath(); // Przerywa animację i czyści ścieżkę animowaną
            JFileChooser binchooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter(".bin", "bin");
            binchooser.setFileFilter(filter);

            int odp = binchooser.showOpenDialog(null);
            if (odp == JFileChooser.APPROVE_OPTION) {
                String binPath = binchooser.getSelectedFile().getAbsolutePath();
                String txtPath = "maze_decoded.txt";
                try {
                    BinInput.binToText(binPath, txtPath);
                    rysowaniePanel.resetSolution();
                    rysowaniePanel.przekazaniepliku(txtPath);
                    zapiszItem.setVisible(true);
                    startend.setVisible(true);
                    dfs.setVisible(true);
                    animateddfs.setVisible(true);
                    bfs.setVisible(true);
                    clear.setVisible(true);
                    scrollPane.revalidate();
                    scrollPane.repaint();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (e.getSource() == zapiszItem) {
            try {
                String filename = "labirynt.png";
                Screenshot.saveComponentAsPNG(rysowaniePanel, filename);
                JOptionPane.showMessageDialog(null, "Labirynt został zapisany do pliku " + filename);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas zapisywania labiryntu.");
            }
        }
        if (e.getSource() == startend) {
            System.out.println("siema");
            rysowaniePanel.clearPath();
            newPK.startSelection();
        }
        if (e.getSource() == dfs) {
            dfsSolver = new DFS(rysowaniePanel.getMazeDFS());
            rysowaniePanel.clearPath();
            List <Point> steps = dfsSolver.solve(rysowaniePanel.getStartX(), rysowaniePanel.getStartY());
            rysowaniePanel.setSolutionPath(steps);
        }
        if (e.getSource() == animateddfs) {
            dfsSolver = new DFS(rysowaniePanel.getMazeDFS());
            rysowaniePanel.clearPath();
            List<Point> steps = dfsSolver.solve(rysowaniePanel.getStartX(), rysowaniePanel.getStartY());
            rysowaniePanel.animateDFS(steps);
        }
        if (e.getSource() == bfs) {
            rysowaniePanel.clearPath();
            BFS bfsSolver = new BFS(rysowaniePanel.getMaze());
            List<Point> path = bfsSolver.solve();
            rysowaniePanel.setVisited(bfsSolver.getVisited());
            rysowaniePanel.setSolutionPath(path);
        }
        if (e.getSource() == clear){
            rysowaniePanel.clearPath();
            JOptionPane.showMessageDialog(null, "Wyczyszczono ścieżkę");
        }

        if (e.getSource() == wyjdzItem) {
            System.exit(0);
        }
    }
}
