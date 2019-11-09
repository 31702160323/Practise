package cn.mypackage.practise;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class MyView extends View {
    private int mRadius;
    private int mColor;
    private int mLinneHeight;
    private int mTextSize;
    private int mProgress;

    private Paint mPaint;
    private RectF mProgressCicleRectF;
    private int width;
    private int height;
    private int textHeight;
    private String text;
    private float angle;
    private Rect bound;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);

        mRadius = (int) ta.getDimension(R.styleable.MyView_radius, dp2px(30));
        mColor = ta.getColor(R.styleable.MyView_color, 0xffff0000);
        mLinneHeight = (int) ta.getDimension(R.styleable.MyView_line_width, dp2px(3));
        mTextSize = (int) ta.getDimension(R.styleable.MyView_android_textSize, dp2px(50));
        mProgress = ta.getInt(R.styleable.MyView_android_progress, 0);

        ta.recycle();

        initPaint();
    }

    private float dp2px(int dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int width = 0;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        } else {
            int needWidth = measureWidth() + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST){
                width = Math.min(needWidth, widthSize);
            } else {
                width = needWidth;
            }
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        } else {
            int needHeight = measureHeight() + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST){
                height = Math.min(needHeight, heightSize);
            } else {
                height = needHeight;
            }
        }
        width = Math.min(width, height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        angle = mProgress * 1.0f / 100 * 360;
        bound = new Rect();
        mProgressCicleRectF = new RectF(0, 0, w - getPaddingLeft() * 2, h - getPaddingLeft() * 2);
    }

    private int measureHeight() {
        return mRadius * 2;
    }

    private int measureWidth() {
        return mRadius * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLinneHeight * 1.0F / 4);

        canvas.drawCircle(width / 2, height / 2, width / 2 - getPaddingLeft() - mPaint.getStrokeWidth() / 2, mPaint);

        mPaint.setStrokeWidth(mLinneHeight);

        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());//移动坐标
        canvas.drawArc( mProgressCicleRectF, 0, angle, false, mPaint);
        canvas.restore();

        text = mProgress + "%";
        mPaint .setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(text, 0, text.length(), bound);
        textHeight = bound.height() / 2;
        canvas.drawText(text,0, text.length(), width / 2, height / 2 + textHeight, mPaint);
    }

    public void setProgress(int progress){
        mProgress = progress;
        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    private static final String INSTANCE = "instance";
    private static final String KEY_PROGRESS = "key_progress";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PROGRESS, mProgress);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            mProgress = bundle.getInt(KEY_PROGRESS);
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
