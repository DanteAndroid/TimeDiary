package com.dante.diary.follow;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dante.diary.R;
import com.dante.diary.base.BaseFragment;
import com.dante.diary.base.Constants;
import com.dante.diary.base.RecyclerFragment;
import com.dante.diary.base.TabPagerAdapter;
import com.dante.diary.login.LoginManager;
import com.dante.diary.notification.NotificationListFragment;
import com.dante.diary.profile.DiaryListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

import static com.dante.diary.base.App.context;

/**
 * Created by yons on 17/3/15.
 */

public class TabsFragment extends BaseFragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    private TabPagerAdapter adapter;
    private List<RecyclerFragment> fragments = new ArrayList<>();
    private String[] titles;

    public static TabsFragment newInstance(String[] titles) {
        Bundle args = new Bundle();
        args.putStringArray(Constants.DATA, titles);
        TabsFragment fragment = new TabsFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    protected void setAnimations() {
//        setReturnTransition(initTransitions());
//        setReenterTransition(new Slide(Gravity.RIGHT));
//        setEnterTransition(new Slide(Gravity.RIGHT));
//        setExitTransition(initTransitions());
//    }

    @Override
    protected int initLayoutId() {
        return R.layout.tab_pager_layout;
    }

    @Override
    protected void initViews() {
        initTabs();

    }

    @Override
    protected void initData() {
        barActivity.showBottomBar();
    }

    private void initTabs() {
        adapter = new TabPagerAdapter(getChildFragmentManager());
        initFragments();
        pager.setAdapter(adapter);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setupWithViewPager(pager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView icon = (TextView) tab.getCustomView();
                assert icon != null;
                icon.setTypeface(null, Typeface.BOLD);
                icon.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView icon = (TextView) tab.getCustomView();
                assert icon != null;
                icon.setTypeface(null, Typeface.NORMAL);
                icon.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                fragments.get(tab.getPosition()).scrollToTop();
                if (barActivity != null) {
                    barActivity.showBottomBar();
                }
            }
        });
        setupTabsIcon();
    }

    private void setupTabsIcon() {
        TextView following = (TextView) LayoutInflater.from(context).inflate(R.layout.tab_icon_0, (ViewGroup) rootView, false);
        following.setText(titles[0]);
        tabs.getTabAt(0).setCustomView(following);
        TextView followDiaries = (TextView) LayoutInflater.from(context).inflate(R.layout.tab_icon_1, (ViewGroup) rootView, false);
        followDiaries.setText(titles[1]);
        tabs.getTabAt(1).setCustomView(followDiaries);
    }

    private void initFragments() {
        titles = getArguments().getStringArray(Constants.DATA);
        assert titles != null;

        if (titles[0].equals(getString(R.string.my_following_diary))) {
            fragments.add(DiaryListFragment.newInstance(LoginManager.getMyId(), DiaryListFragment.FOLLOWING, null));//关注的日记

        } else if (titles[0].equals(getString(R.string.my_notifications))) {
            fragments.add(new NotificationListFragment());//我的通知
        }
        if (titles[1].equals(getString(R.string.my_following))) {
            fragments.add(FollowListFragment.newInstance(FollowListFragment.FOLLOWING));//我关注的人

        } else if (titles[1].equals(getString(R.string.my_followers))) {
            fragments.add(FollowListFragment.newInstance(FollowListFragment.FOLLOWER));//我关注的人
        }

        adapter.setFragments(fragments, titles);
    }


}
