// Generated code from Butter Knife. Do not modify!
package me.darkeet.android.demo.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActionBarFragment$$ViewBinder<T extends me.darkeet.android.demo.fragment.ActionBarFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689610, "method 'addMenuItem'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.addMenuItem();
        }
      });
    view = finder.findRequiredView(source, 2131689611, "method 'addSearchView'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.addSearchView();
        }
      });
    view = finder.findRequiredView(source, 2131689612, "method 'addActionProvider'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.addActionProvider();
        }
      });
    view = finder.findRequiredView(source, 2131689613, "method 'addSettingProvider'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.addSettingProvider();
        }
      });
  }

  @Override public void unbind(T target) {
  }
}
