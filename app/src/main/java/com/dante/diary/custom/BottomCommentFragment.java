package com.dante.diary.custom;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dante.diary.R;

/**
 * A BottomDialogFragment that uses for comment.
 */
public class BottomCommentFragment extends DialogFragment {

    private static final String TAG = "BottomCommentFragment";
    protected View rootView;
    SparseArray<View.OnClickListener> ids = new SparseArray<>();
    DialogInterface.OnDismissListener dismissListener;
    private int layoutId;
    private Fragment f;
    private AppCompatActivity activity;
    private OnViewBind binder;

    public BottomCommentFragment() {
        Log.d(TAG, "BottomCommentFragment: create");
    }

    public static BottomCommentFragment create(int layoutId) {
        if (layoutId <= 0) {
            throw new UnsupportedOperationException("You must pass a valid layoutId");
        }
        return newInstance(layoutId);
    }

    public static BottomCommentFragment newInstance(int layoutId) {

        Bundle args = new Bundle();
        args.putInt("id", layoutId);
        BottomCommentFragment fragment = new BottomCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BottomCommentFragment with(Fragment f) {
        this.f = f;
        return this;
    }

    public BottomCommentFragment with(AppCompatActivity a) {
        this.activity = a;
        return this;
    }

    public void show() {
        if (f != null) {
            this.show(f.getFragmentManager(), "");
        } else if (activity != null) {
            this.show(activity.getSupportFragmentManager(), "");
        }
    }

    public BottomCommentFragment listenViewClick(int id, View.OnClickListener l) {
        ids.put(id, l);
        return this;
    }

    public BottomCommentFragment listenDismiss(Dialog.OnDismissListener l) {
        this.dismissListener = l;
        return this;
    }

    public BottomCommentFragment bindView(OnViewBind l) {
        this.binder = l;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new UnsupportedOperationException("You must pass a valid layoutId when create instance");
        }
        if (rootView == null) {
            layoutId = getArguments().getInt("id");
            rootView = inflater.inflate(layoutId, container, false);
            initViews();
        }
        bindView(rootView);
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    private void initViews() {
        setListeners();

    }

    public void bindView(View v) {
        if (binder != null) {
            binder.bind(v);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(TAG, "onDismiss: ");

        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }

    private void setListeners() {
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.keyAt(0);
            if (rootView.findViewById(id) != null) {
                rootView.findViewById(id).setOnClickListener(ids.get(id));
            }
        }
    }

    public interface OnViewBind {
        void bind(View v);
    }
}
