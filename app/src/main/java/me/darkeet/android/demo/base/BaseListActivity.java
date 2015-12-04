package me.darkeet.android.demo.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import me.darkeet.android.base.DRBaseFragmentActivity;
import me.darkeet.android.demo.R;

/**
 * Name: BaseListActivity
 * User: yi (darkeet.me@gmail.com)
 * Date: 2015/10/15 17:49
 * Desc:
 */
public class BaseListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private View mEmptyView;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_listview);

        mEmptyView = findViewById(android.R.id.empty);
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setEmptyView(mEmptyView);
        mListView.setOnItemClickListener(mOnClickListener);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
    }


    public void setListAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    public ListView getListView() {
        return mListView;
    }

    public void onRefreshCompleted() {
        mSwipeRefreshLayout.setRefreshing(true);
    }


    private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            onListItemClick((ListView) parent, v, position, id);
        }
    };

    protected void onListItemClick(ListView l, View v, int position, long id) {
    }
}
