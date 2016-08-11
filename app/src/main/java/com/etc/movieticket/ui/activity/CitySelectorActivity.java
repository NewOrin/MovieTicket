package com.etc.movieticket.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.SortAdapter;
import com.etc.movieticket.db.DBManager;
import com.etc.movieticket.db.RegionDAO;
import com.etc.movieticket.entity.RegionInfo;
import com.etc.movieticket.entity.SortModel;
import com.etc.movieticket.utils.CharacterParser;
import com.etc.movieticket.utils.PinyinComparator;
import com.etc.movieticket.view.ClearEditText;
import com.etc.movieticket.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CitySelectorActivity extends BaseActivity implements View.OnClickListener {

    private List<RegionInfo> provinceList;
    private List<RegionInfo> cityList;
    private List<String> provinces;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter sortAdapter;
    private ClearEditText mClearEditText;

    private DBManager dbHelper;
    private Toolbar toolbar;
    private TextView toolbar_tv_title;
    private RelativeLayout rl_toolbar;
    /**
     * 汉字转成拼音类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据
     *
     * @param savedInstanceState
     */
    private PinyinComparator pinyinComparator;
    private TextView mToolbarTvLeft;
    private TextView mToolbarTvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selector);
        //导入数据库
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        initData();
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_tv_title = (TextView) findViewById(R.id.toolbar_tv_title);
        rl_toolbar = (RelativeLayout) findViewById(R.id.rl_toolbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        setToolbar(toolbar, "", toolbar_tv_title, "选择");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);
        mToolbarTvLeft = (TextView) findViewById(R.id.toolbar_tv_left);
        mToolbarTvLeft.setVisibility(View.GONE);
        mToolbarTvSearch = (TextView) findViewById(R.id.toolbar_tv_search);
        mToolbarTvSearch.setVisibility(View.GONE);
    }

    private void initData() {
        provinceList = new ArrayList<>();
        provinceList.addAll(RegionDAO.getProvencesOrCity(2));
        cityList = new ArrayList<>();
        provinces = new ArrayList<>();
        for (RegionInfo info : provinceList) {
            provinces.add(info.getName().trim());
        }
    }

    @Override
    protected void initListener() {
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                if (position != 0) {
//                Toast.makeText(getApplication(), ((SortModel) sortAdapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                saveSharedPfStr("place", ((SortModel) sortAdapter.getItem(position)).getName());
//                hideSoftInput(mClearEditText.getWindowToken());
                finish();
//                }
            }
        });
        SourceDateList = filledData(provinceList);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        sortAdapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(sortAdapter);
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<RegionInfo> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getName());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            if (!provinces.contains(filterStr)) {
                filterDateList.clear();
                for (SortModel sortModel : SourceDateList) {
                    String name = sortModel.getName();
                    if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                        filterDateList.add(sortModel);
                    }
                }
            } else {
                filterDateList.clear();
                for (int i = 0; i < provinceList.size(); i++) {
                    String name = provinceList.get(i).getName();
                    if (name.equals(filterStr)) {
                        filterDateList.addAll(filledData(RegionDAO.getProvencesOrCityOnParent(provinceList.get(i).getId())));
                    }
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        sortAdapter.updateListView(filterDateList);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    protected void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
