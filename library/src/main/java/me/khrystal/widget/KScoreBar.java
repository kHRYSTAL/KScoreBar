package me.khrystal.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/3
 * update time:
 * email: 723526676@qq.com
 */
public class KScoreBar extends View {

    private Context mContext;
    private int mColorLeft, mColorRight, mTextColor;
    private int mProgressLeft, mProgressRight;
    private String mTextLeft, mTextRight;
    private Paint mBarPaint, mTextPaint;
    private int mWidth, mHeight;

    private static final int DEGREE                = 10;
    private static final int DEFAULT_WIDTH         = 200;
    private static final int DEFAULT_HEIGHT        = 50;
    private static final int PROGRESS              = 5;


    public KScoreBar(Context context) {
        this(context, null);
    }

    public KScoreBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KScoreBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KScoreBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.KScoreBar);
        mColorLeft = ta.getColor(R.styleable.KScoreBar_leftColor, Color.GREEN);
        mColorRight = ta.getColor(R.styleable.KScoreBar_rightColor, Color.RED);
        mTextLeft = ta.getString(R.styleable.KScoreBar_leftText);
        mTextRight = ta.getString(R.styleable.KScoreBar_rightText);
        mTextColor = ta.getColor(R.styleable.KScoreBar_textColor, Color.WHITE);
        mProgressLeft = ta.getInteger(R.styleable.KScoreBar_leftProgress, PROGRESS);
        mProgressRight = ta.getInteger(R.styleable.KScoreBar_rightProgress, PROGRESS);

        ta.recycle();

        mBarPaint = new Paint();
        mTextPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);

        mTextPaint.setTextSize(Math.min(mWidth / DEGREE, mHeight) /2);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setStrokeWidth(mHeight / 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textBaseLineOffset = (fontMetrics.bottom - fontMetrics.top) / 2
                - fontMetrics.bottom;
        canvas.drawText(mTextLeft, mWidth / DEGREE / 2, mHeight / 2 + textBaseLineOffset, mTextPaint);
        mBarPaint.setColor(mColorLeft);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        mWidth = getWidth() - paddingLeft - paddingRight;
        mHeight = getHeight() - paddingTop - paddingBottom;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidthSize(widthMeasureSpec), measureHeightSize(heightMeasureSpec));
    }

    /**
     * measure width
     * @param measureSpec spec
     * @return width
     */
    private int measureWidthSize(int measureSpec) {
        int defSize = dp2px(DEFAULT_WIDTH);
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        int result = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.min(defSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * measure height
     * @param measureSpec spec
     * @return height
     */
    private int measureHeightSize(int measureSpec) {
        int defSize = dp2px(DEFAULT_HEIGHT);
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        int result = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.min(defSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
