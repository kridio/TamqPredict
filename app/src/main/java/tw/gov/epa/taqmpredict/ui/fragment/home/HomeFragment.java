package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseSwipeBackFragment;
import tw.gov.epa.taqmpredict.base.Constants;
import tw.gov.epa.taqmpredict.data.DataRequestPresenter;
import tw.gov.epa.taqmpredict.data.DataRequestService;
import tw.gov.epa.taqmpredict.db.DBManage;
import tw.gov.epa.taqmpredict.event.ChoiceSiteEvent;
import tw.gov.epa.taqmpredict.gps.area.AreaRequestPresenter;
import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfoData;
import tw.gov.epa.taqmpredict.predict.DriverService;
import tw.gov.epa.taqmpredict.predict.model.Result;
import tw.gov.epa.taqmpredict.ui.fragment.city.CityFragment;
import tw.gov.epa.taqmpredict.util.DateTimeUtil;
import tw.gov.epa.taqmpredict.util.PreferencesUtil;

/**
 * Created by user on 2017/2/14.
 */

public class HomeFragment extends BaseSwipeBackFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    RecyclerView recyclerViewCity;
    TextView tvAddArea;
    TextView tvEditArea;
    TextView tvLocation;
    TextView tvDatetime;
    ImageView ivAddLoc;
    DrawerLayout dlCity;
    FrameLayout fl_navigation_city;
    SwipeRefreshLayout swiperefresh_home;

    TextView tv_pm25_view;
    TextView tv_slogan;
    TextView tv_slogan_alarm_1;
//    TextView tv_slogan_alarm_2;
    ImageView iv_slogan_alarm;

    TextView tv_nh_headline;
    ImageView iv_nh_pic;
    ImageView iv_nsh_pic;
    ImageView iv_nth_pic;
    TextView tv_nh_pm25;
    TextView tv_nsh_pm25;
    TextView tv_nth_pm25;

    //String headline_site = "";
    private static ArrayList<SiteListData> siteDataList = new ArrayList<>();

    Handler mHandler;

    HomeCityRecyclerViewAdapter cityRecyclerViewAdapter;
    DataRequestPresenter dataRequestPresenter;
    DriverService driverService;
    AreaRequestPresenter areaRequestPresenter;

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

        recyclerViewCity = (RecyclerView) view.findViewById(R.id.recyclerView_city);
        tvAddArea = (TextView) view.findViewById(R.id.tv_add_area);
        tvEditArea = (TextView) view.findViewById(R.id.tv_edit_area);
        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        tvDatetime = (TextView) view.findViewById(R.id.tv_datetime);
        ivAddLoc = (ImageView) view.findViewById(R.id.iv_add_location);
        dlCity = (DrawerLayout) view.findViewById(R.id.drawerLayout_city);
        fl_navigation_city = (FrameLayout) view.findViewById(R.id.fl_navigation_city);
        swiperefresh_home = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_home);

        tv_pm25_view = (TextView) view.findViewById(R.id.tv_pm25_view);
        tv_slogan = (TextView) view.findViewById(R.id.tv_slogan);
        tv_slogan_alarm_1 = (TextView) view.findViewById(R.id.tv_slogan_alarm_1);
