// Generated code from Butter Knife. Do not modify!
package me.darkeet.android.demo.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomeFragment$$ViewBinder<T extends me.darkeet.android.demo.fragment.HomeFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 16908298, "field 'mListView'");
    target.mListView = finder.castView(view, 16908298, "field 'mListView'");
  }

  @Override public void unbind(T target) {
    target.mListView = null;
  }
}
