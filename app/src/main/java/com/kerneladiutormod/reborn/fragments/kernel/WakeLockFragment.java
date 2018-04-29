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

package com.kerneladiutormod.reborn.fragments.kernel;

import android.os.Bundle;

import com.kerneladiutormod.reborn.R;
import com.kerneladiutormod.reborn.elements.DAdapter;
import com.kerneladiutormod.reborn.elements.DDivider;
import com.kerneladiutormod.reborn.elements.cards.SeekBarCardView;
import com.kerneladiutormod.reborn.elements.cards.SwitchCardView;
import com.kerneladiutormod.reborn.fragments.RecyclerViewFragment;
import com.kerneladiutormod.reborn.utils.kernel.WakeLock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 27.12.14.
 */

public class WakeLockFragment extends RecyclerViewFragment implements SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener, SwitchCardView.DSwitchCard.OnDSwitchCardListener {

    private SwitchCardView.DSwitchCard mSmb135xWakeLockCard, mBlueSleepWakeLockCard, mSensorIndWakeLockCard, mMsmHsicHostWakeLockCard;
    private SwitchCardView.DSwitchCard mWlanrxWakelockCard, mWlanctrlWakelockCard, mWlanWakelockCard;
    private SeekBarCardView.DSeekBarCard mWlanrxWakelockDividerCard, mMsmHsicWakelockDividerCard, mBCMDHDWakelockDividerCard;
    

    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        wakelockInit();
    }

    
    private void wakelockInit() {
        List<DAdapter.DView> views = new ArrayList<>();

        if (WakeLock.hasSmb135xWakeLock()) {
            mSmb135xWakeLockCard = new SwitchCardView.DSwitchCard();
            mSmb135xWakeLockCard.setTitle(getString(R.string.smb135x_wakelock));
            mSmb135xWakeLockCard.setDescription(String.format(getString(R.string.smb135x_wakelock_summary), WakeLock.isSmb135xWakeLockActive() ? getString(R.string.enabled) : getString(R.string.disabled)));
            mSmb135xWakeLockCard.setChecked(WakeLock.isSmb135xWakeLockActive());
            mSmb135xWakeLockCard.setOnDSwitchCardListener(this);

            views.add(mSmb135xWakeLockCard);
        }

        if (WakeLock.hasBlueSleepWakeLock()) {
            mBlueSleepWakeLockCard = new SwitchCardView.DSwitchCard();
            mBlueSleepWakeLockCard.setTitle(getString(R.string.bluesleep_wakelock));
            mBlueSleepWakeLockCard.setDescription(String.format(getString(R.string.bluesleep_wakelock_summary), WakeLock.isBlueSleepWakeLockActive() ? getString(R.string.enabled) : getString(R.string.disabled)));
            mBlueSleepWakeLockCard.setChecked(WakeLock.isBlueSleepWakeLockActive());
            mBlueSleepWakeLockCard.setOnDSwitchCardListener(this);

            views.add(mBlueSleepWakeLockCard);
        }

        if (WakeLock.hasSensorIndWakeLock()) {
            mSensorIndWakeLockCard = new SwitchCardView.DSwitchCard();
            mSensorIndWakeLockCard.setTitle(getString(R.string.sensor_ind_wakelock));
            mSensorIndWakeLockCard.setDescription(String.format(getString(R.string.sensor_ind_wakelock_summary), WakeLock.isSensorIndWakeLockActive() ? getString(R.string.enabled) : getString(R.string.disabled)));
            mSensorIndWakeLockCard.setChecked(WakeLock.isSensorIndWakeLockActive());
            mSensorIndWakeLockCard.setOnDSwitchCardListener(this);

            views.add(mSensorIndWakeLockCard);
        }

        if (WakeLock.hasMsmHsicHostWakeLock()) {
            mMsmHsicHostWakeLockCard = new SwitchCardView.DSwitchCard();
            mMsmHsicHostWakeLockCard.setTitle(getString(R.string.msm_hsic_host_wakelock));
            mMsmHsicHostWakeLockCard.setDescription(String.format(getString(R.string.msm_hsic_host_wakelock_summary), WakeLock.isMsmHsicHostWakeLockActive() ? getString(R.string.enabled) : getString(R.string.disabled)));
            mMsmHsicHostWakeLockCard.setChecked(WakeLock.isMsmHsicHostWakeLockActive());
            mMsmHsicHostWakeLockCard.setOnDSwitchCardListener(this);

            views.add(mMsmHsicHostWakeLockCard);
        }

        if (WakeLock.hasMsmHsicWakelockDivider()) {
            List<String> list = new ArrayList<>();
            list.add(getString(R.string.disabled));
            for (int i = 1; i < 17; i++) list.add((100 / i) + "%");

            mMsmHsicWakelockDividerCard = new SeekBarCardView.DSeekBarCard(list);
            mMsmHsicWakelockDividerCard.setTitle(getString(R.string.msm_hsic_wakelock_divider));
            mMsmHsicWakelockDividerCard.setProgress(WakeLock.getMsmHsicWakelockDivider());
            mMsmHsicWakelockDividerCard.setOnDSeekBarCardListener(this);

            views.add(mMsmHsicWakelockDividerCard);
        }

        if (WakeLock.hasWlanrxWakeLock()) {
            mWlanrxWakelockCard = new SwitchCardView.DSwitchCard();
            mWlanrxWakelockCard.setTitle(getString(R.string.wlan_rx_wakelock));
            mWlanrxWakelockCard.setDescription(String.format(getString(R.string.wlan_rx_wakelock_summary), WakeLock.isWlanrxWakeLockActive() ? getString(R.string.enabled) : getString(R.string.disabled)));
            mWlanrxWakelockCard.setChecked(WakeLock.isWlanrxWakeLockActive());
            mWlanrxWakelockCard.setOnDSwitchCardListener(this);

            views.add(mWlanrxWakelockCard);
        }

        if (WakeLock.hasWlanctrlWakeLock()) {
            mWlanctrlWakelockCard = new SwitchCardView.DSwitchCard();
            mWlanctrlWakelockCard.setTitle(getString(R.string.wlan_ctrl_wakelock));
            mWlanctrlWakelockCard.setDescription(String.format(getString(R.string.wlan_ctrl_wakelock_summary), WakeLock.isWlanctrlWakeLockActive() ? getString(R.string.enabled) : getString(R.string.disabled)));
            mWlanctrlWakelockCard.setChecked(WakeLock.isWlanctrlWakeLockActive());
            mWlanctrlWakelockCard.setOnDSwitchCardListener(this);

            views.add(mWlanctrlWakelockCard);
        }

        if (WakeLock.hasWlanWakeLock()) {
            mWlanWakelockCard = new SwitchCardView.DSwitchCard();
            mWlanWakelockCard.setTitle(getString(R.string.wlan_wakelock));
            mWlanWakelockCard.setDescription(String.format(getString(R.string.wlan_wakelock_summary), WakeLock.isWlanWakeLockActive() ? getString(R.string.enabled) : getString(R.string.disabled)));
            mWlanWakelockCard.setChecked(WakeLock.isWlanWakeLockActive());
            mWlanWakelockCard.setOnDSwitchCardListener(this);

            views.add(mWlanWakelockCard);
        }

        if (WakeLock.hasWlanrxWakelockDivider()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 17; i++) list.add((100 / i) + "%");
            list.add("0%");

            mWlanrxWakelockDividerCard = new SeekBarCardView.DSeekBarCard(list);
            mWlanrxWakelockDividerCard.setTitle(getString(R.string.wlan_rx_wakelock_divider));
            mWlanrxWakelockDividerCard.setProgress(WakeLock.getWlanrxWakelockDivider());
            mWlanrxWakelockDividerCard.setOnDSeekBarCardListener(this);

            views.add(mWlanrxWakelockDividerCard);
        }

        if (WakeLock.hasBCMDHDWakelockDivider()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 9; i++) list.add(String.valueOf(i));

            mBCMDHDWakelockDividerCard = new SeekBarCardView.DSeekBarCard(list);
            mBCMDHDWakelockDividerCard.setTitle(getString(R.string.bcmdhd_wakelock_divider));
            mBCMDHDWakelockDividerCard.setProgress(WakeLock.getBCMDHDWakelockDivider());
            mBCMDHDWakelockDividerCard.setOnDSeekBarCardListener(this);

            views.add(mBCMDHDWakelockDividerCard);
        }


        if (!views.isEmpty()) {
            DDivider mWakelockDividerCard = new DDivider();
            mWakelockDividerCard.setText(getString(R.string.wakelock));
            mWakelockDividerCard.setDescription(getString(R.string.wakelocks_summary));
            addView(mWakelockDividerCard);

            addAllViews(views);
        }
    }


    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
        if (dSeekBarCard == mWlanrxWakelockDividerCard)
            WakeLock.setWlanrxWakelockDivider(position, getActivity());
        else if (dSeekBarCard == mMsmHsicWakelockDividerCard)
            WakeLock.setMsmHsicWakelockDivider(position, getActivity());
        else if (dSeekBarCard == mBCMDHDWakelockDividerCard)
            WakeLock.setBCMDHDWakelockDivider(position, getActivity());

    }

    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mSmb135xWakeLockCard) {
            mSmb135xWakeLockCard.setDescription(String.format(getString(R.string.smb135x_wakelock_summary), checked ? getString(R.string.enabled) : getString(R.string.disabled)));
            WakeLock.activateSmb135xWakeLock(checked, getActivity());
        }
        else if (dSwitchCard == mBlueSleepWakeLockCard) {
            mBlueSleepWakeLockCard.setDescription(String.format(getString(R.string.bluesleep_wakelock_summary), checked ? getString(R.string.enabled) : getString(R.string.disabled)));
            WakeLock.activateBlueSleepWakeLock(checked, getActivity());
        }
        else if (dSwitchCard == mSensorIndWakeLockCard) {
            mSensorIndWakeLockCard.setDescription(String.format(getString(R.string.sensor_ind_wakelock_summary), checked ? getString(R.string.enabled) : getString(R.string.disabled)));
            WakeLock.activateSensorIndWakeLock(checked, getActivity());
        }
        else if (dSwitchCard == mMsmHsicHostWakeLockCard) {
            mMsmHsicHostWakeLockCard.setDescription(String.format(getString(R.string.msm_hsic_host_wakelock_summary), checked ? getString(R.string.enabled) : getString(R.string.disabled)));
            WakeLock.activateMsmHsicHostWakeLock(checked, getActivity());
        }
        else if (dSwitchCard == mWlanrxWakelockCard) {
            mWlanrxWakelockCard.setDescription(String.format(getString(R.string.wlan_rx_wakelock_summary), checked ? getString(R.string.enabled) : getString(R.string.disabled)));
            WakeLock.activateWlanrxWakeLock(checked, getActivity());
        }
        else if (dSwitchCard == mWlanctrlWakelockCard) {
            mWlanctrlWakelockCard.setDescription(String.format(getString(R.string.wlan_ctrl_wakelock_summary), checked ? getString(R.string.enabled) : getString(R.string.disabled)));
            WakeLock.activateWlanctrlWakeLock(checked, getActivity());
        }
        else if (dSwitchCard == mWlanWakelockCard) {
            mWlanWakelockCard.setDescription(String.format(getString(R.string.wlan_wakelock_summary), checked ? getString(R.string.enabled) : getString(R.string.disabled)));
            WakeLock.activateWlanWakeLock(checked, getActivity());
        }
    }
}
