package com.etc.movieticket.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by NewOrin Zhang on 2016/7/31.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public class ComingListViewAdapter extends BaseAdapter implements StickyListHeadersAdapter{
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public long getHeaderId(int position) {
        return 0;
    }
}
