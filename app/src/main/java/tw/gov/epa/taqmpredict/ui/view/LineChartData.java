package tw.gov.epa.taqmpredict.ui.view;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class LineChartData {
    final int DATA_COUNT = 10;
    private List<Entry> getChartData(){
        List<Entry> chartData = new ArrayList<>();
        for(int i=0;i<DATA_COUNT;i++){
            chartData.add(new Entry(i*2, i));
        }
        return chartData;
    }

    private List<String> getLabels(){
        List<String> chartLabels = new ArrayList<>();
        for(int i=0;i<DATA_COUNT;i++){
            chartLabels.add("X"+i);
        }
        return chartLabels;
    }

    public LineData getLineData(){
        ILineDataSet dataSetA = new LineDataSet(getChartData(), "LabelA");

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA); // add the datasets

        return new LineData(dataSets);
    }

    public void configChartAxis(LineChart chart_bar){
        chart_bar.setAlpha(0.5f);
        chart_bar.getDescription().setEnabled(false);

        XAxis xAxis = chart_bar.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftYAxis = chart_bar.getAxisLeft();
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setEnabled(false);

        YAxis RightYAxis = chart_bar.getAxisRight();
        RightYAxis.setEnabled(false);   //不顯示右側
    }

    public LineChartData(){

    }
}
