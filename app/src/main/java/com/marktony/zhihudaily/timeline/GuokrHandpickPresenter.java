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

import com.marktony.zhihudaily.data.GuokrHandpickNewsResult;
import com.marktony.zhihudaily.data.source.datasource.GuokrHandpickDataSource;
import com.marktony.zhihudaily.data.source.repository.GuokrHandpickNewsRepository;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * Listens to user actions from UI ({@link GuokrHandpickFragment}),
 * retrieves data and update the UI as required.
 */

public class GuokrHandpickPresenter implements GuokrHandpickContract.Presenter {

    @NonNull
    private final GuokrHandpickContract.View mView;

    @NonNull
    private final GuokrHandpickNewsRepository mRepository;

    public GuokrHandpickPresenter(@NonNull GuokrHandpickContract.View  view,
                                  @NonNull GuokrHandpickNewsRepository repository) {
        this.mView = view;
        this.mRepository = repository;
        this.mView.setPresenter(this);
    }

    @Override
    public void load(boolean forceUpdate, boolean clearCache, int offset, int limit) {

        mRepository.getGuokrHandpickNews(forceUpdate, clearCache, offset, limit, new GuokrHandpickDataSource.LoadGuokrHandpickNewsCallback() {
            @Override
            public void onNewsLoad(@NonNull List<GuokrHandpickNewsResult> list) {
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

    @Override
    public void start() {

    }
}
