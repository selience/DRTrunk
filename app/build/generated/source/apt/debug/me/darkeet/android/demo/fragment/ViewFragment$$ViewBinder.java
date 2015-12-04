// Generated code from Butter Knife. Do not modify!
package me.darkeet.android.demo.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ViewFragment$$ViewBinder<T extends me.darkeet.android.demo.fragment.ViewFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689638, "method 'onClickWithText'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClickWithText(p0);
        }
      });
  }

  @Override public void unbind(T target) {
  }
}
