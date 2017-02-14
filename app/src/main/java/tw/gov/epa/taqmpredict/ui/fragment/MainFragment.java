package tw.gov.epa.taqmpredict.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private BottomBar mBottomBar;
    private SupportFragment[] mFragments = new SupportFragment[3];

    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

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

        initView(view);
        return view;
    }

    private void initView(View view) {
        EventBus.getDefault().register(this);
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);

        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_message_white_24dp, "Message"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_account_circle_white_24dp, "contacts"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_discover_white_24dp, "discover"));

        // 模擬未讀消息
        //mBottomBar.getItem(FIRST).setUnreadCount(9);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

                BottomBarTab tab = mBottomBar.getItem(FIRST);
                if (position == FIRST) {
                    //tab.setUnreadCount(0);
                } else {
                    //tab.setUnreadCount(tab.getUnreadCount() + 1);
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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
}
