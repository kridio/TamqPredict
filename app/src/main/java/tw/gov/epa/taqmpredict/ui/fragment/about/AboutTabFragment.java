package tw.gov.epa.taqmpredict.ui.fragment.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.BaseFragment;

/**
 * Created by user on 2017/2/14.
 */

public class AboutTabFragment extends BaseFragment{
    public static AboutTabFragment newInstance() {

        Bundle args = new Bundle();

        AboutTabFragment fragment = new AboutTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_about, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
