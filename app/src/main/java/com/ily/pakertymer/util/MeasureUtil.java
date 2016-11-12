package com.ily.pakertymer.util;

import android.content.Context;

/**
 * Created by ily on 22.08.2016.
 */
public class MeasureUtil {

    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    public static int pxToDp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(px / scale);
    }


}
