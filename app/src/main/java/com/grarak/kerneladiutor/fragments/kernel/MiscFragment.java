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

package com.grarak.kerneladiutor.fragments.kernel;

import android.os.Bundle;
import android.util.Log;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.DAdapter;
import com.grarak.kerneladiutor.elements.DDivider;
import com.grarak.kerneladiutor.elements.cards.EditTextCardView;
import com.grarak.kerneladiutor.elements.cards.PopupCardView;
import com.grarak.kerneladiutor.elements.cards.SeekBarCardView;
import com.grarak.kerneladiutor.elements.cards.SwitchCardView;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.kernel.Misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 02.01.15.
 */
public class MiscFragment extends RecyclerViewFragment implements PopupCardView.DPopupCard.OnDPopupCardListener,
        SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener,
        SwitchCardView.DSwitchCard.OnDSwitchCardListener,
        EditTextCardView.DEditTextCard.OnDEditTextCardListener {

    private SeekBarCardView.DSeekBarCard mVibrationCard;

    private SwitchCardView.DSwitchCard mSELinuxCard, mLoggerEnableCard, mBclCard, mBclHotplugCard, mCrcCard, mFsyncCard, mDynamicFsyncCard;

    private SwitchCardView.DSwitchCard mGentleFairSleepersCard;

    private SeekBarCardView.DSeekBarCard mLedSetSpeedCard;

    private SwitchCardView.DSwitchCard mLedToggleCard;

    private PopupCardView.DPopupCard mTcpCongestionCard;
    private EditTextCardView.DEditTextCard mHostnameCard;

    private SwitchCardView.DSwitchCard mEnableADBOverWifiCard;

    private SwitchCardView.DSwitchCard mEnableUsbOtgCard;

    private SwitchCardView.DSwitchCard mswitchbuttonsCard;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        selinuxInit();
        networkInit();
        if (Misc.hasVibration()) vibrationInit();
        if (Misc.hasswitchbuttons()) switchbuttonsInit();
        if (Misc.hasLoggerEnable()) loggerInit();
        if (Misc.hasBcl()) bclInit();
        if (Misc.hasBclHotplug()) bclHotplugInit();
        if (Misc.hasCrc()) crcInit();
        fsyncInit();
        if (Misc.hasGentleFairSleepers()) gentlefairsleepersInit();
        if (Misc.hasUsbOtg()) usbOtgInit();
		if (Misc.hasLedSpeed()) LedControlInit();
    }

    private void selinuxInit() {
        mSELinuxCard = new SwitchCardView.DSwitchCard();
        mSELinuxCard.setTitle(getString(R.string.se_linux));
        mSELinuxCard.setDescription(getString(R.string.se_linux_summary) + " " + Misc.getSELinuxStatus());
        mSELinuxCard.setChecked(Misc.isSELinuxActive());
        mSELinuxCard.setOnDSwitchCardListener(this);

        addView(mSELinuxCard);

    }

    private void vibrationInit() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 101; i++)
            list.add(i + "%");

        int max = Misc.getVibrationMax();
        int min = Misc.getVibrationMin();
        float offset = (max - min) / (float) 101;

        mVibrationCard = new SeekBarCardView.DSeekBarCard(list);
        mVibrationCard.setTitle(getString(R.string.vibration_strength));
        if ((max - min) == 100)
            mVibrationCard.setProgress(Misc.getCurVibration());
        else
            mVibrationCard.setProgress(Math.round((Misc.getCurVibration() - min) / offset));
        mVibrationCard.setOnDSeekBarCardListener(this);

        addView(mVibrationCard);
    }

    private void loggerInit() {
        mLoggerEnableCard = new SwitchCardView.DSwitchCard();
        mLoggerEnableCard.setDescription(getString(R.string.android_logger));
        mLoggerEnableCard.setChecked(Misc.isLoggerActive());
        mLoggerEnableCard.setOnDSwitchCardListener(this);

        addView(mLoggerEnableCard);
    }

    private void bclInit() {
         mBclCard = new SwitchCardView.DSwitchCard();
         mBclCard.setTitle(getString(R.string.bcl));
         mBclCard.setDescription(getString(R.string.bcl_summary));
         mBclCard.setChecked(Misc.isBclActive());
         mBclCard.setOnDSwitchCardListener(this);
 
         addView(mBclCard);
     }

    private void bclHotplugInit() {
        mBclHotplugCard = new SwitchCardView.DSwitchCard();
        mBclHotplugCard.setTitle(getString(R.string.bcl_hotplug));
        mBclHotplugCard.setDescription(getString(R.string.bcl_hotplug_summary));
        mBclHotplugCard.setChecked(Misc.isBclHotplugActive());
        mBclHotplugCard.setOnDSwitchCardListener(this);

        addView(mBclHotplugCard);
    }

    private void crcInit() {
        mCrcCard = new SwitchCardView.DSwitchCard();
        mCrcCard.setTitle(getString(R.string.crc));
        mCrcCard.setDescription(getString(R.string.crc_summary));
        mCrcCard.setChecked(Misc.isCrcActive());
        mCrcCard.setOnDSwitchCardListener(this);

        addView(mCrcCard);
    }

    private void fsyncInit() {
        if (Misc.hasFsync()) {
            mFsyncCard = new SwitchCardView.DSwitchCard();
            mFsyncCard.setTitle(getString(R.string.fsync));
            mFsyncCard.setDescription(getString(R.string.fsync_summary));
            mFsyncCard.setChecked(Misc.isFsyncActive());
            mFsyncCard.setOnDSwitchCardListener(this);

            addView(mFsyncCard);
        }

        if (Misc.hasDynamicFsync()) {
            mDynamicFsyncCard = new SwitchCardView.DSwitchCard();
            mDynamicFsyncCard.setTitle(getString(R.string.dynamic_fsync));
            mDynamicFsyncCard.setDescription(getString(R.string.dynamic_fsync_summary));
            mDynamicFsyncCard.setChecked(Misc.isDynamicFsyncActive());
            mDynamicFsyncCard.setOnDSwitchCardListener(this);

            addView(mDynamicFsyncCard);
        }
    }

    private void switchbuttonsInit() {
        if (Misc.hasswitchbuttons()) {
            mswitchbuttonsCard = new SwitchCardView.DSwitchCard();
            mswitchbuttonsCard.setTitle(getString(R.string.switchbuttons));
            mswitchbuttonsCard.setDescription(getString(R.string.switchbuttons_summary));
            mswitchbuttonsCard.setChecked(Misc.isswitchbuttonsActive());
            mswitchbuttonsCard.setOnDSwitchCardListener(this);

            addView(mswitchbuttonsCard);
        }
    }

    private void gentlefairsleepersInit() {
        mGentleFairSleepersCard = new SwitchCardView.DSwitchCard();
        mGentleFairSleepersCard.setTitle(getString(R.string.gentlefairsleepers));
        mGentleFairSleepersCard.setDescription(getString(R.string.gentlefairsleepers_summary));
        mGentleFairSleepersCard.setChecked(Misc.isGentleFairSleepersActive());
        mGentleFairSleepersCard.setOnDSwitchCardListener(this);

        addView(mGentleFairSleepersCard);
    }

    private void LedControlInit() {
	List<DAdapter.DView> views = new ArrayList<>();

	if (Misc.hasLedSpeed()) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i <= Misc.getMaxMinLedSpeed(); i++)
                list.add(String.valueOf(i));

        mLedSetSpeedCard = new SeekBarCardView.DSeekBarCard(list);
        mLedSetSpeedCard.setTitle(getString(R.string.led_speed));
        mLedSetSpeedCard.setDescription(getString(R.string.led_speed_summary));
        mLedSetSpeedCard.setProgress(Misc.getCurLedSpeed());
        mLedSetSpeedCard.setOnDSeekBarCardListener(this);

        addView(mLedSetSpeedCard);
        }

	if (Misc.hasLedMode()){
        mLedToggleCard = new SwitchCardView.DSwitchCard();
        mLedToggleCard.setTitle(getString(R.string.Led_toggle));
        mLedToggleCard.setDescription(getString(R.string.Led_toggle_summary));
        mLedToggleCard.setChecked(Misc.isLedActive());
        mLedToggleCard.setOnDSwitchCardListener(this);

        addView(mLedToggleCard);
       }
    }

    private void usbOtgInit() {
        mEnableUsbOtgCard = new SwitchCardView.DSwitchCard();
        mEnableUsbOtgCard.setTitle(getString(R.string.msm_usb_otg));
        mEnableUsbOtgCard.setDescription(getString(R.string.msm_usb_otg_summary));
        mEnableUsbOtgCard.setChecked(Misc.isUsbOtgActive());
        mEnableUsbOtgCard.setOnDSwitchCardListener(this);

        addView(mEnableUsbOtgCard);
    }

    private void networkInit() {
        DDivider mNetworkDividerCard = new DDivider();
        mNetworkDividerCard.setText(getString(R.string.network));
        addView(mNetworkDividerCard);

        try {
            mTcpCongestionCard = new PopupCardView.DPopupCard(Misc.getTcpAvailableCongestions(true));
            mTcpCongestionCard.setTitle(getString(R.string.tcp));
            mTcpCongestionCard.setDescription(getString(R.string.tcp_summary));
            mTcpCongestionCard.setItem(Misc.getCurTcpCongestion());
            mTcpCongestionCard.setOnDPopupCardListener(this);

            addView(mTcpCongestionCard);
        } catch (Exception e) {
            Log.e(Constants.TAG, "Failed to read TCP");
        }

        String hostname = Misc.getHostname();
        mHostnameCard = new EditTextCardView.DEditTextCard();
        mHostnameCard.setTitle(getString(R.string.hostname));
        mHostnameCard.setDescription(hostname);
        mHostnameCard.setValue(hostname);
        mHostnameCard.setOnDEditTextCardListener(this);

        addView(mHostnameCard);


        mEnableADBOverWifiCard = new SwitchCardView.DSwitchCard();
        mEnableADBOverWifiCard.setTitle(getString(R.string.adb_over_wifi));
        if (Misc.isADBOverWifiActive()) {
            mEnableADBOverWifiCard.setDescription(getString(R.string.adb_over_wifi_connect_summary) + Misc.getIpAddr(getActivity()) + ":5555");
        }
        else {
            mEnableADBOverWifiCard.setDescription(getString(R.string.adb_over_wifi_summary));
        }
        mEnableADBOverWifiCard.setChecked(Misc.isADBOverWifiActive());
        mEnableADBOverWifiCard.setOnDSwitchCardListener(this);

        addView(mEnableADBOverWifiCard);

        DDivider mMiscCard = new DDivider();
        mMiscCard.setText(getString(R.string.misc_settings));
        addView(mMiscCard);
    }

    @Override
    public void onItemSelected(PopupCardView.DPopupCard dPopupCard, int position) {
        if (dPopupCard == mTcpCongestionCard)
            Misc.setTcpCongestion(Misc.getTcpAvailableCongestions(true).get(position), getActivity());
    }

    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
        if (dSeekBarCard == mVibrationCard) {
            int max = Misc.getVibrationMax();
            int min = Misc.getVibrationMin();
            float offset = (max - min) / (float) 101;

            if ((max - min) == 100)
                Misc.setVibration(position, getActivity());
            else
                Misc.setVibration(Math.round(offset * position) + min, getActivity());

            // Vibrate
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                        Utils.vibrate(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
		else if (dSeekBarCard == mLedSetSpeedCard) {
            Misc.setLedSpeed(position, getActivity());
		}
    }

    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mSELinuxCard) {
            Misc.activateSELinux(checked, getActivity());
            getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
        else if (dSwitchCard == mLoggerEnableCard)
            Misc.activateLogger(checked, getActivity());
        else if (dSwitchCard == mBclCard)
            Misc.activateBcl(checked, getActivity());
        else if (dSwitchCard == mBclHotplugCard)
            Misc.activateBclHotplug(checked, getActivity());
        else if (dSwitchCard == mCrcCard)
            Misc.activateCrc(checked, getActivity());
        else if (dSwitchCard == mFsyncCard)
            Misc.activateFsync(checked, getActivity());
		else if (dSwitchCard == mLedToggleCard)
	   		Misc.activateLedMode(checked, getActivity());
        else if (dSwitchCard == mDynamicFsyncCard)
            Misc.activateDynamicFsync(checked, getActivity());
        else if (dSwitchCard == mswitchbuttonsCard)
            Misc.activateswitchbuttons(checked, getActivity());
        else if (dSwitchCard == mGentleFairSleepersCard)
            Misc.activateGentleFairSleepers(checked, getActivity());
        else if (dSwitchCard == mEnableUsbOtgCard)
            Misc.activateUsbOtg(checked, getActivity());
        else if (dSwitchCard == mEnableADBOverWifiCard) {
            Misc.activateADBOverWifi(checked, getActivity());
            getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onApply(EditTextCardView.DEditTextCard dEditTextCard, String value) {
        dEditTextCard.setDescription(value);
        if (dEditTextCard == mHostnameCard) Misc.setHostname(value, getActivity());
    }

    @Override
    public boolean onRefresh() {
        if (mBclCard != null) {
            mBclCard.setChecked(Misc.isBclActive());
        }
        if (mBclHotplugCard != null) {
            mBclHotplugCard.setChecked(Misc.isBclHotplugActive());
        }
        return true;
    }
}
