package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by user on 2017/2/23.
 */

public class HomeViewPagerAdapter extends PagerAdapter {
    private List<View> listViews;
    public HomeViewPagerAdapter(List<View> listViews){
        this.listViews = listViews;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = listViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return listViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
