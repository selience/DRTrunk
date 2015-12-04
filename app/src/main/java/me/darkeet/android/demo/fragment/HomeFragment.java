package me.darkeet.android.demo.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.activity.MapViewerActivity;
import me.darkeet.android.demo.adapter.MenuListAdapter;
import me.darkeet.android.demo.app.AppContext;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.demo.service.SocketService;
import me.darkeet.android.demo.zing.view.CaptureActivity;
import me.darkeet.android.log.DebugLog;
import me.darkeet.android.utils.NetworkUtils;
import me.darkeet.android.utils.Toaster;
import me.darkeet.android.utils.Utils;
import permissions.dispatcher.DeniedPermissions;
import permissions.dispatcher.NeedsPermissions;
import permissions.dispatcher.RuntimePermissions;
import permissions.dispatcher.ShowsRationales;

/**
 * Name: HomeFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/13 13:21
 * Desc:
 */
@RuntimePermissions
public class HomeFragment extends DRBaseStackFragment implements OnItemClickListener {

    private static final int REQUEST_CODE_QRCODE = 0x2000;

    @Bind(android.R.id.list)
    ListView mListView;

    private List<String> mDataList;
    private MenuListAdapter mListAdapter;
    private ImagePickerFragment pickerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.demo_fragment_homepage, container, false);
        ButterKnife.bind(this, convertView);
        setTitle("Normal");

        mDataList = new ArrayList<String>();
        String[] mResources = getResources().getStringArray(R.array.main_list_item);
        mDataList.addAll(Arrays.asList(mResources));
        // 设置列表适配器
        mListAdapter = new MenuListAdapter(mActivity);
        mListAdapter.setItems(mDataList);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Bundle bundle = new Bundle();
        bundle.putString(IntentConstants.FRAGMENT_TITLE, text);

        if (position == 0) {
            replace(VolleyFragment.class, "VolleyFragment", bundle);
        } else if (position == 1) {
            replace(NativeFragment.class, "NativeFragment", bundle);
        } else if (position == 2) {
            startNetworkService();
        } else if (position == 3) {
            Intent mapIntent = new Intent(mActivity, MapViewerActivity.class);
            mapIntent.setData(Uri.parse("wuxian://map?lat=" + AppContext.latitude + "&lng=" + AppContext.longitude));
            startActivity(mapIntent);
        } else if (position == 4) {
            HomeFragmentPermissionsDispatcher.startPickImageWithCheck(this);
        } else if (position == 5) {
            replace(NotificationFragment.class, "NotificationFragment", bundle);
        } else if (position == 6) { // 人脸面部检测
            replace(FaceDetectorFragment.class, "FaceDetectorFragment", bundle);
        } else if (position == 7) { // 扫描二维码
            startScanQrCode();
        } else if (position == 8) {
            replace(SwipeFragment.class, "SwipeFragment", bundle);
        } else if (position == 9) {
            replace(WebFlotr2Fragment.class, "WebFlotr2Fragment", bundle);
        } else if (position == 10) {
            replace(PermissionFragment.class, "PermissionFragment", bundle);
        } else if (position == 11) {
            replace(ActionBarFragment.class, "ActionBarFragment", bundle);
        } else if (position == 12) {
            replace(AppInstallFragment.class, "AppInstallFragment", bundle);
        } else if (position == 13) {
            replace(VideoCropFragment.class, "VideoCropFragment", bundle);
        } else if (position == 14) {
            replace(WebpFragment.class, "WebpFragment", bundle);
        }
    }

    /**
     * 网络服务监听
     */
    private void startNetworkService() {
        // 启动socket服务，监听本地4392端口
        startService(new Intent(mActivity, SocketService.class));

        // 打开本地界面
        String url = "http://" + NetworkUtils.ipToString(mActivity);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url + ":" + SocketService.CONNECTION_POST));
        startActivity(intent);
    }

    // 裁剪本地图片
    @NeedsPermissions(value = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void startPickImage() {
        File file = new File(Utils.getAppCacheDir(mActivity, "photo"), "photo.jpg");
        if (pickerFragment == null) {
            pickerFragment = new ImagePickerFragment();
            pickerFragment.setItems(R.array.pick_image);
            pickerFragment.setMaxSize(120, 120);
            pickerFragment.withOutPath(Uri.fromFile(file));
            pickerFragment.setOnImagePickerSelectedListener(new ImagePickerFragment.OnImagePickerSelectedListener() {
                @Override
                public void onImagePickerSelected(String imagePath) {
                    Toaster.show(mActivity, imagePath);
                }
            });
        }
        pickerFragment.show(getChildFragmentManager());
    }

    // 扫描二维码
    void startScanQrCode() {
        Intent openCameraIntent = new Intent(mActivity, CaptureActivity.class);
        startActivityForResult(openCameraIntent, REQUEST_CODE_QRCODE);
    }

    // 授权提示
    @ShowsRationales(value = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void showRationaleForWriteContacts() {
    }

    // 拒绝授权
    @DeniedPermissions(value = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void showDeniedForWriteContacts() {
        Toaster.show(mActivity, "Permission Denied!");
    }

    // 授权回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DebugLog.e("requestCode = " + requestCode);
        DebugLog.e("resultCode = " + resultCode);
        DebugLog.e("data = " + data);

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_QRCODE) { // 二维码扫描
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toaster.show(mActivity, scanResult);
        }

        if (pickerFragment != null) {
            pickerFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
