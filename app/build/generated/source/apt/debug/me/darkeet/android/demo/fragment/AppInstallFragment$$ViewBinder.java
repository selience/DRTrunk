// Generated code from Butter Knife. Do not modify!
package me.darkeet.android.demo.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AppInstallFragment$$ViewBinder<T extends me.darkeet.android.demo.fragment.AppInstallFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689616, "method 'startListener'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.startListener();
        }
      });
    view = finder.findRequiredView(source, 2131689617, "method 'requestFile'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.requestFile();
        }
      });
    view = finder.findRequiredView(source, 2131689618, "method 'startInstall'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.startInstall();
        }
      });
    view = finder.findRequiredView(source, 2131689619, "method 'startUninstall'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.startUninstall();
        }
      });
  }

  @Override public void unbind(T target) {
  }
}