//        tv_slogan_alarm_2 = (TextView) view.findViewById(R.id.tv_slogan_alarm_2);
        iv_slogan_alarm = (ImageView) view.findViewById(R.id.iv_slogan_alarm);
        tv_nh_headline = (TextView) view.findViewById(R.id.tv_nh_headline);
        iv_nh_pic = (ImageView) view.findViewById(R.id.iv_nh_pic);
        iv_nsh_pic = (ImageView) view.findViewById(R.id.iv_nsh_pic);
        iv_nth_pic = (ImageView) view.findViewById(R.id.iv_nth_pic);
        tv_nh_pm25 = (TextView) view.findViewById(R.id.tv_nh_pm25);
        tv_nsh_pm25 = (TextView) view.findViewById(R.id.tv_nsh_pm25);
        tv_nth_pm25 = (TextView) view.findViewById(R.id.tv_nth_pm25);

        mHandler = new Handler();

        dataRequestPresenter = new DataRequestPresenter(new DataRequestService(), HomeFragment.this);
        driverService = new DriverService(HomeFragment.this);
        areaRequestPresenter = new AreaRequestPresenter(getContext(), mObserver);
        areaRequestPresenter.start();

        if(PreferencesUtil.get(Constants.HEAD_SITE_LIST,"").equals("")){
            PreferencesUtil.put(Constants.HEAD_SITE_LIST, Constants.CURRENT_SITE_NAME);
        }

        cityRecyclerViewAdapter = new HomeCityRecyclerViewAdapter(getContext(), this);
        createCityList();
        setHead();
        PreferencesUtil.put(Constants.EDIT_CITY_LIST, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

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

        tvEditArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesUtil.get(Constants.EDIT_CITY_LIST, false)) {
                    PreferencesUtil.put(Constants.EDIT_CITY_LIST, false);
                    tvEditArea.setTextColor(Color.WHITE);
                    tvEditArea.setText(R.string.edit_str);
                    dlCity.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    closeDrawer();
                } else {
                    PreferencesUtil.put(Constants.EDIT_CITY_LIST, true);
                    tvEditArea.setTextColor(Color.RED);
                    tvEditArea.setText(R.string.edit_complete_str);
                    dlCity.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                }
            }
        });

        ivAddLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlCity.openDrawer(fl_navigation_city);
            }
        });



        swiperefresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    public void closeDrawer() {
        dlCity.closeDrawers();
    }

    public void getData() {
        if (dataRequestPresenter != null && driverService != null) {
            swiperefresh_home.setRefreshing(true);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swiperefresh_home.setRefreshing(false);
                }
            }, 1000);

            driverService.getPredictData();
