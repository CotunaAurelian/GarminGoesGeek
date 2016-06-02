package application.ggg.com;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;
import com.viewpagerindicator.CirclePageIndicator;

import application.ggg.com.running.ActivityPiePagerAdapter;
import application.ggg.com.running.AnimationGamePanel;
import application.ggg.com.running.PieContentView;
import application.ggg.com.running.PieDAO;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    private Toolbar mToolbar;

    private CircularBarPager mCircularBarPagerSelection1;
    private CircularBarPager mCircularBarPagerSelection2;

    private static final int BAR_ANIMATION_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();
        initToolbar();

        initPieContainer();
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

        initPie1(new PieDAO(12, 5));
        initPie2(new PieDAO(12, 5));
    }

    private void initPie1(PieDAO pieDAO) {
        View[] views = new View[2];
        views[0] = new PieContentView(this, pieDAO, 0);
        views[1] = new PieContentView(this, pieDAO, 1);

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

    private void initPie2(PieDAO pieDAO) {
        View[] views = new View[2];
        views[0] = new PieContentView(this, pieDAO, 0);
        views[1] = new PieContentView(this, pieDAO, 1);

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

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics()));
        menuParams.setAnimationDuration(70);
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClipToPadding(true);
        menuParams.setClosableOutside(true);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {


        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject topObject = new MenuObject();
        topObject.setBgColor(android.R.color.transparent);
        topObject.setDividerColor(android.R.color.transparent);

        MenuObject walkingObjects = new MenuObject("Walking");
        walkingObjects.setDrawable(getDrawable(R.drawable.gcm3_profile_activity_icon_walking_on));
        walkingObjects.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        MenuObject runningObject = new MenuObject("Running");
        runningObject.setDrawable(getDrawable(R.drawable.gcm3_profile_activity_icon_running_on));
        runningObject.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        MenuObject cyclingObject = new MenuObject("Cycling");
        cyclingObject.setDrawable(getDrawable(R.drawable.gcm3_profile_activity_icon_cycling_on));
        cyclingObject.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        MenuObject multiSports = new MenuObject("Multisport");
        multiSports.setDrawable(getDrawable(R.drawable.gcm3_profile_activity_icon_triathlon_on));
        multiSports.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        menuObjects.add(topObject);
        menuObjects.add(walkingObjects);
        menuObjects.add(runningObject);
        menuObjects.add(cyclingObject);
        menuObjects.add(multiSports);

        return menuObjects;
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("Garmin Goes Geek");
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}
