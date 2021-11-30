package ohos.samples.camera;

import ohos.app.Context;
import ohos.data.usage.DataUsage;
import ohos.data.usage.MountState;
import ohos.media.image.ImagePacker;
import ohos.media.image.PixelMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * File Util
 */
public class FileUtil {
    /**
     * CacheFile Path
     */
    public static String sCacheFilePath = "";

    private static String DST_FOLDER_NAME = "JCamera";
    private static File sParentPath;
    private static String sStoragePath = "";

    /**
     * file 初始化
     *
     * @param context Context
     * @return path
     */
    public static String initPath(Context context) {
        if (context == null) {
            throw new RuntimeException("file init,but context is null");
        }
        if (sParentPath == null) {
            sParentPath = getCachePathFile(context);
        }
        if ("".equals(sStoragePath)) {
            try {
                sStoragePath = sParentPath.getCanonicalPath() + File.separator + DST_FOLDER_NAME;
            } catch (IOException e) {
                LogUtil.error(LogUtil.DEFAULT_TAG, "initPath IOException:" + e.getMessage());
            }
            File file = new File(sStoragePath);
            if (!file.exists()) {
                boolean isSuccess = file.mkdir();
                if (!isSuccess) {
                    LogUtil.error(LogUtil.DEFAULT_TAG, "file create fail");
                }
            }
        }
        return sStoragePath;
    }

    /**
     * get Cache Path File
     *
     * @param context Context
     * @return File
     */
    public static File getCachePathFile(Context context) {
        if (context == null) {
            return null;
        }
        sCacheFilePath = context.getCacheDir().getPath();
        return context.getCacheDir();
    }

    /**
     * save Bitmap
     *
     * @param dir      String
     * @param pixelMap PixelMap
     * @param context  Context
     * @return String
     */
    public static String saveBitmap(String dir, PixelMap pixelMap, Context context) {
        DST_FOLDER_NAME = dir;
        String path = initPath(context);
        long dataTake = System.currentTimeMillis();
        String jpegName = path + File.separator + "picture_" + dataTake + ".jpg";
        ImagePacker imagePacker = ImagePacker.create();
        boolean isSuccess = false;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(jpegName);
            ImagePacker.PackingOptions options = new ImagePacker.PackingOptions();
            options.quality = 100;
            options.format = "image/jpeg";
            imagePacker.initializePacking(fileOutputStream, options);
            imagePacker.addImage(pixelMap);
            imagePacker.finalizePacking();
            isSuccess = true;
        } catch (IOException e) {
            LogUtil.error(LogUtil.DEFAULT_TAG, "saveBitmap e:" + e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException ex) {
                    LogUtil.error(LogUtil.DEFAULT_TAG, "ex:" + ex);
                }
            }
            imagePacker.release();
        }
        return isSuccess ? jpegName : "";
    }

    /**
     * delete File
     *
     * @param url String
     * @return boolean
     */
    public static boolean deleteFile(String url) {
        boolean result = false;
        File file = new File(url);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    /**
     * is ExternalStorage Writable
     *
     * @return boolean
     */
    public static boolean isExternalStorageWritable() {
        return DataUsage.getDiskMountedStatus().equals(MountState.DISK_MOUNTED);
    }
}
