package tw.gov.epa.taqmpredict.ui.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tw.gov.epa.taqmpredict.R;

/**
 * Created by Administrator on 2017/3/1.
 */

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder> {
    TextView tvArea;

    private Context mContext;

    private List<String> mDataArea;

    public void setData(List data){
        mDataArea = data;
    }

    public CityRecyclerViewAdapter(Context context) {
        mContext = context;
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
                .inflate(R.layout.item_city, parent, false);
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
//                Toast.makeText(mContext, "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(mContext, "Item " + position + " is long clicked.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataArea.size();
    }
}
