package me.darkeet.android.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.jni.NativeMethod;
import me.darkeet.android.jni.UninstallObserver;
import me.darkeet.android.log.DebugLog;

/**
 * Name: NativeFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/13 11:47
 * Desc: JNI的基本使用
 */
public class NativeFragment extends DRBaseStackFragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_fragment_jni, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_normal).setOnClickListener(this);
        view.findViewById(R.id.btn_observer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                invokeNormalMethod();
                break;
            case R.id.btn_observer:
                invokeAppUninstall();
                break;
        }
    }

    // JNI方法调用
    private void invokeNormalMethod() {
        NativeMethod nativeMethod = new NativeMethod();
        DebugLog.i("C实现有参的java方法：" + nativeMethod.sayHi("test"));
        DebugLog.i("C实现两个整数相加方法:" + nativeMethod.add(120, 130));
        DebugLog.i("C实现数据元素加5的方法:" + nativeMethod.intMethod(new int[]{2, 5, 8}));

        nativeMethod.callPrint();  // C调用静态方法
        nativeMethod.callMethod(); // C调用实例方法
    }

    // 启动卸载应用监听
    private void invokeAppUninstall() {
        UninstallObserver.startTask(mActivity);
    }
}
