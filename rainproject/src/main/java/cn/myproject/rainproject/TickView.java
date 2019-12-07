package cn.myproject.rainproject;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class TickView extends View {
    private int mColor;
    private int mRadius;
    private int mProgress;

    private Paint mPaint;
    private int width;

    private RectF mProgressCicleRectF;

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int mProgress) {
        if (mProgress<360) {
            this.mProgress = mProgress;
        } else {
            ObjectAnimator.ofInt(this,"progress", 0, 360).setDuration(3000).start();
        }
        invalidate();
    }

    public TickView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmotionRainView);
        mColor = typedArray.getColor(R.styleable.EmotionRainView_color, 0xffff0000);
        mRadius = (int) typedArray.getDimension(R.styleable.EmotionRainView_radius, dp2px(30));
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(25);
    }

    private float dp2px(int dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mProgressCicleRectF = new RectF(15, 15, w - 15, h - 15);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRadius >= 0) {
            width = (int) (dp2px(mRadius) * 2);
            setMeasuredDimension(width, width);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//                canvas.drawCircle(300,300, mRadius, mPaint);
        canvas.drawArc( mProgressCicleRectF, mProgress + 50, mProgress, false, mPaint);
    }
}
