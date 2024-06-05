import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class NewPK {
    private Rysowanie rysowaniePanel;
    private boolean selectingStart;
    private boolean selectingEnd;

    public NewPK(Rysowanie rysowaniePanel) {
        this.rysowaniePanel = rysowaniePanel;
        this.selectingStart = false;
        this.selectingEnd = false;
    }

    public void startSelection() {
        if (rysowaniePanel == null || rysowaniePanel.getMaze() == null) {
            JOptionPane.showMessageDialog(null, "Błąd: Panel rysowania lub labirynt nie są zainicjalizowane.");
            return;
        }

        int komorkax = rysowaniePanel.getKomorkax();
        int komorkay = rysowaniePanel.getKomorkay();
        selectingStart = true;
        selectingEnd = false;
        JOptionPane.showMessageDialog(rysowaniePanel, "Kliknij, aby wybrać nowy punkt startowy.");
        rysowaniePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int x = point.y / komorkay / 3; // komorkay * 3 ze względu na skalowanie
                int y = point.x / komorkax / 3; // komorkax * 3 ze względu na skalowanie

                // Sprawdź czy punkty mieszczą się w granicach labiryntu
                if (x < 0 || x >= rysowaniePanel.getMaze().size() || y < 0 || y >= rysowaniePanel.getMaze().get(0).size()) {
                    JOptionPane.showMessageDialog(rysowaniePanel, "Kliknięcie znajduje się poza obszarem labiryntu.");
                    return;
                }

                if (selectingStart) {
                    if (rysowaniePanel.getMaze().get(x).get(y) == 'X') {
                        JOptionPane.showMessageDialog(rysowaniePanel, "Nie można wybrać ściany jako punktu startowego.");
                        return;
                    }
                    clearOldPoints(); // Usuń stare punkty startowe i końcowe
                    rysowaniePanel.getMaze().get(x).set(y, 'P');
                    selectingStart = false;
                    selectingEnd = true;
                    JOptionPane.showMessageDialog(rysowaniePanel, "Kliknij, aby wybrać nowy punkt końcowy.");
                } else if (selectingEnd) {
                    if (rysowaniePanel.getMaze().get(x).get(y) == 'X') {
                        JOptionPane.showMessageDialog(rysowaniePanel, "Nie można wybrać ściany jako punktu końcowego.");
                        return;
                    }
                    rysowaniePanel.getMaze().get(x).set(y, 'K');
                    selectingEnd = false;
                    rysowaniePanel.removeMouseListener(this);
                    JOptionPane.showMessageDialog(rysowaniePanel, "Nowe punkty zostały wybrane.");
                    rysowaniePanel.repaint();
                }
            }
        });
    }


    private void clearOldPoints() {
        for (int i = 0; i < rysowaniePanel.getMaze().size(); i++) {
            for (int j = 0; j < rysowaniePanel.getMaze().get(i).size(); j++) {
                if (rysowaniePanel.getMaze().get(i).get(j) == 'P' || rysowaniePanel.getMaze().get(i).get(j) == 'K') {
                    rysowaniePanel.getMaze().get(i).set(j, 'X');
                }
            }
        }
        rysowaniePanel.repaint();
    }
}
