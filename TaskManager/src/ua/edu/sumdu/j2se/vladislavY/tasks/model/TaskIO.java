package ua.edu.sumdu.j2se.vladislavY.tasks.model;

import java.io.*;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;


public class TaskIO {
    public static final String DATE_PATTERN = "[yyyy-MM-dd HH:mm:ss.SSS]";
    private static final Logger log = Logger.getLogger(TaskIO.class);

    /**
     * Write tasks list to the stream
     *
     * @param tasks tasks list
     * @param out   stream
     * @throws IOException
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
            dataOutputStream.writeInt(tasks.size());
            for (Task task : tasks) {
                dataOutputStream.writeInt(task.getTitle().length());
                dataOutputStream.writeUTF(task.getTitle());
                dataOutputStream.writeBoolean(task.isActive());
                dataOutputStream.writeInt(task.getRepeatInterval());
                if (task.isRepeated()) {
                    dataOutputStream.writeLong(task.getStartTime().getTime());
                    dataOutputStream.writeLong(task.getEndTime().getTime());
                } else
                    dataOutputStream.writeLong(task.getTime().getTime());
            }
        } catch (IOException e) {
            log.error(e.toString());
        } finally {
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }

    /**
     * Read tasks list to the stream
     *
     * @param tasks tasks list
     * @param in    stream
     * @throws IOException
     */
    public static void read(TaskList tasks, InputStream in) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        try {
            int listSize = dataInputStream.readInt();
            for (int i = 0; i < listSize; i++) {
                Task task;
                int titleLength = dataInputStream.readInt();
                String title = dataInputStream.readUTF();
                boolean isActive = dataInputStream.readBoolean();
                int interval = dataInputStream.readInt();
                if (interval != 0) {
                    Date start = new Date(dataInputStream.readLong());
                    Date end = new Date(dataInputStream.readLong());
                    task = new Task(title, start, end, interval / 1000);
                } else {
                    Date time = new Date(dataInputStream.readLong());
                    task = new Task(title, time);
                }
                task.setActive(isActive);
                tasks.add(task);
            }
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            dataInputStream.close();
        }
    }

    /**
     * Write binary tasks list to the file
     *
     * @param tasks tasks list
     * @param file  where to write the list
     */
    public static void writeBinary(TaskList tasks, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            write(tasks, fileOutputStream);
        } catch (FileNotFoundException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    /**
     * Read binary tasks list to the file
     *
     * @param tasks tasks list
     * @param file  where to read the list
     */
    public static void readBinary(TaskList tasks, File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            read(tasks, fileInputStream);
        } catch (FileNotFoundException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    /**
     * Write tasks list to the writer
     *
     * @param tasks tasks list
     * @param out   Output stream
     * @throws IOException
     */
    public static void write(TaskList tasks, Writer out) throws IOException {
        int counter = 0;
        try {
            for (Task task : tasks) {
                out.write("\"" + task.getTitle().replaceAll("\"", "\"\"") + "\"");
                if (!task.isRepeated()) { // not reapeated task
                    out.append(" at " + dateFormater(task.getTime()));
                    out.append(task.isActive() ? " active" : " inactive");
                } else { // repeated task
                    out.append(" from " + dateFormater(task.getStartTime()) + " to ");
                    out.append(dateFormater(task.getEndTime()) + " every ");
                    out.append(intervalFormater(task.getRepeatInterval()));
                    out.append(task.isActive() ? " active" : " inactive");
                }
                if (counter++ != tasks.size() - 1) {
                    out.append(";");
                    out.write(System.getProperty("line.separator"));
                }
            }
            out.write("");
        } catch (IOException e) {
            log.error(e.toString());
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * Convert date to the string according to the given format
     *
     * @param date date
     * @return date, converted to the string
     */
    public static StringBuffer dateFormater(Date date) {
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(date, stringBuffer, new FieldPosition(0));
    }

    /**
     * Convert task interval to the string
     *
     * @param milliseconds task interval
     * @return milliseconds, converted to he string
     */
    public static String intervalFormater(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        //getting integer values ​​of each parameter
        hours %= 24;
        minutes %= 60;
        seconds %= 60;
        String time = "["; // -> joins days, hours, minutes and seconds if each value is not 0;
        time += days > 0 ? days + (days > 1 ? " days " : " day ") : "";// an ending is added for each plural value
        time += hours > 0 ? hours + (hours > 1 ? " hours " : " hour ") : "";
        time += minutes > 0 ? minutes + (minutes > 1 ? " minutes " : " minute ") : "";
        time += seconds > 0 ? seconds + (seconds > 1 ? " seconds" : " second") : "";
        time += "]";
        return time;
    }

    /**
     * Read tasks list from the reader
     *
     * @param tasks tasks list
     * @param in    Input stream
     * @throws IOException
     */
    public static void read(TaskList tasks, Reader in) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("at")) {
                    Task task = new Task(parseTitle(line), parseDate(line)[0]);
                    task.setActive(checkActive(line));
                    tasks.add(task);
                } else {
                    Task task = new Task(parseTitle(line), parseDate(line)[0], parseDate(line)[1], parseInterval(line));
                    task.setActive(checkActive(line));
                    tasks.add(task);
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            in.close();
        }
    }

    /**
     * Checks if read task is active
     *
     * @param line
     * @return
     */
    public static boolean checkActive(String line) {
        return line.contains("inactive") ? false : true;
    }

    /**
     * Parses task string and returns interval in ms. Gets latest text in [], convert to String array and
     * depending on the number of items - multiplies by the required value (H-3600б, M-60, MS-1)
     *
     * @param line
     * @return task interval
     */
    public static int parseInterval(String line) {
        String[] numbers = line.substring(line.lastIndexOf("[") + 1, line.lastIndexOf("]")).split(" ");
        int interval = 0;
        if (numbers.length > 1) {
            if (numbers[1].contains("hour"))
                interval += Integer.parseInt(numbers[0]) * 3600;
            else if (numbers[1].contains("minute"))
                interval += Integer.parseInt(numbers[0]) * 60;
            else interval += Integer.parseInt(numbers[0]);
        }
        if (numbers.length > 3) {
            if (numbers[1].contains("minute"))
                interval += Integer.parseInt(numbers[2]) * 60;
            else interval += Integer.parseInt(numbers[2]);
        }
        if (numbers.length > 5) interval += Integer.parseInt(numbers[4]);
        return interval;
    }

    /**
     * Parses task title from string
     *
     * @param line
     * @return task title
     */
    public static String parseTitle(String line) {
        return line.substring(1, line.lastIndexOf("\"")).replaceAll("\"\"", "\"");
    }


    /**
     * Parses string from file to get task dates
     *
     * @param line
     * @return array of the task dates (time or start/end)
     * @throws ParseException
     */
    public static Date[] parseDate(String line) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(DATE_PATTERN);
        Date[] dates = new Date[2];
        for (int i = line.indexOf("["), counter = 0; counter < 2 && i > 0; i = line.indexOf("[", i + 1), counter++)
            dates[counter] = format.parse(line, new ParsePosition(i));
        return dates;
    }

    /**
     * Write tasks list to the given file
     *
     * @param tasks tasks list
     * @param file  where to read the list
     */
    public static void writeText(TaskList tasks, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            write(tasks, fileWriter);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    /**
     * Read tasks list from the given file
     *
     * @param tasks tasks list
     * @param file  where to read the list
     */
    public static void readText(TaskList tasks, File file) {
        try {
            FileReader fileReader = new FileReader(file);
            read(tasks, fileReader);
        } catch (IOException e) {
            log.info("File is not created");
        }
    }
}
