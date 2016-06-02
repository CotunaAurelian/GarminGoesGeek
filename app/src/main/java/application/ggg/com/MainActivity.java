package application.ggg.com;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import application.ggg.com.running.AnimationGamePanel;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    private Toolbar mToolbar;
    AnimationGamePanel mGamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();
        initToolbar();


        RelativeLayout containerRunning = (RelativeLayout) findViewById(R.id.containerRunning);

        mGamePanel= new AnimationGamePanel(this);
        mGamePanel.setBackground(null);
        mGamePanel.setLayoutParams(new LinearLayout.LayoutParams(170, 170));

       // containerRunning.addView(mGamePanel);
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
