package com.etc.movieticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.widget.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/7/31.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public class RecyclerViewCinemaAdapter extends BaseRecyclerAdapter<Cinema, CinemaViewHolder> {

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

    public RecyclerViewCinemaAdapter(Context mContext, List<Cinema> mItemLists) {
        super(mContext, mItemLists);
    }

    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_cinema, parent, false);
        return new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CinemaViewHolder holder, final int position) {
        holder.item_tv_cinema_name.setText(mItemLists.get(position).getC_name());
        holder.item_tv_cinema_address.setText(mItemLists.get(position).getC_address());
        holder.item_tv_cinema_price.setText(mItemLists.get(position).getC_price() + "元起");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onItemLongClick(holder.itemView, position);
                return false;
            }
        });
    }
}

class CinemaViewHolder extends RecyclerView.ViewHolder {
    public TextView item_tv_cinema_name, item_tv_cinema_price, item_tv_cinema_address;

    public CinemaViewHolder(View itemView) {
        super(itemView);
        item_tv_cinema_name = (TextView) itemView.findViewById(R.id.item_tv_cinema_name);
        item_tv_cinema_price = (TextView) itemView.findViewById(R.id.item_tv_cinema_price);
        item_tv_cinema_address = (TextView) itemView.findViewById(R.id.item_tv_cinema_address);
    }
}