//            dataRequestPresenter.getEpaData();
        }
    }

    public void setHead(){
        tvDatetime.setText(DateTimeUtil.getCurrentHLDateTime());
        tvLocation.setText(PreferencesUtil.get(Constants.HEADLINE_SITE, getResources().getString(R.string.title_area_display)));
    }

    public void setData(List<Result> results) {
        boolean hasValue = false;
        for (Result rs : results) {
            //if (Arrays.asList(Constants.AREA_PREDICT).contains(rs.getSiteName())) {
            if (PreferencesUtil.get(Constants.SITENAME, "").equals(rs.getSiteName())) {
                if (!rs.getHr().equals(Constants.NO_DATA)) {
                    tv_pm25_view.setText(rs.getHr());
                    setSlogan(Integer.parseInt(rs.getHr()));
                }

                tv_nh_headline.setText(DateTimeUtil.getPredictTime(rs.getTime()) + Constants.AIR_PREDICT_STR);
                if (!rs.getHr1().equals(Constants.NO_DATA)) {
                    tv_nh_pm25.setText(String.valueOf((int)Double.parseDouble(rs.getHr1())));
                    setSloganPredict(((int)Double.parseDouble(rs.getHr1())), iv_nh_pic);
                }
                if (!rs.getHr6().equals(Constants.NO_DATA)) {
                    tv_nsh_pm25.setText(String.valueOf((int)Double.parseDouble(rs.getHr6())));
                    setSloganPredict(((int)Double.parseDouble(rs.getHr6())), iv_nsh_pic);
                }
                if (!rs.getHr12().equals(Constants.NO_DATA)) {
                    tv_nth_pm25.setText(String.valueOf((int)Double.parseDouble(rs.getHr12())));
                    setSloganPredict(((int)Double.parseDouble(rs.getHr12())), iv_nth_pic);
                }
                hasValue = true;
            }
            if (!rs.getHr().equals(Constants.NO_DATA)) {
                cityRecyclerViewAdapter.updateItemValue(rs.getSiteName(), ((int)Double.parseDouble(rs.getHr())));
                cityRecyclerViewAdapter.notifyDataSetChanged();
            }
            //}
        }

        if(!hasValue){
            tv_pm25_view.setText(getResources().getString(R.string.air_value_empty));
            setSlogan(AIR_EMPTY);
            tv_nh_headline.setText(getResources().getString(R.string.air_slogan_nh_empty));
            tv_nh_pm25.setText(getResources().getString(R.string.air_value_empty));
            setSloganPredict(AIR_EMPTY,iv_nh_pic);
            tv_nsh_pm25.setText(getResources().getString(R.string.air_value_empty));
            setSloganPredict(AIR_EMPTY,iv_nsh_pic);
            tv_nth_pm25.setText(getResources().getString(R.string.air_value_empty));
            setSloganPredict(AIR_EMPTY,iv_nth_pic);
        }
        swiperefresh_home.setRefreshing(false);
    }

    Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String county) {
            List<CityInfoData> countys = DBManage.getInstance().getLocality(county);
            for (CityInfoData data : countys) {
                if (PreferencesUtil.get(Constants.SITENAME, "").equals("")) {
                    PreferencesUtil.put(Constants.HEADLINE_SITE, data.getCityName() + "(" + data.getSiteName() + ")");
                    PreferencesUtil.put(Constants.SITENAME, data.getSiteName());
                }
                cityRecyclerViewAdapter.updateCurrentSite(data.getSiteName(),data.getCityName());
                PreferencesUtil.put(Constants.CURRENT_SITE, data.getSiteName());
                PreferencesUtil.put(Constants.CURRENT_HEADLINE_SITE, data.getCityName() + "(" + data.getSiteName() + ")");
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(siteDataList.size()>0) {
            getData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    final static int AIR_EMPTY = -1;
    final static int AIR_GOOD = 36;
    final static int AIR_primary = 54;
    final static int AIR_intermediate = 71;

    public void setSlogan(int pm) {
        if (pm == AIR_EMPTY) {
            tv_slogan.setText(R.string.slogan_empty);
            tv_slogan.setBackgroundResource(R.color.bottom_list);
            tv_slogan_alarm_1.setText(R.string.slogan_empty_sentence);
//            tv_slogan_alarm_2.setText(R.string.slogan_empty_sentence);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_update_black_24dp);
        } else if (pm < AIR_GOOD) {
            tv_slogan.setText(R.string.slogan_good);
            tv_slogan.setBackgroundResource(R.color.pm25_good);
//            tv_slogan_alarm_1.setText(R.string.slogan_good_sentence_1);
            tv_slogan_alarm_1.setText(R.string.slogan_good_sentence_1);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_directions_bike_black_24dp);
        } else if (pm < AIR_primary) {
            tv_slogan.setText(R.string.slogan_primary);
            tv_slogan.setBackgroundResource(R.color.pm25_primary);
//            tv_slogan_alarm_1.setText(R.string.slogan_primary_sentence_1);
            tv_slogan_alarm_1.setText(R.string.slogan_primary_sentence_1);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_directions_run_black_24dp);
        } else if (pm < AIR_intermediate) {
            tv_slogan.setText(R.string.slogan_intermediate);
            tv_slogan.setBackgroundResource(R.color.pm25_intermediate);
//            tv_slogan_alarm_1.setText(R.string.slogan_intermediate_sentence_1);
            tv_slogan_alarm_1.setText(R.string.slogan_intermediate_sentence_1);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
        } else {
            tv_slogan.setText(R.string.slogan_urgent);
            tv_slogan.setBackgroundResource(R.color.pm25_urgent);
//            tv_slogan_alarm_1.setText(R.string.slogan_urgent_sentence_1);
            tv_slogan_alarm_1.setText(R.string.slogan_urgent_sentence_1);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_direction_home_white_24dp);
        }
    }

    public void setSloganPredict(int pm, ImageView iv){
        if (pm == AIR_EMPTY) {
            iv.setImageResource(R.drawable.ic_update_black_24dp);
        } else if (pm < AIR_GOOD) {
            iv.setImageResource(R.drawable.ic_directions_bike_black_24dp);
        } else if (pm < AIR_primary) {
            iv.setImageResource(R.drawable.ic_directions_run_black_24dp);
        } else if (pm < AIR_intermediate) {
            iv.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp);
        } else {
            iv.setImageResource(R.drawable.ic_direction_home_white_24dp);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        areaRequestPresenter.stop();
    }

    /**
     * @param choiceSiteEvent
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessage(ChoiceSiteEvent choiceSiteEvent) {
        String headline_site = choiceSiteEvent.getCityInfo().getCityName() + "(" + choiceSiteEvent.getCityInfo().getSiteName() + ")";
        PreferencesUtil.put(Constants.HEADLINE_SITE, headline_site);
        PreferencesUtil.put(Constants.SITENAME, choiceSiteEvent.getCityInfo().getSiteName());
        //addSite(choiceSiteEvent.getCityInfo().getSiteName());
        setHead();
        addSite(headline_site);
    }

    public void addSite(String headline_site) {
        String siteArrayStr = PreferencesUtil.get(Constants.HEAD_SITE_LIST, "");
        if(siteArrayStr.split(",").length<=5) {
            if (!checkSiteExisted(headline_site, siteArrayStr, true)) {
                siteArrayStr = siteArrayStr + "," + headline_site;
                PreferencesUtil.put(Constants.HEAD_SITE_LIST, siteArrayStr);
            }
            createCityList();
        }
        logd(PreferencesUtil.get(Constants.HEAD_SITE_LIST, ""));
    }

    public void removeSite(String headline_site) {
        String headSiteArrayStr = PreferencesUtil.get(Constants.HEAD_SITE_LIST, "");
        if (checkSiteExisted(headline_site, headSiteArrayStr, false)) {
            String[] headSiteArray = headSiteArrayStr.split(",");
            StringBuilder newHeadSiteArrayStr = new StringBuilder();
            for (String headSite : headSiteArray) {
                if (!headSite.equals(headline_site)) {
                    newHeadSiteArrayStr.append("," + headSite);
                }
            }
            PreferencesUtil.put(Constants.HEAD_SITE_LIST, newHeadSiteArrayStr.substring(1, newHeadSiteArrayStr.length()));
        }
        createCityList();
        logd(PreferencesUtil.get(Constants.HEAD_SITE_LIST, ""));
    }

    public void createCityList() {
        siteDataList.clear();
        if (!PreferencesUtil.get(Constants.HEAD_SITE_LIST, "").equals("")) {
            String[] headSiteArray = PreferencesUtil.get(Constants.HEAD_SITE_LIST, "").split(",");
            for (String headSite : headSiteArray) {
                SiteListData siteListData = new SiteListData();
                if (headSite.equals(Constants.CURRENT_SITE_NAME)) {
                    if(PreferencesUtil.get(Constants.CURRENT_SITE, "").equals("")) {
                        siteListData.setOrder(Constants.CURRENT_SITE_INDEX);
                    }
                    else {
                        siteListData.setSiteName(PreferencesUtil.get(Constants.CURRENT_SITE, ""));
                        siteListData.setCityHead(PreferencesUtil.get(Constants.CURRENT_HEADLINE_SITE, ""));
                    }
                } else {
                    if (!headSite.equals(Constants.CURRENT_SITE_NAME)){
                        siteListData.setSiteName(headSite.substring(4, 6));
                        siteListData.setCityHead(headSite);
                    }
                }
                siteDataList.add(siteListData);
            }
            cityRecyclerViewAdapter.setData(siteDataList);
            cityRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public boolean checkSiteExisted(String siteName, String siteArrayStr, boolean isCurrentSite) {
        boolean isExisted = false;
        if (siteArrayStr != null &&
                !siteArrayStr.equals("") &&
                siteArrayStr.length() > Constants.CURRENT_SITE_NAME.length()) {
            String[] SiteArray = siteArrayStr.split(",");
            for (String site : SiteArray) {
                if (site.equals(siteName)) {
                    isExisted = true;
                }
            }
        }
        if (isCurrentSite && PreferencesUtil.get(Constants.CURRENT_SITE, "").equals(siteName)) {
            isExisted = true;
        }
        return isExisted;
    }

    private void logd(String log) {
        Log.d(TAG, log);
    }
}