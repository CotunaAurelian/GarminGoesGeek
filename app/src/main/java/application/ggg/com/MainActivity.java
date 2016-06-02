package application.ggg.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import application.ggg.com.running.AnimationGamePanel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout containerRunning = (RelativeLayout) findViewById(R.id.containerRunning);

        AnimationGamePanel gamePanel = new AnimationGamePanel(this);
        gamePanel.setBackground(null);
        gamePanel.setLayoutParams(new LinearLayout.LayoutParams(170, 170));
        containerRunning.addView(gamePanel);

        //setContentView(new MainGamePanel(this));
    }
}

