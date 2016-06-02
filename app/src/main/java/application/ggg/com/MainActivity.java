package application.ggg.com;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    private CircularBarPager mCircularBarPagerSelection3;
    private CircularBarPager mCircularBarPagerSelection4;

    private LineCardOne mTopGraphCardConfigurator;

    private static final int BAR_ANIMATION_TIME = 1000;
    private static final int ANIMATION_RUNNING = 1;
    private static final int ANIMATION_WALKING = 2;

    private static final int GOAL_RUNNING_TIME = 10;
    private static final int GOAL_RUNNING_DISTANCE = 10;
    private static final int GOAL_WALKING_TIME = 10;
    private static final int GOAL_WALKING_DISTANCE = 10;
    private List<PieDAO> pieValues;

    private RelativeLayout containerRunning;
    private RelativeLayout containerWalking;

    private int currentSelection;
    private int pastSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();
        initToolbar();

        initPieContainer();
        initActivitiesChart();
    }

    private void initActivitiesChart() {
        mTopGraphCardConfigurator = new LineCardOne((CardView) findViewById(R.id.top_graph_card), this);
        mTopGraphCardConfigurator.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        animatePies();
    }

    private void animatePies() {
        mCircularBarPagerSelection1.getCircularBar().animateProgress(0, pieValues.get(0).getTime() * 100 / GOAL_RUNNING_TIME, 1000);
        mCircularBarPagerSelection2.getCircularBar().animateProgress(0, pieValues.get(1).getTime() * 100 / GOAL_RUNNING_TIME, 1000);
        mCircularBarPagerSelection3.getCircularBar().animateProgress(0, pieValues.get(2).getTime() * 100 / GOAL_WALKING_TIME, 1000);
        mCircularBarPagerSelection4.getCircularBar().animateProgress(0, pieValues.get(3).getTime() * 100 / GOAL_WALKING_TIME, 1000);
    }

    private void initPieContainer() {
        mCircularBarPagerSelection1 = (CircularBarPager) findViewById(R.id.circularBarPager1);
        mCircularBarPagerSelection2 = (CircularBarPager) findViewById(R.id.circularBarPager2);
        mCircularBarPagerSelection3 = (CircularBarPager) findViewById(R.id.circularBarPager3);
        mCircularBarPagerSelection4 = (CircularBarPager) findViewById(R.id.circularBarPager4);

        int animationSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, getResources().getDisplayMetrics());

        containerRunning = (RelativeLayout) findViewById(R.id.containerRunning);
        AnimationGamePanel gamePanelRunning = new AnimationGamePanel(this, ANIMATION_RUNNING);
        gamePanelRunning.setLayoutParams(new LinearLayout.LayoutParams(animationSize, animationSize));
        containerRunning.addView(gamePanelRunning);

        containerWalking = (RelativeLayout) findViewById(R.id.containerWalking);
        AnimationGamePanel gamePanelWalking = new AnimationGamePanel(this, ANIMATION_WALKING);
        gamePanelWalking.setLayoutParams(new LinearLayout.LayoutParams(animationSize, animationSize));
        containerWalking.addView(gamePanelWalking);

        Random r = new Random();
        pieValues = new LinkedList<>();
        // Running
        pieValues.add(new PieDAO(r.nextInt(GOAL_RUNNING_TIME), r.nextInt(GOAL_RUNNING_DISTANCE), ANIMATION_RUNNING));
        pieValues.add(new PieDAO(r.nextInt(GOAL_RUNNING_TIME), r.nextInt(GOAL_RUNNING_DISTANCE), ANIMATION_RUNNING));
        // Walking
        pieValues.add(new PieDAO(r.nextInt(GOAL_WALKING_TIME), r.nextInt(GOAL_WALKING_DISTANCE), ANIMATION_WALKING));
        pieValues.add(new PieDAO(r.nextInt(GOAL_WALKING_TIME), r.nextInt(GOAL_WALKING_DISTANCE), ANIMATION_WALKING));

        initPie(mCircularBarPagerSelection1, pieValues.get(0));
        initPie(mCircularBarPagerSelection2, pieValues.get(1));
        initPie(mCircularBarPagerSelection3, pieValues.get(2));
        initPie(mCircularBarPagerSelection4, pieValues.get(3));
    }

    private void showActivityPies() {
        YoYo.with(Techniques.FadeIn).duration(BAR_ANIMATION_TIME/2).playOn(findViewById(R.id.cardRunning));
        YoYo.with(Techniques.FadeIn).duration(BAR_ANIMATION_TIME/2).playOn(findViewById(R.id.cardWalking));

        pieValues = new LinkedList<>();
        // Running
        pieValues.add(new PieDAO(8, 8, ANIMATION_RUNNING));
        pieValues.add(new PieDAO(7, 5, ANIMATION_RUNNING));
        // Walking
        pieValues.add(new PieDAO(4, 5, ANIMATION_WALKING));
        pieValues.add(new PieDAO(9, 7, ANIMATION_WALKING));

        initPie(mCircularBarPagerSelection1, pieValues.get(0));
        initPie(mCircularBarPagerSelection2, pieValues.get(1));
        initPie(mCircularBarPagerSelection3, pieValues.get(2));
        initPie(mCircularBarPagerSelection4, pieValues.get(3));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ugly, I know :)
                containerRunning.setVisibility(View.VISIBLE);
                containerWalking.setVisibility(View.VISIBLE);
            }
        }, BAR_ANIMATION_TIME);
    }

    private void hideActivityPies() {
        containerRunning.setVisibility(View.INVISIBLE);
        containerWalking.setVisibility(View.INVISIBLE);

        YoYo.with(Techniques.FadeOut).duration(BAR_ANIMATION_TIME/2).playOn(findViewById(R.id.cardRunning));
        YoYo.with(Techniques.FadeOut).duration(BAR_ANIMATION_TIME/2).playOn(findViewById(R.id.cardWalking));
    }

    private void initPie(final CircularBarPager circularBarPager, final PieDAO pieDAO) {
        View[] views = new View[2];
        views[0] = new PieContentView(this, pieDAO, 0);
        views[1] = new PieContentView(this, pieDAO, 1);

        circularBarPager.setViewPagerAdapter(new ActivityPiePagerAdapter(this, views));

        ViewPager viewPager = circularBarPager.getViewPager();
        viewPager.setClipToPadding(true);

        CirclePageIndicator circlePageIndicator = circularBarPager.getCirclePageIndicator();
        circlePageIndicator.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
        circlePageIndicator.setPageColor(ContextCompat.getColor(getApplicationContext(), R.color.very_light_grey));
        circlePageIndicator.setStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));

        //Do stuff based on when pages change
        circlePageIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                ActivityPiePagerAdapter adapter = (ActivityPiePagerAdapter)circularBarPager.getViewPager().getAdapter();
                PieDAO tempPieDAO = ((PieContentView)adapter.mViews[0]).getPieDAO();

                if (circularBarPager!= null && circularBarPager.getCircularBar() != null){
                    switch (position) {
                        case 0:
                            if (tempPieDAO.getActivityType() == ANIMATION_RUNNING) {
                                circularBarPager.getCircularBar().animateProgress(0, tempPieDAO.getTime() * 100 / GOAL_RUNNING_TIME, BAR_ANIMATION_TIME);
                            } else {
                                circularBarPager.getCircularBar().animateProgress(0, tempPieDAO.getTime() * 100 / GOAL_WALKING_TIME, BAR_ANIMATION_TIME);
                            }
                            break;
                        case 1:
                            if (tempPieDAO.getActivityType() == ANIMATION_RUNNING) {
                                circularBarPager.getCircularBar().animateProgress(tempPieDAO.getTime() * 100 / GOAL_RUNNING_TIME, tempPieDAO.getDistance() * 100 / GOAL_RUNNING_DISTANCE, BAR_ANIMATION_TIME);
                            } else {
                                circularBarPager.getCircularBar().animateProgress(tempPieDAO.getTime() * 100 / GOAL_WALKING_TIME, tempPieDAO.getDistance() * 100 / GOAL_WALKING_DISTANCE, BAR_ANIMATION_TIME);
                            }
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

        switch (position) {
            default:
            case 0:
                mTopGraphCardConfigurator.updateData(position - 1, R.color.walk_color);
                break;
            case 1:
                mTopGraphCardConfigurator.updateData(position - 1, R.color.run_color);
                break;
            case 2:
                mTopGraphCardConfigurator.updateData(position - 1, R.color.color_dark_orange);
                break;
            case 3:
                mTopGraphCardConfigurator.updateData(position - 1, R.color.color_deep_purple);
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
    }

    public void onButtonClick(View view){
        view.setActivated(!view.isActivated());

        if (currentSelection == 0) {
            currentSelection = view.getId();
        } else if (pastSelection == 0) {
            pastSelection = view.getId();
        } else {
            View toBeDismissed = findViewById(pastSelection);
            toBeDismissed.setActivated(false);

            pastSelection = currentSelection;
            currentSelection = view.getId();
        }

        initPieContainer();
        animatePies();
    }
}
