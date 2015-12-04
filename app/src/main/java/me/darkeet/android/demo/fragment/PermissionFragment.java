package me.darkeet.android.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.demo.fragment.permission.PermissionAnnotationFragment;
import me.darkeet.android.demo.fragment.permission.PermissionNormalFragment;

/**
 * Name: PermissionFragment
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/11/4 16:06
 * Desc: 运行时请求权限
 */
public class PermissionFragment extends DRBaseStackFragment {
    @Bind(R.id.id_tablayout) TabLayout mTabLayout;
    @Bind(R.id.id_viewpager) ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_activity_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] titles = new String[]{
                "系统API",
                "第三方库支持"
        };

        String[] fragments = new String[]{
                PermissionNormalFragment.class.getName(),
                PermissionAnnotationFragment.class.getName()
        };

        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(
                this, Arrays.asList(titles), Arrays.asList(fragments));
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(titles[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles[1]));

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(pagerAdapter);
    }

    static class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        private Context context;
        private List<String> mTitles;
        private List<String> mFragments;

        public MyViewPagerAdapter(Fragment fragment, List<String> titles, List<String> mFragments) {
            super(fragment.getChildFragmentManager());
            this.mFragments = mFragments;
            this.mTitles = titles;
            this.context = fragment.getActivity();
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(context, mFragments.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
