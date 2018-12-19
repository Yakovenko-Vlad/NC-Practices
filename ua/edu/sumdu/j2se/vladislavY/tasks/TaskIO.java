package ua.edu.sumdu.j2se.vladislavY.tasks;

import java.io.*;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskIO {
    public static final String DATE_PATTERN = "[yyyy-MM-dd HH:mm:ss.SSS]";

    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
            dataOutputStream.write(tasks.size());
            for (Task task : tasks) {
                dataOutputStream.writeInt(task.getTitle().length());
                dataOutputStream.writeUTF(task.getTitle());
                dataOutputStream.writeBoolean(task.isActive());
                dataOutputStream.writeInt(task.getRepeatInterval());
                if (task.isRepeated()) {
                    dataOutputStream.writeLong(task.getStartTime().getTime());
                    dataOutputStream.writeLong(task.getEndTime().getTime());
                } else {
                    dataOutputStream.writeLong(task.getTime().getTime());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }

    public static void read(TaskList tasks, InputStream in) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        try {
            int tasksSize = dataInputStream.readInt();
            for (int i = 0; i < tasksSize; i++) {
                Task task;
                String title = dataInputStream.readUTF();
                boolean isActive = dataInputStream.readBoolean();
                int interval = dataInputStream.readInt();
                if (interval != 0) {
                    Date start = new Date(dataInputStream.readLong());
                    Date end = new Date(dataInputStream.readLong());
                    task = new Task(title, start, end, interval);
                } else {
                    Date time = new Date(dataInputStream.readLong());
                    task = new Task(title, time);
                }
                task.setActive(isActive);
                tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataInputStream.close();
        }
    }

    public static void writeBinary(TaskList tasks, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            write(tasks, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(TaskList tasks, File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            read(tasks, fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(TaskList tasks, Writer out) throws IOException {
        int counter = 0;
        try {
            for (Task task : tasks) {
                out.write("\"" + task.getTitle().replaceAll("\"", "\"\"") + "\"");
                if (!task.isRepeated()) {
                    out.append(" at " + dateFormater(task.getTime()));
                    out.append(task.isActive() ? " active" : " inactive");
                } else {
                    out.append(" from " + dateFormater(task.getStartTime()) + " to ");
                    out.append(dateFormater(task.getEndTime()) + " every ");
                    out.append(intervalFormater(task.getRepeatInterval()));
                }
                if (counter++ != tasks.size() - 1) {
                    out.append(";");
                    out.write(System.getProperty("line.separator"));
                }
            }
            out.write(".");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

    public static StringBuffer dateFormater(Date date) {
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(date, stringBuffer, new FieldPosition(0));
    }

    public static String intervalFormater(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        hours %= 24;
        minutes %= 60;
        seconds %= 60;
        String time = "[";
        time += days > 0 ? days + (days > 1 ? " days " : " day ") : "";
        time += hours > 0 ? hours + (hours > 1 ? " hours " : " hour ") : "";
        time += minutes > 0 ? minutes + (minutes > 1 ? " minutes " : " minute ") : "";
        time += seconds > 0 ? seconds + (seconds > 1 ? " seconds" : " second") : "";
        time += "]";
        return time;
    }

    public static void read(TaskList tasks, Reader in) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("at")) {
                    tasks.add(new Task(parseTitle(line), parseDate(line)[0]));
                } else {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }

    public static Task parseRepeatedTask(String line) throws Exception {
        Date date = null;
        /*for(String str : line.split(" ")) {
            if(str.contains("[")) {
                if(str.matches("[a-zA-Z]")) {

                } else {
                    System.out.println(str);
                    date = parseDate(str);
                }
            }
        }*/
        return new Task(parseTitle(line), parseDate(line)[0], parseDate(line)[1], parseInterval(line));
    }

    public static int parseInterval(String line) {
        String[] numbers = line.substring(line.lastIndexOf("["), line.lastIndexOf("]")).split(" ");
        
        System.out.println(numbers);
        return 0;
    }

    public static String parseTitle(String line) {
        return line.substring(1, line.lastIndexOf("\""));
    }

    public static Date[] parseDate(String line) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(DATE_PATTERN);
        Date[] dates = new Date[2];
        for (int i = line.indexOf("["), counter = 0; counter < 2 && i > 0; i = line.indexOf("[", i + 1), counter++)
            dates[counter] = format.parse(line, new ParsePosition(i));
        return dates;
    }
    public static void writeText(TaskList tasks, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            write(tasks, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(TaskList tasks, File file) {
        try {
            FileReader fileReader = new FileReader(file);
            read(tasks, fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
