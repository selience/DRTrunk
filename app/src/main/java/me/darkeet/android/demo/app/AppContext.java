package me.darkeet.android.demo.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.multidex.MultiDex;
import com.android.volley.Volley;
import com.android.volley.core.RequestManager;
import me.darkeet.android.app.AppException;
import me.darkeet.android.demo.BuildConfig;
import me.darkeet.android.log.DebugLog;
import me.darkeet.android.utils.DebugModeUtils;
import me.darkeet.android.utils.ManifestUtils;
import me.darkeet.android.utils.StorageUtils;
import me.darkeet.android.utils.Utils;

/**
 * @author lilong
 * @file AppContext.java
 * @create 2012-8-15 下午5:08:28
 * @description TODO Application基类，存储全局数据
 */
public class AppContext extends Application {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static double latitude = 39.90960456049752;   // 纬度
    public static double longitude = 116.3972282409668;  // 经度

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DebugModeUtils.initForApplication(this);

        // 多个进程会重复调用
        if (ManifestUtils.checkIfIsAppRunning(this, getPackageName())) {
            initialize();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /*
     * 初始化操作
     */
    private void initialize() {
        Volley.setLoggingEnabled(DEBUG);
        DebugLog.enableDebugLogging(DEBUG);
        StorageUtils.getInstance().init(this);

        Utils.initializeAsyncTask();
        RequestManager.initializeWith(this);

        if (!DEBUG) {
            // 在Release模式下开启日志收集功能，以精确分析应用性能；开发模式下默认关闭以精确查看异常信息；
            Thread.setDefaultUncaughtExceptionHandler(new AppException(this));
        }
    }

    @Override
    public void onTerminate() {
        // FIXME: 根据android文档，onTerminate不会在真实机器上被执行到
        // 因此这些清理动作需要再找合适的地方放置，以确保执行。
        instance = null;
        super.onTerminate();
    }

    /**
     * @return AppContext singleton instance
     */
    public static AppContext getInstance() {
        return instance;
    }

    /**
     * 获取App安装包信息
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null) info = new PackageInfo();
        return info;
    }
}
