package application.ggg.com;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BounceEase;


public class LineCardOne {


    private final LineChartView mChart;


    private final Context mContext;

    private RelativeLayout mTooltipRootView;
    private ImageView mToolTipIconImageView;
    private TextView mSportTitleTextView;


    private final String[] mLabels = { "", "", "", "", "", "", "" };
    private final float[][] mValues = { { 6f, 3f, 2f, 5f, 8f, 2f, 10f }, { 3f, 3f, 6f, 0f, 2f, 1f, 9f }, { 5f, 3f, 4f, 3f, 8f, 2f, 1f },
                    { 1f, 3f, 6f, 4f, 2f, 1f, 9f } };

    private Tooltip mTip;

    public LineCardOne(CardView card, Context context) {
        mContext = context;
        mChart = (LineChartView) card.findViewById(R.id.top_chart);
        mSportTitleTextView = (TextView) card.findViewById(R.id.top_chart_title);
    }


    public void show() {

        // Tooltip
        mTip = new Tooltip(mContext, R.layout.activity_graph_tooltip_layout, R.id.value);


        mTooltipRootView = (RelativeLayout) mTip.findViewById(R.id.tooltip_root_view);
        mToolTipIconImageView = (ImageView) mTip.findViewById(R.id.tooltip_image_view);

        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(65), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1), PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0), PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                            PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            mTip.setPivotX(Tools.fromDpToPx(65) / 2);
            mTip.setPivotY(Tools.fromDpToPx(25));
        }

        mChart.setTooltips(mTip);


        updateData(0, R.color.walk_color);
    }

    public void updateData(int sportSelected, int color) {

        mChart.reset();
        mChart.dismissAllTooltips();

        int backgroundResource = R.drawable.rect_round_corners_walking;
        int sportResource = R.drawable.gcm3_profile_activity_icon_walking_on;
        mSportTitleTextView.setText("Walking");

        switch (sportSelected){
            case 0:
            default:
                break;

            case 1:
                //Running
                mSportTitleTextView.setText("Running");
                backgroundResource = R.drawable.rect_round_corners_running;
                sportResource = R.drawable.gcm3_profile_activity_icon_running_on;
                break;

            case 2:
                //Cycling
                mSportTitleTextView.setText("Cycling");
                backgroundResource = R.drawable.rect_round_corners_cycling;
                sportResource = R.drawable.gcm3_profile_activity_icon_cycling_on;
                break;

            case 3:
                //Multisports
                mSportTitleTextView.setText("Multisport");
                backgroundResource = R.drawable.rect_round_corners_multisport;
                sportResource = R.drawable.gcm3_profile_activity_icon_triathlon_on;
                break;

        }

        mToolTipIconImageView.setImageResource(sportResource);


        // Data
        LineSet dataset = new LineSet(mLabels, mValues[sportSelected]);
        dataset.setColor(Color.parseColor("#758cbb")).setDotsRadius(15).
                        setDotsColor(mContext.getResources().getColor(color)).
                        setThickness(4);
        mChart.addData(dataset);

        // Chart
        mChart.setBorderSpacing(Tools.fromDpToPx(15)).
                        setAxisBorderValues(-2, 13).
                        setYLabels(AxisController.LabelPosition.NONE).
                        setLabelsColor(Color.parseColor("#646464")).
                        setXAxis(false).
                        setYAxis(false);

        Runnable chartAction = new Runnable() {
            @Override
            public void run() {
                mTip.prepare(mChart.getEntriesArea(0).get(3), mValues[0][3]);
                mChart.removeAllViews();
                mChart.showTooltip(mTip, true);
            }
        };

        Animation anim = new Animation().setEasing(new BounceEase()).setEndAction(chartAction);

        mChart.show(anim);
    }


}
