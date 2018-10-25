package ua.edu.sumdu.j2se.vladislavY.tasks;

/**
 * Main task class
 *
 * @author Vladisla
 */
public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;

    public Task(String title, int time) throws Exception {
        this.setTitle(title);
        this.setTime(time);
        this.setActive(false);
    }

    public Task(String title, int start, int end, int interval) throws Exception {
        this.setTitle(title);
        this.setTime(start, end, interval);
        this.setActive(false);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTime() {
        return this.isRepeated() ? start : time;
    }

    public void setTime(int time) throws Exception {
        if (time < 0) throw new Exception("time cannot be < 0");
        this.time = time;
        if (isRepeated()) {
            this.start = time;
            this.end = time;
            this.interval = 0;
        }
    }

    public int getStartTime() {
        return isRepeated() ? start : time;
    }

    public int getEndTime() {
        return isRepeated() ? end : time;
    }

    public void setTime(int start, int end, int interval) throws Exception {
        if (start < 0 || end < 0 || interval < 0)
            throw new Exception("time cannot be <= 0");
        if (interval == 0)
            throw new Exception("interval cannot be == 0");
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public int getRepeatInterval() {
        return interval;
    }

    /**
     * Verify is the task is repeated
     *
     * @return is current task repeated
     */
    public boolean isRepeated() {
        return interval != 0;
    }

    /**
     * Define next task time after current time
     *
     * @param current current time
     * @return next task time after current time OR
     * `-1` if the next time will be after the end of the task
     */
    public int nextTimeAfter(int current) {
        if (isActive()) {
            if (isRepeated()) {
                int nextTime = ((current - start) / interval)
                        * interval + interval + start;
                return current < start ? start
                        : (nextTime > end ? -1 : nextTime);
            } else {
                return time > current ? time : -1;
            }
        } else return -1;
    }
}