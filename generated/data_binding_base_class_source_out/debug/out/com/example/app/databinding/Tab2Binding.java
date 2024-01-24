// Generated by view binder compiler. Do not edit!
package com.example.app.databinding;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.viewbinding.ViewBinding;
import android.widget.LinearLayout;
import android.widget.Switch;
import com.example.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class Tab2Binding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Switch swich1;

  @NonNull
  public final Switch swich2;

  private Tab2Binding(@NonNull LinearLayout rootView, @NonNull Switch swich1,
      @NonNull Switch swich2) {
    this.rootView = rootView;
    this.swich1 = swich1;
    this.swich2 = swich2;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static Tab2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static Tab2Binding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.tab2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static Tab2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      Switch swich1 = rootView.findViewById(R.id.swich1);
      if (swich1 == null) {
        missingId = "swich1";
        break missingId;
      }
      Switch swich2 = rootView.findViewById(R.id.swich2);
      if (swich2 == null) {
        missingId = "swich2";
        break missingId;
      }
      return new Tab2Binding((LinearLayout) rootView, swich1, swich2);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
