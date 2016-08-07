package com.etc.movieticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.widget.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/7.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public class SearchHistoryRecyclerViewAdapter extends BaseRecyclerAdapter<String, HistoryViewHolder> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchHistoryRecyclerViewAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
    }

    public void notifyDataChanged(List<String> mDatas){
    }
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_listview_show_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        holder.tv_search_history.setText(mItemLists.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
    }
}

class HistoryViewHolder extends RecyclerView.ViewHolder {
    TextView tv_search_history;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        tv_search_history = (TextView) itemView.findViewById(R.id.tv_search_history);
    }
}