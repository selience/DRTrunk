package me.darkeet.android.demo.activity;

import android.os.Bundle;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.base.BaseActivity;
import me.darkeet.android.demo.fragment.HomeFragment;

/**
 * Name: HomeActivity
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/3 17:20
 * Desc:
 */
public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        setupFragmentContent(R.id.id_main_content);
        replace(HomeFragment.class, "HomeFragment", null);
    }
}
