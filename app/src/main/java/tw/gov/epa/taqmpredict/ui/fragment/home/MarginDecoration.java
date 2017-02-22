package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import tw.gov.epa.taqmpredict.R;

/**
 * Created by Administrator on 2017/2/23.
 */

public class MarginDecoration extends RecyclerView.ItemDecoration {

    private int margin;

    public MarginDecoration(Context context) {
        margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}
