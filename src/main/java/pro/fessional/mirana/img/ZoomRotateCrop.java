package pro.fessional.mirana.img;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author trydofor
 * @link `http://fengyuanchen.github.io/cropper/#overview`
 * @since 2016-11-01
 */
public class ZoomRotateCrop {

    /**
     * 对图片进行，翻转，旋转，截取，缩放
     *
     * @param photo 图片
     * @param para  参数
     * @return 处理后的图片
     * @throws IOException if io exception
     */
    public static BufferedImage exec(InputStream photo, Para para) throws IOException {

        if (photo == null)
            throw new NullPointerException("photo is null");
        if (para == null)
            throw new NullPointerException("para is null");

        // 获得原始图片及尺寸
        BufferedImage photoImg = ImageIoFix.read(photo);

        // 翻转处理
        if (para.flipX && !para.flipY) {
            photoImg = flip(photoImg, true);
        }
        if (!para.flipX && para.flipY) {
            photoImg = flip(photoImg, false);
        }

        // 左旋转处理
        if (para.rotate % 360 != 0) {
            photoImg = rotate(photoImg, para.rotate);
        }

        // 截取和缩放
        photoImg = cropZoom(photoImg, para);

        return photoImg;
    }

    /**
     * 对图片截取和缩放
     *
     * @param image 图片
     * @param para  参数
     * @return 处理后的图片
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
     * 翻转，要么X轴（上下），要么Y轴（左右）
     *
     * @param image   图片
     * @param isFlipX 要么X轴（上下）
     * @return 处理后的图片
     */
    public static BufferedImage flip(BufferedImage image, boolean isFlipX) {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, image.getType());
        Graphics2D g = result.createGraphics();

        if (isFlipX) {
            g.drawImage(image, 0, 0, width, height, 0, height, width, 0, null);
        } else {
            g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        }

        g.dispose();
        return result;
    }

    /**
     * 顺时针旋转一个角度（360）
     *
     * @param image  图片
     * @param degree 角度
     * @return 处理后的图片
     */
    public static BufferedImage rotate(BufferedImage image, int degree) {

        // 角度转换
        degree = degree % 360;
        if (degree < 0) {
            degree = 360 + degree;
        }

        double angle = Math.toRadians(degree);
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));

        // 计算新区域
        int oldW = image.getWidth();
        int oldH = image.getHeight();
        int newW = (int) Math.floor(oldW * cos + oldH * sin);
        int newH = (int) Math.floor(oldH * cos + oldW * sin);

        // 设定定点，旋转
        BufferedImage result = new BufferedImage(newW, newH, image.getType());
        Graphics2D g = result.createGraphics();
        g.translate((newW - oldW) / 2, (newH - oldH) / 2);
        g.rotate(angle, oldW / 2, oldH / 2);
        g.drawRenderedImage(image, null);
        g.dispose();

        return result;
    }

    /**
     * （1）原图先翻转，旋转 (flipX,flipY,rotate)
     * （2）截取crop区域 (cropX,cropY,cropW,cropH)
     * （3）适配到view(viewW * viewH)
     */
    public static class Para {

        /**
         * 原图是否（沿X轴）上下翻转
         */
        public boolean flipX;

        /**
         * 原图是否（沿Y轴）左右翻转
         */
        public boolean flipY;

        /**
         * 原图顺时针旋转角度（deg 360）
         */
        public int rotate;

        /**
         * 截取左上角X坐标（左0，右+）
         */
        public int cropX;

        /**
         * 截取左上角Y坐标（上0，下+）
         */
        public int cropY;

        /**
         * 截取的宽度（X轴，小于等于0表示不处理）
         */
        public int cropW;

        /**
         * 截取的高度（Y轴，小于等于0表示不处理）
         */
        public int cropH;

        /**
         * 视窗的宽度（X轴）
         */
        public int viewW;

        /**
         * 视窗的高度（Y轴）
         */
        public int viewH;
    }
}
