package pro.fessional.mirana.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author trydofor
 * @since 2016-10-26
 */
public class Watermark {

    public static final String WATER_MARK = "/image/watermark-mirana.png";
    public static final int MAX_SIZE = 1024;
    public static final double ROTATE = Math.toRadians(90);

    /**
     * Create a default #WATER_MARK with equal scaling (one of the length and width does not exceed #MAX_SIZE)
     * and no landscape rotation by default.
     */
    public static BufferedImage exec(String photo) throws IOException {
        return exec(photo, false);
    }

    /**
     * Create a default #WATER_MARK with equal scaling (one of the length and width does not exceed #MAX_SIZE)
     * and no landscape rotation by default.
     */
    public BufferedImage exec(InputStream photo) throws IOException {
        return exec(photo, MAX_SIZE, false);
    }

    /**
     * Create a default #WATER_MARK with equal scaling (one of the length and width does not exceed #MAX_SIZE)
     * Horizontal (landscape), will rotate the vertical board 90 degrees clockwise.
     */
    public static BufferedImage exec(String photo, boolean landscape) throws IOException {
        try (FileInputStream fis = new FileInputStream(photo)) {
            return exec(fis, MAX_SIZE, landscape);
        }
    }

    /**
     * Create a default #WATER_MARK with equal scaling (one of the length and width does not exceed maxSize)
     * Horizontal (landscape), will rotate the vertical board 90 degrees clockwise.
     */
    public static BufferedImage exec(String photo, int maxSize, boolean landscape) throws IOException {
        try (FileInputStream fis = new FileInputStream(photo)) {
            return exec(fis, maxSize, landscape);
        }
    }

    /**
     * Create a default #WATER_MARK with equal scaling (one of the length and width does not exceed #MAX_SIZE)
     * Horizontal (landscape), will rotate the vertical board 90 degrees clockwise.
     */
    public static BufferedImage exec(InputStream photo, boolean landscape) throws IOException {
        return exec(photo, MAX_SIZE, landscape);
    }

    /**
     * Create a default #WATER_MARK with equal scaling (one of the length and width does not exceed maxSize)
     * Horizontal (landscape), will rotate the vertical board 90 degrees clockwise.
     */
    public static BufferedImage exec(InputStream photo, int maxSize, boolean landscape) throws IOException {
        InputStream water = Watermark.class.getResourceAsStream(WATER_MARK);
        return exec(photo, water, maxSize, landscape);
    }

    /**
     * Create a specified watermark with equal scaling (one of the length and width does not exceed maxSize)
     * Horizontal (landscape), will rotate the vertical board 90 degrees clockwise.
     */
    public static BufferedImage exec(InputStream photo, InputStream watermark, int maxSize, boolean landscape) throws IOException {
        BufferedImage photoImg = ImageIoFix.read(photo);
        photo.close();

        int widthPhoto = photoImg.getWidth();
        int heightPhoto = photoImg.getHeight();

        // handle scale
        boolean scaled = false;
        if (widthPhoto > maxSize || heightPhoto > maxSize) {
            if (widthPhoto > heightPhoto) {
                heightPhoto = maxSize * heightPhoto / widthPhoto;
                widthPhoto = maxSize;
            }
            else {
                widthPhoto = maxSize * widthPhoto / heightPhoto;
                heightPhoto = maxSize;
            }
            scaled = true;
        }

        final BufferedImage bufferImg;
        final Graphics2D graphics;

        // handle landscape and rotate
        if (landscape && widthPhoto < heightPhoto) {
            //noinspection SuspiciousNameCombination
            bufferImg = new BufferedImage(heightPhoto, widthPhoto, photoImg.getType());
            graphics = bufferImg.createGraphics();
            double offset = (heightPhoto - widthPhoto) / 2D;
            graphics.translate(offset, -offset);
            graphics.rotate(ROTATE, widthPhoto / 2D, heightPhoto / 2D);
        }
        else {
            bufferImg = new BufferedImage(widthPhoto, heightPhoto, photoImg.getType());
            graphics = bufferImg.createGraphics();
        }

        // draw image
        if (scaled) {
            graphics.drawImage(photoImg.getScaledInstance(widthPhoto, heightPhoto, Image.SCALE_SMOOTH), 0, 0, null);
        }
        else {
            graphics.drawImage(photoImg, 0, 0, null);
        }

        // draw watermark
        BufferedImage waterImg = ImageIO.read(watermark);
        watermark.close();

        int widthWater = waterImg.getWidth();
        int heightWater = waterImg.getHeight();

        float alpha = 0.4f;
        int space = 100;
        int waterX = space;

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        while (waterX < widthPhoto) {
            int waterY = space;
            while (waterY < heightPhoto) {
                graphics.drawImage(waterImg, waterX, waterY, null);
                waterY += heightWater + space;
            }
            waterX += widthWater + space;
        }
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        graphics.dispose();

        return bufferImg;
    }
}
