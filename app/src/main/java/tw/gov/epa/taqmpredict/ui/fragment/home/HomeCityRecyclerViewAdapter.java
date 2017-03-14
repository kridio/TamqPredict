package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tw.gov.epa.taqmpredict.R;
import tw.gov.epa.taqmpredict.base.Constants;
import tw.gov.epa.taqmpredict.util.PreferencesUtil;

/**
 * Created by Administrator on 2017/3/1.
 */

public class HomeCityRecyclerViewAdapter extends RecyclerView.Adapter<HomeCityRecyclerViewAdapter.ViewHolder> {
    TextView tvArea;

    private Context mContext;

    private List<String> mDataArea;

    final static int CURRENT = 0;

    HomeFragment mHf;

    public void setData(List data){
        mDataArea = data;
    }

    public HomeCityRecyclerViewAdapter(Context context,HomeFragment hf) {
        mContext = context;
        mHf = hf;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAreaPm25;
        public ViewHolder(View v) {
            super(v);
            tvAreaPm25 = (TextView)v.findViewById(R.id.tv_area_pm25);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_list_city, parent, false);
        tvArea = (TextView)v.findViewById(R.id.tv_area) ;
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvAreaPm25.setText("79");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==CURRENT){
                    PreferencesUtil.put(Constants.HEADLINE_SITE,PreferencesUtil.get(Constants.CURRENT_HEADLINE_SITE,""));
                    PreferencesUtil.put(Constants.SITENAME,PreferencesUtil.get(Constants.CURRENT_SITE,""));
                    mHf.closeDrawer();
                    mHf.getData();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataArea.size();
    }
}
