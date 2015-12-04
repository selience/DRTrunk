
package me.darkeet.android.demo.fragment;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.darkeet.android.base.DRBaseStackFragment;
import me.darkeet.android.demo.R;
import me.darkeet.android.demo.constant.IntentConstants;
import me.darkeet.android.demo.widget.PagerSlidingTabStrip;

public class SwipeFragment extends DRBaseStackFragment {

    @Bind(R.id.pager) ViewPager pager;
    @Bind(R.id.tabs) PagerSlidingTabStrip tabs;

    private MyPagerAdapter adapter;
    private int currentColor = 0xFF666666;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getArguments().getString(IntentConstants.FRAGMENT_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment_swipe, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pager.setPageTransformer(false, new PageTransformer() {
            @Override
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            public void transformPage(View page, float position) {
                page.setRotationY(position * -30);
            }
        });
        adapter = new MyPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        changeColor(currentColor);
    }

    @OnClick({R.id.image01, R.id.image02, R.id.image03,
            R.id.image04, R.id.image05, R.id.image06})
    public void onClick(View v) {
        changeColor(Color.parseColor(v.getTag().toString()));
    }

    private void changeColor(int newColor) {
        currentColor = newColor;
        tabs.setIndicatorColor(newColor);
        getActionBar().setBackgroundDrawable(new ColorDrawable(newColor));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Categories", "Home", "Top Paid", "Top Free",
                "Top Grossing", "Top New Paid", "Top New Free", "Trending"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return PlanetFragment.newInstance(TITLES[position]);
        }
    }

}
