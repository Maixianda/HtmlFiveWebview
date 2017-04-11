package com.szchangliang.htmlfivewebview.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.szchangliang.htmlfivewebview.config.Config;
import com.szchangliang.htmlfivewebview.config.Globals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.DecimalFormat;


/**
 * 文件处理工具
 * <p/>
 * Created by 麦显达 on 2017/4/10.
 */
public class Files {

    public static final String ANDROID_ASSET = "file:///android_asset/";
    public static final String ANDROID_RES = "file:///android_res/";

    private Files() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    /**
     * 获取asset文件名，应用于WebView/Glide
     *
     * @param name
     * @return
     */
    public static String getAssetFile(String name) {
        return ANDROID_ASSET + name;
    }

    /**
     * 获取res文件名，应用于WebView/Glide
     *
     * @param name
     * @return
     */
    public static String getResFile(String name) {
        return ANDROID_RES + name;
    }

    /**
     * 读取文件数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] readBytes(File file) throws IOException {
        // 文件是否存在
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        try {
            in.read(bytes);
        } finally {
            in.close();
        }
        return bytes;
    }

    /**
     * 读文件内容
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readString(File file) throws IOException {
        return readString(file, Charset.defaultCharset());
    }

    /**
     * 读文件内容
     *
     * @param file
     * @param charset
     * @return
     * @throws IOException
     */
    public static String readString(File file, Charset charset) throws IOException {
        return new String(readBytes(file), charset);
    }

    /**
     * 写入数据
     *
     * @param file
     * @param bytes
     * @throws IOException
     */
    public static void write(File file, byte[] bytes) throws IOException {
        write(file, bytes, false);
    }

    /**
     * 写入数据
     *
     * @param file
     * @param bytes
     * @throws IOException
     */
    public static void write(File file, byte[] bytes, boolean append) throws IOException {
        FileOutputStream out = new FileOutputStream(file, append);
        try {
            out.write(bytes);
        } finally {
            out.close();
        }
    }

    /**
     * 写入文本内容
     *
     * @param file
     * @param content
     * @throws IOException
     */
    public static void write(File file, String content) throws IOException {
        write(file, content, false);
    }

    /**
     * 写入文本内容
     *
     * @param file
     * @param content
     * @param append
     * @throws IOException
     */
    public static void write(File file, String content, boolean append) throws IOException {
        write(file, content.getBytes(), append);
    }

    /**
     * 写入文件流
     *
     * @param file
     * @param in
     * @throws IOException
     */
    public static void write(File file, InputStream in) throws IOException {
        write(file, in, false);
    }

    /**
     * 写入文件流
     *
     * @param file
     * @param in
     * @param append
     * @throws IOException
     */
    public static void write(File file, InputStream in, boolean append) throws IOException {
        OutputStream out = new FileOutputStream(file, append);
        try {
            byte data[] = new byte[1024];
            int length;
            while ((length = in.read(data)) != -1) {
                out.write(data, 0, length);
            }
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 写入序列化文件
     *
     * @param file
     * @param serializable
     * @throws IOException
     */
    public static void write(File file, Serializable serializable) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fs = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fs);
        try {
            os.writeObject(serializable);
        } finally {
            os.close();
            fs.close();
        }
    }

    /**
     * 读取序列化文件
     *
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Serializable readSerializable(File file) throws IOException, ClassNotFoundException {
        FileInputStream fs = new FileInputStream(file);
        ObjectInputStream os = new ObjectInputStream(fs);
        try {
            return (Serializable) os.readObject();
        } finally {
            os.close();
            fs.close();
        }
    }

    /**
     * 移动文件
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public static void move(File from, File to) throws IOException {
        boolean rename = from.renameTo(to);
        if (!rename) {
            copy(from, to);
            from.delete();
        }
    }

    /**
     * 复制文件
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public static void copy(File from, File to) throws IOException {
        InputStream in = new FileInputStream(from);
        try {
            write(to, in);
        } finally {
            in.close();
        }
    }

    /**
     * 复制asset文件
     * # File to = new File(Config.getDataPath(), "test");
     * # copyAssetFile(ctx, "test", to);
     *
     * @param context
     * @param name
     * @param to
     * @throws IOException
     */
    public static void copyAssetFile(Context context, String name, File to) throws IOException {
        InputStream in = context.getAssets().open(name);
        try {
            write(to, in, false);
        } finally {
            in.close();
        }
    }

