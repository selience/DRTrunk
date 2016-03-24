package me.darkeet.android.demo.fragment;

import java.io.File;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import me.darkeet.android.common.Crop;
import me.darkeet.android.log.DebugLog;
import me.darkeet.android.util.IntentUtils;
import me.darkeet.android.util.MediaUtils;
import me.darkeet.android.util.Utils;

/**
 * Name: ImagePickerFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/5 16:57
 * Desc: 图片选择和拍照处理类
 */
public class ImagePickerFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private static final String TAG = "ImagePickerFragment";

    private static final int REQUEST_CODE_CROP_PHOTO = 1001;
    private static final int REQUEST_CODE_PICK_IMAGE = 1002;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1003;

    private static final String[] ITEMS = {"使用相机拍照", "从手机相册选择"};

    private int mItemsId;
    private int mMaxWidth;
    private int mMaxHeight;
    private boolean isCrop;
    private File mPhotoFile;
    private Uri mOutputFile;
    private OnImagePickerSelectedListener mOnSelectedListener;


    private Bundle getArgs() {
        Bundle res = getArguments();
        if (res == null) {
            res = new Bundle();
            setArguments(res);
        }
        return res;
    }

    /**
     * 是否执行图片裁剪
     */
    public void setIsCrop(boolean isCrop) {
        getArgs().putBoolean("crop", isCrop);
    }

    /**
     * 设置图片的存储路径
     */
    public void withOutPath(Uri output) {
        getArgs().putParcelable("output", output);
    }

    /**
     * 设置裁剪图片大小
     *
     * @param width
     * @param height
     */
    public void setMaxSize(int width, int height) {
        getArgs().putInt("width", width);
        getArgs().putInt("height", height);
    }


    /**
     * 设置dialog列表项
     *
     * @param itemsId
     */
    public void setItems(int itemsId) {
        getArgs().putInt("itemsId", itemsId);
    }

    /**
     * 设置回调函数，处理函调结果
     */
    public void setOnImagePickerSelectedListener(OnImagePickerSelectedListener listener) {
        this.mOnSelectedListener = listener;
    }

    /**
     * 显示Fragment
     *
     * @param manager
     */
    public void show(FragmentManager manager) {
        show(manager, TAG);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCrop = getArguments().getBoolean("crop", true);
        mItemsId = getArguments().getInt("itemsId");
        mMaxWidth = getArguments().getInt("width", 100);
        mMaxHeight = getArguments().getInt("height", 100);
        mOutputFile = getArguments().getParcelable("output");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        if (mItemsId > 0) {
            builder.setItems(mItemsId, this);
        } else {
            builder.setItems(ITEMS, this);
        }
        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == 0) {
            final String fileName = System.currentTimeMillis() + ".jpg";
            this.mPhotoFile = new File(Utils.getAppCacheDir(currentContext(), "photo"), fileName);
            currentFragment().startActivityForResult(IntentUtils.newTakePhoto(mPhotoFile), REQUEST_CODE_TAKE_PHOTO);
        } else {
            startActivityForResult(IntentUtils.newPickImage(), REQUEST_CODE_PICK_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_TAKE_PHOTO:
                DebugLog.d(TAG, "Take Photo: uri = " + mPhotoFile.getPath());
                startCropPhoto(mPhotoFile);
                break;
            case REQUEST_CODE_PICK_IMAGE:
                File destFile = new File(MediaUtils.getPath(currentContext(), data.getData()));
                DebugLog.d(TAG, "Choose Photo: uri = " + destFile.getPath());
                startCropPhoto(destFile);
                break;
            case REQUEST_CODE_CROP_PHOTO:
                if (mPhotoFile != null) {
                    mPhotoFile.delete();
                    mPhotoFile = null;
                }
                if (mOnSelectedListener != null) {
                    mOnSelectedListener.onImagePickerSelected(mOutputFile.toString());
                }
                break;
        }
    }

    /**
     * 开始裁剪图片
     */
    private void startCropPhoto(File srcFile) {
        if (!isCrop) return;
        // 执行裁剪操作
        new Crop(Uri.fromFile(srcFile)).asSquare().output(mOutputFile).returnData(false).withScale(true)
                .withOutSize(mMaxWidth, mMaxHeight).start(currentFragment(), REQUEST_CODE_CROP_PHOTO);
    }

    /**
     * 上下文对象Context
     */
    private Context currentContext() {
        Fragment fragment = currentFragment();
        return fragment.getActivity();
    }

    private Fragment currentFragment() {
        if (getParentFragment() == null) {
            return this;
        }
        return getParentFragment();
    }

    public interface OnImagePickerSelectedListener {

        void onImagePickerSelected(String imagePath);
    }
}
