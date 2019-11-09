package cn.mypackage.practise;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TestView extends View {

    private String mText = "IMOOC";
    private Paint mPaint;

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestView);

        //方法一
        boolean booleanTest = ta.getBoolean(R.styleable.TestView_test_boolean, false);
        int integerTest = ta.getInteger(R.styleable.TestView_test_integer, -1);
        float dimensionTest = ta.getDimension(R.styleable.TestView_test_dimension, 0);
        int emumTest = ta.getInt(R.styleable.TestView_test_emum, 1);
//        stringTest = ta.getString(R.styleable.TestView_test_string);

        //方法二
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = ta.getIndex(i);
            switch (index){
                case R.styleable.TestView_test_string:
                    mText = ta.getString(R.styleable.TestView_test_string);
                    break;
            }
        }

        ta.recycle();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(0xFFFF0000);
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
        setMeasuredDimension(width, height);
    }

    private int measureHeight() {
        return 0;
    }

    private int measureWidth() {
        return 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mPaint.getStrokeWidth() / 2, mPaint);
//        mPaint.setStrokeWidth(1);
//        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
//        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mPaint);
        mPaint.setTextSize(100);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        canvas.drawText(mText, 0, mText.length(), 0, getHeight(), mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mText = "10086";
        invalidate();
        return true;
    }

    private static final String INSTANCE = "instance";
    private static final String KEY_TEXT = "key_text";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TEXT, mText);
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            mText = bundle.getString(KEY_TEXT);
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
