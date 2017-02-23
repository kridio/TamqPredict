package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;

/**
 * Created by user on 2017/2/14.
 */

public class HomeTabFragment extends BaseFragment {
    private static final String TAG = HomeTabFragment.class.getSimpleName();
    @BindView(R.id.mainhead_viewpager)
    ViewPager mainheadViewpager;

    List<View> viewList;

    private HomeRecyclerAdapter mAdapter;

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

        View v1 = inflater.inflate(R.layout.main_header_pm25, null);
        View v2 = inflater.inflate(R.layout.main_header_aqi, null);

        viewList = new ArrayList<View>();
        viewList.add(v1);
        viewList.add(v2);

        mainheadViewpager.setAdapter(new HomeViewPagerAdapter(viewList));
        mainheadViewpager.setCurrentItem(0);

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

//        LineChartData lineChartData = new LineChartData();
//        lineChartData.configChartAxis(lineChart);
//        lineChart.setData(lineChartData.getLineData());

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
