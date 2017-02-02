package tw.gov.epa.taqmpredict.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 2017/1/25.
 */

public class DataCalendar {

    private final static int WEEK = 7;
    private final static int DAY = 24;
    private final static int HOUR = 60;
    private final static int MIN = 60;
    private final static int SEC = 1000; //msec

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Calendar calendar;

    public DataCalendar(){
        calendar = Calendar.getInstance();
    }

    public String getDateFormat(Date date){
        return simpleDateFormat.format(date);
    }

    public Date getNextHour(int nextHour){
        calendar.add(Calendar.HOUR, nextHour);
        return calendar.getTime();
    }
    public Date getPreviousHour(int previousHour){
        calendar.add(Calendar.HOUR, -previousHour);
        return calendar.getTime();
    }
    public Date getPreviousDay(int previousDay){
        calendar.add(Calendar.DAY_OF_MONTH, -previousDay);
        return calendar.getTime();
    }
    public Date getPreviousWeek(){
        calendar.add(Calendar.DAY_OF_MONTH, -WEEK);
        return calendar.getTime();
    }
    public Date getPreviouseMonth(int previousMonth){
        calendar.add(Calendar.MONTH, -previousMonth);
        return calendar.getTime();
    }
    public Date getPreviouseYear(int previousYear){
        calendar.add(Calendar.YEAR, -previousYear);
        return calendar.getTime();
    }
}
