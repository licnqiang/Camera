package ohos.samples.camera;

import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;
import ohos.media.camera.CameraKit;
import ohos.media.camera.device.CameraInfo;
import ohos.media.image.common.Size;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * CameraParam Util
 */
public class CameraParamUtil {
    /**
     * Rotation constant: 0 degree rotation.
     */
    public static final int ROTATION_0 = 0;

    /**
     * Rotation constant: 90 degree rotation.
     */
    public static final int ROTATION_90 = 1;

    /**
     * Rotation constant: 180 degree rotation.
     */
    public static final int ROTATION_180 = 2;

    /**
     * Rotation constant: 270 degree rotation.
     */
    public static final int ROTATION_270 = 3;

    private static CameraParamUtil sCameraParamUtil = null;

    private CameraSizeComparator sizeComparator = new CameraSizeComparator();


    private CameraParamUtil() {
    }

    /**
     * get Instance
     *
     * @return CameraParamUtil
     */
    public static CameraParamUtil getInstance() {
        if (sCameraParamUtil == null) {
            sCameraParamUtil = new CameraParamUtil();
            return sCameraParamUtil;
        } else {
            return sCameraParamUtil;
        }
    }

    /**
     * get PreviewSize
     *
     * @param list List<Size>
     * @param th   int
     * @param rate float
     * @return Size
     */
    public Size getPreviewSize(List<Size> list, int th, float rate) {
        int i = 0;
        for (Size size : list) {
            if ((size.width > th) && equalRate(size, rate)) {
                LogUtil.info(LogUtil.DEFAULT_TAG, "MakeSure Preview :w = " + size.width +
                        " h = " + size.height);
                break;
            }
            i++;
        }
        if (i == list.size()) {
            return getBestSize(list, rate);
        } else {
            return list.get(i);
        }
    }

    /**
     * get Picture Size
     *
     * @param list List
     * @param th   int
     * @param rate float
     * @return Size
     */
    public Size getPictureSize(List<Size> list, int th, float rate) {
        Collections.sort(list, sizeComparator);
        int i = 0;
        for (Size size : list) {
            if ((size.width > th) && equalRate(size, rate)) {
                LogUtil.info(LogUtil.DEFAULT_TAG, "MakeSure Picture :w = " + size.width +
                        " h = " + size.height);
                break;
            }
            i++;
        }
        if (i == list.size()) {
            return getBestSize(list, rate);
        } else {
            return list.get(i);
        }
    }

    private Size getBestSize(List<Size> list, float rate) {
        float previewDisparity = 100;
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            Size cur = list.get(i);
            float prop = (float) cur.width / (float) cur.height;
            if (Math.abs(rate - prop) < previewDisparity) {
                previewDisparity = Math.abs(rate - prop);
                index = i;
            }
        }
        return list.get(index);
    }

    private boolean equalRate(Size ss, float rate) {
        float rr = (float) (ss.width) / (float) (ss.height);
        return Math.abs(rr - rate) <= 0.2;
    }

    /**
     * Supported FocusMode
     *
     * @param focusList List<String>
     * @param focusMode String
     * @return boolean
     */
    public boolean isSupportedFocusMode(List<String> focusList, String focusMode) {
        for (int i = 0; i < focusList.size(); i++) {
            if (focusMode.equals(focusList.get(i))) {
                LogUtil.info(LogUtil.DEFAULT_TAG, "FocusMode supported " + focusMode);
                return true;
            }
        }
        return false;
    }

    /**
     * Supported PictureFormats
     *
     * @param supportedPictureFormats List<Integer>
     * @param jpeg                    int
     * @return boolean
     */
    public boolean isSupportedPictureFormats(List<Integer> supportedPictureFormats, int jpeg) {
        for (int i = 0; i < supportedPictureFormats.size(); i++) {
            if (jpeg == supportedPictureFormats.get(i)) {
                LogUtil.info(LogUtil.DEFAULT_TAG, "Formats supported " + jpeg);
                return true;
            }
        }
        LogUtil.info(LogUtil.DEFAULT_TAG, "Formats not supported " + jpeg);
        return false;
    }

    /**
     * Camera Size Comparator
     */
    private static class CameraSizeComparator implements Comparator<Size> {
        /**
         * compare
         *
         * @param lhs Size
         * @param rhs Size
         * @return int
         */
        public int compare(Size lhs, Size rhs) {
            if (lhs.width == rhs.width) {
                return 0;
            } else if (lhs.width > rhs.width) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    /**
     * getCameraDisplayOrientation
     *
     * @param context     Context
     * @param cameraId    int
     * @param orientation int
     * @return int
     */
    public int getCameraDisplayOrientation(Context context, int cameraId, int orientation) {
        CameraInfo info = CameraKit.getInstance(context).getCameraInfo(String.valueOf(cameraId));
        Display display = DisplayManager.getInstance().getDefaultDisplay(context).get();
        int rotation = display.getRotation();

        int degrees = 0;
        switch (rotation) {
            case ROTATION_0:
                degrees = 0;
                break;
            case ROTATION_90:
                degrees = 90;
                break;
            case ROTATION_180:
                degrees = 180;
                break;
            case ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.getFacingType() == CameraInfo.FacingType.CAMERA_FACING_FRONT) {
            result = (orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            // back-facing
            result = (orientation - degrees + 360) % 360;
        }
        return result;
    }
}
