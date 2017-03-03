package tw.gov.epa.taqmpredict.predict;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 2017/3/3.
 */
public class DriverServiceTest {
    DriverService ds;
    public DriverServiceTest() {
        ds = new DriverService();
    }

    @Test
    public void getPredictData() throws Exception {
        ds.getPredictData();
    }

}