package me.darkeet.android.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Listener;
import com.android.volley.core.RequestCallback;
import com.android.volley.core.RequestManager;
import com.android.volley.error.VolleyError;
import java.io.File;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.demo.request.UpdateRequest;
import me.darkeet.android.log.DebugLog;
import me.darkeet.android.util.FileUtils;
import me.darkeet.android.util.Toaster;
import me.darkeet.android.util.Utils;

/**
 * Name: VolleyFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/12 18:16
 * Desc: Volley的基本使用
 */
public class VolleyFragment extends DRBaseStackFragment {

    private RequestManager.RequestController mRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequest = RequestManager.queue().useDefaultQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
        View view = inflater.inflate(R.layout.demo_fragment_volley, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 请求文本数据
     */
    @OnClick(R.id.btn_text)
    public void requestString() {
        mRequest.addRequest(new UpdateRequest(), new RequestCallback<String, String>() {
            @Override
            public String doInBackground(String response) {
                return response;
            }

            @Override
            public void onPostExecute(String result) {
                DebugLog.d(result);
                Toaster.show(mActivity, result);
            }

            @Override
            public void onError(VolleyError error) {
                super.onError(error);
                error.printStackTrace();
            }
        });
    }


    /**
     * 下文文件到本地磁盘
     */
    @OnClick(R.id.btn_download)
    public void requestFile() {
        String url = "http://112.65.222.35/dd.myapp.com/16891/6189936FAB997424DADAFAA289E0F7A1.apk";
        File storeFilePath = new File(Utils.getAppCacheDir(mActivity, "file"), FileUtils.getFileNameWithExtension(url));
        // 启动文件下载
        RequestManager.downloader().useDefaultLoader().obtain().add(storeFilePath.getPath(), url, new DownloadListener());
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
}
