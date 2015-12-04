package me.darkeet.android.demo.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.darkeet.android.demo.R;
import me.darkeet.android.view.empty.EmptyViewListener;
import me.darkeet.android.view.empty.EmptyViewManager;

/**
 * Name: EmptyView
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/11 17:53
 * Desc:
 */
public class EmptyView implements EmptyViewListener, View.OnClickListener {

    private LayoutInflater mInflater;

    public EmptyView(View view) {
        mInflater = LayoutInflater.from(view.getContext());
        EmptyViewManager manager = new EmptyViewManager();
        manager.bindView(view, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEmpty:
                break;
            case R.id.buttonError:
                break;
        }
    }


    @Override
    public View generateEmptyView(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.view_empty, parent, false);
        view.findViewById(R.id.buttonEmpty).setOnClickListener(this);
        return view;
    }

    @Override
    public View generateErrorView(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.view_error, parent, false);
        view.findViewById(R.id.buttonError).setOnClickListener(this);
        return view;
    }

    @Override
    public View generateLoadingView(ViewGroup parent) {
        return mInflater.inflate(R.layout.view_loading, parent, false);
    }
}
