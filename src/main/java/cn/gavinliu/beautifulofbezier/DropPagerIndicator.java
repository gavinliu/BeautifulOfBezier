package cn.gavinliu.beautifulofbezier;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavin on 15-4-2.
 */
public class DropPagerIndicator extends View {

    private List<Integer> mColors;

    private ArrayList<Animator> animators;

    private int mWidth;
    private int mHeight;

    private float fromCircleRadius;
    private float fromCircleX;

    private float toCircleRadius;
    private float toCircleX;

    private List<PointF> mPoints;

    private float mMaxRadius;

    private int mPagerCount;

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
        animators = new ArrayList<Animator>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void createAnimator() {
        animators.clear();
    }

    private void seekAnimator() {

    }

    public void setPositionAndOffset(int position, float offSet) {
        createAnimator();
        seekAnimator();
    }

    public float getFromCircleRadius() {
        return fromCircleRadius;
    }

    public void setFromCircleRadius(float fromCircleRadius) {
        this.fromCircleRadius = fromCircleRadius;
    }

    public float getFromCircleX() {
        return fromCircleX;
    }

    public void setFromCircleX(float fromCircleX) {
        this.fromCircleX = fromCircleX;
    }

    public float getToCircleRadius() {
        return toCircleRadius;
    }

    public void setToCircleRadius(float toCircleRadius) {
        this.toCircleRadius = toCircleRadius;
    }

    public float getToCircleX() {
        return toCircleX;
    }

    public void setToCircleX(float toCircleX) {
        this.toCircleX = toCircleX;
    }
}
