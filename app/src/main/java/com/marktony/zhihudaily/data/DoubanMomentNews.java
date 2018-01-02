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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/5/20.
 *
 * Immutable model class for douban moment news.
 * Entity class for {@link com.google.gson.Gson}.
 */

public class DoubanMomentNews {

    @Expose
    @SerializedName("count")
    private int count;

    @Expose
    @SerializedName("posts")
    private List<DoubanMomentNewsPosts> posts;

    @Expose
    @SerializedName("offset")
    private int offset;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("total")
    private int total;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DoubanMomentNewsPosts> getPosts() {
        return posts;
    }

    public void setPosts(List<DoubanMomentNewsPosts> posts) {
        this.posts = posts;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
