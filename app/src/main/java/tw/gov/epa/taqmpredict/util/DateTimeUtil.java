package tw.gov.epa.taqmpredict.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 2017/3/8.
 */

public class DateTimeUtil {
    static String HEADLINE_DATE_PATTERN = "yyyy-MM-dd";
    static String HEADLINE_TIME_PATTERN = "HH:mm:ss";
    static String HEADLINE_DAY_PATTERN = "E";
    static String PREDICT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    static String PREDICT_TIME_PATTERN = "HH:mm";

    //headline current date time
    public static String getCurrentHLDateTime(){
        return getDateTime(HEADLINE_DATE_PATTERN) + " " +
                getDateTime(HEADLINE_DAY_PATTERN) + " " +
                getDateTime(HEADLINE_TIME_PATTERN);
    }

    public static String getPredictTime(String time){
        Date dt = null;
        try {
            dt = getSdf(PREDICT_DATE_TIME_PATTERN).parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            cal.add(Calendar.HOUR_OF_DAY, 1);
            dt = cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dt==null){
            return "";
        }
        else {
            return getSdf(PREDICT_TIME_PATTERN).format(dt);
        }
    }

    public static String getDateTime(String pattern){
        Calendar cal = Calendar.getInstance();
        Date dt = cal.getTime();
        return getSdf(pattern).format(dt);
    }

    public static SimpleDateFormat getSdf(String pattern){
        return new SimpleDateFormat(pattern);
    }
}
