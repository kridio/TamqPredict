package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private Context mContext;

    private List<SiteListData> mDataArea;

    HomeFragment mHf;

    public void setData(List<SiteListData> data) {
        mDataArea = data;
    }

    public List<SiteListData> getData() {
        return mDataArea;
    }

    public HomeCityRecyclerViewAdapter(Context context, HomeFragment hf) {
        mContext = context;
        mHf = hf;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAreaPm25;
        private TextView tvArea;
        public ViewHolder(View v) {
            super(v);
            tvAreaPm25 = (TextView) v.findViewById(R.id.tv_area_pm25);
            tvArea = (TextView) v.findViewById(R.id.tv_area);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_list_city, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position==Constants.CURRENT_SITE_INDEX) {
            holder.tvArea.setText(mContext.getResources().getString(R.string.current_place));
        }
        else{
            holder.tvArea.setText(mDataArea.get(position).getSiteName());
        }
        holder.tvAreaPm25.setText(String.valueOf(mDataArea.get(position).getPm25_value()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PreferencesUtil.put(Constants.HEADLINE_SITE, );
                if(position==Constants.CURRENT_SITE_INDEX) {
                    PreferencesUtil.put(Constants.HEADLINE_SITE, PreferencesUtil.get(Constants.CURRENT_HEADLINE_SITE, ""));
                    PreferencesUtil.put(Constants.SITENAME, PreferencesUtil.get(Constants.CURRENT_SITE, ""));
                }
                else{
                    PreferencesUtil.put(Constants.HEADLINE_SITE,mDataArea.get(position).getCityHead());
                    PreferencesUtil.put(Constants.SITENAME, mDataArea.get(position).getSiteName());
                }
                mHf.closeDrawer();
                mHf.getData();
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
