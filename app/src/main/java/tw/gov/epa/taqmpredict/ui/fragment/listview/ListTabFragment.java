package tw.gov.epa.taqmpredict.ui.fragment.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;

/**
 * Created by user on 2017/2/18.
 */

public class ListTabFragment extends BaseFragment {
    private final static String TAG = ListTabFragment.class.getSimpleName();
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tables)
    TableLayout tables;

    public static ListTabFragment newInstance() {
        Bundle args = new Bundle();

        ListTabFragment fragment = new ListTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_list, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    private void logd(String log) {
        Log.d(TAG, log);
    }

//    private void initData() {
//        pageList = new ArrayList<>();
//        pageList.add(new PageOne(MainActivity.this));
//        pageList.add(new PageTwo(MainActivity.this));
//        pageList.add(new PageThree(MainActivity.this));
//    }

//    private void initView() {
//        mTablayout = (TabLayout) findViewById(R.id.tabs);
//        mTablayout.addTab(mTablayout.newTab().setText("Page one"));
//        mTablayout.addTab(mTablayout.newTab().setText("Page two"));
//        mTablayout.addTab(mTablayout.newTab().setText("Page three"));
//
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(new SamplePagerAdapter());
//        initListener();
//    }

//    private void initListener() {
//        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mViewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
//    }

//    private class SamplePagerAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return pageList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object o) {
//            return o == view;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            container.addView(pageList.get(position));
//            return pageList.get(position);
//        }
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//    }
}
