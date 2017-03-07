package tw.gov.epa.taqmpredict.ui.fragment.city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import tw.gov.epa.taqmpredict.AirPollutionApplication;
import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;
import tw.gov.epa.taqmpredict.base.BaseSwipeBackFragment;
import tw.gov.epa.taqmpredict.db.DBManage;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfoData;
import tw.gov.epa.taqmpredict.gps.area.city.search.SearchCityView;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;
import tw.gov.epa.taqmpredict.ui.fragment.home.HomeFragment;

/**
 * Created by Administrator on 2017/2/28.
 */

public class CityFragment extends BaseSwipeBackFragment implements SearchCityView {
    private RecyclerView rv_city;
    private CityAdapter mCityAdapter;
    private ArrayList<String> dataList = new ArrayList<>();
    HomeFragment homeFragment;
    CityFragment cityFragment;

    @Override
    public void onMatched(List<CityInfoData> cityInfoDatas) {

    }

    @Override
    public void onAllCities(List<CityInfoData> allInfoDatas) {

    }

    public static CityFragment newInstance() {
        Bundle args = new Bundle();
        CityFragment fragment = new CityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rv_city = (RecyclerView)view.findViewById(R.id.rv_city);
        initRecyclerView();

        return attachToSwipeBack(view);
        //return view;
    }

    public void initRecyclerView(){
        rv_city.setLayoutManager(new LinearLayoutManager(_mActivity));
        rv_city.setHasFixedSize(true);

        List<CityInfoData> allCities = DBManage.getInstance().getAllCities();
        for(CityInfoData data:allCities){
            dataList.add("("+data.getCityId()+")"+data.getCityName()+" "+data.getCounty()+" "+data.getSiteName());
        }

        mCityAdapter = new CityAdapter(_mActivity, dataList);
        rv_city.setAdapter(mCityAdapter);

        mCityAdapter.setOnItemClickListener((view, pos)->{
            Log.d("CityFragment:",dataList.get(pos));
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }


    /**
     * reselected fragment
     *
     * @param event
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.FIRST) return;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
