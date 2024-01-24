// Generated by view binder compiler. Do not edit!
package com.example.app.databinding;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.viewbinding.ViewBinding;
import android.widget.LinearLayout;
import com.example.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final BottomNavigationView bv;

  @NonNull
  public final ViewPager vp;

  private ActivityMainBinding(@NonNull LinearLayout rootView, @NonNull BottomNavigationView bv,
      @NonNull ViewPager vp) {
    this.rootView = rootView;
    this.bv = bv;
    this.vp = vp;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      BottomNavigationView bv = rootView.findViewById(R.id.bv);
      if (bv == null) {
        missingId = "bv";
        break missingId;
      }
      ViewPager vp = rootView.findViewById(R.id.vp);
      if (vp == null) {
        missingId = "vp";
        break missingId;
      }
      return new ActivityMainBinding((LinearLayout) rootView, bv, vp);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
