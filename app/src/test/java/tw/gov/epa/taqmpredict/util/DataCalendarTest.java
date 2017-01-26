package tw.gov.epa.taqmpredict.util;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * Created by user on 2017/1/25.
 */
public class DataCalendarTest{
    private DataCalendar dataCalendar;
    private SimpleDateFormat simpleDateFormat;

    @Before
    public void setConfig(){
        dataCalendar = new DataCalendar();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @Test
    public void getDateFormat() throws Exception {
        String testDataStr = "2017-01-25 11:00";
        String dateStr = dataCalendar.getDateFormat(simpleDateFormat.parse(testDataStr));
        assertEquals(testDataStr,dateStr);
    }

    @Test
    public void getNextHour() throws Exception {

    }

    @Test
    public void getPreviousHour() throws Exception {

    }

    @Test
    public void getPreviousDay() throws Exception {

    }

    @Test
    public void getPreviousWeek() throws Exception {

    }

    @Test
    public void getPreviouseMonth() throws Exception {

    }

    @Test
    public void getPreviouseYear() throws Exception {
        dataCalendar.getPreviouseYear(1);

    }

}