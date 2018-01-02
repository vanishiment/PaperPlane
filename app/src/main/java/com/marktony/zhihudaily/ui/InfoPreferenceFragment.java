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

package com.marktony.zhihudaily.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marktony.zhihudaily.R;
import com.marktony.zhihudaily.customtabs.CustomTabsHelper;
import com.marktony.zhihudaily.util.InfoConstants;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * A preference fragment that displays the setting options and
 * about page.
 */

public class InfoPreferenceFragment extends PreferenceFragmentCompat {

    private static final int MSG_GLIDE_CLEAR_CACHE_DONE = 1;

    private final Handler handler = new Handler(message -> {
        switch (message.what) {
            case MSG_GLIDE_CLEAR_CACHE_DONE:
                showMessage(R.string.clear_image_cache_successfully);
                break;
            default:
                break;
        }
        return true;
    });

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.info_preference);

        // Setting of night mode
        findPreference(InfoConstants.KEY_NIGHT_MODE).setOnPreferenceChangeListener((p, o) -> {
            if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                    == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
            getActivity().recreate();
            return true;
        });

        // Clear the cache of glide
        findPreference("clear_glide_cache").setOnPreferenceClickListener(v -> {
            new Thread(() -> {
                Glide.get(getContext()).clearDiskCache();
                handler.sendEmptyMessage(MSG_GLIDE_CLEAR_CACHE_DONE);
            }).start();
            return true;
        });

        // Rate
        findPreference("rate").setOnPreferenceClickListener(p -> {
            try {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException ex){
                showMessage(R.string.something_wrong);
            }
            return true;
        });

        // Open the github contributors page
        findPreference("contributors").setOnPreferenceClickListener( p -> {
            CustomTabsHelper.openUrl(getContext(), getString(R.string.contributors_desc));
            return true;
        });

        // Open the github links
        findPreference("follow_me_on_github").setOnPreferenceClickListener(p -> {
            CustomTabsHelper.openUrl(getContext(), getString(R.string.follow_me_on_github_desc));
            return true;
        });

        // Open the zhihu links
        findPreference("follow_me_on_zhihu").setOnPreferenceClickListener(p -> {
            CustomTabsHelper.openUrl(getContext(), getString(R.string.follow_me_on_zhihu_desc));
            return true;
        });

        // Feedback through sending an email
        findPreference("feedback").setOnPreferenceClickListener(p -> {
            try{
                Uri uri = Uri.parse(getString(R.string.sendto));
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic));
                intent.putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.device_model) + Build.MODEL + "\n"
                                + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
                                + getString(R.string.version));
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException ex){
                showMessage(R.string.no_mail_app);
            }
            return true;
        });

        // Open the github home page
        findPreference("source_code").setOnPreferenceClickListener(p -> {
            CustomTabsHelper.openUrl(getContext(), getString(R.string.source_code_desc));
            return true;
        });

        // Show the donation dialog
        findPreference("coffee").setOnPreferenceClickListener(p -> {
            AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
            dialog.setTitle(R.string.donate);
            dialog.setMessage(getString(R.string.donate_content));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.positive), (dialogInterface, i) -> {
                // 将指定账号添加到剪切板
                // add the alipay account to clipboard
                ClipboardManager manager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", getString(R.string.donate_account));
                manager.setPrimaryClip(clipData);
                showMessage(R.string.copied_to_clipboard);
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.negative), (dialogInterface, i) -> {

            });
            dialog.show();
            return true;
        });

        // Show the open source licenses
        findPreference("open_source_license").setOnPreferenceClickListener(p -> {
            startActivity(new Intent(getActivity(), LicenseActivity.class));
            return true;
        });

    }

    private void showMessage(@StringRes int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

}
