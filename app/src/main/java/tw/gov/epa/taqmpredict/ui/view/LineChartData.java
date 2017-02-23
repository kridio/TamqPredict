package tw.gov.epa.taqmpredict.ui.view;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
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


    private List<Entry> getChartData(){
        List<Entry> chartData = new ArrayList<>();
        chartData.add(new Entry(3, 36));
        chartData.add(new Entry(6, 67));
        chartData.add(new Entry(9, 53));
        chartData.add(new Entry(12, 48));
        chartData.add(new Entry(15, 34));
        chartData.add(new Entry(18, 73));
        chartData.add(new Entry(21, 28));
        chartData.add(new Entry(24, 37));
        return chartData;
    }

    public LineData getLineData(){
        ILineDataSet dataSetA = new LineDataSet(getChartData(), mContext.getString(R.string.chart_predict));

        dataSetA.setValueTextSize(20);
        dataSetA.setValueTextColor(Color.WHITE);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA); // add the datasets

        return new LineData(dataSets);
    }

    public void configChartAxis(LineChart chart_bar){
        chart_bar.setAlpha(0.5f);
        chart_bar.getDescription().setEnabled(false);

        XAxis xAxis = chart_bar.getXAxis();
        xAxis.setTextSize(15);
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
