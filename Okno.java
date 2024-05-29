import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class Okno extends JFrame implements ActionListener {

    private Rysowanie rysowaniePanel;

    JMenuBar menubar;
    JMenu labiryntMenu;
    JMenuItem txtItem;
    JMenuItem binItem;
    JMenuItem zapiszItem;
    JMenuItem wyjdzItem;
    JButton dfs;
    JButton bfs;
    JButton clear;
    JScrollPane scrollPane;

    Okno() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLayout(new BorderLayout());

        menubar = new JMenuBar();

        labiryntMenu = new JMenu("Labirynt");

        txtItem = new JMenuItem("Wczytaj z .txt");
        binItem = new JMenuItem("Wczytaj z .bin");
        zapiszItem = new JMenuItem("Zapisz rozwiązanie");
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
        labiryntMenu.add(wyjdzItem);
        menubar.add(labiryntMenu);
        this.setJMenuBar(menubar);

        rysowaniePanel = new Rysowanie();
        scrollPane = new JScrollPane(rysowaniePanel);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        dfs = new JButton();
        bfs = new JButton();
        clear = new JButton();

        dfs.addActionListener(this);
        dfs.setText("DFS");
        dfs.setFocusable(false);
        dfs.setVisible(false);

        bfs.addActionListener(this);
        bfs.setText("BFS - najkrótsza ścieżka");
        bfs.setFocusable(false);
        bfs.setVisible(false);

        clear.addActionListener(this);
        clear.setText("Wyczyść labirynt");
        clear.setFocusable(false);
        clear.setVisible(false);

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(dfs);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(bfs);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(clear);
        buttonPanel.add(Box.createHorizontalGlue());

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == txtItem) {
            JFileChooser txtchooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
            txtchooser.setFileFilter(filter);

            int odp = txtchooser.showOpenDialog(null);
            if (odp == JFileChooser.APPROVE_OPTION) {
                String maze = txtchooser.getSelectedFile().getAbsolutePath();
                rysowaniePanel.resetSolution(); // Resetowanie przed wczytaniem nowego labiryntu
                rysowaniePanel.przekazaniepliku(maze);
                dfs.setVisible(true);
                bfs.setVisible(true);
                clear.setVisible(true);
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        }
        if (e.getSource() == binItem) {
            JOptionPane.showMessageDialog(null, "Skąd ty wziąłeś plik binarny?!");
        }
        if (e.getSource() == zapiszItem) {
            JOptionPane.showMessageDialog(null, "Kiedyś będzie się dało zapisywać");
        }
        if (e.getSource() == dfs) {
            JOptionPane.showMessageDialog(null, "Sam se znajdź ścieżkę");
        }
        if (e.getSource() == bfs) {
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
