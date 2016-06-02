package application.ggg.com.running;

/**
 * Created by simina on 6/2/16.
 */
public class PieDAO {
    private float time;
    private float distance;

    public PieDAO(float time, float distance) {
        this.time = time;
        this.distance = distance;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
