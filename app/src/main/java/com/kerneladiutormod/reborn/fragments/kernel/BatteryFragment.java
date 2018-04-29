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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.kerneladiutormod.reborn.R;
import com.kerneladiutormod.reborn.elements.DAdapter;
import com.kerneladiutormod.reborn.elements.DDivider;
import com.kerneladiutormod.reborn.elements.cards.CardViewItem;
import com.kerneladiutormod.reborn.elements.cards.PopupCardView;
import com.kerneladiutormod.reborn.elements.cards.SeekBarCardView;
import com.kerneladiutormod.reborn.elements.cards.SwitchCardView;
import com.kerneladiutormod.reborn.elements.cards.UsageCardView;
import com.kerneladiutormod.reborn.fragments.RecyclerViewFragment;
import com.kerneladiutormod.reborn.utils.Utils;
import com.kerneladiutormod.reborn.utils.kernel.Battery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by willi on 03.01.15.
 */
public class BatteryFragment extends RecyclerViewFragment implements SwitchCardView.DSwitchCard.OnDSwitchCardListener,
        SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener, PopupCardView.DPopupCard.OnDPopupCardListener {

    private UsageCardView.DUsageCard mBatteryLevelCard;
    private CardViewItem.DCardView mBatteryVoltageCard, mBatteryTemperature, mBatteryChargingCurrentCard;

    private SwitchCardView.DSwitchCard mForceFastChargeCard, mArchPowerCard;

    private SeekBarCardView.DSeekBarCard mBlxCard, mACLevelCard, mUSBLevelCard;

    private SwitchCardView.DSwitchCard mCustomChargeRateEnableCard;
    private SeekBarCardView.DSeekBarCard mForceFastChargeCurrentCard;
    private SeekBarCardView.DSeekBarCard mChargingRateCard;
    private SeekBarCardView.DSeekBarCard mlowpowervalueCard;

    private SwitchCardView.DSwitchCard mC0StateCard, mC1StateCard, mC2StateCard, mC3StateCard;

    private PopupCardView.DPopupCard mPowerSuspendModeCard;
    private SwitchCardView.DSwitchCard mOldPowerSuspendStateCard;
    private SeekBarCardView.DSeekBarCard mNewPowerSuspendStateCard;

    private SwitchCardView.DSwitchCard mStateNotifierStateCard;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        batteryLevelInit();
        lowpowervalueInit();
        batteryVoltageInit();
        batteryTemperatureInit();
		batteryChargingCurrentInit();
        if (Battery.hasForceFastCharge()) forceFastChargeInit();
        if (Battery.hasChargeLevelControl()) chargeLevelControlInit();
        if (Battery.hasBlx()) blxInit();
        if (Battery.hasChargeRate()) chargerateInit();

        try {
            getActivity().registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        } catch (NullPointerException ignored) {
        }
        if (Battery.hasArchPower()) archpowerInit();
        if (Battery.hasPowerSuspend()) powersuspendInit();
        if (Battery.hasStateNotifier()) statenotifierInit();
        cstatesInit();
    }

    @Override
    public void postInit(Bundle savedInstanceState) {
        super.postInit(savedInstanceState);
        if (getCount() < 4) showApplyOnBoot(false);
    }

    private void batteryLevelInit() {
        mBatteryLevelCard = new UsageCardView.DUsageCard();
        mBatteryLevelCard.setText(getString(R.string.battery_level));

        addView(mBatteryLevelCard);
    }

    private void lowpowervalueInit() {
         if (Battery.haslowpowervalue()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 101; i++) list.add(String.valueOf(i));

            mlowpowervalueCard = new SeekBarCardView.DSeekBarCard(list);
            mlowpowervalueCard.setTitle(getString(R.string.lowpowervalue));
            mlowpowervalueCard.setDescription(getString(R.string.lowpowervalue_summary));
            mlowpowervalueCard.setProgress(Battery.getlowpowervalue());
            mlowpowervalueCard.setOnDSeekBarCardListener(this);

            addView(mlowpowervalueCard);
        }}

    private void batteryVoltageInit() {
        mBatteryVoltageCard = new CardViewItem.DCardView();
        mBatteryVoltageCard.setTitle(getString(R.string.battery_voltage));

        addView(mBatteryVoltageCard);
    }

    private void batteryTemperatureInit() {
        mBatteryTemperature = new CardViewItem.DCardView();
        mBatteryTemperature.setTitle(getString(R.string.battery_temperature));

        addView(mBatteryTemperature);
    }

    private void batteryChargingCurrentInit() {
        if(Battery.hasChargingCurrent()) {
            mBatteryChargingCurrentCard = new CardViewItem.DCardView();
            mBatteryChargingCurrentCard.setTitle(getString(R.string.battery_charging_current));

            addView(mBatteryChargingCurrentCard);
        }
    }

    private void forceFastChargeInit() {
        mForceFastChargeCard = new SwitchCardView.DSwitchCard();
        mForceFastChargeCard.setTitle(getString(R.string.usb_fast_charge));
        mForceFastChargeCard.setDescription(getString(R.string.usb_fast_charge_summary));
        mForceFastChargeCard.setChecked(Battery.isForceFastChargeActive());
        mForceFastChargeCard.setOnDSwitchCardListener(this);
        addView(mForceFastChargeCard);

        if(Battery.hasForceFastChargeCurrent()){
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 2200; i+=10) list.add(String.valueOf(i));

            mForceFastChargeCurrentCard = new SeekBarCardView.DSeekBarCard(list);
            mForceFastChargeCurrentCard.setTitle(getString(R.string.usb_fast_charge_current));
            mForceFastChargeCurrentCard.setDescription(getString(R.string.usb_fast_charge_current_summary));
            mForceFastChargeCurrentCard.setProgress(Battery.getFastChargeCurrent() / 10);
            mForceFastChargeCurrentCard.setOnDSeekBarCardListener(this);
            addView(mForceFastChargeCurrentCard);
        }

    }

    private void chargeLevelControlInit(){
        DDivider mChargeLevelCard = new DDivider();
        mChargeLevelCard.setText(getString(R.string.charge_levels));
        mChargeLevelCard.setDescription(getString(R.string.charge_levels_summary));
        addView(mChargeLevelCard);


        if (Battery.hasChargeLevelControlAC()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 2200; i+=10) list.add(String.valueOf(i));

            mACLevelCard = new SeekBarCardView.DSeekBarCard(list);
            mACLevelCard.setTitle(getString(R.string.charge_level_ac));
            mACLevelCard.setDescription(getString(R.string.charge_level_ac_summary));
            mACLevelCard.setProgress(Battery.getChargeLevelControlAC() / 10);
            mACLevelCard.setOnDSeekBarCardListener(this);

            addView(mACLevelCard);
        }

        if (Battery.hasChargeLevelControlUSB()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 900; i+=10) list.add(String.valueOf(i));

            mUSBLevelCard = new SeekBarCardView.DSeekBarCard(list);
            mUSBLevelCard.setTitle(getString(R.string.charge_level_usb));
            mUSBLevelCard.setDescription(getString(R.string.charge_level_usb_summary));
            mUSBLevelCard.setProgress(Battery.getChargeLevelControlUSB() / 10);
            mUSBLevelCard.setOnDSeekBarCardListener(this);

            addView(mUSBLevelCard);
        }

    }

    private void blxInit() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 101; i++) list.add(String.valueOf(i));

        mBlxCard = new SeekBarCardView.DSeekBarCard(list);
        mBlxCard.setTitle(getString(R.string.blx));
        mBlxCard.setDescription(getString(R.string.blx_summary));
        mBlxCard.setProgress(Battery.getCurBlx());
        mBlxCard.setOnDSeekBarCardListener(this);

        addView(mBlxCard);
    }

    private void chargerateInit() {

        if (Battery.hasCustomChargeRateEnable()) {
            mCustomChargeRateEnableCard = new SwitchCardView.DSwitchCard();
            mCustomChargeRateEnableCard.setDescription(getString(R.string.custom_charge_rate));
            mCustomChargeRateEnableCard.setChecked(Battery.isCustomChargeRateActive());
            mCustomChargeRateEnableCard.setOnDSwitchCardListener(this);

            addView(mCustomChargeRateEnableCard);
        }

        if (Battery.hasChargingRate()) {
            List<String> list = new ArrayList<>();
            for (int i = 10; i < 151; i++) list.add((i * 10) + getString(R.string.ma));

            mChargingRateCard = new SeekBarCardView.DSeekBarCard(list);
            mChargingRateCard.setTitle(getString(R.string.charge_rate));
            mChargingRateCard.setDescription(getString(R.string.charge_rate_summary));
            mChargingRateCard.setProgress((Battery.getChargingRate() / 10) - 10);
            mChargingRateCard.setOnDSeekBarCardListener(this);

            addView(mChargingRateCard);
        }
    }

    private void archpowerInit() {
        mArchPowerCard = new SwitchCardView.DSwitchCard();
        mArchPowerCard.setTitle(getString(R.string.archpower));
        mArchPowerCard.setDescription(getString(R.string.archpower_summary));
        mArchPowerCard.setChecked(Battery.isArchPowerActive());
        mArchPowerCard.setOnDSwitchCardListener(this);

        addView(mArchPowerCard);
    }

    private void powersuspendInit() {
        if (Battery.hasPowerSuspendMode()) {
            mPowerSuspendModeCard = new PopupCardView.DPopupCard(new ArrayList<>(
                    Arrays.asList(getResources().getStringArray(R.array.powersuspend_items))));
            mPowerSuspendModeCard.setTitle(getString(R.string.power_suspend_mode));
            mPowerSuspendModeCard.setDescription(getString(R.string.power_suspend_mode_summary));
            mPowerSuspendModeCard.setItem(Battery.getPowerSuspendMode());
            mPowerSuspendModeCard.setOnDPopupCardListener(this);

            addView(mPowerSuspendModeCard);
        }

        if (Battery.hasOldPowerSuspendState()) {
            mOldPowerSuspendStateCard = new SwitchCardView.DSwitchCard();
            mOldPowerSuspendStateCard.setTitle(getString(R.string.power_suspend_state));
            mOldPowerSuspendStateCard.setDescription(getString(R.string.power_suspend_state_summary));
            mOldPowerSuspendStateCard.setChecked(Battery.isOldPowerSuspendStateActive());
            mOldPowerSuspendStateCard.setOnDSwitchCardListener(this);

            addView(mOldPowerSuspendStateCard);
        }

        if (Battery.hasNewPowerSuspendState()) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                list.add(String.valueOf(i));

            mNewPowerSuspendStateCard = new SeekBarCardView.DSeekBarCard(list);
            mNewPowerSuspendStateCard.setTitle(getString(R.string.power_suspend_state));
            mNewPowerSuspendStateCard.setDescription(getString(R.string.power_suspend_state_summary));
            mNewPowerSuspendStateCard.setProgress(Battery.getNewPowerSuspendState());
            mNewPowerSuspendStateCard.setOnDSeekBarCardListener(this);

            addView(mNewPowerSuspendStateCard);
        }}

    private void statenotifierInit() {
            mStateNotifierStateCard = new SwitchCardView.DSwitchCard();
            mStateNotifierStateCard.setTitle(getString(R.string.state_notifier_mode));
            mStateNotifierStateCard.setDescription(getString(Battery.isStateNotifierStateActive() ? R.string.state_notifier_mode_summary_enabled : R.string.state_notifier_mode_summary_disabled));
            mStateNotifierStateCard.setChecked(Battery.isStateNotifierStateActive());
            mStateNotifierStateCard.setOnDSwitchCardListener(this);

            addView(mStateNotifierStateCard);
        }


    private void cstatesInit() {
        List<DAdapter.DView> views = new ArrayList<>();

        if (Battery.hasC0State()) {
            mC0StateCard = new SwitchCardView.DSwitchCard();
            mC0StateCard.setTitle(getString(R.string.c0state));
            mC0StateCard.setDescription(getString(R.string.c0state_summary));
            mC0StateCard.setChecked(Battery.isC0StateActive());
            mC0StateCard.setOnDSwitchCardListener(this);

            views.add(mC0StateCard);
        }

        if (Battery.hasC1State()) {
            mC1StateCard = new SwitchCardView.DSwitchCard();
            mC1StateCard.setTitle(getString(R.string.c1state));
            mC1StateCard.setDescription(getString(R.string.c1state_summary));
            mC1StateCard.setChecked(Battery.isC1StateActive());
            mC1StateCard.setOnDSwitchCardListener(this);

            views.add(mC1StateCard);
        }

        if (Battery.hasC2State()) {
            mC2StateCard = new SwitchCardView.DSwitchCard();
            mC2StateCard.setTitle(getString(R.string.c2state));
            mC2StateCard.setDescription(getString(R.string.c2state_summary));
            mC2StateCard.setChecked(Battery.isC2StateActive());
            mC2StateCard.setOnDSwitchCardListener(this);

            views.add(mC2StateCard);
        }

        if (Battery.hasC3State()) {
            mC3StateCard = new SwitchCardView.DSwitchCard();
            mC3StateCard.setTitle(getString(R.string.c3state));
            mC3StateCard.setDescription(getString(R.string.c3state_summary));
            mC3StateCard.setChecked(Battery.isC3StateActive());
            mC3StateCard.setOnDSwitchCardListener(this);

            views.add(mC3StateCard);
        }

        if (views.size() > 0) {
            DDivider mCstatesCard = new DDivider();
            mCstatesCard.setText(getString(R.string.cstates));
            addView(mCstatesCard);

           addAllViews(views);
        }
    }

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
			int current = Battery.getChargingCurrent();

            if (mBatteryLevelCard != null) mBatteryLevelCard.setProgress(level);
            if (mBatteryVoltageCard != null)
                mBatteryVoltageCard.setDescription(voltage + getString(R.string.mv));
            if (mBatteryTemperature != null) {
                double celsius = (double) temperature / 10;
                mBatteryTemperature.setDescription(Utils.formatCelsius(celsius) + " " + Utils.celsiusToFahrenheit(celsius));
            }
			if(mBatteryChargingCurrentCard != null)
                mBatteryChargingCurrentCard.setDescription(current + getString(R.string.ma));
        }
    };

    @Override
    public void onItemSelected(PopupCardView.DPopupCard dPopupCard, int position) {
        if (dPopupCard == mPowerSuspendModeCard)
            Battery.setPowerSuspendMode(position, getActivity());
    }

    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mForceFastChargeCard)
            Battery.activateForceFastCharge(checked, getActivity());
        else if (dSwitchCard == mCustomChargeRateEnableCard)
            Battery.activateCustomChargeRate(checked, getActivity());
        else if (dSwitchCard == mC0StateCard)
            Battery.activateC0State(checked, getActivity());
        else if (dSwitchCard == mC1StateCard)
            Battery.activateC1State(checked, getActivity());
        else if (dSwitchCard == mC2StateCard)
            Battery.activateC2State(checked, getActivity());
        else if (dSwitchCard == mC3StateCard)
            Battery.activateC3State(checked, getActivity());
        else if (dSwitchCard == mArchPowerCard)
            Battery.activateArchPower(checked, getActivity());
        else if (dSwitchCard == mStateNotifierStateCard) {
            Battery.activateStateNotifier(checked, getActivity());
            mStateNotifierStateCard.setDescription(getString(checked ? R.string.state_notifier_mode_summary_enabled : R.string.state_notifier_mode_summary_disabled));
        }
        else if (dSwitchCard == mOldPowerSuspendStateCard)
            if (Battery.getPowerSuspendMode() == 1) {
                Battery.activateOldPowerSuspend(checked, getActivity());
            } else dSwitchCard.setChecked(Battery.isOldPowerSuspendStateActive());
    }

    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
        if (dSeekBarCard == mBlxCard)
            Battery.setBlx(position, getActivity());
        else if (dSeekBarCard == mACLevelCard)
            Battery.setChargeLevelControlAC(position * 10, getActivity());
        else if (dSeekBarCard == mForceFastChargeCurrentCard)
            Battery.setFastChargeCurrent(position * 10, getActivity());
        else if (dSeekBarCard == mUSBLevelCard)
            Battery.setChargeLevelControlUSB(position * 10, getActivity());
        else if (dSeekBarCard == mChargingRateCard)
            Battery.setChargingRate((position * 10) + 100, getActivity());
        else if (dSeekBarCard == mNewPowerSuspendStateCard)
            if (Battery.getPowerSuspendMode() == 1) {
                Battery.setNewPowerSuspend(position, getActivity());
            } else dSeekBarCard.setProgress(Battery.getNewPowerSuspendState());
        else if (dSeekBarCard == mlowpowervalueCard) {
            Battery.setlowpowervalue((position) ,getActivity());
	}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(mBatInfoReceiver);
        } catch (IllegalArgumentException ignored) {
        }
    }
}
