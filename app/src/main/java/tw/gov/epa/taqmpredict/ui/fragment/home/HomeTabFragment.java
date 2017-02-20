package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;
import tw.gov.epa.taqmpredict.ui.view.ChartData;

/**
 * Created by user on 2017/2/14.
 */

public class HomeTabFragment extends BaseFragment {
    private static final String TAG = HomeTabFragment.class.getSimpleName();
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_datetime)
    TextView tvDatetime;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.barChart)
    BarChart barChart;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    public static HomeTabFragment newInstance() {
        Bundle args = new Bundle();

        HomeTabFragment fragment = new HomeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_home, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_playlist_add_white_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "navigation", Toast.LENGTH_LONG).show();
//            }
//        });

        ChartData chartData = new ChartData(getContext());
        chartData.configChartAxis(barChart);
        barChart.setData(chartData.getBarData());
        barChart.setVisibleXRangeMaximum(6);
        barChart.moveViewToX(15);
        barChart.invalidate();

        tabLayout.addTab(tabLayout.newTab().setText("PM2.5"));
        tabLayout.addTab(tabLayout.newTab().setText("PM10"));
        tabLayout.addTab(tabLayout.newTab().setText("O3"));
        tabLayout.addTab(tabLayout.newTab().setText("CO"));
        tabLayout.addTab(tabLayout.newTab().setText("SO2"));
        tabLayout.addTab(tabLayout.newTab().setText("NO"));
        tabLayout.setTabTextColors(Color.WHITE,Color.WHITE);

        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "tab", Toast.LENGTH_LONG).show();
            }
        });


//        toolbar.inflateMenu(R.menu.menu_layout);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        logd("onLazyInitView");
    }

    /**
     * reselected fragment
     *
     * @param event
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.FIRST) return;
        logd("onTabSelectedEvent");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void logd(String log) {
        Log.d(TAG, log);
    }
}
