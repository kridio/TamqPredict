package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;

import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;

/**
 * Created by user on 2017/2/14.
 */

public class HomeTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
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
        initView(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView(View view) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.FIRST) return;


    }

    @Override
    public void onRefresh() {

    }
}
