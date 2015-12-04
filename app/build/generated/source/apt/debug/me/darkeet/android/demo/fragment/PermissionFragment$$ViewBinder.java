// Generated code from Butter Knife. Do not modify!
package me.darkeet.android.demo.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PermissionFragment$$ViewBinder<T extends me.darkeet.android.demo.fragment.PermissionFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689604, "field 'mTabLayout'");
    target.mTabLayout = finder.castView(view, 2131689604, "field 'mTabLayout'");
    view = finder.findRequiredView(source, 2131689605, "field 'mViewPager'");
    target.mViewPager = finder.castView(view, 2131689605, "field 'mViewPager'");
  }

  @Override public void unbind(T target) {
    target.mTabLayout = null;
    target.mViewPager = null;
  }
}
