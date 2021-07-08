package pro.fessional.mirana.img;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * @author trydofor
 * @since 2016-11-01
 */
public class StreamJpg {

    public static final String FORMAT = "JPG";
    public static final float QUALITY = 0.85f;

    /**
     * 按85%压缩jpg
     *
     * @param jpg   输出图片 jpg
     * @param image 输入图片
     * @throws IOException if io exception
     */
    public static void writeJpg(OutputStream jpg, BufferedImage image) throws IOException {
        writeJpg(jpg, image, QUALITY);
    }

    /**
     * 压缩jpg
     *
     * @param jpg     输出图片 jpg
     * @param image   输入图片
     * @param quality 压缩质量
     * @throws IOException if io exception
     */
    public static void writeJpg(OutputStream jpg, BufferedImage image, float quality) throws IOException {
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
        if (iter.hasNext()) {
            ImageWriter writer = iter.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(quality);
            ImageOutputStream ios = new MemoryCacheImageOutputStream(jpg);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), iwp);
            writer.dispose();
        }
        else {
            ImageIO.write(image, FORMAT, jpg); // default 70%
        }
    }

    /**
     * 转换成Jpg数组
     *
     * @param image 图形
     * @return 字节数组
     * @throws IOException if io exception
     */
    public static byte[] bytes(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            writeJpg(baos, image);
            baos.flush();
            return baos.toByteArray();
        }
    }

    /**
     * 保持到Jpg文件
     *
     * @param image  图片
     * @param outjpg 输出文件
     * @throws IOException if io exception
     */
    public static void file(BufferedImage image, File outjpg) throws IOException {

        File dir = outjpg.getParentFile();
        if (!dir.exists())
            dir.mkdirs();

        try (FileOutputStream os = new FileOutputStream(outjpg)) {
            writeJpg(os, image);
        }
    }

    /**
     * 双向输出，文件和InputStream
     *
     * @param image  图片
     * @param outjpg jpg文件
     * @return inputStream
     * @throws IOException if io exception
     */
    public static InputStream tee(BufferedImage image, File outjpg) throws IOException {
        File dir = outjpg.getParentFile();
        if (!dir.exists())
            dir.mkdirs();

        return tee(image, new FileOutputStream(outjpg));
    }

    /**
     * 双向输出，文件和InputStream
     *
     * @param image 图片
     * @param out   jpg流
     * @return inputStream
     * @throws IOException if io exception
     */
    public static InputStream tee(BufferedImage image, OutputStream out) throws IOException {
        byte[] bytes = bytes(image);
        out.write(bytes);
        out.flush();
        out.close();

        return new ByteArrayInputStream(bytes);
    }
}
