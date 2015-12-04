package me.darkeet.android.demo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import me.darkeet.android.demo.R;
import me.darkeet.android.demo.adapter.RecyclerViewAdapter;
import me.darkeet.android.demo.adapter.StaggeredViewAdapter;
import me.darkeet.android.demo.model.ViewModel;

import java.util.ArrayList;
import java.util.List;

import me.darkeet.android.base.DRBaseStackFragment;

public class RecyclerViewFragment extends DRBaseStackFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String MOCK_URL = "http://lorempixel.com/800/400/nightlife/";

    private static final int VERTICAL_LIST = 0;
    private static final int HORIZONTAL_LIST = 1;
    private static final int VERTICAL_GRID = 2;
    private static final int HORIZONTAL_GRID = 3;
    private static final int STAGGERED_GRID = 4;

    private static final int COLUMN_COUNT = 2;

    private int mStatus;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerViewAdapter mRecyclerViewAdapter;
    private StaggeredViewAdapter mStaggeredAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStatus = getArguments().getInt("flag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.recyclerview_list, container, false);
        mRecyclerView = (RecyclerView) convertView.findViewById(R.id.id_recyclerView);
        mRefreshLayout = (SwipeRefreshLayout) convertView.findViewById(R.id.id_swiperefreshlayout);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_dark, android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark, android.R.color.holo_red_dark);

        return convertView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        configRecyclerView();
    }

    private void configRecyclerView() {
        switch (mStatus) {
            case VERTICAL_LIST:
                mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_LIST:
                mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
                break;
            case VERTICAL_GRID:
                mLayoutManager = new GridLayoutManager(mActivity, COLUMN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_GRID:
                mLayoutManager = new GridLayoutManager(mActivity, COLUMN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            case STAGGERED_GRID:
                mLayoutManager = new StaggeredGridLayoutManager(COLUMN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }

        if (mStatus != STAGGERED_GRID) {
            mRecyclerViewAdapter = new RecyclerViewAdapter(mActivity);
            mRecyclerViewAdapter.addItems(createMockList());
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        } else {
            mStaggeredAdapter = new StaggeredViewAdapter(mActivity);
            mStaggeredAdapter.addItems(createMockList());
            mRecyclerView.setAdapter(mStaggeredAdapter);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
    }


    private List<ViewModel> createMockList() {
        List<ViewModel> items = new ArrayList<ViewModel>();
        for (int i = 0; i < 20; i++) {
            items.add(new ViewModel(i, "Item " + (i + 1), MOCK_URL + (i % 10 + 1)));
        }
        return items;
    }

    @Override
    public void onRefresh() {
        // 刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
                if (mStatus != STAGGERED_GRID) {
                    mRecyclerViewAdapter.add(new ViewModel(0, "Item", MOCK_URL + 10), 0);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    mStaggeredAdapter.add(new ViewModel(0, "Item", MOCK_URL + 10), 0);
                    mStaggeredAdapter.notifyDataSetChanged();
                }
            }
        }, 1000);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(mActivity, R.anim.abc_slide_in_top);
        } else {
            return AnimationUtils.loadAnimation(mActivity, R.anim.abc_slide_in_bottom);
        }
    }
}
