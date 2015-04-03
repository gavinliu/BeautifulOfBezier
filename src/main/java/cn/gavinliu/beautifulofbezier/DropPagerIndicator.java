package cn.gavinliu.beautifulofbezier;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class DropPagerIndicator extends View {

    private List<Integer> mColors;

    private ArrayList<ValueAnimator> mAnimators;

    private int mWidth;
    private int mHeight;

    private float leftCircleRadius;
    private float leftCircleX;

    private float rightCircleRadius;
    private float rightCircleX;

    private List<PointF> mPoints;

    private int mPagerCount;

    private float mMaxCircleRadius;
    private float mMinCircleRadius;

    private Paint mPaint;
    private Path mPath = new Path();

    private int mMode = MODE_NORMAL;

    public static final int MODE_NORMAL = 1;
    public static final int MODE_BEND = 2;

    public DropPagerIndicator(Context context) {
        this(context, null);
    }

    public DropPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mAnimators = new ArrayList<>();
        mPoints = new ArrayList<>();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(leftCircleX, mHeight / 2, leftCircleRadius, mPaint);
        canvas.drawCircle(rightCircleX, mHeight / 2, rightCircleRadius, mPaint);

        switch (mMode) {
            case MODE_NORMAL:
                drawModeNormal(canvas);
                break;

            case MODE_BEND:
                drawModeBend(canvas);
                break;
        }

    }

    private void drawModeNormal(Canvas canvas) {
        mPath.reset();

        mPath.moveTo(rightCircleX, mHeight / 2);

        mPath.lineTo(rightCircleX, mHeight / 2 - rightCircleRadius);

        mPath.quadTo(rightCircleX,
                mHeight / 2 - rightCircleRadius,

                leftCircleX,
                mHeight / 2 - leftCircleRadius);

        mPath.lineTo(leftCircleX, mHeight / 2 + leftCircleRadius);

        mPath.quadTo(leftCircleX,
                mHeight / 2 + leftCircleRadius,

                rightCircleX,
                mHeight / 2 + rightCircleRadius);

        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    private void drawModeBend(Canvas canvas) {
        float middleOffset = (leftCircleX - rightCircleX) / (mPoints.get(1).x - mPoints.get(0).x) * (mHeight / 10);

        mPath.reset();

        mPath.moveTo(rightCircleX, mHeight / 2);

        mPath.lineTo(rightCircleX, mHeight / 2 - rightCircleRadius);

        mPath.cubicTo(rightCircleX,
                mHeight / 2 - rightCircleRadius,

                rightCircleX + (leftCircleX - rightCircleX) / 2.0F,
                mHeight / 2 + middleOffset,

                leftCircleX,
                mHeight / 2 - leftCircleRadius);

        mPath.lineTo(leftCircleX, mHeight / 2 + leftCircleRadius);

        mPath.cubicTo(leftCircleX,
                mHeight / 2 + leftCircleRadius,

                rightCircleX + (leftCircleX - rightCircleX) / 2.0F,
                mHeight / 2 - middleOffset,

                rightCircleX,
                mHeight / 2 + rightCircleRadius);

        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mMaxCircleRadius = 0.45f * mHeight;
        mMinCircleRadius = 0.15f * mHeight;

        resetPoint();
    }

    private void createAnimator(int paramInt) {
        if (mPoints.isEmpty()) {
            return;
        }
        mAnimators.clear();

        int i = Math.min(mPagerCount - 1, paramInt + 1);

        float leftX = mPoints.get(paramInt).x;
        float rightX = mPoints.get(i).x;

        ObjectAnimator leftPointAnimator = ObjectAnimator.ofFloat(this, "rightCircleX", leftX, rightX);
        leftPointAnimator.setDuration(5000L);
        leftPointAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimators.add(leftPointAnimator);

        ObjectAnimator rightPointAnimator = ObjectAnimator.ofFloat(this, "leftCircleX", leftX, rightX);
        rightPointAnimator.setDuration(5000L);
        rightPointAnimator.setInterpolator(new AccelerateInterpolator(1.5F));
        mAnimators.add(rightPointAnimator);

        ObjectAnimator leftCircleRadiusAnimator = ObjectAnimator.ofFloat(this, "rightCircleRadius", mMinCircleRadius, mMaxCircleRadius);
        leftCircleRadiusAnimator.setDuration(5000L);
        leftCircleRadiusAnimator.setInterpolator(new AccelerateInterpolator(1.5F));
        mAnimators.add(leftCircleRadiusAnimator);

        ObjectAnimator rightCircleRadiusAnimator = ObjectAnimator.ofFloat(this, "leftCircleRadius", mMaxCircleRadius, mMinCircleRadius);
        rightCircleRadiusAnimator.setDuration(5000L);
        rightCircleRadiusAnimator.setInterpolator(new DecelerateInterpolator(0.8F));
        mAnimators.add(rightCircleRadiusAnimator);

        int color1 = mColors.get(paramInt);
        int color2 = mColors.get(i);
        ValueAnimator paintColorAnimator = ObjectAnimator.ofInt(color1, color2);
        paintColorAnimator.setDuration(5000L);
        paintColorAnimator.setEvaluator(new ArgbEvaluator());
        paintColorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        paintColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            public void onAnimationUpdate(ValueAnimator animator) {
                mPaint.setColor((Integer) animator.getAnimatedValue());
            }
        });

        mAnimators.add(paintColorAnimator);
    }

    private void seekAnimator(float offset) {
        for (ValueAnimator animator : mAnimators) {
            animator.setCurrentPlayTime((long) (5000.0F * offset));
        }

        postInvalidate();
    }

    public void setPositionAndOffset(int position, float offSet) {
        createAnimator(position);
        seekAnimator(offSet);
    }

    private void resetPoint() {
        mPoints.clear();
        for (int i = 0; i < mPagerCount; i++) {
            int x = mWidth / (mPagerCount + 1) * (i + 1);
            mPoints.add(new PointF(x, 0));
        }

        if (!mPoints.isEmpty()) {
            leftCircleX = mPoints.get(0).x;
            leftCircleRadius = mMaxCircleRadius;

            rightCircleX = mPoints.get(0).x;
            rightCircleRadius = mMinCircleRadius;
            postInvalidate();
        }
    }

    public void setPagerCount(int pagerCount) {
        mPagerCount = pagerCount;
    }

    public void setColors(List<Integer> colors) {
        mColors = colors;

        if (!colors.isEmpty()) {
            mPaint.setColor(colors.get(0));
        }
    }

    public void setMode(int mode) {
        this.mMode = mode;
    }


    public float getLeftCircleX() {
        return leftCircleX;
    }

    public void setLeftCircleX(float leftCircleX) {
        this.leftCircleX = leftCircleX;
    }

    public float getLeftCircleRadius() {
        return leftCircleRadius;
    }

    public void setLeftCircleRadius(float leftCircleRadius) {
        this.leftCircleRadius = leftCircleRadius;
    }

    public float getRightCircleRadius() {
        return rightCircleRadius;
    }

    public void setRightCircleRadius(float rightCircleRadius) {
        this.rightCircleRadius = rightCircleRadius;
    }

    public float getRightCircleX() {
        return rightCircleX;
    }

    public void setRightCircleX(float rightCircleX) {
        this.rightCircleX = rightCircleX;
    }
}
