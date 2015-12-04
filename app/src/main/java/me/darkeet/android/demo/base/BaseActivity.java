package me.darkeet.android.demo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import me.darkeet.android.base.DRBaseFragmentActivity;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.activity.MainActivity;

/**
 * Name: BaseActivity
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/22 17:23
 * Desc:
 */
public class BaseActivity extends DRBaseFragmentActivity {

    private Toolbar mToolBar;
    private LinearLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.demo_activity_base);

        mRootView = (LinearLayout) findViewById(R.id.id_main_parent);
        mToolBar = (Toolbar) findViewById(R.id.id_toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null && enableDefaultBack()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, mRootView, true);
    }

    @Override
    public void setContentView(View view) {
        mRootView.addView(view);
    }


    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public boolean enableDefaultBack() {
        return true;
    }
}
