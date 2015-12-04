package me.darkeet.android.demo.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.demo.widget.WebpImageView;
import me.darkeet.android.jni.WebpManager;
import me.darkeet.android.utils.FileUtils;
import me.darkeet.android.utils.IoUtils;
import me.darkeet.android.utils.Utils;

/**
 * Name: WebpFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/1 16:41
 * Desc: 加载webp格式图片
 */
public class WebpFragment extends DRBaseStackFragment {
    private static final int[] sImageArray = {
            R.raw.image0, R.raw.image1,
            R.raw.image2, R.raw.image3, R.raw.image4};

    @Bind(R.id.id_imageView)
    ImageView imageView;

    private int sImageId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment_webp, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.id_button_decode)
    public void decodeWebpBitmap() {
        sImageId = sImageId % 5;

        InputStream rawImageStream = getResources().openRawResource(
                sImageArray[sImageId]);
        updateImageBitmap(IoUtils.readAllBytes(rawImageStream));

        sImageId += 1;
    }

    @OnClick(R.id.id_button_widget)
    public void decodeWebpBitmapView() {
        ((WebpImageView) imageView).setBitmapWithWebp(R.raw.image3);
    }

    @OnClick(R.id.id_button_encode)
    public void encodeWebpBitmap() {
        File destFile = Utils.getAppCacheDir(mActivity, "image");
        InputStream rawImageStream = getResources().openRawResource(R.raw.image5);
        Bitmap bitmap = BitmapFactory.decodeStream(rawImageStream);
        // 转换webp格式
        byte[] data = WebpManager.bitmapToWebp(bitmap, 100);

        updateImageBitmap(data);

        // webp图片存储本地
        FileUtils.writeFile(new ByteArrayInputStream(data), new File(destFile, "image05.webp"));

    }

    @OnClick(R.id.id_button_argb565)
    public void encodeWebpBitmapWith565() {
        File destFile = Utils.getAppCacheDir(mActivity, "image");

        // 改变bitmap的编码格式
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;

        InputStream rawImageStream = getResources().openRawResource(R.raw.image5);
        byte[] data = IoUtils.readAllBytes(rawImageStream);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // 转换webp格式
        data = WebpManager.bitmapToWebp(bitmap, 100);

        updateImageBitmap(data);

        // webp图片存储本地
        FileUtils.writeFile(new ByteArrayInputStream(data), new File(destFile, "image06.webp"));
    }

    /**
     * 更新图像
     */
    private void updateImageBitmap(byte[] data) {
        // 解密webp格式并显示图像
        Bitmap bitmap = WebpManager.webpToBitmap(data);
        imageView.setImageBitmap(bitmap);
    }
}
