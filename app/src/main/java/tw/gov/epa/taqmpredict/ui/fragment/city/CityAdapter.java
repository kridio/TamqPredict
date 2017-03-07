package tw.gov.epa.taqmpredict.ui.fragment.city;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tw.gov.epa.taqmpredict.R;

/**
 * Created by user on 2017/3/7.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    Context mContext;
    ArrayList<String> mDataList;
    OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CityAdapter(Context context, ArrayList<String> dataList){
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_list_city, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        holder.mTv_item_city.setText(mDataList.get(position));
        holder.mCardView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int pos);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTv_item_city;
        CardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTv_item_city = (TextView)itemView.findViewById(R.id.tv_item_city) ;
            mCardView = (CardView)itemView.findViewById(R.id.city_search_cardView) ;
        }
    }
}
