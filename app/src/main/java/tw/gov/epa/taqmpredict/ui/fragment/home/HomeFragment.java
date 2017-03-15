package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.os.Bundle;
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
import tw.gov.epa.taqmpredict.data.model.Record;
import tw.gov.epa.taqmpredict.db.DBManage;
import tw.gov.epa.taqmpredict.event.ChoiceSiteEvent;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.gps.area.AreaRequestPresenter;
import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfoData;
import tw.gov.epa.taqmpredict.predict.DriverService;
import tw.gov.epa.taqmpredict.predict.model.Result;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;
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
    TextView tv_slogan_alarm_2;
    ImageView iv_slogan_alarm;

    TextView tv_pm25_view_nh;
    TextView tv_slogan_nh;
    TextView tv_slogan_alarm_1_nh;
    TextView tv_slogan_alarm_2_nh;
    ImageView iv_slogan_alarm_nh;
    TextView tv_nh_predict;

    //String headline_site = "";
    private static ArrayList<SiteListData> siteDataList = new ArrayList<>();

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

        recyclerViewCity = (RecyclerView)view.findViewById(R.id.recyclerView_city);
        tvAddArea = (TextView)view.findViewById(R.id.tv_add_area);
        tvEditArea = (TextView)view.findViewById(R.id.tv_edit_area);
        tvLocation = (TextView)view.findViewById(R.id.tv_location);
        tvDatetime = (TextView)view.findViewById(R.id.tv_datetime);
        ivAddLoc = (ImageView)view.findViewById(R.id.iv_add_location);
        dlCity = (DrawerLayout)view.findViewById(R.id.drawerLayout_city);
        fl_navigation_city = (FrameLayout) view.findViewById(R.id.fl_navigation_city);
        swiperefresh_home = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_home);

        tv_pm25_view = (TextView)view.findViewById(R.id.tv_pm25_view);
        tv_slogan = (TextView)view.findViewById(R.id.tv_slogan);
        tv_slogan_alarm_1 = (TextView)view.findViewById(R.id.tv_slogan_alarm_1);
        tv_slogan_alarm_2 = (TextView)view.findViewById(R.id.tv_slogan_alarm_2);
        iv_slogan_alarm = (ImageView)view.findViewById(R.id.iv_slogan_alarm);
        tv_pm25_view_nh = (TextView)view.findViewById(R.id.tv_pm25_view_nh);
        tv_slogan_nh = (TextView)view.findViewById(R.id.tv_slogan_nh);
        tv_slogan_alarm_1_nh = (TextView)view.findViewById(R.id.tv_slogan_alarm_1_nh);
        tv_slogan_alarm_2_nh = (TextView)view.findViewById(R.id.tv_slogan_alarm_2_nh);
        iv_slogan_alarm_nh = (ImageView)view.findViewById(R.id.iv_slogan_alarm_nh);
        tv_nh_predict = (TextView)view.findViewById(R.id.tv_nh_predict);

        dataRequestPresenter = new DataRequestPresenter(new DataRequestService(),HomeFragment.this);
        driverService = new DriverService(HomeFragment.this);

        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        cityRecyclerViewAdapter = new HomeCityRecyclerViewAdapter(getContext(),this);
        cityRecyclerViewAdapter.setData(siteDataList);

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

        ivAddLoc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dlCity.openDrawer(fl_navigation_city);
            }
        });

        swiperefresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    public void closeDrawer(){
        dlCity.closeDrawers();
    }

    public void getData(){
        if(dataRequestPresenter!=null && driverService!=null) {
            swiperefresh_home.setRefreshing(true);
            dataRequestPresenter.getEpaData();
            driverService.getPredictData();
            tvDatetime.setText(DateTimeUtil.getCurrentHLDateTime());
            tvLocation.setText(PreferencesUtil.get(Constants.HEADLINE_SITE,getResources().getString(R.string.title_area_display)));
        }
    }

    boolean predictReady=false;
    boolean realReady=false;
    public void setData(List<Result> results){
        boolean hasValue = false;
        for(Result rs:results) {
            if(PreferencesUtil.get(Constants.SITENAME,"").equals(rs.getSiteName())){
                tv_pm25_view_nh.setText(String.valueOf(rs.getHr1().intValue()));
                tv_nh_predict.setText(DateTimeUtil.getPredictTime(rs.getTime())+Constants.AIR_PREDICT_STR);
                setPredictSlogan(rs.getHr1().intValue());
                hasValue = true;
            }
        }
        if(!hasValue){
            tv_pm25_view_nh.setText(getResources().getString(R.string.air_value_empty));
            tv_nh_predict.setText(getResources().getString(R.string.air_slogan_nh_empty));
            setPredictSlogan(AIR_EMPTY);
        }
        if(predictReady){
            swiperefresh_home.setRefreshing(false);
            predictReady = false;
        }
        else{
            realReady = true;
        }
    }

    public void setEpaData(List<Record> result){
        boolean hasValue=false;
        parse();
        for(Record rc:result) {
            if(PreferencesUtil.get(Constants.SITENAME,"").equals(rc.getSiteName())){
                if(!rc.getPM25().equals("")) {
                    tv_pm25_view.setText(String.valueOf(rc.getPM25()));
                    setSlogan(Integer.valueOf(rc.getPM25()));
                    hasValue=true;
                }
            }
            String list = PreferencesUtil.get(Constants.SITE_LIST,"");
            //need modified to be better
            if(!list.equals("")){
                for (SiteListData data : siteDataList) {
                    if (data.getSiteName().equals(rc.getSiteName())) {
                        data.setPm25_value(Integer.valueOf(rc.getPM25()));
                    }
                }
                cityRecyclerViewAdapter.setData(siteDataList);
                cityRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
        if(!hasValue){
            tv_pm25_view.setText(getResources().getString(R.string.air_value_empty));
            setSlogan(AIR_EMPTY);
        }
        if(realReady){
            swiperefresh_home.setRefreshing(false);
            realReady = false;
        }
        else{
            predictReady = true;
        }
    }

    Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String county) {
            List<CityInfoData> countys = DBManage.getInstance().getLocality(county);
            for(CityInfoData data:countys){
                if(PreferencesUtil.get(Constants.SITENAME,"").equals("")){
                    PreferencesUtil.put(Constants.HEADLINE_SITE,data.getCityName()+"("+data.getSiteName()+")");
                    PreferencesUtil.put(Constants.SITENAME,data.getSiteName());
                    addSite(Constants.CURRENT_SITE_NAME);
                    PreferencesUtil.put(Constants.SITE_LIST,Constants.CURRENT_SITE_NAME);
                    SiteListData siteListData = new SiteListData();
                    siteListData.setSiteName(data.getSiteName());
                    siteListData.setOrder(Constants.CURRENT_SITE_INDEX);
                    siteDataList.add(siteListData);
                    getData();
                }
                for(SiteListData siteData:siteDataList){
                    if(siteData.getOrder()==Constants.CURRENT_SITE_INDEX){
                        siteData.setSiteName(data.getSiteName());
                    }
                }
                PreferencesUtil.put(Constants.CURRENT_SITE,data.getSiteName());
                PreferencesUtil.put(Constants.CURRENT_HEADLINE_SITE,data.getCityName()+"("+data.getSiteName()+")");
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

        areaRequestPresenter = new AreaRequestPresenter(getContext(),mObserver);
        areaRequestPresenter.start();

        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        areaRequestPresenter.stop();
    }

    final static int AIR_EMPTY = -1;
    final static int AIR_GOOD = 36;
    final static int AIR_primary = 54;
    final static int AIR_intermediate = 71;
    public void setPredictSlogan(int pm){
        if(pm==AIR_EMPTY){
            tv_slogan_nh.setText(R.string.slogan_empty);
            tv_slogan_nh.setBackgroundResource(R.color.bottom_list);
            tv_slogan_alarm_1_nh.setText(R.string.slogan_empty_sentence);
            tv_slogan_alarm_2_nh.setText(R.string.slogan_empty_sentence);
            iv_slogan_alarm_nh.setBackgroundResource(R.drawable.ic_update_black_24dp);
        }
        else if(pm>AIR_EMPTY && pm<AIR_GOOD){
            tv_slogan_nh.setText(R.string.slogan_good);
            tv_slogan_nh.setBackgroundResource(R.color.pm25_good);
            tv_slogan_alarm_1_nh.setText(R.string.slogan_good_sentence_1);
            tv_slogan_alarm_2_nh.setText(R.string.slogan_good_sentence_2);
            iv_slogan_alarm_nh.setBackgroundResource(R.drawable.ic_directions_bike_black_24dp);

        }
        else if(pm<AIR_primary){
            tv_slogan_nh.setText(R.string.slogan_primary);
            tv_slogan_nh.setBackgroundResource(R.color.pm25_primary);
            tv_slogan_alarm_1_nh.setText(R.string.slogan_primary_sentence_1);
            tv_slogan_alarm_2_nh.setText(R.string.slogan_primary_sentence_2);
            iv_slogan_alarm_nh.setBackgroundResource(R.drawable.ic_directions_run_black_24dp);
        }
        else if(pm<AIR_intermediate){
            tv_slogan_nh.setText(R.string.slogan_intermediate);
            tv_slogan_nh.setBackgroundResource(R.color.pm25_intermediate);
            tv_slogan_alarm_1_nh.setText(R.string.slogan_intermediate_sentence_1);
            tv_slogan_alarm_2_nh.setText(R.string.slogan_intermediate_sentence_2);
            iv_slogan_alarm_nh.setBackgroundResource(R.drawable.ic_directions_bike_black_24dp);
        }
        else{
            tv_slogan_nh.setText(R.string.slogan_urgent);
            tv_slogan_nh.setBackgroundResource(R.color.pm25_urgent);
            tv_slogan_alarm_1_nh.setText(R.string.slogan_urgent_sentence_1);
            tv_slogan_alarm_2_nh.setText(R.string.slogan_urgent_sentence_2);
            iv_slogan_alarm_nh.setBackgroundResource(R.drawable.ic_directions_bike_black_24dp);
        }
    }
    public void setSlogan(int pm){
        if(pm==AIR_EMPTY){
            tv_slogan.setText(R.string.slogan_empty);
            tv_slogan.setBackgroundResource(R.color.bottom_list);
            tv_slogan_alarm_1.setText(R.string.slogan_empty_sentence);
            tv_slogan_alarm_2.setText(R.string.slogan_empty_sentence);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_update_black_24dp);
        }
        else if(pm<AIR_GOOD){
            tv_slogan.setText(R.string.slogan_good);
            tv_slogan.setBackgroundResource(R.color.pm25_good);
            tv_slogan_alarm_1.setText(R.string.slogan_good_sentence_1);
            tv_slogan_alarm_2.setText(R.string.slogan_good_sentence_2);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_directions_bike_black_24dp);
        }
        else if(pm<AIR_primary){
            tv_slogan.setText(R.string.slogan_primary);
            tv_slogan.setBackgroundResource(R.color.pm25_primary);
            tv_slogan_alarm_1.setText(R.string.slogan_primary_sentence_1);
            tv_slogan_alarm_2.setText(R.string.slogan_primary_sentence_2);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_directions_run_black_24dp);
        }
        else if(pm<AIR_intermediate){
            tv_slogan.setText(R.string.slogan_intermediate);
            tv_slogan.setBackgroundResource(R.color.pm25_intermediate);
            tv_slogan_alarm_1.setText(R.string.slogan_intermediate_sentence_1);
            tv_slogan_alarm_2.setText(R.string.slogan_intermediate_sentence_2);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_directions_bike_black_24dp);
        }
        else{
            tv_slogan.setText(R.string.slogan_urgent);
            tv_slogan.setBackgroundResource(R.color.pm25_urgent);
            tv_slogan_alarm_1.setText(R.string.slogan_urgent_sentence_1);
            tv_slogan_alarm_2.setText(R.string.slogan_urgent_sentence_2);
            iv_slogan_alarm.setBackgroundResource(R.drawable.ic_directions_bike_black_24dp);
        }
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

    /**
     *
     * @param choiceSiteEvent
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessage(ChoiceSiteEvent choiceSiteEvent){
        String headline_site = choiceSiteEvent.getCityInfo().getCityName()+"("+choiceSiteEvent.getCityInfo().getSiteName()+")";
        PreferencesUtil.put(Constants.HEADLINE_SITE,headline_site);
        PreferencesUtil.put(Constants.SITENAME,choiceSiteEvent.getCityInfo().getSiteName());
        addSite(choiceSiteEvent.getCityInfo().getSiteName());
    }

    public void addSite(String siteName){
        String siteArrayStr = PreferencesUtil.get(Constants.SITE_LIST,"");
        if (!checkSiteExisted(siteName,siteArrayStr,true)) {
            siteArrayStr = siteArrayStr+","+siteName;
            PreferencesUtil.put(Constants.SITE_LIST,siteArrayStr);
        }
        parse();
        logd(PreferencesUtil.get(Constants.SITE_LIST,""));
    }

    public void removeSite(String siteName){
        String siteArrayStr = PreferencesUtil.get(Constants.SITE_LIST,"");
        if (checkSiteExisted(siteName,siteArrayStr,false)) {
            String[] SiteArray = siteArrayStr.split(",");
            for(String site:SiteArray){
                if(!site.equals(siteName)){
                    siteArrayStr = site + ",";
                }
            }
            PreferencesUtil.put(Constants.SITE_LIST, siteArrayStr.substring(0,siteArrayStr.length()-1));
        }
        parse();
        cityRecyclerViewAdapter.setData(siteDataList);
        cityRecyclerViewAdapter.notifyDataSetChanged();
        logd(PreferencesUtil.get(Constants.SITE_LIST,""));
    }

    public void parse(){
        siteDataList.clear();
        if(!PreferencesUtil.get(Constants.SITE_LIST,"").equals("")) {
            String[] SiteArray = PreferencesUtil.get(Constants.SITE_LIST, "").split(",");
            for (String site:SiteArray) {
                SiteListData siteListData = new SiteListData();
                if(site.equals(Constants.CURRENT_SITE_NAME)) {
                    siteListData.setSiteName(PreferencesUtil.get(Constants.CURRENT_SITE,""));
                }
                else{
                    siteListData.setSiteName(site);
                }
                siteDataList.add(siteListData);
            }
        }
    }

    public boolean checkSiteExisted(String siteName,String siteArrayStr,boolean isCurrentSite){
        boolean isExisted = false;
        if(siteArrayStr!=null &&
                !siteArrayStr.equals("") &&
                siteArrayStr.length()>Constants.CURRENT_SITE_NAME.length()) {
            String[] SiteArray = siteArrayStr.split(",");
            for(String site:SiteArray){
                if(site.equals(siteName)){
                    isExisted = true;
                }
            }
        }
        if(isCurrentSite&&PreferencesUtil.get(Constants.CURRENT_SITE,"").equals(siteName)){
            isExisted = true;
        }
        return isExisted;
    }

    private void logd(String log) {
        Log.d(TAG, log);
    }
}