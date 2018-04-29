/*
 * Copyright (C) 2015 Willi Ye
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kerneladiutormod.reborn.fragments.other;

import android.os.Bundle;
import android.view.View;

import com.kerneladiutormod.reborn.BuildConfig;
import com.kerneladiutormod.reborn.R;
import com.kerneladiutormod.reborn.elements.cards.CardViewItem;
import com.kerneladiutormod.reborn.fragments.RecyclerViewFragment;
import com.kerneladiutormod.reborn.utils.Utils;

/**
 * Created by willi on 27.12.14.
 */
public class AboutusFragment extends RecyclerViewFragment {

    private final String APP_SOURCE = "https://github.com/AliHaide001/kernel_adiutor/";
    private final String ISSUE_LINK = "https://github.com/AliHaide001/kernel_adiutor/issues";
    private final String TELEGRAM_GROUP_LINK = "https://t.me/joinchat/F6q_fE3dluT94algpcDsTg";
    private final String XDA_THREAD_LINK = "https://forum.xda-developers.com/android/apps-games/approot4-4-ka-mod-reborn-v18-t3714105";

    @Override
    public boolean showApplyOnBoot() {
        return false;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        ModificationInit();
        ModificationVersionInit();
        TelegramGroupInit();
        ThreadInit();
        appSourceInit();
        featureRequestInit();
        licenseInit();
    }

    private void ModificationInit() {
            CardViewItem.DCardView mModificationCard = new CardViewItem.DCardView();
            mModificationCard.setTitle(getString(R.string.modification));
            mModificationCard.setDescription(getString(R.string.modification_summary));

        addView(mModificationCard);
    }

    private void ModificationVersionInit() {
        CardViewItem.DCardView mModificationVersionCard = new CardViewItem.DCardView();
        mModificationVersionCard.setTitle(getString(R.string.modification_version));
        mModificationVersionCard.setDescription(getString(R.string.modification_version_number, BuildConfig.VERSION_CODE));

        addView(mModificationVersionCard);
    }

    private void TelegramGroupInit() {
        CardViewItem.DCardView mTelegramGroupCard = new CardViewItem.DCardView();
        mTelegramGroupCard.setTitle(getString(R.string.telegram_group));
        mTelegramGroupCard.setDescription(getString(R.string.telegram_group_summary));
        mTelegramGroupCard.setOnDCardListener(new CardViewItem.DCardView.OnDCardListener() {
            @Override
            public void onClick(CardViewItem.DCardView dCardView) {
                Utils.launchUrl(getActivity(), TELEGRAM_GROUP_LINK);
            }
        });

        addView(mTelegramGroupCard);
    }

    private void ThreadInit() {
        CardViewItem.DCardView mThreadCard = new CardViewItem.DCardView();
        mThreadCard.setTitle(getString(R.string.xda_thread));
        mThreadCard.setDescription(getString(R.string.xda_thread_summary));
        mThreadCard.setOnDCardListener(new CardViewItem.DCardView.OnDCardListener() {
            @Override
            public void onClick(CardViewItem.DCardView dCardView) {
                Utils.launchUrl(getActivity(), XDA_THREAD_LINK);
            }
        });

        addView(mThreadCard);
    }

    private void appSourceInit() {
        CardViewItem.DCardView mAppSourceCard = new CardViewItem.DCardView();
        mAppSourceCard.setTitle(getString(R.string.open_source));
        mAppSourceCard.setDescription(getString(R.string.open_source_summary));
        mAppSourceCard.setOnDCardListener(new CardViewItem.DCardView.OnDCardListener() {
            @Override
            public void onClick(CardViewItem.DCardView dCardView) {
                Utils.launchUrl(getActivity(), APP_SOURCE);
            }
        });

        addView(mAppSourceCard);
    }

    private void featureRequestInit() {
        CardViewItem.DCardView mFeatureRequestCard = new CardViewItem.DCardView();
        mFeatureRequestCard.setTitle(getString(R.string.feature_request));
        mFeatureRequestCard.setDescription(getString(R.string.feature_request_summary));
        mFeatureRequestCard.setOnDCardListener(new CardViewItem.DCardView.OnDCardListener() {
            @Override
            public void onClick(CardViewItem.DCardView dCardView) {
                Utils.launchUrl(getActivity(), ISSUE_LINK);
            }
        });

        addView(mFeatureRequestCard);
    }

    private void licenseInit() {
        CardViewItem.DCardView mLicenseCard = new CardViewItem.DCardView();
        mLicenseCard.setTitle(getString(R.string.license));

        View view = inflater.inflate(R.layout.app_license_view, container, false);

        mLicenseCard.setView(view);
        addView(mLicenseCard);
    }

}

