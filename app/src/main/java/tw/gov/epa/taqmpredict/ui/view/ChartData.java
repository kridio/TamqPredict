package tw.gov.epa.taqmpredict.ui.view;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import tw.gov.epa.taqmpredict.R;

/**
 * Created by user on 2017/2/20.
 */

public class ChartData {
    private Context mContext;

    private int DATA_COUNT = 24;

    public ChartData(Context context){
        mContext = context;
    }

    public BarData getBarData(){
        BarDataSet dataSetA = new BarDataSet(getChartData(), mContext.getString(R.string.chart_title));
        //設定顏色
        dataSetA.setColors(getChartColors());
        //設定顯示字串
        dataSetA.setStackLabels(getStackLabels());

        dataSetA.setValueTextSize(20);
        dataSetA.setValueTextColor(Color.WHITE);

        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetA); // add the datasets

        return new BarData(dataSets);
    }

    public void configChartAxis(BarChart chart_bar){
        chart_bar.setAlpha(0.5f);
        chart_bar.getDescription().setEnabled(false);
        chart_bar.setDrawBarShadow(false);

        XAxis xAxis = chart_bar.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftYAxis = chart_bar.getAxisLeft();
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setEnabled(false);

        YAxis RightYAxis = chart_bar.getAxisRight();
        RightYAxis.setEnabled(false);   //不顯示右側
    }

    private String[] getStackLabels(){
        return new String[]{mContext.getString(R.string.chart_label_Predict), mContext.getString(R.string.chart_label_Real)};
    }

    private int[] getChartColors() {
        int[] colors = new int[]{getResourceColor(R.color.chart_color_Predict),
                getResourceColor(R.color.chart_color_Real)};
        return colors;
    }

    private int getResourceColor(int resID){
        return mContext.getResources().getColor(resID);
    }

    private List<BarEntry> getChartData(){
        List<BarEntry> chartData = new ArrayList<>();
        for(int i=0;i<DATA_COUNT;i++){
            float revenue_Predict = i;
            float revenue_Real = i+2;
            chartData.add(new BarEntry(i, new float[]{revenue_Predict, revenue_Real}));
        }
        return chartData;
    }

    public int getDATA_COUNT() {
        return DATA_COUNT;
    }

    public void setDATA_COUNT(int DATA_COUNT) {
        this.DATA_COUNT = DATA_COUNT;
    }
}
