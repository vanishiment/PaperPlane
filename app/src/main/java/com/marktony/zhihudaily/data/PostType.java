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

package com.marktony.zhihudaily.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by lizhaotailang on 2017/6/19.
 *
 * Content types of in annotation type.
 */
@Retention(SOURCE)
@IntDef({PostType.TYPE_ZHIHU, PostType.TYPE_DOUBAN, PostType.TYPE_GUOKR})
public @interface PostType {

    int TYPE_ZHIHU = 0;

    int TYPE_DOUBAN = 1;

    int TYPE_GUOKR = 2;

}
