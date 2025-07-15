package game.graphics;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SpriteLoader {

    public static BufferedImage loadImage(String path) {
        try (InputStream is = SpriteLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("[Error] No se encontró la imagen en: " + path);
                return null;
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Font loadFont(String path, float size) {
        try (InputStream is = SpriteLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("[Error] No se encontró la fuente en: " + path);
                return new Font("Arial", Font.PLAIN, (int) size);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

}
