package me.khrystal.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Collection;

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
    private Animator.AnimatorListener mAnimatorListener;

    private static final int DEGREE                = 10;
    private static final int DEFAULT_WIDTH         = 200;
    private static final int DEFAULT_HEIGHT        = 50;
    private static final int PROGRESS              = 5;
    private Collection<Animator> mAnimList;
    private ValueAnimator leftAnim;
    private ValueAnimator rightAnim;


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
        mTextColor = ta.getColor(R.styleable.KScoreBar_textColor, Color.BLACK);
        mProgressLeft = ta.getInteger(R.styleable.KScoreBar_leftProgress, PROGRESS);
        mProgressRight = ta.getInteger(R.styleable.KScoreBar_rightProgress, PROGRESS);
        ta.recycle();

        mBarPaint = new Paint();
        mTextPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);
        mAnimList = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textBaseLineOffset = (fontMetrics.bottom - fontMetrics.top) / 2
                - fontMetrics.bottom;
        if (TextUtils.isEmpty(mTextLeft)) mTextLeft = String.valueOf(mProgressLeft);
        canvas.drawText(mTextLeft, mWidth / DEGREE / 2, mHeight / 2 + textBaseLineOffset, mTextPaint);

        mBarPaint.setColor(mColorLeft);
        canvas.drawLine(mWidth / DEGREE,
                mHeight / 2,
                mWidth / DEGREE + mWidth * (DEGREE - 2) / DEGREE * mProgressLeft / (mProgressLeft + mProgressRight),
                mHeight / 2,
                mBarPaint);

        fontMetrics = mTextPaint.getFontMetrics();
        textBaseLineOffset = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        if (TextUtils.isEmpty(mTextRight)) mTextRight = String.valueOf(mProgressRight);
        canvas.drawText(mTextRight, mWidth - mWidth / DEGREE / 2, mHeight / 2 + textBaseLineOffset,mTextPaint);

        mBarPaint.setColor(mColorRight);
        canvas.drawLine(mWidth/ DEGREE + mWidth * (DEGREE -2)/ DEGREE * mProgressLeft /(mProgressLeft + mProgressRight),
                mHeight / 2,
                mWidth * (DEGREE -1) / DEGREE,
                mHeight/2,
                mBarPaint);
    }

    /**
     * set left and right progress
     * the left display is left/(left+right)
     * the right display is 1 - left display
     * @param leftProgress
     * @param rightProgress
     */
    public void setProgress(int leftProgress, int rightProgress) {
        mProgressLeft = leftProgress;
        mProgressRight = rightProgress;
        invalidate();
    }

    /**
     * set left and right textColor
     * @param textColor
     */
    public void setTextColor(int textColor) {
        mTextColor = textColor;
        invalidate();
    }

    /**
     * set left and right progressColor
     * @param colorLeft
     * @param colorRight
     */
    public void setProgressColor(int colorLeft, int colorRight) {
        mColorLeft = colorLeft;
        mColorRight = colorRight;
        invalidate();
    }

    /**
     * set left and right Text
     * if text is null or "" the progress will instead them
     * @param textLeft
     * @param textRight
     */
    public void setLeftAndRightText(String textLeft, String textRight) {
        mTextLeft = textLeft;
        mTextRight = textRight;
        invalidate();
    }


    public void addRightProgressWitAnim(final boolean showOnText, int...rightProgress) {
        rightAnim = ValueAnimator.ofInt(rightProgress);
        rightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgressRight = (int) animation.getAnimatedValue();
                if (showOnText)
                    mTextRight = String.valueOf(mProgressRight);
                invalidate();
            }
        });
    }

    public void addLeftProgressWithAnim(final boolean showOnText, int...leftProgress) {
        leftAnim = ValueAnimator.ofInt(leftProgress);
        leftAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgressLeft = (int) animation.getAnimatedValue();
                if (showOnText)
                    mTextLeft = String.valueOf(mProgressLeft);
            }
        });
    }


    /**
     * show with anim progress
     * @param duration anim duration
     */
    public void showWithAnim(long duration) {
        mAnimList.clear();
        mAnimList.add(leftAnim);
        mAnimList.add(rightAnim);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(duration);
        animationSet.playTogether(mAnimList);
        animationSet.setInterpolator(new LinearInterpolator());

        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animationSet.start();
    }

    public void setProgressAnimatorListener(Animator.AnimatorListener animatorListener) {
        mAnimatorListener = animatorListener;
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

        mTextPaint.setTextSize(Math.min(mWidth / DEGREE, mHeight) /2);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mBarPaint.setStyle(Paint.Style.STROKE);
        mBarPaint.setStrokeWidth(mHeight / 10);
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
