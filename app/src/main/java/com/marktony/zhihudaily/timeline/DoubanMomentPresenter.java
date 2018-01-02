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

import android.support.annotation.NonNull;

import com.marktony.zhihudaily.data.DoubanMomentNewsPosts;
import com.marktony.zhihudaily.data.source.datasource.DoubanMomentNewsDataSource;
import com.marktony.zhihudaily.data.source.repository.DoubanMomentNewsRepository;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * Listens to user actions from the UI ({@link DoubanMomentFragment}),
 * retrieves the data and update the UI as required.
 */

public class DoubanMomentPresenter implements DoubanMomentContract.Presenter {

    @NonNull
    private final DoubanMomentContract.View mView;

    @NonNull
    private final DoubanMomentNewsRepository mRepository;

    public DoubanMomentPresenter(@NonNull DoubanMomentContract.View view,
                                 @NonNull DoubanMomentNewsRepository repository) {
        this.mView = view;
        this.mRepository = repository;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(boolean forceUpdate, boolean clearCache, long date) {

        mRepository.getDoubanMomentNews(forceUpdate, clearCache, date, new DoubanMomentNewsDataSource.LoadDoubanMomentDailyCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<DoubanMomentNewsPosts> list) {
                if (mView.isActive()) {
                    mView.showResult(list);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                }
            }
        });
    }

}
