package me.darkeet.android.demo.fragment;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.activity.HomeActivity;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.demo.service.NotificationService;
import me.darkeet.android.util.DeviceUtils;

/**
 * Name: NotificationFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/13 12:05
 * Desc:本地通知相关操作
 */
public class NotificationFragment extends DRBaseStackFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment_notification, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // 1. 开启通知服务
    @OnClick(R.id.btn_start_service)
    public void startService() {
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    // 2. 开启通知监听
    @OnClick(R.id.btn_start_listener)
    public void startListener() {
        if (DeviceUtils.sdkVersion() > Build.VERSION_CODES.JELLY_BEAN) {
            startService(new Intent(mActivity, NotificationService.class));
        }
    }

    // 3. 构建通知信息
    @OnClick(R.id.btn_send_notification)
    public void sendNotification() {
        NotificationManagerCompat.from(mActivity).cancelAll();
        NotificationCompat.Builder notificationBuilder = createBuilder();
        Notification notification = notificationBuilder.build();
        NotificationManagerCompat.from(mActivity).notify(1000, notification);
    }

    /*
    * 自定义本地通知
    */
    private NotificationCompat.Builder createBuilder() {
        Intent resultIntent = new Intent(mActivity, HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mActivity);
        stackBuilder.addParentStack(HomeActivity.class);// 添加后台堆栈
        stackBuilder.addNextIntent(resultIntent);// 添加Intent到栈顶
        // 获得一个PendingIntent包含整个后台堆栈 containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(mActivity)
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .setTicker("消息提示文字")
                .setContentText("本地消息通知")
                .setLights(0xff0000ff, 300, 1000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("消息标题")
                .setContentIntent(resultPendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("消息内容"))
                .addAction(android.R.drawable.ic_menu_share, "Fix Now", resultPendingIntent)
                .addAction(android.R.drawable.ic_menu_revert, "Remind Me", resultPendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_image));
    }

}
