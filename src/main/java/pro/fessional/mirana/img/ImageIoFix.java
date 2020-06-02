package pro.fessional.mirana.img;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author trydofor
 * @since 2017-07-19
 */
public class ImageIoFix {

    /**
     * https://stackoverflow.com/questions/4386446/problem-using-imageio-write-jpg-file
     */
    public static BufferedImage read(InputStream photo) throws IOException {
        BufferedImage image = ImageIO.read(photo);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                newImage.setRGB(x, y, image.getRGB(x, y));
            }
        }
        return newImage; // fixed for jpeg
    }
}
