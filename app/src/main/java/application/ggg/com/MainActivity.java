package application.ggg.com;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;
import com.nineoldandroids.animation.Animator;
import com.viewpagerindicator.CirclePageIndicator;

import application.ggg.com.running.ActivityPiePagerAdapter;
import application.ggg.com.running.AnimationGamePanel;
import application.ggg.com.running.PieContentView;

public class MainActivity extends AppCompatActivity {
    private CircularBarPager mCircularBarPagerSelection1;
    private CircularBarPager mCircularBarPagerSelection2;

    private static final int BAR_ANIMATION_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPieContainer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCircularBarPagerSelection1.getCircularBar().animateProgress(0, 80, 1000);
        mCircularBarPagerSelection2.getCircularBar().animateProgress(0, 80, 1000);
    }

    private void initPieContainer() {
        RelativeLayout containerRunning = (RelativeLayout) findViewById(R.id.containerRunning);
        mCircularBarPagerSelection1 = (CircularBarPager) findViewById(R.id.circularBarPager1);
        mCircularBarPagerSelection2 = (CircularBarPager) findViewById(R.id.circularBarPager2);

        AnimationGamePanel gamePanel = new AnimationGamePanel(this);
        gamePanel.setBackground(null);
        gamePanel.setLayoutParams(new LinearLayout.LayoutParams(170, 170));
        containerRunning.addView(gamePanel);

        initPie1();
        initPie2();
    }

    private void initPie1() {
        View[] views = new View[2];
        views[0] = new PieContentView(this);
        views[1] = new PieContentView(this);

        mCircularBarPagerSelection1.setViewPagerAdapter(new ActivityPiePagerAdapter(this, views));

        ViewPager viewPager = mCircularBarPagerSelection1.getViewPager();
        viewPager.setClipToPadding(true);

        CirclePageIndicator circlePageIndicator = mCircularBarPagerSelection1.getCirclePageIndicator();
        circlePageIndicator.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
        circlePageIndicator.setPageColor(ContextCompat.getColor(getApplicationContext(), R.color.very_light_grey));
        circlePageIndicator.setStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));

        //Do stuff based on when pages change
        circlePageIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if(mCircularBarPagerSelection1!= null && mCircularBarPagerSelection1.getCircularBar() != null){
                    switch (position){
                        case 0:
                            mCircularBarPagerSelection1.getCircularBar().animateProgress(0, 80, BAR_ANIMATION_TIME);
                            break;
                        case 1:
                            mCircularBarPagerSelection1.getCircularBar().animateProgress(80, 20, BAR_ANIMATION_TIME);
                            break;
                    }
                }
            }
        });
    }

    private void initPie2() {
        View[] views = new View[2];
        views[0] = new PieContentView(this);
        views[1] = new PieContentView(this);

        mCircularBarPagerSelection2.setViewPagerAdapter(new ActivityPiePagerAdapter(this, views));

        ViewPager viewPager = mCircularBarPagerSelection2.getViewPager();
        viewPager.setClipToPadding(true);

        CirclePageIndicator circlePageIndicator = mCircularBarPagerSelection2.getCirclePageIndicator();
        circlePageIndicator.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
        circlePageIndicator.setPageColor(ContextCompat.getColor(getApplicationContext(), R.color.very_light_grey));
        circlePageIndicator.setStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));

        //Do stuff based on when pages change
        circlePageIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if(mCircularBarPagerSelection2!= null && mCircularBarPagerSelection2.getCircularBar() != null){
                    switch (position){
                        case 0:
                            mCircularBarPagerSelection2.getCircularBar().animateProgress(0, 80, BAR_ANIMATION_TIME);
                            break;
                        case 1:
                            mCircularBarPagerSelection2.getCircularBar().animateProgress(80, 20, BAR_ANIMATION_TIME);
                            break;
                    }
                }
            }
        });
    }
}

