package me.darkeet.android.demo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Listener;
import com.android.volley.core.RequestManager;
import com.android.volley.error.VolleyError;
import java.io.File;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.utils.FileUtils;
import me.darkeet.android.utils.IntentUtils;
import me.darkeet.android.utils.Toaster;
import me.darkeet.android.utils.Utils;

/**
 * Name: AppInstallFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/13 16:00
 * Desc:
 */
public class AppInstallFragment extends DRBaseStackFragment {
    private static final String URL = "http://112.65.222.35/dd.myapp.com/16891/6189936FAB997424DADAFAA289E0F7A1.apk";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
        View view = inflater.inflate(R.layout.demo_fragment_install, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_observer)
    public void startListener() {
        InstallBroadcastReceiver receiver = new InstallBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(receiver, intentFilter);
    }

    /**
     * 下文文件到本地磁盘
     */
    @OnClick(R.id.btn_download)
    public void requestFile() {
        RequestManager.downloader().useDefaultLoader().obtain().add(
                saveToDiskFile().getPath(), URL, new DownloadListener());
    }

    @OnClick(R.id.btn_install)
    public void startInstall() {
        File destFile = saveToDiskFile();
        if (!destFile.exists()) return;
        startActivity(IntentUtils.newInstallApkIntent(destFile));
    }

    @OnClick(R.id.btn_uninstall)
    public void startUninstall() {
        startActivity(IntentUtils.newUnInstallApkIntent("com.qzone"));
    }

    /**
     * 文件存储路径
     */
    private File saveToDiskFile() {
        File storeFilePath = new File(
                Utils.getAppCacheDir(mActivity, "file"),
                FileUtils.getFileNameWithExtension(URL));
        return storeFilePath;
    }


    private class DownloadListener extends Listener<Void> {
        private ProgressDialogFragment mFragment;

        @Override
        public void onStart() {
            mFragment = ProgressDialogFragment.newInstance();
            mFragment.showFragment(getChildFragmentManager());
        }

        @Override
        public void onProgressUpdate(long fileSize, long downloadedSize) {
            mFragment.setMessage("正在下载文件：" + (int) ((downloadedSize * 100) / fileSize) + "%");
        }

        @Override
        public void onSuccess(Void response) {
            Toaster.show(mActivity, "request is success");
        }

        @Override
        public void onFinish() {
            mFragment.hideFragment();
        }

        @Override
        public void onError(VolleyError error) {
            Toaster.show(mActivity, error.getMessage());
        }
    }


    private class InstallBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收安装广播
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                String packageName = intent.getDataString();
                Toast.makeText(context, "安装了:" + packageName + "包名的程序", Toast.LENGTH_SHORT).show();
            }
            //接收卸载广播
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                String packageName = intent.getDataString();
                Toast.makeText(context, "卸载了:" + packageName + "包名的程序", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
