/*
 * Copyright 2016 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marktony.zhihudaily.timeline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marktony.zhihudaily.R;
import com.marktony.zhihudaily.data.source.repository.DoubanMomentNewsRepository;
import com.marktony.zhihudaily.data.source.repository.GuokrHandpickNewsRepository;
import com.marktony.zhihudaily.data.source.repository.ZhihuDailyNewsRepository;
import com.marktony.zhihudaily.data.source.local.DoubanMomentNewsLocalDataSource;
import com.marktony.zhihudaily.data.source.local.GuokrHandpickNewsLocalDataSource;
import com.marktony.zhihudaily.data.source.local.ZhihuDailyNewsLocalDataSource;
import com.marktony.zhihudaily.data.source.remote.DoubanMomentNewsRemoteDataSource;
import com.marktony.zhihudaily.data.source.remote.GuokrHandpickNewsRemoteDataSource;
import com.marktony.zhihudaily.data.source.remote.ZhihuDailyNewsRemoteDataSource;

/**
 * Created by lizhaotailang on 2017/5/20.
 *
 * Main UI for displaying the {@link ViewPager}
 * which was set up with {@link TabLayout}.
 */

public class TimelineFragment extends Fragment {

    private FloatingActionButton mFab;
    private TabLayout mTabLayout;

    private ZhihuDailyFragment mZhihuFragment;
    private DoubanMomentFragment mDoubanFragment;
    private GuokrHandpickFragment mGuokrFragment;

    public TimelineFragment() {
        // Requires the empty constructor
    }

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            FragmentManager fm = getChildFragmentManager();
            mZhihuFragment = (ZhihuDailyFragment) fm.getFragment(savedInstanceState, ZhihuDailyFragment.class.getSimpleName());
            mDoubanFragment = (DoubanMomentFragment) fm.getFragment(savedInstanceState, DoubanMomentFragment.class.getSimpleName());
            mGuokrFragment = (GuokrHandpickFragment) fm.getFragment(savedInstanceState, GuokrHandpickFragment.class.getSimpleName());
        } else {
            mZhihuFragment = ZhihuDailyFragment.newInstance();
            mDoubanFragment = DoubanMomentFragment.newInstance();
            mGuokrFragment = GuokrHandpickFragment.newInstance();
        }

        new ZhihuDailyPresenter(mZhihuFragment, ZhihuDailyNewsRepository.getInstance(
                ZhihuDailyNewsRemoteDataSource.getInstance(),
                ZhihuDailyNewsLocalDataSource.getInstance(getContext())));

        new DoubanMomentPresenter(mDoubanFragment, DoubanMomentNewsRepository.getInstance(
                DoubanMomentNewsRemoteDataSource.getInstance(),
                DoubanMomentNewsLocalDataSource.getInstance(getContext())));

        new GuokrHandpickPresenter(mGuokrFragment, GuokrHandpickNewsRepository.getInstance(
                GuokrHandpickNewsRemoteDataSource.getInstance(),
                GuokrHandpickNewsLocalDataSource.getInstance(getContext())));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        initViews(view);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mFab.setOnClickListener(v -> {
            if (mTabLayout.getSelectedTabPosition() == 0) {
                mZhihuFragment.showDatePickerDialog();
            } else {
                mDoubanFragment.showDatePickerDialog();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fm = getChildFragmentManager();
        if (mZhihuFragment.isAdded()) {
            fm.putFragment(outState, ZhihuDailyFragment.class.getSimpleName(), mZhihuFragment);
        }
        if (mGuokrFragment.isAdded()) {
            fm.putFragment(outState, DoubanMomentFragment.class.getSimpleName(), mDoubanFragment);
        }
        if (mDoubanFragment.isAdded()) {
            fm.putFragment(outState, GuokrHandpickFragment.class.getSimpleName(), mGuokrFragment);
        }
    }

    private void initViews(View view) {
        ViewPager mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TimelineFragmentPagerAdapter(
                getChildFragmentManager(),
                getContext(),
                mZhihuFragment,
                mDoubanFragment,
                mGuokrFragment));
        mViewPager.setOffscreenPageLimit(3);

        mTabLayout = view.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mFab = view.findViewById(R.id.fab);
    }

}
