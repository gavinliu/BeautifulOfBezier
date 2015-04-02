package cn.gavinliu.beautifulofbezier;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

/**
 * Created by gavin on 15-4-2.
 */
public class BezierEvaluatorActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_evaluator);

        final TextView textView = (TextView) findViewById(R.id.textView);

        PointF p0 = new PointF(100, 100);
        PointF p1 = new PointF(800, 100);
        PointF p2 = new PointF(100, 800);
        PointF p3 = new PointF(800, 800);

        BezierEvaluator evaluator = new BezierEvaluator(p0, p1, p2, p3);

        ValueAnimator animator = ValueAnimator.ofObject(evaluator, p0, p3)
                .setDuration(4500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                textView.setX(pointF.x);
                textView.setY(pointF.y);
            }

        });

        animator.start();

    }

}
