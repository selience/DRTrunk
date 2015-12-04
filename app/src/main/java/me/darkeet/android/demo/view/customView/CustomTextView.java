package me.darkeet.android.demo.view.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import me.darkeet.android.demo.R;
import me.darkeet.android.log.DebugLog;
import me.darkeet.android.utils.DeviceUtils;

/**
 * Name: CustomTextView
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/27 16:36
 * Desc: 自定义文字显示
 */
public class CustomTextView extends View {

    private Paint mPaint;
    private Rect mTextBound;

    private int mTextColor;
    private float mTextSize;
    private String mText;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, defStyleAttr);
        mText = typedArray.getString(R.styleable.CustomTextView_text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_textSize, (int) DeviceUtils.sp2px(context, 16));
        mTextColor = typedArray.getColor(R.styleable.CustomTextView_textColor, Color.RED);

        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);

        mTextBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            // 根据内容计算宽度
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

            width = mTextBound.width() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            // 根据内容计算宽度
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

            height = mTextBound.height() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);

        DebugLog.d("width:" + getMeasuredWidth() + "- height:" + getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTextColor);
        canvas.drawText(mText,
                (getWidth() - mTextBound.width()) / 2,
                (getHeight() + mTextBound.height()) / 2,
                mPaint);
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        invalidate();
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        invalidate();
    }

    public void setText(String mText) {
        this.mText = mText;
        invalidate();
    }
}
