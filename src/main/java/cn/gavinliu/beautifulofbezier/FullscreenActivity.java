package cn.gavinliu.beautifulofbezier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class FullscreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

    }

    public void onBezierView(View view) {
        Intent intent = new Intent(this, BezierViewActivity.class);
        startActivity(intent);
    }

    public void onDropPagerIndicator(View view) {
        Intent intent = new Intent(this, DropPagerIndicatorActivity.class);
        startActivity(intent);
    }

    public void onBezierEvaluator(View view) {
        Intent intent = new Intent(this, BezierEvaluatorActivity.class);
        startActivity(intent);
    }

}
