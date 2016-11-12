package com.ily.pakertymer.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ily.pakertymer.R;
import com.ily.pakertymer.util.MeasureUtil;

/**
 * Created by ily on 22.08.2016.
 */
public class CircleTimerView extends ImageView {

    private long levelFullTime, levelCurrentTime;

    private boolean mProgressBarEnabled;
    private int mProgressWidth = MeasureUtil.dpToPx(getContext(), 6f);
    private RectF mProgressCircleBounds = new RectF();
    private Paint mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private long mLastTimeAnimated;
    private float mSpinSpeed;
    private float mCurrentProgress;
    private float mTargetProgress;
    private int mProgress;
    private boolean mAnimateProgress;
    private boolean mShouldSetProgress;
    private int mProgressMax = 100;


    public CircleTimerView(Context context) {
        super(context);
    }

    public CircleTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleTimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mProgress = 0;
        setClickable(true);
    }

    public void setLevelFullTime(long levelFullTime) {
        this.levelFullTime = levelFullTime;
    }

    public void setLevelCurrentTime(long levelCurrentTime) {
        this.levelCurrentTime = levelCurrentTime;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setupProgressBounds();
        setupProgressBarPaints();
    }

    public synchronized int getProgress() {
        return mProgress;
    }

    public synchronized void hideProgress() {
        mProgressBarEnabled = false;
    }

    public void startAnimation() {
        float progress = (float) ((levelFullTime - levelCurrentTime) * 100) / levelFullTime;
        mProgress = (int) progress;
        mCurrentProgress = (progress / (float) mProgressMax) * 360;
        mSpinSpeed = mCurrentProgress / ((float) levelCurrentTime / 1000);
        mTargetProgress = 0;
        mLastTimeAnimated = SystemClock.uptimeMillis();
        mProgressBarEnabled = true;
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mProgressBarEnabled) {

            boolean shouldInvalidate = false;

            if (mCurrentProgress != mTargetProgress) {
                shouldInvalidate = true;
                float deltaTime = (float) (SystemClock.uptimeMillis() - mLastTimeAnimated) / 1000;
                float deltaNormalized = deltaTime * mSpinSpeed;

                if (mCurrentProgress > mTargetProgress) {
                    mCurrentProgress = Math.max(mCurrentProgress - deltaNormalized, mTargetProgress);
                } else {
                    mCurrentProgress = Math.min(mCurrentProgress + deltaNormalized, mTargetProgress);
                }
                mLastTimeAnimated = SystemClock.uptimeMillis();
            }

            canvas.drawArc(mProgressCircleBounds, 0, mCurrentProgress, false, mProgressPaint);

            if (shouldInvalidate) {
                invalidate();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(calculateMeasuredWidth(), calculateMeasuredHeight());
    }

    private int calculateMeasuredWidth() {
        int width = getCircleSize();
        if (mProgressBarEnabled) {
            width += mProgressWidth * 2;
        }
        return width;
    }

    private int calculateMeasuredHeight() {
        int height = getCircleSize();
        if (mProgressBarEnabled) {
            height += mProgressWidth * 2;
        }
        return height;
    }

    private int getCircleSize() {
        return getResources().getDimensionPixelSize(R.dimen.fab_size_normal);
    }

    private void setupProgressBarPaints() {
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        int[] resIds = new int[]{
                ResourcesCompat.getColor(getResources(), R.color.timer_red, null),
                ResourcesCompat.getColor(getResources(), R.color.timer_yellow, null),
                ResourcesCompat.getColor(getResources(), R.color.timer_green_last, null)};

        Shader shader = new SweepGradient(getWidth() / 2, getHeight() / 2, resIds, null);

        mProgressPaint.setShader(shader);
    }

    private void setupProgressBounds() {
        int circleInsetHorizontal = 0;
        int circleInsetVertical = 0;
        mProgressCircleBounds = new RectF(
                circleInsetHorizontal + mProgressWidth / 2,
                circleInsetVertical + mProgressWidth / 2,
                calculateMeasuredWidth() - circleInsetHorizontal - mProgressWidth / 2,
                calculateMeasuredHeight() - circleInsetVertical - mProgressWidth / 2
        );
    }

}
