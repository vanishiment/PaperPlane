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

package com.marktony.zhihudaily.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.marktony.zhihudaily.R;
import com.marktony.zhihudaily.data.ContentType;
import com.marktony.zhihudaily.data.source.local.GuokrHandpickContentLocalDataSource;
import com.marktony.zhihudaily.data.source.local.GuokrHandpickNewsLocalDataSource;
import com.marktony.zhihudaily.data.source.local.ZhihuDailyContentLocalDataSource;
import com.marktony.zhihudaily.data.source.local.ZhihuDailyNewsLocalDataSource;
import com.marktony.zhihudaily.data.source.remote.GuokrHandpickContentRemoteDataSource;
import com.marktony.zhihudaily.data.source.remote.GuokrHandpickNewsRemoteDataSource;
import com.marktony.zhihudaily.data.source.remote.ZhihuDailyContentRemoteDataSource;
import com.marktony.zhihudaily.data.source.remote.ZhihuDailyNewsRemoteDataSource;
import com.marktony.zhihudaily.data.source.repository.DoubanMomentContentRepository;
import com.marktony.zhihudaily.data.source.repository.DoubanMomentNewsRepository;
import com.marktony.zhihudaily.data.source.local.DoubanMomentContentLocalDataSource;
import com.marktony.zhihudaily.data.source.local.DoubanMomentNewsLocalDataSource;
import com.marktony.zhihudaily.data.source.remote.DoubanMomentContentRemoteDataSource;
import com.marktony.zhihudaily.data.source.remote.DoubanMomentNewsRemoteDataSource;
import com.marktony.zhihudaily.data.source.repository.GuokrHandpickContentRepository;
import com.marktony.zhihudaily.data.source.repository.GuokrHandpickNewsRepository;
import com.marktony.zhihudaily.data.source.repository.ZhihuDailyContentRepository;
import com.marktony.zhihudaily.data.source.repository.ZhihuDailyNewsRepository;

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * Activity for displaying the details of content.
 */

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_ARTICLE_TYPE = "KEY_ARTICLE_TYPE";
    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";
    public static final String KEY_ARTICLE_TITLE = "KEY_ARTICLE_TITLE";
    public static final String KEY_ARTICLE_IS_FAVORITE = "KEY_ARTICLE_IS_FAVORITE";

    private DetailsFragment mDetailsFragment;

    private ContentType mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        if (savedInstanceState != null) {
            mDetailsFragment = (DetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, DetailsFragment.class.getSimpleName());
        } else {
            mDetailsFragment = DetailsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mDetailsFragment, DetailsFragment.class.getSimpleName())
                    .commit();
        }

        mType = (ContentType) getIntent().getSerializableExtra(KEY_ARTICLE_TYPE);
        if (mType == ContentType.TYPE_ZHIHU_DAILY) {

            new DetailsPresenter(mDetailsFragment,
                    ZhihuDailyNewsRepository.getInstance(ZhihuDailyNewsRemoteDataSource.getInstance(),
                            ZhihuDailyNewsLocalDataSource.getInstance(DetailsActivity.this)),
                    ZhihuDailyContentRepository.getInstance(ZhihuDailyContentRemoteDataSource.getInstance(),
                            ZhihuDailyContentLocalDataSource.getInstance(DetailsActivity.this)));

        } else if (mType == ContentType.TYPE_DOUBAN_MOMENT) {
            new DetailsPresenter(mDetailsFragment,
                    DoubanMomentNewsRepository.getInstance(DoubanMomentNewsRemoteDataSource.getInstance(),
                            DoubanMomentNewsLocalDataSource.getInstance(DetailsActivity.this)),
                    DoubanMomentContentRepository.getInstance(DoubanMomentContentRemoteDataSource.getInstance(),
                            DoubanMomentContentLocalDataSource.getInstance(DetailsActivity.this)));
        } else if (mType == ContentType.TYPE_GUOKR_HANDPICK) {
            new DetailsPresenter(mDetailsFragment,
                    GuokrHandpickNewsRepository.getInstance(GuokrHandpickNewsRemoteDataSource.getInstance(),
                            GuokrHandpickNewsLocalDataSource.getInstance(DetailsActivity.this)),
                    GuokrHandpickContentRepository.getInstance(GuokrHandpickContentRemoteDataSource.getInstance(), GuokrHandpickContentLocalDataSource.getInstance(DetailsActivity.this)));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZhihuDailyContentRepository.destroyInstance();
        DoubanMomentContentRepository.destroyInstance();
        GuokrHandpickContentRepository.destroyInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mDetailsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, DetailsFragment.class.getSimpleName(), mDetailsFragment);
        }
    }
}