    /**
     * 检查文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean checkFileExists(String filePath) {
        if (Strings.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * 检查目录是否存在
     *
     * @param path
     * @return
     */
    public static boolean checkFolderExists(String path) {
        if (Strings.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        return (file.exists() && file.isDirectory());
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static boolean makeDirs(String path) {
        if (checkFolderExists(path)) {
            return true;
        }
        return new File(path).mkdirs();
    }

    /**
     * 删除目录中的所有文件
     *
     * @param file
     */
    public static boolean deleteFiles(File file) {
        // check
        if (file == null) {
            return false;
        } else if (!file.exists()) {
            return true;
        }
        // delete all
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (child.isFile()) {
                    child.delete();
                } else if (child.isDirectory()) {
                    deleteFiles(child);
                }
            }
        }
        return file.delete();
    }

    /**
     * 获取目录大小
     *
     * @param dir
     * @return
     */
    public static long getFolderSize(File dir) {
        // check
        if (dir == null || !dir.exists()) {
            return 0L;
        }
        // is folder
        if (dir.isDirectory()) {
            long dirSize = 0L;
            File[] files = dir.listFiles();
            if (files == null) {
                return dirSize;
            }
            for (File file : files) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += getFolderSize(file); // 递归调用继续统计
                }
            }
            return dirSize;
        }
        // is file
        return dir.length();
    }

    /**
     * Formats given size in bytes to KB, MB, GB or whatever. This will work up to 1000 TB
     *
     * @param size
     * @return
     */
    public static String formatSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * Gets the extension of a file.
     *
     * @param file
     * @return
     */
    public static String getExtension(File file) {
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /**
     * 检查是否挂载SDCard
     *
     * @return
     */
    public static boolean isSdCardMounted() {
        String status = Environment.getExternalStorageState();
        return Strings.isEquals(status, Environment.MEDIA_MOUNTED);
    }

    /**
     * 取得空闲SD卡空间大小
     *
     * @return bit
     */
    public static long getSDCardAvailableSize() {
        if (!isSdCardMounted()) {
            return 0L;
        }
        // 取得sdcard文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        // 获取block的size
        long blockSize;
        if (Build.VERSION.SDK_INT < 18) {
            blockSize = stat.getBlockSize();
        } else {
            blockSize = stat.getBlockSizeLong();
        }
        // 空闲的Block的数量
        long availableBlocks;
        if (Build.VERSION.SDK_INT < 18) {
            availableBlocks = stat.getAvailableBlocks();
        } else {
            availableBlocks = stat.getAvailableBlocksLong();
        }
        // 返回bit大小值
        return availableBlocks * blockSize;
    }

    /**
     * 获取存储目录
     * # SDCard ganguo
     *
     * @param context
     * @param dirName
     * @return
     */
    public static File getStorageDirectory(Context context, String dirName) {
        File file;
        if (isSdCardMounted()) {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + dirName);
        } else {
            // media is removed, unmounted etc
            // /data/data/<package-name>/cache/dir/photograph.jpeg
            file = new File(context.getCacheDir() + File.separator + dirName);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    public static String getAssetsToPath(Context context, String filename) {
        File f = new File(Config.getImagePath() + filename);
        if (!f.exists()) {
            f.delete();
        }
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            fos = new FileOutputStream(f);
            fos.write(buffer);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f.getAbsolutePath();
    }

}
