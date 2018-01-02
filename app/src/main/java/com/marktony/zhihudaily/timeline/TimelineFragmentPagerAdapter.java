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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.marktony.zhihudaily.R;


/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * {@link FragmentPagerAdapter} of {@link TimelineFragment}.
 */

public class TimelineFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int pageCount = 3;
    private String[] titles;

    private ZhihuDailyFragment mZhihuFragment;
    private DoubanMomentFragment mDoubanFragment;
    private GuokrHandpickFragment mGuokrFragment;

    public TimelineFragmentPagerAdapter(@NonNull FragmentManager fm,
                                        @NonNull Context context,
                                        @NonNull ZhihuDailyFragment zhihuDailyFragment,
                                        @NonNull DoubanMomentFragment doubanMomentFragment,
                                        @NonNull GuokrHandpickFragment guokrFragment) {
        super(fm);
        titles = new String[]{
                context.getString(R.string.zhihu_daily),
                context.getString(R.string.douban_moment),
                context.getString(R.string.guokr_handpick)};
        this.mZhihuFragment = zhihuDailyFragment;
        this.mDoubanFragment = doubanMomentFragment;
        this.mGuokrFragment = guokrFragment;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return mZhihuFragment;
        } else if (i == 1) {
            return mDoubanFragment;
        }
        return mGuokrFragment;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
