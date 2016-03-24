package me.darkeet.android.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import me.darkeet.android.demo.R;
import me.darkeet.android.jni.WebpManager;
import me.darkeet.android.util.IoUtils;

/**
 * Name: WebpImageView
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/12/3 17:29
 * Desc: 扩展ImageView，增加对webp格式图片支持；
 */
public class WebpImageView extends ImageView {

    public WebpImageView(Context context) {
        this(context, null);
    }

    public WebpImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebpImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WebpImageView);
        int resId = typedArray.getResourceId(R.styleable.WebpImageView_webp, 0);
        typedArray.recycle();

        // 设置web格式图片
        if (resId > 0) {
            setBitmapWithWebp(resId);
        }
    }

    /**
     * 解析webp格式图片
     *
     * @param resId
     */
    public void setBitmapWithWebp(int resId) {
        setBitmapWithWebp(IoUtils.readAllBytes(
                getResources().openRawResource(resId)));
    }

    /**
     * 解析webp格式图片
     *
     * @param encoded
     */
    public void setBitmapWithWebp(byte[] encoded) {
        Bitmap bitmap = null;
        // 4.3以下版本采用JNI方式解析webp图像
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bitmap = WebpManager.webpToBitmap(encoded);
        } else {
            bitmap = BitmapFactory.decodeByteArray(encoded, 0, encoded.length);
        }
        setImageBitmap(bitmap);
    }
}
