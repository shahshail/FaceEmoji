// Generated code from Butter Knife. Do not modify!
package com.geekathlon.shailshah.faceemoji;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  private View view2131230776;

  private View view2131230846;

  private View view2131230828;

  private View view2131230759;

  @UiThread
  public MainActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.mImageView = Utils.findRequiredViewAsType(source, R.id.image_view, "field 'mImageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.emojify_button, "field 'mEmojifyButton' and method 'emojifyMe'");
    target.mEmojifyButton = Utils.castView(view, R.id.emojify_button, "field 'mEmojifyButton'", Button.class);
    view2131230776 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.emojifyMe();
      }
    });
    view = Utils.findRequiredView(source, R.id.share_button, "field 'mShareFeb' and method 'shareMe'");
    target.mShareFeb = Utils.castView(view, R.id.share_button, "field 'mShareFeb'", FloatingActionButton.class);
    view2131230846 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.shareMe();
      }
    });
    view = Utils.findRequiredView(source, R.id.save_button, "field 'mSaveFeb' and method 'saveMe'");
    target.mSaveFeb = Utils.castView(view, R.id.save_button, "field 'mSaveFeb'", FloatingActionButton.class);
    view2131230828 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.saveMe();
      }
    });
    view = Utils.findRequiredView(source, R.id.clear_button, "field 'mClearFeb' and method 'clearImage'");
    target.mClearFeb = Utils.castView(view, R.id.clear_button, "field 'mClearFeb'", FloatingActionButton.class);
    view2131230759 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clearImage();
      }
    });
    target.mTitleTextView = Utils.findRequiredViewAsType(source, R.id.title_text_view, "field 'mTitleTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mImageView = null;
    target.mEmojifyButton = null;
    target.mShareFeb = null;
    target.mSaveFeb = null;
    target.mClearFeb = null;
    target.mTitleTextView = null;

    view2131230776.setOnClickListener(null);
    view2131230776 = null;
    view2131230846.setOnClickListener(null);
    view2131230846 = null;
    view2131230828.setOnClickListener(null);
    view2131230828 = null;
    view2131230759.setOnClickListener(null);
    view2131230759 = null;

    this.target = null;
  }
}
