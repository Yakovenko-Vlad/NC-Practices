package ua.edu.sumdu.j2se.vladislavY.tasks;

import java.io.Serializable;
import java.util.Date;

/**
 * Main task class
 *
 * @author vladislav
 */
public class Task implements Cloneable, Serializable {
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;

    public Task(String title, Date time) throws Exception {
        this.setTitle(title);
        this.setTime(time);
        this.setActive(false);
    }

    public Task(String title, Date start, Date end, int interval) throws Exception {
        this.setTitle(title);
        this.setTime(start, end, interval * 1000);
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

    public Date getTime() {
        return this.isRepeated() ? start : time;
    }

    public void setTime(Date time) throws Exception {
        if (time.equals(null)) throw new Exception("time cannot be NULL");
        this.time = time;
        if (isRepeated()) {
            this.start = time;
            this.end = time;
            this.interval = 0;
        }
    }

    public Date getStartTime() {
        return isRepeated() ? start : time;
    }

    public Date getEndTime() {
        return isRepeated() ? end : time;
    }

    public void setTime(Date start, Date end, int interval) throws Exception {
        if (start.equals(null) || end.equals(null))
            throw new Exception("time cannot be NULL");
        if (interval <= 0)
            throw new Exception("interval cannot be <= 0");
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
     * `null` if the next time will be after the end of the task
     */
    public Date nextTimeAfter(Date current) {
        if (isActive()) {
            if (isRepeated()) {
                Date nextTime = new Date(((current.getTime() - start.getTime()) / interval)
                        * interval + interval + start.getTime());
                return current.before(start) ? start : (nextTime.after(end) ? null : nextTime);
            } else {
                return time.after(current) ? time : null;
            }
        } else return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Task) || this.hashCode() != o.hashCode()) return false;
        Task task = (Task) o;
        return time.equals(task.time) &&
                interval == task.interval &&
                active == task.active &&
                title.equals(((Task) o).title);
    }

    @Override
    public int hashCode() {
        int hash = 23;
        hash += hash * interval;
        hash += hash * title.length();
        if (time != null)  hash += hash * time.getTime();
        return hash;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Task task = null;
        try {
            task = new Task(this.getTitle(), this.start, this.end, this.interval);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }
}