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

package com.marktony.zhihudaily.favorites;

import android.support.annotation.NonNull;

import com.marktony.zhihudaily.data.DoubanMomentNewsPosts;
import com.marktony.zhihudaily.data.GuokrHandpickNewsResult;
import com.marktony.zhihudaily.data.ZhihuDailyNewsQuestion;
import com.marktony.zhihudaily.data.source.datasource.DoubanMomentNewsDataSource;
import com.marktony.zhihudaily.data.source.datasource.GuokrHandpickDataSource;
import com.marktony.zhihudaily.data.source.datasource.ZhihuDailyNewsDataSource;
import com.marktony.zhihudaily.data.source.repository.DoubanMomentNewsRepository;
import com.marktony.zhihudaily.data.source.repository.GuokrHandpickNewsRepository;
import com.marktony.zhihudaily.data.source.repository.ZhihuDailyNewsRepository;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/6/6.
 *
 * Listens the actions from UI ({@link FavoritesFragment}),
 * retrieves the data and update the UI as required.
 */

public class FavoritesPresenter implements FavoritesContract.Presenter {

    @NonNull
    private final FavoritesContract.View mView;

    @NonNull
    private final ZhihuDailyNewsRepository mZhihuRepository;

    @NonNull
    private final DoubanMomentNewsRepository mDoubanRepository;

    @NonNull
    private final GuokrHandpickNewsRepository mGuokrRepository;

    public FavoritesPresenter(@NonNull FavoritesContract.View view,
                              @NonNull ZhihuDailyNewsRepository zhihuRepository,
                              @NonNull DoubanMomentNewsRepository doubanRepository,
                              @NonNull GuokrHandpickNewsRepository guokrRepository) {
        mView = view;
        mView.setPresenter(this);
        this.mZhihuRepository = zhihuRepository;
        this.mDoubanRepository = doubanRepository;
        this.mGuokrRepository = guokrRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadFavorites() {
        mZhihuRepository.getFavorites(new ZhihuDailyNewsDataSource.LoadZhihuDailyNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<ZhihuDailyNewsQuestion> zhihuList) {

                mDoubanRepository.getFavorites(new DoubanMomentNewsDataSource.LoadDoubanMomentDailyCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<DoubanMomentNewsPosts> doubanList) {

                        mGuokrRepository.getFavorites(new GuokrHandpickDataSource.LoadGuokrHandpickNewsCallback() {
                            @Override
                            public void onNewsLoad(@NonNull List<GuokrHandpickNewsResult> guokrList) {
                                if (mView.isActive()) {
                                    mView.showFavorites(zhihuList, doubanList, guokrList);
                                }
                                mView.setLoadingIndicator(false);
                            }

                            @Override
                            public void onDataNotAvailable(){
                                mView.setLoadingIndicator(false);
                            }
                        });
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
