package com.blog.ken.myblog.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.blog.ken.myblog.R;

/**
 * Created by huangyuekai on 16/11/30.
 */
public class myTextView extends TextView {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public myTextView(Context context) {
        super(context,null);
    }

    //我们重写了3个构造方法，*******默认的布局文件调用的是两个参数的构造方法，****
    // 所以记得让所有的构造调用我们的三个参数的构造，我们在三个参数的构造中获得自定义属性
    public myTextView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    /**
     * 获得我自定义的样式属性
    *
     * @param context
    * @param attrs
    * @param defStyleAttr
    */
    public myTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们自定义的样式属性
         */
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.myTextView,defStyleAttr,0);
        int n = array.getIndexCount();
        for(int i = 0;i < n; i++){
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.myTextView_titleText:
                    mTitleText = array.getString(attr);
                    break;
                case R.styleable.myTextView_titleTextColors:
                    // 默认颜色设置为黑色
                    mTitleTextColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.myTextView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = array.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            }
        }
        array.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
//        mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
    }

    //onMesure中如此设置设置wrapcontent就有效了，不然会默认mathparent，除非设置了固定高宽
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }



        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

}
