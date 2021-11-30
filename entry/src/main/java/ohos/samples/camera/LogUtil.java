package ohos.samples.camera;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * log util
 */
public class LogUtil {
    /**
     * log default tag
     */
    public static final String DEFAULT_TAG = "CameraView";

    private static final int WATCH_DOMAIN = 0xD003A01;

    private static final String SEPARATE_SYMBOL = ":";

    private static final String SEPARATE_COMMA_SYMBOL = ",";

    private static final String VERSION_CODE = "100001";

    private static final String SEPARATE_SLASH_SYMBOL = "/";

    private static final String TAG_LOG = DEFAULT_TAG + SEPARATE_SLASH_SYMBOL + VERSION_CODE;

    private static final int LOG_TYPE = 3;

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(LOG_TYPE, WATCH_DOMAIN, LogUtil.TAG_LOG);

    private static String sCombinedMsg;

    private LogUtil() {
    }

    /**
     * debug级别日志打印方法
     *
     * @param tag    日志打印类标签
     * @param format 标准格式化参数
     * @param msg    日志打印信息
     * @param <T>    信息可以传入任何类型
     */
    public static <T> void debug(String tag, String format, T... msg) {
        if (HiLog.isLoggable(WATCH_DOMAIN, tag, HiLog.INFO)) {
            if (!isEmpty(tag)) {
                sCombinedMsg = tag + SEPARATE_SYMBOL + format;
                HiLog.debug(LABEL_LOG, sCombinedMsg, msg);
            }
        }
    }

    /**
     * info级别日志打印方法
     *
     * @param tag    日志打印类标签
     * @param format 标准格式化参数
     * @param msg    日志打印信息
     */
    public static <T> void info(String tag, String format, T... msg) {
        if (HiLog.isLoggable(WATCH_DOMAIN, tag, HiLog.INFO)) {
            if (!isEmpty(tag)) {
                sCombinedMsg = tag + SEPARATE_SYMBOL + format;
                HiLog.info(LABEL_LOG, sCombinedMsg, msg);
            }
        }
    }

    /**
     * warn级别日志打印方法
     *
     * @param tag    日志打印类标签
     * @param format 标准格式化参数
     * @param msg    日志打印信息
     */
    public static <T> void warn(String tag, String format, T... msg) {
        if (HiLog.isLoggable(WATCH_DOMAIN, tag, HiLog.INFO)) {
            if (!isEmpty(tag)) {
                sCombinedMsg = tag + SEPARATE_SYMBOL + format;
                HiLog.warn(LABEL_LOG, sCombinedMsg, msg);
            }
        }
    }

    /**
     * error级别日志打印方法
     *
     * @param tag    日志打印类标签
     * @param format 标准格式化参数
     * @param msg    日志打印信息
     */
    public static <T> void error(String tag, String format, T... msg) {
        if (HiLog.isLoggable(WATCH_DOMAIN, tag, HiLog.INFO)) {
            if (!isEmpty(tag)) {
                sCombinedMsg = tag + SEPARATE_SYMBOL + format;
                HiLog.error(LABEL_LOG, sCombinedMsg, msg);
            }
        }
    }

    /**
     * fatal级别日志打印方法
     *
     * @param tag    日志打印类标签
     * @param format 标准格式化参数
     * @param msg    日志打印信息
     */
    public static <T> void fatal(String tag, String format, T... msg) {
        if (HiLog.isLoggable(WATCH_DOMAIN, tag, HiLog.INFO)) {
            if (!isEmpty(tag)) {
                sCombinedMsg = tag + SEPARATE_SYMBOL + format;
                HiLog.fatal(LABEL_LOG, sCombinedMsg, msg);
            }
        }
    }

    private static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}
