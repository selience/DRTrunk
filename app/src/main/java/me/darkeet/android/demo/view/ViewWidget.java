package me.darkeet.android.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import me.darkeet.android.demo.R;

/**
 * Name: ViewSample
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/27 17:07
 * Desc:
 */
public class ViewWidget extends View {

    private boolean translate;
    private boolean roate;
    private boolean scale;

    private Paint mPaint;

    private int translateX;
    private int translateY;

    private int degree;

    public ViewWidget(Context context) {
        this(context, null);
    }

    public ViewWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        Rect rect = new Rect(0, 0, 400, 200);
        canvas.drawRect(rect, mPaint);

        if (translate) {
            translateX += 100;
            translateY += 100;
            canvas.translate(translateX, translateY);
            canvas.drawRect(rect, mPaint);
            canvas.restore();
        }

        if (roate) {
            degree += 45;
            canvas.rotate(degree);
            canvas.drawRect(rect, mPaint);
            canvas.restore();
        }
    }


    public void setTranslate(boolean translate) {
        this.translate = translate;
        this.roate = false;
        this.scale = false;
        invalidate();
    }

    public void setRoate(boolean roate) {
        this.roate = roate;
        this.translate = false;
        this.scale = false;
        invalidate();
    }

    public void setScale(boolean scale) {
        this.scale = scale;
        this.translate = false;
        this.roate = false;
        invalidate();
    }
}
