package me.darkeet.android.demo.activity;

import android.os.Bundle;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.base.BaseActivity;
import me.darkeet.android.demo.fragment.ViewFragment;

/**
 * Name: ViewActivity
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/27 16:28
 * Desc: 自定义View
 */
public class ViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        setupFragmentContent(R.id.id_main_content);
        replace(ViewFragment.class, "ViewFragment", null);
    }
}
