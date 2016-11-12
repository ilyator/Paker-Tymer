package com.ily.pakertymer.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
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

import java.util.Calendar;

/**
 * Created by ily on 22.08.2016.
 */
public class CircleTimerView extends ImageView {

    private Calendar endDate;

    private boolean mProgressBarEnabled;
    private int mProgressWidth = MeasureUtil.dpToPx(getContext(), 6f);
    private float mOriginalX = -1;
    private float mOriginalY = -1;
    private boolean mButtonPositionSaved;
    private RectF mProgressCircleBounds = new RectF();
    private Paint mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private long mLastTimeAnimated;
    private float mSpinSpeed = 195.0f;
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
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleTimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, defStyleAttr, 0);

            mProgress =  0;
            mShouldSetProgress = true;

        attr.recycle();

        if (isInEditMode()) {
            if (mShouldSetProgress) {
                saveButtonOriginalPosition();
                setProgress(mProgress, false);
            }
        }

        setClickable(true);
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        saveButtonOriginalPosition();

        if (mShouldSetProgress) {
            setProgress(mProgress, mAnimateProgress);
            mShouldSetProgress = false;
        }

        super.onSizeChanged(w, h, oldw, oldh);

        setupProgressBounds();
        setupProgressBarPaints();
    }

    public synchronized void setProgress(int progress, boolean animate) {
        mProgress = progress;
        mAnimateProgress = animate;

        if (!mButtonPositionSaved) {
            mShouldSetProgress = true;
            return;
        }

        mProgressBarEnabled = true;
        setupProgressBounds();
        saveButtonOriginalPosition();

        if (progress < 0) {
            progress = 0;
        } else if (progress > mProgressMax) {
            progress = mProgressMax;
        }

        if (progress == mTargetProgress) {
            return;
        }

        mTargetProgress = mProgressMax > 0 ? (progress / (float) mProgressMax) * 360 : 0;
        mLastTimeAnimated = SystemClock.uptimeMillis();

        if (!animate) {
            mCurrentProgress = mTargetProgress;
        }

        invalidate();
    }

    public synchronized int getProgress() {
        return mProgress;
    }

    public synchronized void hideProgress() {
        mProgressBarEnabled = false;
    }

    public void startAnimation(){
        long curTime = Calendar.getInstance().getTimeInMillis();
        if(endDate.getTimeInMillis() - curTime>0) {
            float progress = (float) ((endDate.getTimeInMillis() - curTime) * 100) / (1000 * 60 * 15);
            mProgress = (int) progress;
            mCurrentProgress = (progress / (float) mProgressMax) * 360;
            mSpinSpeed = mCurrentProgress / ((float)(Math.abs(endDate.getTimeInMillis() - curTime)) / 1000);
            mTargetProgress = 0;
            mLastTimeAnimated = SystemClock.uptimeMillis();
            mProgressBarEnabled = true;
            invalidate();
        } else {
            mProgressBarEnabled = true;
            setupProgressBounds();
            saveButtonOriginalPosition();
            mTargetProgress = 0;
            mCurrentProgress = 0;
            mProgress = 0;
            mLastTimeAnimated = SystemClock.uptimeMillis();
            invalidate();
        }
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
        int [] resIds = new int[] {
                ResourcesCompat.getColor(getResources(), R.color.timer_green_last, null),
                ResourcesCompat.getColor(getResources(), R.color.timer_yellow, null),
                ResourcesCompat.getColor(getResources(), R.color.timer_red, null)};

        Shader shader = new SweepGradient(getWidth() / 2, getHeight() / 2,
                resIds, null);


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

    private void saveButtonOriginalPosition() {
        if (!mButtonPositionSaved) {
            if (mOriginalX == -1) {
                mOriginalX = getX();
            }

            if (mOriginalY == -1) {
                mOriginalY = getY();
            }

            mButtonPositionSaved = true;
        }
    }

}
