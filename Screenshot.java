import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Screenshot {

    public static void saveComponentAsPNG(Component component, String filename) throws IOException {
        Dimension size = component.getSize();
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        component.paint(g2d);   //rysowanie komponentu - labiryntu
        g2d.dispose();      //zwolnienie zasobów wykorzystywanych przez Graphics2D
        File file = new File(filename);
        ImageIO.write(image, "png", file);
    }
}
