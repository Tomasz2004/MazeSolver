import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Drawer");
            MazeDrawer mazeDrawer = new MazeDrawer();
            frame.setLayout(new BorderLayout());
            frame.add(mazeDrawer, BorderLayout.CENTER);

            JButton loadButton = new JButton("Labirynt");
            loadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        mazeDrawer.loadMaze(selectedFile.getPath());
                        frame.pack();
                    }
                }
            });

            JPanel panel = new JPanel();
            panel.add(loadButton);
            frame.add(panel, BorderLayout.SOUTH);

            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
