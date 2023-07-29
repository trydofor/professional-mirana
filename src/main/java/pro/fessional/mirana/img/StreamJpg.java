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
import java.nio.file.Files;
import java.util.Iterator;

/**
 * @author trydofor
 * @since 2016-11-01
 */
public class StreamJpg {

    public static final String FORMAT = "JPG";
    public static final float QUALITY = 0.85f;

    /**
     * write jpg at 85% quality
     */
    public static void writeJpg(OutputStream jpg, BufferedImage image) throws IOException {
        writeJpg(jpg, image, QUALITY);
    }

    /**
     * write jpg at quality
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
     * image to byte array
     */
    public static byte[] bytes(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            writeJpg(baos, image);
            baos.flush();
            return baos.toByteArray();
        }
    }

    /**
     * save image to file
     */
    public static void file(BufferedImage image, File outjpg) throws IOException {

        File dir = outjpg.getParentFile();
        if (!dir.exists())
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();

        try (FileOutputStream os = new FileOutputStream(outjpg)) {
            writeJpg(os, image);
        }
    }

    /**
     * Bidirectional output to file and InputStream
     */
    public static InputStream tee(BufferedImage image, File jpgFile) throws IOException {
        File dir = jpgFile.getParentFile();
        if (!dir.exists())
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();

        return tee(image, Files.newOutputStream(jpgFile.toPath()));
    }

    /**
     * Bidirectional output to OutputStream and InputStream
     */
    public static InputStream tee(BufferedImage image, OutputStream out) throws IOException {
        byte[] bytes = bytes(image);
        out.write(bytes);
        out.flush();
        out.close();

        return new ByteArrayInputStream(bytes);
    }
}
