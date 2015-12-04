// Generated code from Butter Knife. Do not modify!
package me.darkeet.android.demo.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebpFragment$$ViewBinder<T extends me.darkeet.android.demo.fragment.WebpFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689644, "field 'imageView'");
    target.imageView = finder.castView(view, 2131689644, "field 'imageView'");
    view = finder.findRequiredView(source, 2131689640, "method 'decodeWebpBitmap'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.decodeWebpBitmap();
        }
      });
    view = finder.findRequiredView(source, 2131689641, "method 'decodeWebpBitmapView'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.decodeWebpBitmapView();
        }
      });
    view = finder.findRequiredView(source, 2131689642, "method 'encodeWebpBitmap'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.encodeWebpBitmap();
        }
      });
    view = finder.findRequiredView(source, 2131689643, "method 'encodeWebpBitmapWith565'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.encodeWebpBitmapWith565();
        }
      });
  }

  @Override public void unbind(T target) {
    target.imageView = null;
  }
}
