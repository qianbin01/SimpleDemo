package com.qb.simpledemo.view.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.qb.simpledemo.R;
import com.qb.simpledemo.adapter.MusicAdapter;
import com.qb.simpledemo.bean.music.MusicBean;
import com.qb.simpledemo.presenter.music.IMusicPresenter;
import com.qb.simpledemo.presenter.music.IMusicPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class IMusicFragment extends Fragment implements IMusicView {
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycle_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.btnSearch)
    Button btnSearch;
    @Bind(R.id.etSearch)
    EditText etSearch;
    @Bind(R.id.rlSearch)
    RelativeLayout rlSearch;
    private LinearLayoutManager mLayoutManager;
    private MusicAdapter mAdapter;
    private List<MusicBean> mList;
    private IMusicPresenter mPresenter;


    private static int ADD_FLAG = 0;
    private static int ADD_HEAD = 0;
    private static int ADD_BOTTOM = 1;
    private String q = "张学友";
    private int start = 0, count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new IMusicPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_fragment, null);
        ButterKnife.bind(this, view);
        initData();
        initConfig();
        return view;
    }

    private void initData() {
        mList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new MusicAdapter(getActivity(), mList);
    }

    private void initConfig() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count += 3;
                mPresenter.OnLoadMusic(q, start, count);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount() && ADD_FLAG == ADD_BOTTOM) {
                    //加载更多
                    Snackbar.make(getActivity().findViewById(R.id.drawer_layout), "正在加载", Snackbar.LENGTH_SHORT).show();
                    count += 3;
                    mPresenter.OnLoadMusic(q, start, count);
                }
                if(mLayoutManager.findFirstVisibleItemPosition()==0){
                    rlSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                boolean isSignificantDelta = Math.abs(dy) > 10;
                if (isSignificantDelta) {
                    if (dy > 0) {//判断上下滑
                        ADD_FLAG = ADD_BOTTOM;
                        rlSearch.setVisibility(View.GONE);
                    } else {
                        ADD_FLAG = ADD_HEAD;
                    }
                }

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MusicAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String title, image, content;
                title = mList.get(position).getTitle();
                image = mList.get(position).getImage();
                content = mList.get(position).getMobile_link();
                Intent intent = new Intent(getActivity(), IMusicDetailActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("image", image);
                intent.putExtra("content", content);
                getActivity().startActivity(intent);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q = etSearch.getText().toString();
                mPresenter.OnLoadMusic(q, start, count);
            }
        });
        mPresenter.OnLoadMusic(q,start,count);
    }

    @Override
    public void addMusic(List<MusicBean> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.addAll(list);
        mAdapter.setList(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if (isAdded()) {
            View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
            Snackbar.make(view, "加载失败", Snackbar.LENGTH_SHORT).show();
        }
    }
}
