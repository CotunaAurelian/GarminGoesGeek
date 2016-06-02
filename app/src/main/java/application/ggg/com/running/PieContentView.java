package application.ggg.com.running;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import application.ggg.com.R;

/**
 * Created by simina on 6/2/16.
 */
public class PieContentView extends LinearLayout {

    public PieContentView(Context context, PieDAO pieDAO, int index) {
        super(context);

        initView(pieDAO, index);
    }

    private void initView(PieDAO pieDAO, int index) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout mainV = (LinearLayout) inflater.inflate(R.layout.pie_content, this);

        TextView pieTextTop = (TextView)mainV.findViewById(R.id.pie_top);
        TextView pieTextBottom = (TextView)mainV.findViewById(R.id.pie_bottom);
        if (index == 0) {
            pieTextTop.setText("Time");
            pieTextBottom.setText(pieDAO.getTime() + " h");
        } else {
            pieTextTop.setText("Distance");
            pieTextBottom.setText(pieDAO.getDistance() + " km");
        }
    }

}
