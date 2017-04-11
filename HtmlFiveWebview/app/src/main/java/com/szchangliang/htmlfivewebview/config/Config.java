package com.szchangliang.htmlfivewebview.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.szchangliang.htmlfivewebview.BaseApp;
import com.szchangliang.htmlfivewebview.util.Files;

import java.io.File;

/**
 * 程序配置文件（读取和写入）
 * 1.本程序所有数据存储在{@link #DATA_PATH}以及应用程序级别的Context中的{@link Context#getCacheDir()}中,参考{@link #clearData()}
 * Created by 麦显达 on 2017/4/10.
 */
public class Config {

    //region static变量
    /**
     * 数据目录
     */

    public static String DATA_PATH = "ChangLiang";

    /**
     * 图片目录
     */
    public static String IMAGES_PATH = "images";

    /**
     * 图片缓存目录 ImageLoader
     */
    public static String IMAGE_CACHE_PATH = "imageCache";

    /**
     * 程序context
     */
    private static Context context = BaseApp.me();
    //endregion static变量

    //region final static变量
    /**
     * 日志文件目录名称
     */
    public final static String APP_LOG_PATH = "logs";

    /**
     * 临时目录名称
     */
    public final static String APP_TEMP_PATH = "temp";
    //endregion final static变量

    //region 数据目录操作
    /**
     * 获取app数据目录
     *
     * @return
     */
    public static File getDataPath() {
        return Files.getStorageDirectory(context, DATA_PATH);
    }

    /**
     * 获取程序图片目录
     *
     * @return
     */
    public static File getImagePath() {
        return Files.getStorageDirectory(context, DATA_PATH + File.separator + IMAGES_PATH);
    }

    /**
     * 获取程序图片缓存目录 不可见图片 ImageLoader
     *
     * @return
     */
    public static File getImageCachePath() {
        return Files.getStorageDirectory(context, DATA_PATH + File.separator + IMAGE_CACHE_PATH);
    }

    /**
     * 获取程序日志文件
     *
     * @return
     */
    public static File getLogPath() {
        return Files.getStorageDirectory(context, DATA_PATH + File.separator + APP_LOG_PATH);
    }

    /**
     * 获取程序临时目录
     *
     * @return
     */
    public static File getTempPath() {
        return Files.getStorageDirectory(context, DATA_PATH + File.separator + APP_TEMP_PATH);
    }

    /**
     * 获取目录的所有大小
     *
     * @return
     */
    public static long getDataSize() {
        return Files.getFolderSize(getDataPath());
    }

    /**
     * 清空所有app数据
     */
    public static void clearData() {
        File cacheFile = context.getCacheDir();
        if (cacheFile != null) {
            Files.deleteFiles(cacheFile);
        }
        Files.deleteFiles(getDataPath());
    }
    //endregion 数据目录操作

    //region SharedPreferences操作(应用程序级别的)
    /**
     * 获取Preference设置
     */
    public static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(Config.context);
    }

    /**
     * 写入配置信息，需要最后面进行 commit()
     *
     * @param key
     * @param value
     * @return
     */
    public static void putString(String key, String value) {
        SharedPreferences sharedPref = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 写入配置信息，需要最后面进行 commit()
     *
     * @param key
     * @param value
     * @return
     */
    public static void putInt(String key, int value) {
        SharedPreferences sharedPref = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 写入配置信息，需要最后面进行 commit()
     *
     * @param key
     * @param value
     * @return
     */
    public static void putLong(String key, long value) {
        SharedPreferences sharedPref = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 写入配置信息，需要最后面进行 commit()
     *
     * @param key
     * @param value
     * @return
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences sharedPref = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 读取配置信息
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key, boolean def) {
        return getSharedPreferences().getBoolean(key, def);
    }

    /**
     * 读取配置信息
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    /**
     * 读取配置信息
     *
     * @param key
     * @return
     */
    public static int getInt(String key, int def) {
        return getSharedPreferences().getInt(key, def);
    }

    /**
     * 读取配置信息
     *
     * @param key
     * @return
     */
    public static long getLong(String key, long def) {
        return getSharedPreferences().getLong(key, def);
    }

    /**
     * 本地是否保存有该值
     *
     * @param key
     * @return
     */
    public static boolean containsKey(String key) {
        return getSharedPreferences().contains(key);
    }

    /**
     * 删除配置信息，可以同时删除多个
     *
     * @param keys
     */
    public static void remove(String... keys) {
        SharedPreferences sharedPref = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPref.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.commit();
    }

    /**
     * 清除所有配置文件
     */
    public static void clearAll() {
        SharedPreferences sharedPref = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }
    //endregion SharedPreferences操作(应用程序级别的)
}
