package com.etc.movieticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.utils.MyImageUtils;
import com.etc.movieticket.widget.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/7/29.
 * E-mail: NewOrin@163.com
 */

public class RecyclerViewMovieAdapter extends BaseRecyclerAdapter<Movie, MyViewHolder> {

    private String movieFlag;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public interface OnItemViewClickListener {
        void onItemViewClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemViewClickListener onItemViewClickListener;

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewMovieAdapter(Context context) {
        super(context);
    }

    public RecyclerViewMovieAdapter(Context mContext, List<Movie> mItemLists, String movieFlag) {
        super(mContext, mItemLists);
        this.movieFlag = movieFlag;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_item_movie_movieName.setText(mItemLists.get(position).getMv_cname());
        holder.item_movie_category.setText(mItemLists.get(position).getMv_category());
        holder.item_movie_releaseTime.setText(mItemLists.get(position).getMv_releaseTime());
        MyImageUtils.loadMovieIconImageView(mContext, holder.item_recyclerview_movie_image, mItemLists.get(position).getMv_imageUrl());
        if (mItemLists.get(position).getMv_score().equals("")) {
            holder.item_movie_ratingbar.setVisibility(View.GONE);
            holder.item_movie_ratingNums.setText("暂无评分");
        } else {
            holder.item_movie_ratingNums.setText(mItemLists.get(position).getMv_score());
            holder.item_movie_ratingbar.setNumStars((int) (Float.parseFloat(mItemLists.get(position).getMv_score()) / 2));
        }
        MyImageUtils.set3DIcon(holder.movie_buy_is3D,holder.movie_buy_isImax,mItemLists.get(position).getMv_3d());
        if (movieFlag.equals(Constants.MOVIE_ISRELEASED)) {
            holder.btn_item_movie_buy.setBackgroundResource(R.drawable.btn_buy_selector);
            holder.btn_item_movie_buy.setText("购买");
        } else {
            holder.btn_item_movie_buy.setTextColor(mContext.getResources().getColor(R.color.btn_sell_bg_pressed));
            holder.btn_item_movie_buy.setBackgroundResource(R.drawable.btn_sell_selector);
            holder.btn_item_movie_buy.setText("预售");
        }
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

        holder.btn_item_movie_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClickListener.onItemViewClick(v, position);
            }
        });
    }

    public void notifyRecyclerView(List<Movie> movieList) {
        this.mItemLists = movieList;
        notifyDataSetChanged();
    }
}


class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv_item_movie_movieName, item_movie_ratingNums, movie_buy_is3D, movie_buy_isImax, item_movie_category, item_movie_releaseTime;
    RatingBar item_movie_ratingbar;
    Button btn_item_movie_buy;
    ImageView item_recyclerview_movie_image;

    public MyViewHolder(View itemView) {
        super(itemView);
        item_recyclerview_movie_image = (ImageView) itemView.findViewById(R.id.item_recyclerview_movie_image);
        tv_item_movie_movieName = (TextView) itemView.findViewById(R.id.movie_buy_name);
        btn_item_movie_buy = (Button) itemView.findViewById(R.id.item_btn_buy_ticket);
        item_movie_ratingNums = (TextView) itemView.findViewById(R.id.item_movie_ratingNums);
        movie_buy_is3D = (TextView) itemView.findViewById(R.id.movie_buy_is3D);
        movie_buy_isImax = (TextView) itemView.findViewById(R.id.movie_buy_isImax);
        item_movie_category = (TextView) itemView.findViewById(R.id.item_movie_category);
        item_movie_releaseTime = (TextView) itemView.findViewById(R.id.item_movie_releaseTime);
        item_movie_ratingbar = (RatingBar) itemView.findViewById(R.id.item_movie_ratingbar);
    }
}