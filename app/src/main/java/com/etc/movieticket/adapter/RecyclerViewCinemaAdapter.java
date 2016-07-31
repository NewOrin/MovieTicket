package com.etc.movieticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.widget.BaseRecyclerAdapter;

import java.util.LinkedList;

/**
 * Created by NewOrin Zhang on 2016/7/31.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public class RecyclerViewCinemaAdapter extends BaseRecyclerAdapter<Movie, MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewCinemaAdapter(Context context) {
        super(context);
    }

    public RecyclerViewCinemaAdapter(Context mContext, LinkedList<Movie> mItemLists) {
        super(mContext, mItemLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_cinema, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int layoutPosition = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int layoutPosition = holder.getLayoutPosition();
                mOnItemClickListener.onItemLongClick(holder.itemView, layoutPosition);
                return false;
            }
        });
    }
}

class CinemaViewHolder extends RecyclerView.ViewHolder {

    public CinemaViewHolder(View itemView) {
        super(itemView);

    }
}