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

    public static final String WATER_MARK = "/image/watermark-clearance.png";
    public static final int MAX_SIZE = 1024;
    public static final double ROTATE = Math.toRadians(90);

    /**
     * 生成默认水印，并进行等比例缩放(长宽之一不超过#MAX_SIZE)，默认不做横屏旋转。
     *
     * @param photo 原始图片
     * @return 图片
     * @throws IOException if io exception
     */
    public static BufferedImage exec(String photo) throws IOException {
        return exec(photo, false);
    }

    /**
     * 生成默认水印，并进行等比例缩放(长宽之一不超过#MAX_SIZE)。
     * 横版（landscape），会把竖板顺时针90度旋转。
     *
     * @param photo     原始图片
     * @param landscape 是否横屏
     * @return 图片
     * @throws IOException if io exception
     */
    public static BufferedImage exec(String photo, boolean landscape) throws IOException {
        try (FileInputStream fis = new FileInputStream(photo)) {
            return exec(fis, MAX_SIZE, landscape);
        }
    }

    /**
     * 生成默认水印，并进行等比例缩放(长宽之一不超过最大值)。
     * 横版（landscape），会把竖板顺时针90度旋转。
     *
     * @param photo     原始图片
     * @param maxSize   最大尺寸
     * @param landscape 是否横屏
     * @return 图片
     * @throws IOException if io exception
     */
    public static BufferedImage exec(String photo, int maxSize, boolean landscape) throws IOException {
        try (FileInputStream fis = new FileInputStream(photo)) {
            return exec(fis, maxSize, landscape);
        }
    }

    /**
     * 生成默认水印，并进行等比例缩放(长宽之一不超过#MAX_SIZE)。
     * 横版（landscape），会把竖板顺时针90度旋转。
     *
     * @param photo     原始图片
     * @param landscape 是否横屏
     * @return 图片
     * @throws IOException if io exception
     */
    public static BufferedImage exec(InputStream photo, boolean landscape) throws IOException {
        return exec(photo, MAX_SIZE, landscape);
    }

    /**
     * 生成默认水印，并进行等比例缩放(长宽之一不超过最大值)。
     * 横版（landscape），会把竖板顺时针90度旋转。
     *
     * @param photo     原始图片
     * @param maxSize   最大尺寸
     * @param landscape 是否横屏
     * @return 图片
     * @throws IOException if io exception
     */
    public static BufferedImage exec(InputStream photo, int maxSize, boolean landscape) throws IOException {
        InputStream water = Watermark.class.getResourceAsStream(WATER_MARK);
        return exec(photo, water, maxSize, landscape);
    }

    /**
     * 生成水印，并进行等比例缩放(长宽之一不超过最大值)。
     * 横版（landscape），会把竖板顺时针90度旋转。
     *
     * @param photo     原始图片
     * @param watermark 水印图片
     * @param maxSize   最大尺寸
     * @param landscape 是否横屏
     * @return 处理后的图片
     * @throws IOException if io exception
     */
    public static BufferedImage exec(InputStream photo, InputStream watermark, int maxSize, boolean landscape) throws IOException {
        BufferedImage photoImg = ImageIoFix.read(photo);
        photo.close();

        int widthPhoto = photoImg.getWidth();
        int heightPhoto = photoImg.getHeight();

        // 缩放判断
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

        // 是否横版，进行旋转
        if (landscape && widthPhoto < heightPhoto) {
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

        // 绘制图片
        if (scaled) {
            graphics.drawImage(photoImg.getScaledInstance(widthPhoto, heightPhoto, Image.SCALE_SMOOTH), 0, 0, null);
        }
        else {
            graphics.drawImage(photoImg, 0, 0, null);
        }

        // 生成水印
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

        // 完成
        return bufferImg;
    }

    /**
     * 生成默认水印，并进行等比例缩放(长宽之一不超过#MAX_SIZE)，默认不做横屏旋转。
     *
     * @param photo 原始图片
     * @return 图片
     * @throws IOException if io exception
     */
    public BufferedImage exec(InputStream photo) throws IOException {
        return exec(photo, MAX_SIZE, false);
    }
}
