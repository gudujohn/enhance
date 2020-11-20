package org.enhance.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.zip.ZipEntry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GzipUtil {

    private static final int BUFFER_SIZE = 8192;

    public static File unGzip(File file) {
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        if (!getExtension(file.getName()).equalsIgnoreCase("gz")) {
            log.error("File name must have extension of \".gz\"");
            return null;
        }
        File outputfile = new File(creatUnGzFileName(file));
        try (GZIPInputStream in = new GZIPInputStream(new FileInputStream(file)); FileOutputStream out = new FileOutputStream(outputfile)) {
            if (outputfile.exists()) {
                log.info("原文件存在，正在删除");
                boolean delete = outputfile.delete();
                log.info("原文件已经删除");
            }
            if (!outputfile.exists()) {
                final boolean newFile = outputfile.createNewFile();
            }
            log.info("开始解压文件");
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            log.info("解压成功");
        } catch (IOException e) {
            log.error("解压文件" + file.getAbsolutePath() + "异常");
            log.error(e.getMessage(), e);
            return null;
        }
        return outputfile;
    }

    public static File gzip(List<File> files, String fileName) {
        Assertion.notEmpty(fileName, "压缩文件名必须设置。");
        String gzipFileFullName = null;
        for (File file : files) {
            if (!file.exists() || file.isDirectory()) {
                return null;
            }
            if (Detect.isEmpty(gzipFileFullName)) {
                gzipFileFullName = file.getAbsolutePath();
            }
        }
        assert gzipFileFullName != null;
        gzipFileFullName = gzipFileFullName.substring(0, gzipFileFullName.lastIndexOf(File.separator)) + File.separator + fileName;
        File gzipFile = new File(gzipFileFullName);
        if (gzipFile.exists()) {
            boolean delete = gzipFile.delete();
        }
        // 压缩文件
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(gzipFile))) {
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int len;
                    while (0 < (len = fis.read(buffer))) {
                        zos.write(buffer, 0, len);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return null;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return gzipFile;
    }

    public static File gzip(File file, String fileName) {
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        String gzipFileFullName = file.getAbsolutePath();
        if (Detect.notEmpty(fileName)) {
            gzipFileFullName = gzipFileFullName.substring(0, gzipFileFullName.lastIndexOf(File.separator)) + File.separator + fileName;
        } else {
            gzipFileFullName = file.getAbsolutePath() + ".gz";
        }
        File gzipFile = new File(gzipFileFullName);
        if (gzipFile.exists()) {
            boolean delete = gzipFile.delete();
        }

        try (FileInputStream fis = new FileInputStream(file); GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(gzipFile))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while (0 < (len = fis.read(buffer))) {
                gos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

        return gzipFile;
    }

    public static boolean unTarGz(File file, String outputDir) {
        TarInputStream tarIn = null;
        boolean result = true;
        try {
            tarIn = new TarInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(file))), 1024 * 2);
            createDirectory(outputDir, null);// 创建输出目录
            TarEntry entry;
            while ((entry = tarIn.getNextEntry()) != null) {
                if (entry.isDirectory()) {// 是目录
                    entry.getName();
                    createDirectory(outputDir, entry.getName());// 创建空目录
                } else {// 是文件
                    File tmpFile = new File(outputDir + "/" + entry.getName());
                    createDirectory(tmpFile.getParent() + "/", null);// 创建输出目录
                    try (OutputStream out = new FileOutputStream(tmpFile)) {
                        int length;
                        byte[] b = new byte[2048];
                        while ((length = tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            log.error("解压tar.gz文件出现异常" + e.getMessage(), e);
            result = false;
        } finally {
            try {
                if (tarIn != null) {
                    tarIn.close();
                }
            } catch (IOException e) {
                log.error("关闭tarFile出现异常" + e.getMessage(), e);
                result = false;
            }
        }
        return result;
    }

    private static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || subDir.trim().equals(""))) {// 子目录不为空
            file = new File(outputDir + "/" + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
            }
            file.mkdirs();
        }
    }

    /**
     * 默认，将解压缩文件解压到压缩文件所在目录下
     */
    private static String creatUnGzFileName(File file) {
        return file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".gz"));
    }

    /**
     * Used to extract and return the extension of a given file.
     *
     * @param f Incoming file to get the extension of
     * @return <code>String</code> representing the extension of the incoming file.
     */
    private static String getExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');

        if (i > 0 && i < f.length() - 1) {
            ext = f.substring(i + 1);
        }
        return ext;
    }
}