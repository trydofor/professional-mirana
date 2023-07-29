package pro.fessional.mirana.img;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * see <a href="http://fengyuanchen.github.io/cropper/#overview">jquery cropper</a>
 * or <a href="https://github.com/trydofor/cropper">backup repo</a>
 *
 * @author trydofor
 * @since 2016-11-01
 */
public class ZoomRotateCrop {

    /**
     * Flip, rotate, capture, and zoom images.
     */
    public static BufferedImage exec(InputStream photo, Para para) throws IOException {

        if (photo == null) throw new NullPointerException("photo is null");
        if (para == null) throw new NullPointerException("para is null");

        // get the original size
        BufferedImage photoImg = ImageIoFix.read(photo);

        // handle flip
        if (para.flipX && !para.flipY) {
            photoImg = flip(photoImg, true);
        }
        if (!para.flipX && para.flipY) {
            photoImg = flip(photoImg, false);
        }

        // handle rotate
        if (para.rotate % 360 != 0) {
            photoImg = rotate(photoImg, para.rotate);
        }

        // handle crop and zoom
        photoImg = cropZoom(photoImg, para);

        return photoImg;
    }

    /**
     * crop and zoom
     */
    public static BufferedImage cropZoom(BufferedImage image, Para para) {
        BufferedImage result = new BufferedImage(para.viewW, para.viewH, image.getType());
        Graphics2D g = result.createGraphics();

        BufferedImage subImg = image;
        if (para.cropW > 0 && para.cropH > 0) {
            subImg = image.getSubimage(para.cropX, para.cropY, para.cropW, para.cropH);
        }
        g.drawImage(subImg.getScaledInstance(para.viewW, para.viewH, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();
        return result;
    }

    /**
     * Flip, either X-axis (up and down) or Y-axis (left and right)
     */
    public static BufferedImage flip(BufferedImage image, boolean isFlipX) {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, image.getType());
        Graphics2D g = result.createGraphics();

        if (isFlipX) {
            g.drawImage(image, 0, 0, width, height, 0, height, width, 0, null);
        }
        else {
            g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        }

        g.dispose();
        return result;
    }

    /**
     * Rotate one angle clockwise (360)
     */
    public static BufferedImage rotate(BufferedImage image, int degree) {

        // convert to degree
        degree = degree % 360;
        if (degree < 0) {
            degree = 360 + degree;
        }

        double angle = Math.toRadians(degree);
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));

        // calc new range
        int oldW = image.getWidth();
        int oldH = image.getHeight();
        int newW = (int) Math.floor(oldW * cos + oldH * sin);
        int newH = (int) Math.floor(oldH * cos + oldW * sin);

        // set the point and rotate
        BufferedImage result = new BufferedImage(newW, newH, image.getType());
        Graphics2D g = result.createGraphics();
        g.translate((newW - oldW) / 2, (newH - oldH) / 2);
        g.rotate(angle, oldW / 2D, oldH / 2D);
        g.drawRenderedImage(image, null);
        g.dispose();

        return result;
    }

    /**
     * <pre>
     * (1) flip first, then rotate (flipX,flipY,rotate)
     * (2) crop range (cropX,cropY,cropW,cropH)
     * (3) adapt to the view (viewW * viewH)
     * </pre>
     */
    public static class Para {

        /**
         * flip X-axis (up and down)
         */
        public boolean flipX;

        /**
         * flip Y-axis (left and right)
         */
        public boolean flipY;

        /**
         * Rotate one angle clockwise (deg 360)
         */
        public int rotate;

        /**
         * crop from the left-top X axis (left:0, right:+)
         */
        public int cropX;

        /**
         * crop from the left-top Y axis (top:0, down:+)
         */
        public int cropY;

        /**
         * crop width, X-axis, less than or equal to 0 means not processed.
         */
        public int cropW;

        /**
         * crop height, Y-axis, less than or equal to 0 means not processed.
         */
        public int cropH;

        /**
         * view width X-axis
         */
        public int viewW;

        /**
         * view height, Y-axis
         */
        public int viewH;
    }
}
