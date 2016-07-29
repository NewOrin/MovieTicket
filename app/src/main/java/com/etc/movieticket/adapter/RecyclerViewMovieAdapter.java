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
 * Created by NewOrin Zhang on 2016/7/29.
 * E-mail: NewOrin@163.com
 */
public class RecyclerViewMovieAdapter extends BaseRecyclerAdapter<Movie, MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewMovieAdapter(Context context) {
        super(context);
    }

    public RecyclerViewMovieAdapter(Context mContext, LinkedList<Movie> mItemLists) {
        super(mContext, mItemLists);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv_item_movie_movieName.setText(mItemLists.get(position).getMv_cname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int layoutPosition = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView,layoutPosition);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int layoutPosition = holder.getLayoutPosition();
                mOnItemClickListener.onItemLongClick(holder.itemView,layoutPosition);
                return false;
            }
        });
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv_item_movie_movieName;

    public MyViewHolder(View itemView) {
        super(itemView);
        tv_item_movie_movieName = (TextView) itemView.findViewById(R.id.item_movie_title);
    }
}