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

import android.support.annotation.NonNull;

import com.marktony.zhihudaily.R;
import com.marktony.zhihudaily.data.ContentType;
import com.marktony.zhihudaily.data.DoubanMomentContent;
import com.marktony.zhihudaily.data.DoubanMomentNewsPosts;
import com.marktony.zhihudaily.data.GuokrHandpickContentResult;
import com.marktony.zhihudaily.data.ZhihuDailyContent;
import com.marktony.zhihudaily.data.source.datasource.DoubanMomentContentDataSource;
import com.marktony.zhihudaily.data.source.datasource.GuokrHandpickContentDataSource;
import com.marktony.zhihudaily.data.source.datasource.ZhihuDailyContentDataSource;
import com.marktony.zhihudaily.data.source.repository.DoubanMomentContentRepository;
import com.marktony.zhihudaily.data.source.datasource.DoubanMomentNewsDataSource;
import com.marktony.zhihudaily.data.source.repository.DoubanMomentNewsRepository;
import com.marktony.zhihudaily.data.source.repository.GuokrHandpickContentRepository;
import com.marktony.zhihudaily.data.source.repository.GuokrHandpickNewsRepository;
import com.marktony.zhihudaily.data.source.repository.ZhihuDailyContentRepository;
import com.marktony.zhihudaily.data.source.repository.ZhihuDailyNewsRepository;

