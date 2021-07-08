package pro.fessional.mirana.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author trydofor
 * @since 2016-10-26
 */
public class Zipper {

    /**
     * zip files to byte array
     *
     * @param files source files
     * @return zipped bytes
     * @throws IOException exception
     */
    public static byte[] zip(List<File> files) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        zip(bos, files);
        return bos.toByteArray();
    }

    /**
     * zip files to target file
     *
     * @param zip   the target file, eg. a/b/c.zip
     * @param files source files
     * @throws IOException exception
     */
    public static void zip(String zip, List<File> files) throws IOException {
        File zipFile = new File(zip);
        File dir = zipFile.getParentFile();
        if (!dir.exists()) dir.mkdirs();
        zip(new FileOutputStream(zipFile), files);
    }


    /**
     * zip files to stream
     *
     * @param out   the stream
     * @param files source files
     * @throws IOException exception
     */
    public static void zip(OutputStream out, List<File> files) throws IOException {
        Map<String, InputStream> map = new HashMap<>(files.size());
        for (File file : files) {
            map.put(file.getName(), new FileInputStream(file));
        }
        zip(out, map);
    }

    /**
     * zip files to byte array
     *
     * @param files source files with key-stream
     * @return zipped bytes
     * @throws IOException exception
     */
    public static byte[] zip(Map<String, InputStream> files) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        zip(bos, files);
        return bos.toByteArray();
    }

    /**
     * zip files to target file
     *
     * @param zip   the target file, eg. a/b/c.zip
     * @param files source files
     * @throws IOException exception
     */
    public static void zip(String zip, Map<String, InputStream> files) throws IOException {
        File zipFile = new File(zip);
        File dir = zipFile.getParentFile();
        if (!dir.exists()) dir.mkdirs();
        zip(new FileOutputStream(zipFile), files);
    }

    /**
     * zip files to stream
     *
     * @param out   the stream
     * @param files source files with key-stream
     * @throws IOException exception
     */
    public static void zip(OutputStream out, Map<String, InputStream> files) throws IOException {

        try (ZipOutputStream zout = new ZipOutputStream(out)) {
            byte[] buffer = new byte[1024];
            int length;
            for (Map.Entry<String, InputStream> file : files.entrySet()) {
                zout.putNextEntry(new ZipEntry(file.getKey()));
                try (InputStream in = file.getValue()) {
                    while ((length = in.read(buffer)) > 0) {
                        zout.write(buffer, 0, length);
                    }
                }
                zout.flush();
                zout.closeEntry();
            }
            zout.flush();
        }
    }

    /**
     * unzip zip file to some path
     *
     * @param zip  zip file
     * @param path target path
     * @throws IOException exception
     */
    public static void unzip(String zip, String path) throws IOException {
        unzip(new FileInputStream(zip), path);
    }

    /**
     * unzip zip file to some path
     *
     * @param zip  zip file
     * @param path target path
     * @throws IOException exception
     */
    public static void unzip(InputStream zip, String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();

        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(zip)) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                String fileName = ze.getName();
                File newFile = new File(dir, fileName);
                if (ze.isDirectory()) {
                    newFile.mkdirs();
                }
                else {
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zis.closeEntry();
            }
            zip.close();
        }
    }

    /**
     * unzip zip file to map
     *
     * @param zip zip file
     * @return mapped result
     * @throws IOException exception
     */
    public static Map<String, byte[]> unzip(InputStream zip) throws IOException {
        Map<String, byte[]> entries = new HashMap<>();
        byte[] buffer = new byte[1024];

        try (ZipInputStream zis = new ZipInputStream(zip)) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) continue;

                String fileName = ze.getName();
                ByteArrayOutputStream fos = new ByteArrayOutputStream();
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zis.closeEntry();
                entries.put(fileName, fos.toByteArray());
            }
            zis.closeEntry();
        }
        zip.close();
        return entries;
    }

    /**
     * flat all zip file (unzip recursively, zip in zip)
     *
     * @param zip              zip file
     * @param lowercaseExtName "zip","jar","war"
     * @return unzip files
     * @throws IOException exception
     */
    public static Map<String, byte[]> unzip(InputStream zip, String... lowercaseExtName) throws IOException {
        Map<String, byte[]> unzip = unzip(zip);
        Map<String, byte[]> result = new HashMap<>();
        for (Iterator<Map.Entry<String, byte[]>> iter = unzip.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<String, byte[]> entry = iter.next();
            String lcn = entry.getKey().toLowerCase();
            boolean deep = false;
            for (String s : lowercaseExtName) {
                if (lcn.endsWith(s)) {
                    deep = true;
                    break;
                }
            }
            if (deep) {
                iter.remove();
                Map<String, byte[]> sub = unzip(new ByteArrayInputStream(entry.getValue()));
                result.putAll(sub);
            }
            else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * zip helper
     *
     * @return helper
     */
    public static Z build() {
        return new Z();
    }
    //

    public static class Z {
        Map<String, InputStream> files = new HashMap<>();

        public Z add(String path) throws IOException {
            if (path == null) return this;
            return add(new File(path));
        }

        public Z add(File file) throws IOException {
            if (file == null) return this;
            return add(file.getName(), file);
        }

        public Z add(String name, String file) throws IOException {
            if (name == null || file == null) return this;
            return add(name, new File(file));
        }

        public Z add(String name, File file) throws IOException {
            if (name == null || file == null) return this;
            return add(file.getName(), new FileInputStream(file));
        }

        public Z add(String name, InputStream inputStream) {
            if (name == null || inputStream == null) return this;
            files.put(name, inputStream);
            return this;
        }

        public Map<String, InputStream> files() {
            return this.files;
        }

        public ByteArrayInputStream zip() throws IOException {
            return new ByteArrayInputStream(Zipper.zip(files));
        }

        public void zip(OutputStream out) throws IOException {
            Zipper.zip(out, files);
        }

        public void zip(String zip) throws IOException {
            Zipper.zip(zip, files);
        }
    }
}
