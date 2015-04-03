package cn.gavinliu.beautifulofbezier;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavin on 15-4-2.
 */
public class DropPagerIndicatorActivity extends ActionBarActivity {

    private DropPagerIndicator indicator;
    private ViewPager viewPager;

    private List<View> views;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        viewPager = (ViewPager) findViewById(R.id.pager);
        indicator = (DropPagerIndicator) findViewById(R.id.indicator);


        views = new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.layout_pager, null));
        views.add(getLayoutInflater().inflate(R.layout.layout_pager, null));
        views.add(getLayoutInflater().inflate(R.layout.layout_pager, null));
        views.add(getLayoutInflater().inflate(R.layout.layout_pager, null));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(0xFFFF0000);
        colors.add(0xFF00FF00);
        colors.add(0xFF0000FF);
        colors.add(0xFFFF00FF);
        indicator.setColors(colors);
        indicator.setPagerCount(views.size());
        indicator.setMode(DropPagerIndicator.MODE_BEND);



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.setPositionAndOffset(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }


            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position), 0);
                return views.get(position);
            }
        });
    }

}
