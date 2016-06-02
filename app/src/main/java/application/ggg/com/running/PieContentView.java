package application.ggg.com.running;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import application.ggg.com.R;

/**
 * Created by simina on 6/2/16.
 */
public class PieContentView extends LinearLayout {
    /**
     * TAG for logging
     */
    private static final String TAG = "HomeUserView";

    public PieContentView(Context context) {
        super(context);

        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout mainV = (LinearLayout) inflater.inflate(R.layout.pie_content, this);

        //TODO init view
    }

}
