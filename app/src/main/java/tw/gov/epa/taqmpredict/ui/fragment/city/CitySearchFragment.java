package tw.gov.epa.taqmpredict.ui.fragment.city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfoData;
import tw.gov.epa.taqmpredict.gps.area.city.search.SearchCityView;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;

/**
 * Created by Administrator on 2017/2/28.
 */

public class CitySearchFragment extends BaseFragment implements SearchCityView {

    @Override
    public void onMatched(List<CityInfoData> cityInfoDatas) {

    }

    @Override
    public void onAllCities(List<CityInfoData> allInfoDatas) {

    }

    public static CitySearchFragment newInstance() {
        Bundle args = new Bundle();
        CitySearchFragment fragment = new CitySearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        EventBus.getDefault().register(this);
        return view;
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
