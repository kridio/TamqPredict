package tw.gov.epa.taqmpredict.ui.fragment.home;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    int[] image_list = {R.drawable.bg1,R.drawable.bg2,R.drawable.bg3,R.drawable.bg4,R.drawable.bg5,R.drawable.bg6};



    public HomeCityRecyclerViewAdapter(Context context, HomeFragment hf) {
        mContext = context;
        mHf = hf;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAreaPm25;
        private TextView tvArea;
        private ImageView ivBg;

        public ViewHolder(View v) {
            super(v);
            tvAreaPm25 = (TextView) v.findViewById(R.id.tv_area_pm25);
            tvArea = (TextView) v.findViewById(R.id.tv_area);
            ivBg = (ImageView)v.findViewById(R.id.im_area_bg);

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
            holder.tvArea.setText(mDataArea.get(position).getCityHead());
        }
        holder.tvAreaPm25.setText(String.valueOf(mDataArea.get(position).getPm25_value()));
        holder.ivBg.setBackground(mContext.getResources().getDrawable(image_list[position]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PreferencesUtil.put(Constants.HEADLINE_SITE, );
                if(PreferencesUtil.get(Constants.EDIT_CITY_LIST,false)){
                    if(mDataArea.get(position).getOrder() != Constants.CURRENT_SITE_INDEX) {
                        PreferencesUtil.put(Constants.HEADLINE_SITE, PreferencesUtil.get(Constants.CURRENT_HEADLINE_SITE, ""));
                        PreferencesUtil.put(Constants.SITENAME, PreferencesUtil.get(Constants.CURRENT_SITE, ""));
                        mHf.removeSite(mDataArea.get(position).getCityHead());
                        mHf.getData();
                    }
                }
                else {
                    if (position == Constants.CURRENT_SITE_INDEX) {
                        PreferencesUtil.put(Constants.HEADLINE_SITE, PreferencesUtil.get(Constants.CURRENT_HEADLINE_SITE, ""));
                        PreferencesUtil.put(Constants.SITENAME, PreferencesUtil.get(Constants.CURRENT_SITE, ""));
                    } else {
                        PreferencesUtil.put(Constants.HEADLINE_SITE, mDataArea.get(position).getCityHead());
                        PreferencesUtil.put(Constants.SITENAME, mDataArea.get(position).getSiteName());
                    }
                    mHf.closeDrawer();
                    mHf.setHead();
                    mHf.getData();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataArea.size();
    }

    public void updateItemValue(String siteName,int value){
        for(SiteListData data:mDataArea){
            if(data.getSiteName()!=null && data.getSiteName().equals(siteName)){
                data.setPm25_value(value);
            }
        }
    }
    public boolean updateCurrentSite(String siteName,String cityName){
        boolean hasCurrent = false;
        for (SiteListData siteData : mDataArea) {
            if (siteData.getOrder() == Constants.CURRENT_SITE_INDEX) {
                siteData.setSiteName(siteName);
                siteData.setCityHead(cityName + "(" + siteName + ")");
                hasCurrent = true;
            }
        }
        return hasCurrent;
    }
}
