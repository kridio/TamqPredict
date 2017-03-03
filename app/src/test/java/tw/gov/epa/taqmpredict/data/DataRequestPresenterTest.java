package tw.gov.epa.taqmpredict.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 2017/3/3.
 */
public class DataRequestPresenterTest {
    @Test
    public void getEpaData() throws Exception {
        new DataRequestPresenter(new DataRequestService()).getEpaData();
    }

    @Test
    public void getEpaData1() throws Exception {

    }

}