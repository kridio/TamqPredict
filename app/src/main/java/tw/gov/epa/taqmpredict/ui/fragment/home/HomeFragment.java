package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;
import tw.gov.epa.taqmpredict.base.BaseSwipeBackFragment;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;
import tw.gov.epa.taqmpredict.ui.fragment.city.CityFragment;

/**
 * Created by user on 2017/2/14.
 */

public class HomeFragment extends BaseSwipeBackFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
//    @BindView(R.id.mainhead_viewpager)
//    ViewPager mainheadViewpager;
//    @BindView(R.id.lineChart)
//    LineChart lineChart;
//    @BindView(R.id.smartTabLayout)
//    SmartTabLayout smartTabLayout;

//    List<View> viewList;

//    private HomeRecyclerAdapter mAdapter;

    RecyclerView recyclerViewCity;
    TextView tvAddArea;
    TextView tvEditArea;

    HomeCityRecyclerViewAdapter cityRecyclerViewAdapter;

    HomeFragment homeFragment;
    CityFragment cityFragment;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        EventBus.getDefault().register(this);

        recyclerViewCity = (RecyclerView)view.findViewById(R.id.recyclerView_city);
        tvAddArea = (TextView)view.findViewById(R.id.tv_add_area);
        tvEditArea = (TextView)view.findViewById(R.id.tv_edit_area);

        cityRecyclerViewAdapter = new HomeCityRecyclerViewAdapter(getContext());
        ArrayList<String> myDataset = new ArrayList<>();
        for(int i = 0; i < 1; i++){
            myDataset.add(Integer.toString(i));
        }
        cityRecyclerViewAdapter.setData(myDataset);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCity.setLayoutManager(layoutManager);
        recyclerViewCity.setHasFixedSize(true);
        recyclerViewCity.setAdapter(cityRecyclerViewAdapter);

        tvAddArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(CityFragment.newInstance());
            }
        });

//        View v1 = inflater.inflate(R.layout.main_header_pm25, null);
//        View v2 = inflater.inflate(R.layout.main_header_pm25_n1, null);
//        View v3 = inflater.inflate(R.layout.main_header_pm25_n6, null);
//        View v4 = inflater.inflate(R.layout.main_header_pm25_n12, null);


//        viewList = new ArrayList<View>();
//        viewList.add(v1);
//        //viewList.add(v2);
//        viewList.add(v3);
//        viewList.add(v4);

//        mainheadViewpager.setAdapter(new HomeViewPagerAdapter(viewList));
//        mainheadViewpager.setCurrentItem(0);

//        smartTabLayout.setViewPager(mainheadViewpager);

//        ArrayList<String> labels = new ArrayList<String>();
//        labels.add("January");
//        labels.add("February");

//        LineChartData lineChartData = new LineChartData(getContext());
//        lineChartData.configChartAxis(lineChart);
//        lineChart.setData(lineChartData.getLineData());
//        lineChart.setVisibleXRangeMaximum(7);
//        lineChart.moveViewToX(15);
//        lineChart.setBackgroundColor(Color.GRAY);
//        lineChart.setAlpha(0.3f);
//        lineChart.invalidate();

//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_playlist_add_white_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "navigation", Toast.LENGTH_LONG).show();
//            }
//        });

//        BarChartData chartData = new BarChartData(getContext());
//        chartData.configChartAxis(barChart);
//        barChart.setData(chartData.getBarData());
//        barChart.setVisibleXRangeMaximum(6);
//        barChart.moveViewToX(15);
//        barChart.invalidate();

//        tabLayout.addTab(tabLayout.newTab().setText("PM2.5"));
//        tabLayout.addTab(tabLayout.newTab().setText("PM10"));
//        tabLayout.addTab(tabLayout.newTab().setText("O3"));
//        tabLayout.addTab(tabLayout.newTab().setText("CO"));
//        tabLayout.addTab(tabLayout.newTab().setText("SO2"));
//        tabLayout.addTab(tabLayout.newTab().setText("NO"));
//        tabLayout.setTabTextColors(Color.WHITE,Color.WHITE);
//
//        tabLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "tab", Toast.LENGTH_LONG).show();
//            }
//        });


//        toolbar.inflateMenu(R.menu.menu_layout);
//        mAdapter = new HomeRecyclerAdapter(getContext());
//        ArrayList<String> myDataset = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            myDataset.add(Integer.toString(i));
//        }
//        mAdapter.setData(myDataset);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerView.addItemDecoration(new MarginDecoration(getContext()));
//        recyclerView.setAdapter(mAdapter);
        return attachToSwipeBack(view);
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
