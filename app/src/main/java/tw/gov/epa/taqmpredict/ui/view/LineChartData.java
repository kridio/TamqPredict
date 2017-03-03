package tw.gov.epa.taqmpredict.ui.view;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import tw.gov.epa.taqmpredict.R;

/**
 * Created by Administrator on 2017/2/22.
 */

public class LineChartData {
    private Context mContext;
    public LineChartData(Context context){
        mContext = context;
    }

    final String[] quarters = new String[] { "03:00", "06:00", "09:00", "12:00", "15:00", "18:00", "21:00","00:00" };

    IAxisValueFormatter formatter = new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return quarters[(int) value];
        }
    };


    private List<Entry> getChartDataReal(){
        List<Entry> chartData = new ArrayList<>();
        chartData.add(new Entry(0, 44));
        chartData.add(new Entry(1, 35));
        chartData.add(new Entry(2, 65));
        chartData.add(new Entry(3, 89));
        chartData.add(new Entry(4, 48));
        chartData.add(new Entry(5, 32));
        chartData.add(new Entry(6, 67));
        chartData.add(new Entry(7, 22));
        chartData.add(new Entry(8, 45));
        chartData.add(new Entry(9, 36));
        chartData.add(new Entry(10, 74));
        chartData.add(new Entry(11, 82));
        chartData.add(new Entry(12, 48));
        chartData.add(new Entry(13, 32));
        chartData.add(new Entry(14, 49));
        chartData.add(new Entry(15, 41));
        chartData.add(new Entry(16, 47));
        chartData.add(new Entry(17, 56));
        chartData.add(new Entry(18, 50));
        chartData.add(new Entry(19, 55));
        chartData.add(new Entry(20, 54));
        chartData.add(new Entry(21, 78));
        chartData.add(new Entry(22, 62));
        chartData.add(new Entry(23, 65));
        return chartData;
    }

    private List<Entry> getChartDataPredict(){
        List<Entry> chartData = new ArrayList<>();
        chartData.add(new Entry(0, 40));
        chartData.add(new Entry(1, 32));
        chartData.add(new Entry(2, 68));
        chartData.add(new Entry(3, 86));
        chartData.add(new Entry(4, 48));
        chartData.add(new Entry(5, 37));
        chartData.add(new Entry(6, 69));
        chartData.add(new Entry(7, 26));
        chartData.add(new Entry(8, 40));
        chartData.add(new Entry(9, 32));
        chartData.add(new Entry(10, 79));
        chartData.add(new Entry(11, 86));
        chartData.add(new Entry(12, 48));
        chartData.add(new Entry(13, 37));
        chartData.add(new Entry(14, 42));
        chartData.add(new Entry(15, 43));
        chartData.add(new Entry(16, 46));
        chartData.add(new Entry(17, 52));
        chartData.add(new Entry(18, 56));
        chartData.add(new Entry(19, 57));
        chartData.add(new Entry(20, 59));
        chartData.add(new Entry(21, 73));
        chartData.add(new Entry(22, 65));
        chartData.add(new Entry(23, 68));
        return chartData;
    }

    public LineData getLineData(){
        ILineDataSet dataSetA = new LineDataSet(getChartDataReal(), mContext.getString(R.string.chart_label_Real));
        dataSetA.setDrawFilled(true);
        dataSetA.setDrawValues(false);


        LineDataSet dataSetB = new LineDataSet(getChartDataPredict(), mContext.getString(R.string.chart_label_Predict));
        dataSetB.setDrawFilled(true);
        dataSetB.setDrawValues(false);
        dataSetB.setFillColor(mContext.getResources().getColor(R.color.alarm_shape_red));


        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA); // add the datasets
        dataSets.add(dataSetB);

        return new LineData(dataSets);
    }

    public void configChartAxis(LineChart chart_bar){
        chart_bar.setAlpha(0.5f);
        chart_bar.getDescription().setEnabled(false);
        Legend legend = chart_bar.getLegend();
        legend.setTextSize(20);

        XAxis xAxis = chart_bar.getXAxis();
        xAxis.setTextSize(15);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftYAxis = chart_bar.getAxisLeft();
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setEnabled(true);

        YAxis RightYAxis = chart_bar.getAxisRight();
        RightYAxis.setEnabled(false);   //不顯示右側
    }
}
