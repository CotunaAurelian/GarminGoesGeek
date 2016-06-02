package application.ggg.com.running;

/**
 * Created by simina on 6/2/16.
 */
public class PieDAO {
    private int time;
    private int distance;
    private int activityType;

    public PieDAO(int time, int distance, int activityType) {
        this.time = time;
        this.distance = distance;
        this.activityType = activityType;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }
}
