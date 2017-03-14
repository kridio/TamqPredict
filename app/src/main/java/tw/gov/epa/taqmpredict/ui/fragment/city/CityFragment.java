package tw.gov.epa.taqmpredict.ui.fragment.city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseSwipeBackFragment;
import tw.gov.epa.taqmpredict.base.Constants;
import tw.gov.epa.taqmpredict.db.DBManage;
import tw.gov.epa.taqmpredict.event.ChoiceSiteEvent;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.gps.area.city.model.CityInfoData;
import tw.gov.epa.taqmpredict.gps.area.city.search.SearchCityView;
import tw.gov.epa.taqmpredict.ui.fragment.MainFragment;
import tw.gov.epa.taqmpredict.ui.fragment.home.HomeFragment;
import tw.gov.epa.taqmpredict.util.Check;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2017/2/28.
 */

public class CityFragment extends BaseSwipeBackFragment implements SearchCityView {
    private RecyclerView rv_city;
    private LinearLayout mEmptyView;
    private EditText mSearchTextView;
    private ImageButton mActionEmptyBtn;
    private CityAdapter mCityAdapter;
    private ArrayList<String> dataList = new ArrayList<>();

    List<CityInfoData> listCities;
    HomeFragment homeFragment;
    boolean mEnterGroup=false;

    @Override
    public void onMatched(String key,boolean isGroup) {
        if(isGroup){
            listCities = DBManage.getInstance().getGroupArea(key);
        }
        else {
            listCities = DBManage.getInstance().searchCity(key);
        }
        dataList.clear();
        for(CityInfoData data:listCities){
            dataList.add(data.getCityName()+data.getSiteName()+"測站");
        }
        if (Check.isNull(listCities) || listCities.isEmpty()) {
            mEmptyView.setVisibility(VISIBLE);
            rv_city.setVisibility(GONE);
        } else {
            mEmptyView.setVisibility(GONE);
            rv_city.setVisibility(VISIBLE);
            mCityAdapter.setData(dataList);
            mCityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAllGroups() {
        //listCities = DBManage.getInstance().getAllCities();
        //dataList.clear();
//        for(CityInfoData data:listCities){
//            dataList.add(data.getCityName()+data.getSiteName()+"測站");
//        }
//        rv_city.setVisibility(VISIBLE);

        dataList.clear();
        for(String group: Constants.AREA_GROUP){
            dataList.add(group);
        }
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
        mEmptyView = (LinearLayout)view.findViewById(R.id.empty_view);
        mSearchTextView = (EditText)view.findViewById(R.id.searchTextView);
        mActionEmptyBtn = (ImageButton)view.findViewById(R.id.action_empty_btn);

        initRecyclerView();

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
        } else {
            homeFragment = findFragment(HomeFragment.class);
        }

        return attachToSwipeBack(view);
    }

    public void initRecyclerView(){
        rv_city.setLayoutManager(new LinearLayoutManager(_mActivity));
        rv_city.setHasFixedSize(true);

        onAllGroups();

        mCityAdapter = new CityAdapter(_mActivity);
        rv_city.setAdapter(mCityAdapter);

        mCityAdapter.setOnItemClickListener((view, pos)->{
            Log.d("CityFragment:",dataList.get(pos));
            if(Arrays.asList(Constants.AREA_GROUP).contains(dataList.get(pos))){
                mEnterGroup = true;
                onMatched(dataList.get(pos),mEnterGroup);
                mCityAdapter.setData(dataList);
                mCityAdapter.notifyDataSetChanged();
            }else{
                ChoiceSiteEvent event = new ChoiceSiteEvent();
                event.setCityInfo(listCities.get(pos));
                EventBus.getDefault().post(event);
                replaceFragment(homeFragment,false);
            }
        });

        mActionEmptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchTextView.setText("");
                onAllGroups();
                mCityAdapter.setData(dataList);
                mCityAdapter.notifyDataSetChanged();
            }
        });

        mSearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });

        mSearchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CharSequence text = mSearchTextView.getText();
                boolean hasText = !TextUtils.isEmpty(text);
                if (hasText) {
                    mActionEmptyBtn.setVisibility(View.VISIBLE);
                } else {
                    mActionEmptyBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    mActionEmptyBtn.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    onAllGroups();
                } else {
                    mActionEmptyBtn.setVisibility(View.VISIBLE);
                    onMatched(keyword,false);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mCityAdapter.setData(dataList);
        mCityAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onBackPressedSupport() {
        if(mEnterGroup){
            onAllGroups();
            mCityAdapter.setData(dataList);
            mCityAdapter.notifyDataSetChanged();
        }
        else {
            replaceFragment(homeFragment, false);
        }
        mEnterGroup = false;
        return true;
    }
}