/**
 * Created by lizhaotailang on 2017/5/24.
 *
 * Listen to user actions from the UI ({@link DetailsFragment}),
 * retrieves the data and updates the UI as required.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    @NonNull
    private final DetailsContract.View mView;

    private DoubanMomentNewsRepository mDoubanNewsRepository;
    private DoubanMomentContentRepository mDoubanContentRepository;

    private ZhihuDailyNewsRepository mZhihuNewsRepository;
    private ZhihuDailyContentRepository mZhihuContentRepository;

    private GuokrHandpickNewsRepository mGuokrNewsRepository;
    private GuokrHandpickContentRepository mGuokrContentRepository;

    public DetailsPresenter(@NonNull DetailsContract.View view,
                            @NonNull DoubanMomentNewsRepository doubanNewsRepository,
                            @NonNull DoubanMomentContentRepository doubanContentRepository) {
        this.mView = view;
        mView.setPresenter(this);
        this.mDoubanContentRepository = doubanContentRepository;
        this.mDoubanNewsRepository = doubanNewsRepository;
    }

    public DetailsPresenter(@NonNull DetailsContract.View view,
                            @NonNull ZhihuDailyNewsRepository zhihuNewsRepository,
                            @NonNull ZhihuDailyContentRepository zhihuContentRepository) {
        this.mView = view;
        mView.setPresenter(this);
        mZhihuNewsRepository = zhihuNewsRepository;
        mZhihuContentRepository = zhihuContentRepository;
    }

    public DetailsPresenter(@NonNull DetailsContract.View view,
                            @NonNull GuokrHandpickNewsRepository guokrNewsRepository,
                            @NonNull GuokrHandpickContentRepository guokrContentRepository) {
        this.mView = view;
        this.mView.setPresenter(this);
        mGuokrNewsRepository = guokrNewsRepository;
        mGuokrContentRepository = guokrContentRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void favorite(ContentType type, int id, boolean favorite) {
        if (type == ContentType.TYPE_ZHIHU_DAILY) {
            mZhihuNewsRepository.favoriteItem(id, favorite);
        } else if (type == ContentType.TYPE_DOUBAN_MOMENT) {
            mDoubanNewsRepository.favoriteItem(id, favorite);
        } else {
            mGuokrNewsRepository.favoriteItem(id, favorite);
        }
    }

    @Override
    public void loadDoubanContent(int id) {
        mDoubanContentRepository.getDoubanMomentContent(id, new DoubanMomentContentDataSource.LoadDoubanMomentContentCallback() {
            @Override
            public void onContentLoaded(@NonNull DoubanMomentContent content) {
                mDoubanNewsRepository.getItem(id, new DoubanMomentNewsDataSource.GetNewsItemCallback() {
                    @Override
                    public void onItemLoaded(@NonNull DoubanMomentNewsPosts item) {
                        if (mView.isActive()) {
                            mView.showDoubanMomentContent(content, item.getThumbs());
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (mView.isActive()) {
                            mView.showMessage(R.string.something_wrong);
                        }
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.showMessage(R.string.something_wrong);
                }
            }
        });
    }

    @Override
    public void loadZhihuDailyContent(int id) {
        mZhihuContentRepository.getZhihuDailyContent(id, new ZhihuDailyContentDataSource.LoadZhihuDailyContentCallback() {
            @Override
            public void onContentLoaded(@NonNull ZhihuDailyContent content) {
                if (mView.isActive()) {
                    mView.showZhihuDailyContent(content);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.showMessage(R.string.something_wrong);
                }
            }
        });
    }

    @Override
    public void loadGuokrHandpickContent(int id) {
        mGuokrContentRepository.getGuokrHandpickContent(id, new GuokrHandpickContentDataSource.LoadGuokrHandpickContentCallback() {
            @Override
            public void onContentLoaded(@NonNull GuokrHandpickContentResult content) {
                if (mView.isActive()) {
                    mView.showGuokrHandpickContent(content);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.showMessage(R.string.something_wrong);
                }
            }
        });
    }

    @Override
    public void getLink(ContentType type, int requestCode, int id) {
        switch (type) {
            case TYPE_ZHIHU_DAILY:
                mZhihuContentRepository.getZhihuDailyContent(id, new ZhihuDailyContentDataSource.LoadZhihuDailyContentCallback() {
                    @Override
                    public void onContentLoaded(@NonNull ZhihuDailyContent content) {
                        if (mView.isActive()) {
                            String url = content.getShareUrl();
                            if (requestCode == DetailsFragment.REQUEST_SHARE) {
                                mView.share(url);
                            } else if (requestCode == DetailsFragment.REQUEST_COPY_LINK) {
                                mView.copyLink(url);
                            } else if (requestCode == DetailsFragment.REQUEST_OPEN_WITH_BROWSER){
                                mView.openWithBrowser(url);
                            }
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (mView.isActive()) {
                            mView.showMessage(R.string.share_error);
                        }
                    }
                });
                break;
            case TYPE_DOUBAN_MOMENT:
                mDoubanContentRepository.getDoubanMomentContent(id, new DoubanMomentContentDataSource.LoadDoubanMomentContentCallback() {
                    @Override
                    public void onContentLoaded(@NonNull DoubanMomentContent content) {
                        if (mView.isActive()) {
                            String url = content.getUrl();
                            if (requestCode == DetailsFragment.REQUEST_SHARE) {
                                mView.share(url);
                            } else if (requestCode == DetailsFragment.REQUEST_COPY_LINK){
                                mView.copyLink(url);
                            } else if (requestCode == DetailsFragment.REQUEST_OPEN_WITH_BROWSER) {
                                mView.openWithBrowser(url);
                            }
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (mView.isActive()) {
                            mView.showMessage(R.string.share_error);
                        }
                    }
                });
                break;
            case TYPE_GUOKR_HANDPICK:
                mGuokrContentRepository.getGuokrHandpickContent(id, new GuokrHandpickContentDataSource.LoadGuokrHandpickContentCallback() {
                    @Override
                    public void onContentLoaded(@NonNull GuokrHandpickContentResult content) {
                        if (mView.isActive()) {
                            String url = content.getUrl();
                            if (requestCode == DetailsFragment.REQUEST_SHARE) {
                                mView.share(url);
                            } else if (requestCode == DetailsFragment.REQUEST_COPY_LINK) {
                                mView.copyLink(url);
                            } else if (requestCode == DetailsFragment.REQUEST_OPEN_WITH_BROWSER) {
                                mView.openWithBrowser(url);
                            }
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (mView.isActive()) {
                            mView.showMessage(R.string.share_error);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
