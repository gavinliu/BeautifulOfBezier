package cn.gavinliu.beautifulofbezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gavin on 15-4-1.
 */
public class BezierView extends View {

    private final Path mPath = new Path();
    private Paint mPaint;

    private DrawFilter mDrawFilter;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(0xFF009688);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);

        mPath.moveTo(100, 100);
        mPath.cubicTo(800, 100, 100, 800, 800, 800);

//        mPath.quadTo(800, 100, 100, 800);
//        mPath.quadTo(100, 800, 800, 800);

//        mPath.moveTo(100, 100);
//        mPath.cubicTo(200, 150, 700, 150, 800, 100);

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
