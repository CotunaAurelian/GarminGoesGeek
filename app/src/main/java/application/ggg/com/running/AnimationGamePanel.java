/**
 *
 */
package application.ggg.com.running;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import application.ggg.com.R;

/**
 * @author impaler
 *         This is the main surface that handles the ontouch events and draws
 *         the image to the screen.
 */
public class AnimationGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = AnimationGamePanel.class.getSimpleName();

    private MainThread thread;
    private StickmanAnimated elaine;

    // the fps to be displayed
    private String avgFps;

    public void setAvgFps(String avgFps) {
        this.avgFps = avgFps;
    }

    public AnimationGamePanel(Context context, int animationType) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSPARENT);

        // create Elaine and load bitmap
        if (animationType == 1) {
            elaine = new StickmanAnimated(BitmapFactory.decodeResource(getResources(), R.mipmap.running), 10, 50    // initial position
                    , 123, 128    // width and height of sprite
                    , 14, 7);    // FPS and number of frames in the animation
        } else {
            elaine = new StickmanAnimated(BitmapFactory.decodeResource(getResources(), R.mipmap.walk), 50, 40    // initial position
                    , 64, 128    // width and height of sprite
                    , 24, 12);    // FPS and number of frames in the animation
        }

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop
        thread.interrupt();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // handle touch
        }
        return true;
    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#F5F2F0"));
        elaine.draw(canvas);
    }

    /**
     * This is the game update method. It iterates through all the objects
     * and calls their update method if they have one or calls specific
     * engine's update method.
     */
    public void update() {
        elaine.update(System.currentTimeMillis());
    }

}
