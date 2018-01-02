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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marktony.zhihudaily.database.converter.StringTypeConverter;

import java.util.List;

/**
 * Created by lizhaotailang on 2017/5/20.
 *
 * Immutable model class for zhihu daily content.
 * Entity class for {@link com.google.gson.Gson} and {@link android.arch.persistence.room.Room}.
 */

@Entity(tableName = "zhihu_daily_content")
@TypeConverters(StringTypeConverter.class)
public class ZhihuDailyContent {

    @ColumnInfo(name = "body")
    @Expose
    @SerializedName("body")
    private String body;

    @ColumnInfo(name = "image_source")
    @Expose
    @SerializedName("image_source")
    private String imageSource;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "image")
    @Expose
    @SerializedName("image")
    private String image;

    @ColumnInfo(name = "share_url")
    @Expose
    @SerializedName("share_url")
    private String shareUrl;

    @ColumnInfo(name = "js")
    @Expose
    @SerializedName("js")
    private List<String> js;

    @Ignore // This field will be ignored.
    @Expose
    @SerializedName("ga_prefix")
    private String gaPrefix;

    @ColumnInfo(name = "images")
    @Expose
    @SerializedName("images")
    private List<String> images;

    @ColumnInfo(name = "type")
    @Expose
    @SerializedName("type")
    private int type;

    @PrimaryKey
    @ColumnInfo(name = "id")
    @Expose
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "css")
    @Expose
    @SerializedName("css")
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

}
