package me.darkeet.android.demo.service;

import android.os.Build;
import android.annotation.TargetApi;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import me.darkeet.android.utils.Toaster;

/**
 * 确认开启通知监听服务才可以监听到通知内容
 * <p/>
 * 1. 扩展NotificationListenerService实现其两个方法；
 * 2. 需要在manifest文件中声明这个服务权限：android.permission.BIND_NOTIFICATION_LISTENER_SERVICE，
 * 在intent-filter中定义action: android.service.notification.NotificationListenerService
 * 3. 需要启用服务。没有这一步监听服务不能运行；路径：”Settings” -> ”Security” -> ”Notification Access“
 * startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // 4.4引入了新的新的Notification.extras 字段，可以获取更多的Bundle字段信息；
        // Bundle bundle = sbn.getNotification().extras;
        String message = "ID :" + sbn.getId() + "--" + sbn.getNotification().tickerText + "--" + sbn.getPackageName();
        Toaster.show(getBaseContext(), message);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        String message = "ID :" + sbn.getId() + "--" + sbn.getNotification().tickerText + "--" + sbn.getPackageName();
        Toaster.show(getBaseContext(), message);
    }
}
