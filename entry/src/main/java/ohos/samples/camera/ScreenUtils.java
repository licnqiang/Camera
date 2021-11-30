package ohos.samples.camera;

import ohos.agp.window.service.DisplayAttributes;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;

/**
 * Screen Utils
 */
public class ScreenUtils {
    /**
     * get Screen Height
     *
     * @param context Context
     * @return int
     */
    public static int getScreenHeight(Context context) {
        DisplayAttributes displayAttributes =
                DisplayManager.getInstance().getDefaultDisplay(context).get().getAttributes();
        return displayAttributes.height;
    }

    /**
     * get Screen Width
     *
     * @param context Context
     * @return int
     */
    public static int getScreenWidth(Context context) {
        DisplayAttributes displayAttributes =
                DisplayManager.getInstance().getDefaultDisplay(context).get().getAttributes();
        return displayAttributes.width;
    }
}
