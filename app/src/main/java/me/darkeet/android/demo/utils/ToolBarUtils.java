package me.darkeet.android.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

/**
 * 扩展toolbar功能
 *
 * @author Jacky.Lee <leeiii3s@163.com>
 */
public class ToolBarUtils {

    public static void compat(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(statusColor);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = contentView.getChildAt(0);
            int statusBarHeight = getStatusBarHeight(activity);

            //改变颜色时避免重复添加statusBarView
            if (statusBarView != null && statusBarView.getMeasuredHeight() == statusBarHeight) {
                statusBarView.setBackgroundColor(statusColor);
                return;
            }

            statusBarView = new View(activity);
            statusBarView.setBackgroundColor(statusColor);
            contentView.addView(statusBarView, new LayoutParams(LayoutParams.MATCH_PARENT, statusBarHeight));
        }
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
