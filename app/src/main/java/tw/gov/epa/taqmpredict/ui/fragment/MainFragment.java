package tw.gov.epa.taqmpredict.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;
import tw.gov.epa.taqmpredict.event.StartBrotherEvent;
import tw.gov.epa.taqmpredict.event.TabSelectedEvent;
import tw.gov.epa.taqmpredict.ui.fragment.about.AboutTabFragment;
import tw.gov.epa.taqmpredict.ui.fragment.home.HomeTabFragment;
import tw.gov.epa.taqmpredict.ui.fragment.map.MapTabFragment;
import tw.gov.epa.taqmpredict.ui.view.BottomBar;
import tw.gov.epa.taqmpredict.ui.view.BottomBarTab;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private SupportFragment[] mFragments = new SupportFragment[3];

    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeTabFragment.newInstance();
            mFragments[SECOND] = MapTabFragment.newInstance();
            mFragments[THIRD] = AboutTabFragment.newInstance();

            loadMultipleRootFragment(R.id.tamq_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            mFragments[FIRST] = findChildFragment(HomeTabFragment.class);
            mFragments[SECOND] = findChildFragment(MapTabFragment.class);
            mFragments[THIRD] = findChildFragment(AboutTabFragment.class);
        }
        initView();

        EventBus.getDefault().register(this);

        return view;
    }

    private void initView() {

        bottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_home_balance_white_24dp, "Home"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_map_white_24dp, "Map"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_insert_chart_white_24dp, "List"));

        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

                BottomBarTab tab = bottomBar.getItem(FIRST);
                if (position == SECOND) {
                    //tab.setUnreadCount(0);
                } else {
                    //tab.setUnreadCount(tab.getUnreadCount() + 1);
                }
            }

            @Override
            public void onTabUnselected(int position) {
                logd("unselected:" + position);
            }

            @Override
            public void onTabReselected(int position) {
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    private void logd(String log) {
        Log.d(TAG, log);
    }
}
