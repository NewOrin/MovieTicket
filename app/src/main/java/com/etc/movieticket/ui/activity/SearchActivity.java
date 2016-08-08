package com.etc.movieticket.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.SearchHistoryRecyclerViewAdapter;
import com.etc.movieticket.db.SearchHistoryDAO;
import com.etc.movieticket.utils.DialogTool;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.view.ClearEditText;
import com.etc.movieticket.widget.WrapAdapter;

import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private ClearEditText et_search_activity;
    private TextView tv_search_cancel;
    private RecyclerView search_recyclerview;
    private SearchHistoryDAO searchHistoryDAO;
    private SearchHistoryRecyclerViewAdapter searchHistoryRecyclerViewAdapter;
    private WrapAdapter<SearchHistoryRecyclerViewAdapter> mWrapAdapter;
    private String TAG = "SearchActivity";
    private List<String> mDatas;
    private View footerView;
    private LinearLayout footer_clear_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchHistoryDAO = new SearchHistoryDAO(this);
        initView();
        initDatas();
        initListener();
    }

    @Override
    protected void initView() {
        et_search_activity = (ClearEditText) findViewById(R.id.et_search_activity);
        tv_search_cancel = (TextView) findViewById(R.id.tv_search_cancel);
        search_recyclerview = (RecyclerView) findViewById(R.id.search_recyclerview);
    }

    private void initDatas() {
        mDatas = searchHistoryDAO.queryHistory();
        Log.d(TAG, "搜索记录长度:" + mDatas.size());
        if (mDatas.size() > 0) {
            footerView = LayoutInflater.from(this).inflate(R.layout.item_listview_foot_clear, null);
            footer_clear_view = (LinearLayout) footerView.findViewById(R.id.footer_clear_view);
            searchHistoryRecyclerViewAdapter = new SearchHistoryRecyclerViewAdapter(this, mDatas);
            mWrapAdapter = new WrapAdapter<>(searchHistoryRecyclerViewAdapter);
            mWrapAdapter.adjustSpanSize(search_recyclerview);
            search_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            search_recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            search_recyclerview.setAdapter(mWrapAdapter);
            mWrapAdapter.addFooterView(footerView, true);
            footer_clear_view.setOnClickListener(this);
            searchHistoryRecyclerViewAdapter.setOnItemClickListener(new SearchHistoryRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    showToast("搜索" + mDatas.get(position));
                }
            });
        }
    }

    @Override
    protected void initListener() {
        tv_search_cancel.setOnClickListener(this);
        et_search_activity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    String inputStr = et_search_activity.getText().toString().trim();
                    if (TextUtils.isEmpty(inputStr)) {
                        showToast("不能为空");
                    } else {
                        searchHistoryDAO.insertHistory("moviename", inputStr);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_cancel:
                finish();
                break;
            case R.id.footer_clear_view:
                DialogTool.createAlertDialog(SearchActivity.this, "确认清楚记录?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchHistoryDAO.deleteHistory();
                        mDatas.clear();
                        mWrapAdapter.notifyDataSetChanged();
                        search_recyclerview.setVisibility(View.GONE);
                    }
                }).show();
                break;
        }
    }

}
