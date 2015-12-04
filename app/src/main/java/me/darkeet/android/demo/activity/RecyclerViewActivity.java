package me.darkeet.android.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.List;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.base.BaseActivity;
import me.darkeet.android.demo.fragment.RecyclerViewFragment;

/**
 * Name: RecyclerViewActivity
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/22 17:37
 * Desc:
 */
public class RecyclerViewActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] mTitles;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_tab);
        mTitles = getResources().getStringArray(R.array.tab_titles);

        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Bundle mBundle = new Bundle();
            mBundle.putInt("flag", i);
            RecyclerViewFragment mFragment = new RecyclerViewFragment();
            mFragment.setArguments(mBundle);
            mFragments.add(i, mFragment);
        }

        MyViewPagerAdapter mPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }

    static class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        private String[] mTitles;
        private List<Fragment> mFragments;

        public MyViewPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> mFragments) {
            super(fm);
            this.mTitles = mTitles;
            this.mFragments = mFragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
