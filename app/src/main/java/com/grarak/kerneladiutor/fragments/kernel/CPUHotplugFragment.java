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

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.DAdapter;
import com.grarak.kerneladiutor.elements.DDivider;
import com.grarak.kerneladiutor.elements.cards.PopupCardView;
import com.grarak.kerneladiutor.elements.cards.SeekBarCardView;
import com.grarak.kerneladiutor.elements.cards.SwitchCardView;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.kernel.CPU;
import com.grarak.kerneladiutor.utils.kernel.CPUHotplug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by willi on 06.02.15.
 */
public class CPUHotplugFragment extends RecyclerViewFragment implements
        SwitchCardView.DSwitchCard.OnDSwitchCardListener,
        PopupCardView.DPopupCard.OnDPopupCardListener, SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener {

    private SwitchCardView.DSwitchCard mMpdecisionCard;

    private SwitchCardView.DSwitchCard mbchCard;
    private SwitchCardView.DSwitchCard mIntelliPlugCard;
    private PopupCardView.DPopupCard mIntelliPlugProfileCard;
    private SwitchCardView.DSwitchCard mIntelliPlugEcoCard;
    private SwitchCardView.DSwitchCard mIntelliPlugTouchBoostCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugHysteresisCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugThresholdCard;
    private PopupCardView.DPopupCard mIntelliPlugScreenOffMaxCard;
    private SwitchCardView.DSwitchCard mIntelliPlugDebugCard;
    private SwitchCardView.DSwitchCard mIntelliPlugSuspendCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugCpusBoostedCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugMinCpusOnlineCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugMaxCpusOnlineCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugMaxCpusOnlineSuspCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugSuspendDeferTimeCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugDeferSamplingCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugBoostLockDurationCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugDownLockDurationCard;
    private SeekBarCardView.DSeekBarCard mIntelliPlugFShiftCard;

    private SwitchCardView.DSwitchCard mBluPlugCard;
    private SwitchCardView.DSwitchCard mBluPlugPowersaverModeCard;
    private SeekBarCardView.DSeekBarCard mBluPlugMinOnlineCard;
    private SeekBarCardView.DSeekBarCard mBluPlugMaxOnlineCard;
    private SeekBarCardView.DSeekBarCard mBluPlugMaxCoresScreenOffCard;
    private PopupCardView.DPopupCard mBluPlugMaxFreqScreenOffCard;
    private SeekBarCardView.DSeekBarCard mBluPlugUpThresholdCard;
    private SeekBarCardView.DSeekBarCard mBluPlugUpTimerCntCard;
    private SeekBarCardView.DSeekBarCard mBluPlugDownTimerCntCard;

    private SwitchCardView.DSwitchCard mMsmHotplugEnabledCard;
    private SwitchCardView.DSwitchCard mMsmHotplugDebugMaskCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugMinCpusOnlineCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugMaxCpusOnlineCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugCpusBoostedCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugCpusOnlineSuspCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugBoostLockDurationCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugDownLockDurationCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugHistorySizeCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugUpdateRateCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugFastLaneLoadCard;
    private PopupCardView.DPopupCard mMsmHotplugFastLaneMinFreqCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugOfflineLoadCard;
    private SwitchCardView.DSwitchCard mMsmHotplugIoIsBusyCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugSuspendMaxCpusCard;
    private PopupCardView.DPopupCard mMsmHotplugSuspendFreqCard;
    private SeekBarCardView.DSeekBarCard mMsmHotplugSuspendDeferTimeCard;

    private SwitchCardView.DSwitchCard mMakoHotplugEnableCard;
    private SeekBarCardView.DSeekBarCard mMakoCoreOnTouchCard;
    private PopupCardView.DPopupCard mMakoHotplugCpuFreqUnplugLimitCard;
    private SeekBarCardView.DSeekBarCard mMakoHotplugFirstLevelCard;
    private SeekBarCardView.DSeekBarCard mMakoHotplugHighLoadCounterCard;
    private SeekBarCardView.DSeekBarCard mMakoHotplugLoadThresholdCard;
    private SeekBarCardView.DSeekBarCard mMakoHotplugMaxLoadCounterCard;
    private SeekBarCardView.DSeekBarCard mMakoHotplugMinTimeCpuOnlineCard;
    private SeekBarCardView.DSeekBarCard mMakoHotplugMinCoresOnlineCard;
    private SeekBarCardView.DSeekBarCard mMakoHotplugTimerCard;
    private PopupCardView.DPopupCard mMakoSuspendFreqCard;

    private SwitchCardView.DSwitchCard mMBHotplugEnableCard;
    private SwitchCardView.DSwitchCard mMBHotplugScroffSingleCoreCard;
    private SeekBarCardView.DSeekBarCard mMBHotplugMinCpusCard;
    private SeekBarCardView.DSeekBarCard mMBHotplugMaxCpusCard;
    private SeekBarCardView.DSeekBarCard mMBHotplugMaxCpusOnlineSuspCard;
    private PopupCardView.DPopupCard mMBHotplugIdleFreqCard;
    private SwitchCardView.DSwitchCard mMBHotplugBoostEnableCard;
    private SeekBarCardView.DSeekBarCard mMBHotplugBoostTimeCard;
    private SeekBarCardView.DSeekBarCard mMBHotplugCpusBoostedCard;
    private PopupCardView.DPopupCard[] mMBHotplugBoostFreqsCard;
    private SeekBarCardView.DSeekBarCard mMBHotplugStartDelayCard;
    private SeekBarCardView.DSeekBarCard mMBHotplugDelayCard;
    private SeekBarCardView.DSeekBarCard mMBHotplugPauseCard;
    private SeekBarCardView.DSeekBarCard mBrickedDownLockDurationCard;
    private SeekBarCardView.DSeekBarCard mBrickedNWNSCard[], mBrickedTWTSCard[];

    private SwitchCardView.DSwitchCard mAlucardHotplugEnableCard;
    private SwitchCardView.DSwitchCard mAlucardHotplugHpIoIsBusyCard;
    private SeekBarCardView.DSeekBarCard mAlucardHotplugSamplingRateCard;
    private SwitchCardView.DSwitchCard mAlucardHotplugSuspendCard;
    private SeekBarCardView.DSeekBarCard mAlucardHotplugMinCpusOnlineCard;
    private SeekBarCardView.DSeekBarCard mAlucardHotplugMaxCoresLimitCard;
    private SeekBarCardView.DSeekBarCard mAlucardHotplugMaxCoresLimitSleepCard;
    private SeekBarCardView.DSeekBarCard mAlucardHotplugCpuDownRateCard;
    private SeekBarCardView.DSeekBarCard mAlucardHotplugCpuUpRateCard;

    private SwitchCardView.DSwitchCard mThunderPlugEnableCard;
    private SeekBarCardView.DSeekBarCard mThunderPlugSuspendCpusCard;
    private PopupCardView.DPopupCard mThunderPlugEnduranceLevelCard;
    private PopupCardView.DPopupCard mThunderPlugHPStyleCard;
    private SeekBarCardView.DSeekBarCard mThunderPlugSamplingRateCard;
    private SeekBarCardView.DSeekBarCard mThunderPlugLoadThresholdCard;
    private SwitchCardView.DSwitchCard mThunderPlugTouchBoostCard;
    private SwitchCardView.DSwitchCard mThunderPlugSchedBoostCard;

    private SwitchCardView.DSwitchCard mZenDecisionEnableCard;
    private SeekBarCardView.DSeekBarCard mZenDecisionWakeWaitTimeCard;
    private SeekBarCardView.DSeekBarCard mZenDecisionBatThresholdIgnoreCard;

    private SwitchCardView.DSwitchCard mAutoSmpEnableCard;
    private SeekBarCardView.DSeekBarCard mAutoSmpCpufreqDownCard;
    private SeekBarCardView.DSeekBarCard mAutoSmpCpufreqUpCard;
    private SeekBarCardView.DSeekBarCard mAutoSmpCycleDownCard;
    private SeekBarCardView.DSeekBarCard mAutoSmpCycleUpCard;
    private SeekBarCardView.DSeekBarCard mAutoSmpDelayCard;
    private SeekBarCardView.DSeekBarCard mAutoSmpMaxCpusCard;
    private SeekBarCardView.DSeekBarCard mAutoSmpMinCpusCard;
    private SwitchCardView.DSwitchCard mAutoSmpScroffSingleCoreCard;

    private SwitchCardView.DSwitchCard mLazyPlugEnableCard;
    private SeekBarCardView.DSeekBarCard mLazyPlugTresholdCard;
    private SeekBarCardView.DSeekBarCard mLazyPlugNrPossibleCoresCard;
    private SeekBarCardView.DSeekBarCard mLazyPlugHysteresisCard;
    private PopupCardView.DPopupCard mLazyPlugProfileCard;
    private SwitchCardView.DSwitchCard mLazyPlugTouchBoostActiveCard;

    private SwitchCardView.DSwitchCard mDynPlugEnableCard;
    private SeekBarCardView.DSeekBarCard mDynPlugUpThresholdCard;
    private SeekBarCardView.DSeekBarCard mDynPlugUpTimerCntCard;
    private SeekBarCardView.DSeekBarCard mDynPlugDownTimerCntCard;
    private SeekBarCardView.DSeekBarCard mDynPlugMaxOnlineCard;
    private SeekBarCardView.DSeekBarCard mDynPlugMinOnlineCard;

    private SwitchCardView.DSwitchCard mMSMSleeperEnableCard;
    private SeekBarCardView.DSeekBarCard mMSMSleeperUpThresholdCard, mMSMSleeperMaxOnlineCard, mMSMSleeperSuspendMaxOnlineCard,
            mMSMSleeperUpCountMaxCard, mMSMSleeperDownCountMaxCard;

    private SwitchCardView.DSwitchCard mStateHelperEnableCard;
    private SeekBarCardView.DSeekBarCard mStateHelper_batt_level_eco_Card, mStateHelper_max_cpus_eco_Card,mStateHelper_batt_level_cri_Card,
            mStateHelper_max_cpus_cri_Card, mStateHelper_max_cpus_online_Card, mStateHelper_max_cpus_susp_Card;

    private SeekBarCardView.DSeekBarCard msmperformanceCard;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        if (CPUHotplug.hasZenDecisionEnable()) zenDecisionInit();
        if (CPUHotplug.hasMpdecision()) mpdecisionInit();
        if (CPUHotplug.hasIntelliPlugEnable()) intelliPlugInit();
        if (CPUHotplug.hasBluPlugEnable()) bluPlugInit();
        if (CPUHotplug.hasMsmHotplugEnable()) msmHotplugInit();
        if (CPUHotplug.hasMakoHotplugEnable()) makoHotplugInit();
        if (CPUHotplug.hasMBHotplugEnable()) mbHotplugInit();
        if (CPUHotplug.hasAlucardHotplugEnable()) alucardHotplugInit();
        if (CPUHotplug.hasThunderPlugEnable()) thunderPlugInit();
        if (CPUHotplug.hasAutoSmpEnable()) autoSmpInit();
        if (CPUHotplug.hasLazyPlugEnable()) lazyPlugInit();
        if (CPUHotplug.hasDynPlugEnable()) dynPlugInit();
        if (CPUHotplug.hasMSMSleeperEnable()) msmSleeperInit();
        if (CPUHotplug.hasStateHelperEnable()) msmState_Helper_Init();
        if (CPUHotplug.hasbch()) bchInit();
        if (CPUHotplug.hasmsmperformance()) msmperformanceInit();
        tunablesInit();
    }

    private void mpdecisionInit() {
        mMpdecisionCard = new SwitchCardView.DSwitchCard();
        mMpdecisionCard.setTitle(getString(R.string.mpdecision));
        mMpdecisionCard.setDescription(getString(R.string.mpdecision_summary));
        mMpdecisionCard.setChecked(CPUHotplug.isMpdecisionActive());
        mMpdecisionCard.setOnDSwitchCardListener(this);

        addView(mMpdecisionCard);
    }

    private void intelliPlugInit() {
            mIntelliPlugCard = new SwitchCardView.DSwitchCard();
            mIntelliPlugCard.setTitle(getString(R.string.intelliplug));
            mIntelliPlugCard.setDescription(getString(R.string.intelliplug_summary));
            mIntelliPlugCard.setChecked(CPUHotplug.isIntelliPlugActive());
            mIntelliPlugCard.setOnDSwitchCardListener(this);

            addView(mIntelliPlugCard);
    }

    private void bluPlugInit() {
        if (CPUHotplug.hasBluPlugEnable()) {
            mBluPlugCard = new SwitchCardView.DSwitchCard();
            mBluPlugCard.setTitle(getString(R.string.blu_plug));
            mBluPlugCard.setDescription(getString(R.string.blu_plug_summary));
            mBluPlugCard.setChecked(CPUHotplug.isBluPlugActive());
            mBluPlugCard.setOnDSwitchCardListener(this);

            addView(mBluPlugCard);
        }
    }

    private void msmHotplugInit() {
        if (CPUHotplug.hasMsmHotplugEnable()) {
            mMsmHotplugEnabledCard = new SwitchCardView.DSwitchCard();
            mMsmHotplugEnabledCard.setTitle(getString(R.string.msm_hotplug));
            mMsmHotplugEnabledCard.setDescription(getString(R.string.msm_hotplug_summary));
            mMsmHotplugEnabledCard.setChecked(CPUHotplug.isMsmHotplugActive());
            mMsmHotplugEnabledCard.setOnDSwitchCardListener(this);

            addView(mMsmHotplugEnabledCard);
        }
    }

    private void makoHotplugInit() {
        if (CPUHotplug.hasMakoHotplugEnable()) {
            mMakoHotplugEnableCard = new SwitchCardView.DSwitchCard();
            mMakoHotplugEnableCard.setTitle(getString(R.string.mako_hotplug));
            mMakoHotplugEnableCard.setDescription(getString(R.string.mako_hotplug_summary));
            mMakoHotplugEnableCard.setChecked(CPUHotplug.isMakoHotplugActive());
            mMakoHotplugEnableCard.setOnDSwitchCardListener(this);

            addView(mMakoHotplugEnableCard);
        }
    }

    private void mbHotplugInit() {
        if (CPUHotplug.hasMBHotplugEnable()) {
            mMBHotplugEnableCard = new SwitchCardView.DSwitchCard();
            mMBHotplugEnableCard.setTitle(getString(R.string.bricked_hotplug));
            mMBHotplugEnableCard.setDescription(getString(R.string.mb_hotplug_summary));
            mMBHotplugEnableCard.setChecked(CPUHotplug.isMBHotplugActive());
            mMBHotplugEnableCard.setOnDSwitchCardListener(this);

            addView(mMBHotplugEnableCard);
        }
    }

    private void alucardHotplugInit() {
        if (CPUHotplug.hasAlucardHotplugEnable()) {
            mAlucardHotplugEnableCard = new SwitchCardView.DSwitchCard();
            mAlucardHotplugEnableCard.setTitle(getString(R.string.alucard_hotplug));
            mAlucardHotplugEnableCard.setDescription(getString(R.string.alucard_hotplug_summary));
            mAlucardHotplugEnableCard.setChecked(CPUHotplug.isAlucardHotplugActive());
            mAlucardHotplugEnableCard.setOnDSwitchCardListener(this);

            addView(mAlucardHotplugEnableCard);
        }
    }

    private void thunderPlugInit() {
        if (CPUHotplug.hasThunderPlugEnable()) {
            mThunderPlugEnableCard = new SwitchCardView.DSwitchCard();
            mThunderPlugEnableCard.setTitle(getString(R.string.thunderplug));
            mThunderPlugEnableCard.setDescription(getString(R.string.thunderplug_summary));
            mThunderPlugEnableCard.setChecked(CPUHotplug.isThunderPlugActive());
            mThunderPlugEnableCard.setOnDSwitchCardListener(this);

            addView(mThunderPlugEnableCard);
        }
    }

    private void zenDecisionInit() {
        if (CPUHotplug.hasZenDecisionEnable()) {
            mZenDecisionEnableCard = new SwitchCardView.DSwitchCard();
            mZenDecisionEnableCard.setTitle(getString(R.string.zen_decision));
            mZenDecisionEnableCard.setDescription(getString(R.string.zen_decision_summary));
            mZenDecisionEnableCard.setChecked(CPUHotplug.isZenDecisionActive());
            mZenDecisionEnableCard.setOnDSwitchCardListener(this);

            addView(mZenDecisionEnableCard);
        }
    }

    private void autoSmpInit() {
        if (CPUHotplug.hasAutoSmpEnable()) {
            mAutoSmpEnableCard = new SwitchCardView.DSwitchCard();
            mAutoSmpEnableCard.setTitle(getString(R.string.autosmp));
            mAutoSmpEnableCard.setDescription(getString(R.string.autosmp_summary));
            mAutoSmpEnableCard.setChecked(CPUHotplug.isAutoSmpActive());
            mAutoSmpEnableCard.setOnDSwitchCardListener(this);

            addView(mAutoSmpEnableCard);
        }
    }

    private void lazyPlugInit() {
        if (CPUHotplug.hasLazyPlugEnable()) {
            mLazyPlugEnableCard = new SwitchCardView.DSwitchCard();
            mLazyPlugEnableCard.setTitle(getString(R.string.lazyplug));
            mLazyPlugEnableCard.setDescription(getString(R.string.lazyplug_summary));
            mLazyPlugEnableCard.setChecked(CPUHotplug.isLazyPlugActive());
            mLazyPlugEnableCard.setOnDSwitchCardListener(this);

            addView(mLazyPlugEnableCard);
        }
    }

    private void dynPlugInit() {
        if (CPUHotplug.hasDynPlugEnable()) {
            mDynPlugEnableCard = new SwitchCardView.DSwitchCard();
            mDynPlugEnableCard.setTitle(getString(R.string.dyn_plug));
            mDynPlugEnableCard.setDescription(getString(R.string.dyn_plug_summary));
            mDynPlugEnableCard.setChecked(CPUHotplug.isDynPlugActive());
            mDynPlugEnableCard.setOnDSwitchCardListener(this);

            addView(mDynPlugEnableCard);
        }
    }

    private void msmSleeperInit() {
        if (CPUHotplug.hasMSMSleeper()) {
            mMSMSleeperEnableCard = new SwitchCardView.DSwitchCard();
            mMSMSleeperEnableCard.setTitle(getString(R.string.msm_sleeper));
            mMSMSleeperEnableCard.setDescription(getString(R.string.msm_sleeper_summary));
            mMSMSleeperEnableCard.setChecked(CPUHotplug.isMSMSleeperActive());
            mMSMSleeperEnableCard.setOnDSwitchCardListener(this);

            addView(mMSMSleeperEnableCard);
        }
    }

    private void bchInit() {
        mbchCard = new SwitchCardView.DSwitchCard();
        mbchCard.setTitle(getString(R.string.bch));
        mbchCard.setDescription(getString(R.string.bch_summary));
        mbchCard.setChecked(CPUHotplug.isbchActive());
        mbchCard.setOnDSwitchCardListener(this);

        addView(mbchCard);
    }

    private void msmperformanceInit() {
        List<String> list = new ArrayList<>();
        String[] listitems = {"Disabled", "-----", "1", "2", "3", "4"};
        list.addAll(Arrays.asList(listitems));

        DDivider mmsmperformanceDivider = new DDivider();
        mmsmperformanceDivider.setText(getString(R.string.msmperformance));
        addView(mmsmperformanceDivider);

        msmperformanceCard = new SeekBarCardView.DSeekBarCard(list);
        msmperformanceCard.setTitle(getString(R.string.msm_max_cores));
        msmperformanceCard.setProgress(CPUHotplug.getmsmperformance() + 1);
        msmperformanceCard.setOnDSeekBarCardListener(this);

        addView(msmperformanceCard);
    }

    private void msmState_Helper_Init() {
        if (CPUHotplug.hasStateHelperEnable()) {
            mStateHelperEnableCard = new SwitchCardView.DSwitchCard();
            mStateHelperEnableCard.setTitle(getString(R.string.state_helper));
            mStateHelperEnableCard.setDescription(getString(R.string.state_helper_summary));
            mStateHelperEnableCard.setChecked(CPUHotplug.isStateHelperActive());
            mStateHelperEnableCard.setOnDSwitchCardListener(this);

            addView(mStateHelperEnableCard);
        }
    }


    private void tunablesInit() {
        List<DAdapter.DView> views = new ArrayList<>();
        views.clear();

        if (CPUHotplug.isIntelliPlugActive() || (!CPUHotplug.hasIntelliPlugEnable() && CPUHotplug.hasIntelliPlug())) {

            DDivider mIntelliPlugDividerCard = new DDivider();
            mIntelliPlugDividerCard.setText(getString(R.string.intelliplug));
            if (!CPUHotplug.hasIntelliPlugEnable() && CPUHotplug.hasIntelliPlug()) {
                mIntelliPlugDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mIntelliPlugDividerCard);

            if (CPUHotplug.hasIntelliPlugProfile()) {
                mIntelliPlugProfileCard = new PopupCardView.DPopupCard(CPUHotplug.getIntelliPlugProfileMenu(getActivity()));
                mIntelliPlugProfileCard.setTitle(getString(R.string.profile));
                mIntelliPlugProfileCard.setDescription(getString(R.string.profile_summary));
                mIntelliPlugProfileCard.setItem(CPUHotplug.getIntelliPlugProfile());
                mIntelliPlugProfileCard.setOnDPopupCardListener(this);

                views.add(mIntelliPlugProfileCard);
            }

            if (CPUHotplug.hasIntelliPlugEco()) {
                mIntelliPlugEcoCard = new SwitchCardView.DSwitchCard();
                mIntelliPlugEcoCard.setTitle(getString(R.string.eco_mode));
                mIntelliPlugEcoCard.setDescription(getString(R.string.eco_mode_summary));
                mIntelliPlugEcoCard.setChecked(CPUHotplug.isIntelliPlugEcoActive());
                mIntelliPlugEcoCard.setOnDSwitchCardListener(this);

                views.add(mIntelliPlugEcoCard);
            }

            if (CPUHotplug.hasIntelliPlugTouchBoost()) {
                mIntelliPlugTouchBoostCard = new SwitchCardView.DSwitchCard();
                mIntelliPlugTouchBoostCard.setTitle(getString(R.string.touch_boost));
                mIntelliPlugTouchBoostCard.setDescription(getString(R.string.touch_boost_summary));
                mIntelliPlugTouchBoostCard.setChecked(CPUHotplug.isIntelliPlugTouchBoostActive());
                mIntelliPlugTouchBoostCard.setOnDSwitchCardListener(this);

                views.add(mIntelliPlugTouchBoostCard);
            }

            if (CPUHotplug.hasIntelliPlugHysteresis()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 17; i++)
                    list.add(String.valueOf(i));

                mIntelliPlugHysteresisCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugHysteresisCard.setTitle(getString(R.string.hysteresis));
                mIntelliPlugHysteresisCard.setDescription(getString(R.string.hysteresis_summary));
                mIntelliPlugHysteresisCard.setProgress(CPUHotplug.getIntelliPlugHysteresis());
                mIntelliPlugHysteresisCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugHysteresisCard);
            }

            if (CPUHotplug.hasIntelliPlugThresold()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 1001; i++)
                    list.add(String.valueOf(i));

                mIntelliPlugThresholdCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugThresholdCard.setTitle(getString(R.string.threshold));
                mIntelliPlugThresholdCard.setProgress(CPUHotplug.getIntelliPlugThresold());
                mIntelliPlugThresholdCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugThresholdCard);
            }

            if (CPUHotplug.hasIntelliPlugScreenOffMax() && CPU.getFreqs() != null) {
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.disabled));
                for (int freq : CPU.getFreqs())
                    list.add((freq / 1000) + getString(R.string.mhz));

                mIntelliPlugScreenOffMaxCard = new PopupCardView.DPopupCard(list);
                mIntelliPlugScreenOffMaxCard.setTitle(getString(R.string.cpu_max_screen_off_freq));
                mIntelliPlugScreenOffMaxCard.setDescription(getString(R.string.cpu_max_screen_off_freq_summary));
                mIntelliPlugScreenOffMaxCard.setItem(CPUHotplug.getIntelliPlugScreenOffMax());
                mIntelliPlugScreenOffMaxCard.setOnDPopupCardListener(this);

                views.add(mIntelliPlugScreenOffMaxCard);
            }

            if (CPUHotplug.hasIntelliPlugDebug()) {
                mIntelliPlugDebugCard = new SwitchCardView.DSwitchCard();
                mIntelliPlugDebugCard.setTitle(getString(R.string.debug));
                mIntelliPlugDebugCard.setDescription(getString(R.string.debug_summary));
                mIntelliPlugDebugCard.setChecked(CPUHotplug.isIntelliPlugDebugActive());
                mIntelliPlugDebugCard.setOnDSwitchCardListener(this);

                views.add(mIntelliPlugDebugCard);
            }

            if (CPUHotplug.hasIntelliPlugSuspend()) {
                mIntelliPlugSuspendCard = new SwitchCardView.DSwitchCard();
                mIntelliPlugSuspendCard.setTitle(getString(R.string.suspend));
                mIntelliPlugSuspendCard.setDescription(getString(R.string.suspend_summary));
                mIntelliPlugSuspendCard.setChecked(CPUHotplug.isIntelliPlugSuspendActive());
                mIntelliPlugSuspendCard.setOnDSwitchCardListener(this);

                views.add(mIntelliPlugSuspendCard);
            }

            if (CPUHotplug.hasIntelliPlugCpusBoosted()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mIntelliPlugCpusBoostedCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugCpusBoostedCard.setTitle(getString(R.string.cpus_boosted));
                mIntelliPlugCpusBoostedCard.setDescription(getString(R.string.cpus_boosted_summary));
                mIntelliPlugCpusBoostedCard.setProgress(CPUHotplug.getIntelliPlugCpusBoosted());
                mIntelliPlugCpusBoostedCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugCpusBoostedCard);

            }

            if (CPUHotplug.hasIntelliPlugMinCpusOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mIntelliPlugMinCpusOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugMinCpusOnlineCard.setTitle(getString(R.string.min_cpu_online));
                mIntelliPlugMinCpusOnlineCard.setDescription(getString(R.string.min_cpu_online_summary));
                mIntelliPlugMinCpusOnlineCard.setProgress(CPUHotplug.getIntelliPlugMinCpusOnline() - 1);
                mIntelliPlugMinCpusOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugMinCpusOnlineCard);
            }

            if (CPUHotplug.hasIntelliPlugMaxCpusOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mIntelliPlugMaxCpusOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugMaxCpusOnlineCard.setTitle(getString(R.string.max_cpu_online));
                mIntelliPlugMaxCpusOnlineCard.setDescription(getString(R.string.max_cpu_online_summary));
                mIntelliPlugMaxCpusOnlineCard.setProgress(CPUHotplug.getIntelliPlugMaxCpusOnline() - 1);
                mIntelliPlugMaxCpusOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugMaxCpusOnlineCard);
            }

            if (CPUHotplug.hasIntelliPlugMaxCpusOnlineSusp()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mIntelliPlugMaxCpusOnlineSuspCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugMaxCpusOnlineSuspCard.setTitle(getString(R.string.max_cores_screen_off));
                mIntelliPlugMaxCpusOnlineSuspCard.setDescription(getString(R.string.max_cores_screen_off_summary));
                mIntelliPlugMaxCpusOnlineSuspCard.setProgress(CPUHotplug.getIntelliPlugMaxCpusOnlineSusp() - 1);
                mIntelliPlugMaxCpusOnlineSuspCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugMaxCpusOnlineSuspCard);
            }

            if (CPUHotplug.hasIntelliPlugSuspendDeferTime()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 501; i++)
                    list.add((i * 10) + getString(R.string.ms));

                mIntelliPlugSuspendDeferTimeCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugSuspendDeferTimeCard.setTitle(getString(R.string.suspend_defer_time));
                mIntelliPlugSuspendDeferTimeCard.setProgress(CPUHotplug.getIntelliPlugSuspendDeferTime() / 10);
                mIntelliPlugSuspendDeferTimeCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugSuspendDeferTimeCard);
            }

            if (CPUHotplug.hasIntelliPlugDeferSampling()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 1001; i++)
                    list.add(i + getString(R.string.ms));

                mIntelliPlugDeferSamplingCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugDeferSamplingCard.setTitle(getString(R.string.defer_sampling));
                mIntelliPlugDeferSamplingCard.setProgress(CPUHotplug.getIntelliPlugDeferSampling());
                mIntelliPlugDeferSamplingCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugDeferSamplingCard);
            }

            if (CPUHotplug.hasIntelliPlugBoostLockDuration()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 5001; i++)
                    list.add(i + getString(R.string.ms));

                mIntelliPlugBoostLockDurationCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugBoostLockDurationCard.setTitle(getString(R.string.boost_lock_duration));
                mIntelliPlugBoostLockDurationCard.setDescription(getString(R.string.boost_lock_duration_summary));
                mIntelliPlugBoostLockDurationCard.setProgress(CPUHotplug.getIntelliPlugBoostLockDuration() - 1);
                mIntelliPlugBoostLockDurationCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugBoostLockDurationCard);
            }

            if (CPUHotplug.hasIntelliPlugDownLockDuration()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 5001; i++)
                    list.add(i + getString(R.string.ms));

                mIntelliPlugDownLockDurationCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugDownLockDurationCard.setTitle(getString(R.string.down_lock_duration));
                mIntelliPlugDownLockDurationCard.setDescription(getString(R.string.down_lock_duration_summary));
                mIntelliPlugDownLockDurationCard.setProgress(CPUHotplug.getIntelliPlugDownLockDuration() - 1);
                mIntelliPlugDownLockDurationCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugDownLockDurationCard);
            }

            if (CPUHotplug.hasIntelliPlugFShift()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 4; i++)
                    list.add(String.valueOf(i));

                mIntelliPlugFShiftCard = new SeekBarCardView.DSeekBarCard(list);
                mIntelliPlugFShiftCard.setTitle(getString(R.string.fshift));
                mIntelliPlugFShiftCard.setProgress(CPUHotplug.getIntelliPlugFShift());
                mIntelliPlugFShiftCard.setOnDSeekBarCardListener(this);

                views.add(mIntelliPlugFShiftCard);
            }
        }

        if (CPUHotplug.isBluPlugActive() || (!CPUHotplug.hasBluPlugEnable() && CPUHotplug.hasBluPlug())) {
            DDivider mBluPlugDividerCard = new DDivider();
            mBluPlugDividerCard.setText(getString(R.string.blu_plug));
            if (!CPUHotplug.hasBluPlugEnable() && CPUHotplug.hasBluPlug()) {
                mBluPlugDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mBluPlugDividerCard);

            if (CPUHotplug.hasBluPlugPowersaverMode()) {
                mBluPlugPowersaverModeCard = new SwitchCardView.DSwitchCard();
                mBluPlugPowersaverModeCard.setTitle(getString(R.string.powersaver_mode));
                mBluPlugPowersaverModeCard.setDescription(getString(R.string.powersaver_mode_summary));
                mBluPlugPowersaverModeCard.setChecked(CPUHotplug.isBluPlugPowersaverModeActive());
                mBluPlugPowersaverModeCard.setOnDSwitchCardListener(this);

                views.add(mBluPlugPowersaverModeCard);
            }

            if (CPUHotplug.hasBluPlugMinOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mBluPlugMinOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mBluPlugMinOnlineCard.setTitle(getString(R.string.min_cpu_online));
                mBluPlugMinOnlineCard.setDescription(getString(R.string.min_cpu_online_summary));
                mBluPlugMinOnlineCard.setProgress(CPUHotplug.getBluPlugMinOnline() - 1);
                mBluPlugMinOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mBluPlugMinOnlineCard);
            }

            if (CPUHotplug.hasBluPlugMaxOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mBluPlugMaxOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mBluPlugMaxOnlineCard.setTitle(getString(R.string.max_cpu_online));
                mBluPlugMaxOnlineCard.setDescription(getString(R.string.max_cpu_online_summary));
                mBluPlugMaxOnlineCard.setProgress(CPUHotplug.getBluPlugMaxOnline() - 1);
                mBluPlugMaxOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mBluPlugMaxOnlineCard);
            }

            if (CPUHotplug.hasBluPlugMaxCoresScreenOff()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mBluPlugMaxCoresScreenOffCard = new SeekBarCardView.DSeekBarCard(list);
                mBluPlugMaxCoresScreenOffCard.setTitle(getString(R.string.max_cores_screen_off));
                mBluPlugMaxCoresScreenOffCard.setDescription(getString(R.string.max_cores_screen_off_summary));
                mBluPlugMaxCoresScreenOffCard.setProgress(CPUHotplug.getBluPlugMaxCoresScreenOff() - 1);
                mBluPlugMaxCoresScreenOffCard.setOnDSeekBarCardListener(this);

                views.add(mBluPlugMaxCoresScreenOffCard);
            }

            if (CPUHotplug.hasBluPlugMaxFreqScreenOff() && CPU.getFreqs() != null) {
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.disabled));
                for (int freq : CPU.getFreqs())
                    list.add((freq / 1000) + getString(R.string.mhz));

                mBluPlugMaxFreqScreenOffCard = new PopupCardView.DPopupCard(list);
                mBluPlugMaxFreqScreenOffCard.setTitle(getString(R.string.cpu_max_screen_off_freq));
                mBluPlugMaxFreqScreenOffCard.setDescription(getString(R.string.cpu_max_screen_off_freq_summary));
                mBluPlugMaxFreqScreenOffCard.setItem(CPUHotplug.getBluPlugMaxFreqScreenOff());
                mBluPlugMaxFreqScreenOffCard.setOnDPopupCardListener(this);

                views.add(mBluPlugMaxFreqScreenOffCard);
            }

            if (CPUHotplug.hasBluPlugUpThreshold()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(i + "%");

                mBluPlugUpThresholdCard = new SeekBarCardView.DSeekBarCard(list);
                mBluPlugUpThresholdCard.setTitle(getString(R.string.up_threshold));
                mBluPlugUpThresholdCard.setDescription(getString(R.string.up_threshold_summary));
                mBluPlugUpThresholdCard.setProgress(CPUHotplug.getBluPlugUpThreshold());
                mBluPlugUpThresholdCard.setOnDSeekBarCardListener(this);

                views.add(mBluPlugUpThresholdCard);
            }

            if (CPUHotplug.hasBluPlugUpTimerCnt()) {
                List<String> list = new ArrayList<>();
                for (float i = 0; i < 21; i++)
                    list.add(String.valueOf(i / (float) 2).replace(".0", ""));

                mBluPlugUpTimerCntCard = new SeekBarCardView.DSeekBarCard(list);
                mBluPlugUpTimerCntCard.setTitle(getString(R.string.up_timer_cnt));
                mBluPlugUpTimerCntCard.setDescription(getString(R.string.up_timer_cnt_summary));
                mBluPlugUpTimerCntCard.setProgress(CPUHotplug.getBluPlugUpTimerCnt());
                mBluPlugUpTimerCntCard.setOnDSeekBarCardListener(this);

                views.add(mBluPlugUpTimerCntCard);
            }

            if (CPUHotplug.hasBluPlugDownTimerCnt()) {
                List<String> list = new ArrayList<>();
                for (float i = 0; i < 21; i++)
                    list.add(String.valueOf(i / (float) 2).replace(".0", ""));

                mBluPlugDownTimerCntCard = new SeekBarCardView.DSeekBarCard(list);
                mBluPlugDownTimerCntCard.setTitle(getString(R.string.down_timer_cnt));
                mBluPlugDownTimerCntCard.setDescription(getString(R.string.down_timer_cnt_summary));
                mBluPlugDownTimerCntCard.setProgress(CPUHotplug.getBluPlugDownTimerCnt());
                mBluPlugDownTimerCntCard.setOnDSeekBarCardListener(this);

                views.add(mBluPlugDownTimerCntCard);
            }
        }

        if (CPUHotplug.isMsmHotplugActive() || (!CPUHotplug.hasMsmHotplugEnable() && CPUHotplug.hasMsmHotplug())) {
            DDivider mMsmHotplugDividerCard = new DDivider();
            mMsmHotplugDividerCard.setText(getString(R.string.msm_hotplug));
            if (!CPUHotplug.hasMsmHotplugEnable() && CPUHotplug.hasMsmHotplug()) {
                mMsmHotplugDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mMsmHotplugDividerCard);

            if (CPUHotplug.hasMsmHotplugDebugMask()) {
                mMsmHotplugDebugMaskCard = new SwitchCardView.DSwitchCard();
                mMsmHotplugDebugMaskCard.setTitle(getString(R.string.debug));
                mMsmHotplugDebugMaskCard.setDescription(getString(R.string.debug_summary));
                mMsmHotplugDebugMaskCard.setChecked(CPUHotplug.isMsmHotplugDebugMaskActive());
                mMsmHotplugDebugMaskCard.setOnDSwitchCardListener(this);

                views.add(mMsmHotplugDebugMaskCard);
            }

            if (CPUHotplug.hasMsmHotplugMinCpusOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMsmHotplugMinCpusOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugMinCpusOnlineCard.setTitle(getString(R.string.min_cpu_online));
                mMsmHotplugMinCpusOnlineCard.setDescription(getString(R.string.min_cpu_online_summary));
                mMsmHotplugMinCpusOnlineCard.setProgress(CPUHotplug.getMsmHotplugMinCpusOnline() - 1);
                mMsmHotplugMinCpusOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugMinCpusOnlineCard);
            }

            if (CPUHotplug.hasMsmHotplugMaxCpusOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMsmHotplugMaxCpusOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugMaxCpusOnlineCard.setTitle(getString(R.string.max_cpu_online));
                mMsmHotplugMaxCpusOnlineCard.setDescription(getString(R.string.max_cpu_online_summary));
                mMsmHotplugMaxCpusOnlineCard.setProgress(CPUHotplug.getMsmHotplugMaxCpusOnline() - 1);
                mMsmHotplugMaxCpusOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugMaxCpusOnlineCard);
            }

            if (CPUHotplug.hasMsmHotplugCpusBoosted()) {
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.disabled));
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMsmHotplugCpusBoostedCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugCpusBoostedCard.setTitle(getString(R.string.cpus_boosted));
                mMsmHotplugCpusBoostedCard.setDescription(getString(R.string.cpus_boosted_summary));
                mMsmHotplugCpusBoostedCard.setProgress(CPUHotplug.getMsmHotplugCpusBoosted());
                mMsmHotplugCpusBoostedCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugCpusBoostedCard);
            }

            if (CPUHotplug.hasMsmHotplugMaxCpusOnlineSusp()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMsmHotplugCpusOnlineSuspCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugCpusOnlineSuspCard.setTitle(getString(R.string.max_cores_screen_off));
                mMsmHotplugCpusOnlineSuspCard.setDescription(getString(R.string.max_cores_screen_off_summary));
                mMsmHotplugCpusOnlineSuspCard.setProgress(CPUHotplug.getMsmHotplugMaxCpusOnlineSusp() - 1);
                mMsmHotplugCpusOnlineSuspCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugCpusOnlineSuspCard);
            }

            if (CPUHotplug.hasMsmHotplugBoostLockDuration()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 5001; i++)
                    list.add(String.valueOf(i));

                mMsmHotplugBoostLockDurationCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugBoostLockDurationCard.setTitle(getString(R.string.boost_lock_duration));
                mMsmHotplugBoostLockDurationCard.setDescription(getString(R.string.boost_lock_duration_summary));
                mMsmHotplugBoostLockDurationCard.setProgress(CPUHotplug.getMsmHotplugBoostLockDuration() - 1);
                mMsmHotplugBoostLockDurationCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugBoostLockDurationCard);
            }

            if (CPUHotplug.hasMsmHotplugDownLockDuration()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 5001; i++)
                    list.add(String.valueOf(i));

                mMsmHotplugDownLockDurationCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugDownLockDurationCard.setTitle(getString(R.string.down_lock_duration));
                mMsmHotplugDownLockDurationCard.setDescription(getString(R.string.down_lock_duration_summary));
                mMsmHotplugDownLockDurationCard.setProgress(CPUHotplug.getMsmHotplugDownLockDuration() - 1);
                mMsmHotplugDownLockDurationCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugDownLockDurationCard);
            }

            if (CPUHotplug.hasMsmHotplugHistorySize()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 61; i++)
                    list.add(String.valueOf(i));

                mMsmHotplugHistorySizeCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugHistorySizeCard.setTitle(getString(R.string.history_size));
                mMsmHotplugHistorySizeCard.setDescription(getString(R.string.history_size_summary));
                mMsmHotplugHistorySizeCard.setProgress(CPUHotplug.getMsmHotplugHistorySize() - 1);
                mMsmHotplugHistorySizeCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugHistorySizeCard);
            }

            if (CPUHotplug.hasMsmHotplugUpdateRate()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 61; i++)
                    list.add(String.valueOf(i));

                mMsmHotplugUpdateRateCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugUpdateRateCard.setTitle(getString(R.string.update_rate));
                mMsmHotplugUpdateRateCard.setDescription(getString(R.string.update_rate_summary));
                mMsmHotplugUpdateRateCard.setProgress(CPUHotplug.getMsmHotplugUpdateRate());
                mMsmHotplugUpdateRateCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugUpdateRateCard);
            }

            if (CPUHotplug.hasMsmHotplugFastLaneLoad()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 401; i++)
                    list.add(String.valueOf(i));

                mMsmHotplugFastLaneLoadCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugFastLaneLoadCard.setTitle(getString(R.string.fast_lane_load));
                mMsmHotplugFastLaneLoadCard.setDescription(getString(R.string.fast_lane_load_summary));
                mMsmHotplugFastLaneLoadCard.setProgress(CPUHotplug.getMsmHotplugFastLaneLoad());
                mMsmHotplugFastLaneLoadCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugFastLaneLoadCard);
            }

            if (CPUHotplug.hasMsmHotplugFastLaneMinFreq() && CPU.getFreqs() != null) {
                List<String> list = new ArrayList<>();
                for (int freq : CPU.getFreqs())
                    list.add((freq / 1000) + getString(R.string.mhz));

                mMsmHotplugFastLaneMinFreqCard = new PopupCardView.DPopupCard(list);
                mMsmHotplugFastLaneMinFreqCard.setTitle(getString(R.string.fast_lane_min_freq));
                mMsmHotplugFastLaneMinFreqCard.setDescription(getString(R.string.fast_lane_min_freq_summary));
                mMsmHotplugFastLaneMinFreqCard.setItem((CPUHotplug.getMsmHotplugFastLaneMinFreq() / 1000) + getString(R.string.mhz));
                mMsmHotplugFastLaneMinFreqCard.setOnDPopupCardListener(this);

                views.add(mMsmHotplugFastLaneMinFreqCard);
            }

            if (CPUHotplug.hasMsmHotplugOfflineLoad()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(String.valueOf(i));

                mMsmHotplugOfflineLoadCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugOfflineLoadCard.setTitle(getString(R.string.offline_load));
                mMsmHotplugOfflineLoadCard.setDescription(getString(R.string.offline_load_summary));
                mMsmHotplugOfflineLoadCard.setProgress(CPUHotplug.getMsmHotplugOfflineLoad());
                mMsmHotplugOfflineLoadCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugOfflineLoadCard);
            }

            if (CPUHotplug.hasMsmHotplugIoIsBusy()) {
                mMsmHotplugIoIsBusyCard = new SwitchCardView.DSwitchCard();
                mMsmHotplugIoIsBusyCard.setTitle(getString(R.string.io_is_busy));
                mMsmHotplugIoIsBusyCard.setDescription(getString(R.string.io_is_busy_summary));
                mMsmHotplugIoIsBusyCard.setChecked(CPUHotplug.isMsmHotplugIoIsBusyActive());
                mMsmHotplugIoIsBusyCard.setOnDSwitchCardListener(this);

                views.add(mMsmHotplugIoIsBusyCard);
            }

            if (CPUHotplug.hasMsmHotplugSuspendMaxCpus()) {
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.disabled));
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMsmHotplugSuspendMaxCpusCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugSuspendMaxCpusCard.setTitle(getString(R.string.max_cores_screen_off));
                mMsmHotplugSuspendMaxCpusCard.setDescription(getString(R.string.max_cores_screen_off_summary));
                mMsmHotplugSuspendMaxCpusCard.setProgress(CPUHotplug.getMsmHotplugSuspendMaxCpus());
                mMsmHotplugSuspendMaxCpusCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugSuspendMaxCpusCard);
            }

            if (CPUHotplug.hasMsmHotplugSuspendFreq() && CPU.getFreqs() != null) {
                List<String> list = new ArrayList<>();
                for (int freq : CPU.getFreqs())
                    list.add((freq / 1000) + getString(R.string.mhz));

                mMsmHotplugSuspendFreqCard = new PopupCardView.DPopupCard(list);
                mMsmHotplugSuspendFreqCard.setTitle(getString(R.string.cpu_max_screen_off_freq));
                mMsmHotplugSuspendFreqCard.setDescription(getString(R.string.cpu_max_screen_off_freq_summary));
                mMsmHotplugSuspendFreqCard.setItem((CPUHotplug.getMsmHotplugSuspendFreq() / 1000) + getString(R.string.mhz));
                mMsmHotplugSuspendFreqCard.setOnDPopupCardListener(this);

                views.add(mMsmHotplugSuspendFreqCard);
            }

            if (CPUHotplug.hasMsmHotplugSuspendDeferTime()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i <= 5001; i += 10)
                    list.add(String.valueOf(i));

                mMsmHotplugSuspendDeferTimeCard = new SeekBarCardView.DSeekBarCard(list);
                mMsmHotplugSuspendDeferTimeCard.setTitle(getString(R.string.defer_time));
                mMsmHotplugSuspendDeferTimeCard.setProgress(CPUHotplug.getMsmHotplugSuspendDeferTime());
                mMsmHotplugSuspendDeferTimeCard.setOnDSeekBarCardListener(this);

                views.add(mMsmHotplugSuspendDeferTimeCard);
            }
        }

        if (CPUHotplug.isMakoHotplugActive() || (!CPUHotplug.hasMakoHotplugEnable() && CPUHotplug.hasMakoHotplug())) {
            DDivider mMakoHotplugDividerCard = new DDivider();
            mMakoHotplugDividerCard.setText(getString(R.string.mako_hotplug));
            if (!CPUHotplug.hasMakoHotplugEnable() && CPUHotplug.hasMakoHotplug()) {
                mMakoHotplugDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mMakoHotplugDividerCard);

            if (CPUHotplug.hasMakoHotplugCoresOnTouch()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMakoCoreOnTouchCard = new SeekBarCardView.DSeekBarCard(list);
                mMakoCoreOnTouchCard.setTitle(getString(R.string.cores_on_touch));
                mMakoCoreOnTouchCard.setDescription(getString(R.string.cores_on_touch_summary));
                mMakoCoreOnTouchCard.setProgress(CPUHotplug.getMakoHotplugCoresOnTouch() - 1);
                mMakoCoreOnTouchCard.setOnDSeekBarCardListener(this);

                views.add(mMakoCoreOnTouchCard);
            }

            if (CPUHotplug.hasMakoHotplugCpuFreqUnplugLimit() && CPU.getFreqs() != null) {
                List<String> list = new ArrayList<>();
                for (int freq : CPU.getFreqs())
                    list.add((freq / 1000) + getString(R.string.mhz));

                mMakoHotplugCpuFreqUnplugLimitCard = new PopupCardView.DPopupCard(list);
                mMakoHotplugCpuFreqUnplugLimitCard.setDescription(getString(R.string.cpu_freq_unplug_limit));
                mMakoHotplugCpuFreqUnplugLimitCard.setItem((CPUHotplug.getMakoHotplugCpuFreqUnplugLimit() / 1000)
                        + getString(R.string.mhz));
                mMakoHotplugCpuFreqUnplugLimitCard.setOnDPopupCardListener(this);

                views.add(mMakoHotplugCpuFreqUnplugLimitCard);
            }

            if (CPUHotplug.hasMakoHotplugFirstLevel()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(i + "%");

                mMakoHotplugFirstLevelCard = new SeekBarCardView.DSeekBarCard(list);
                mMakoHotplugFirstLevelCard.setTitle(getString(R.string.first_level));
                mMakoHotplugFirstLevelCard.setDescription(getString(R.string.first_level_summary));
                mMakoHotplugFirstLevelCard.setProgress(CPUHotplug.getMakoHotplugFirstLevel());
                mMakoHotplugFirstLevelCard.setOnDSeekBarCardListener(this);

                views.add(mMakoHotplugFirstLevelCard);
            }

            if (CPUHotplug.hasMakoHotplugHighLoadCounter()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(String.valueOf(i));

                mMakoHotplugHighLoadCounterCard = new SeekBarCardView.DSeekBarCard(list);
                mMakoHotplugHighLoadCounterCard.setTitle(getString(R.string.high_load_counter));
                mMakoHotplugHighLoadCounterCard.setProgress(CPUHotplug.getMakoHotplugHighLoadCounter());
                mMakoHotplugHighLoadCounterCard.setOnDSeekBarCardListener(this);

                views.add(mMakoHotplugHighLoadCounterCard);
            }

            if (CPUHotplug.hasMakoHotplugLoadThreshold()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(i + "%");

                mMakoHotplugLoadThresholdCard = new SeekBarCardView.DSeekBarCard(list);
                mMakoHotplugLoadThresholdCard.setTitle(getString(R.string.load_threshold));
                mMakoHotplugLoadThresholdCard.setDescription(getString(R.string.load_threshold_summary));
                mMakoHotplugLoadThresholdCard.setProgress(CPUHotplug.getMakoHotplugLoadThreshold());
                mMakoHotplugLoadThresholdCard.setOnDSeekBarCardListener(this);

                views.add(mMakoHotplugLoadThresholdCard);
            }

            if (CPUHotplug.hasMakoHotplugMaxLoadCounter()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(String.valueOf(i));

                mMakoHotplugMaxLoadCounterCard = new SeekBarCardView.DSeekBarCard(list);
                mMakoHotplugMaxLoadCounterCard.setTitle(getString(R.string.max_load_counter));
                mMakoHotplugMaxLoadCounterCard.setProgress(CPUHotplug.getMakoHotplugMaxLoadCounter());
                mMakoHotplugMaxLoadCounterCard.setOnDSeekBarCardListener(this);

                views.add(mMakoHotplugMaxLoadCounterCard);
            }

            if (CPUHotplug.hasMakoHotplugMinTimeCpuOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(String.valueOf(i));

                mMakoHotplugMinTimeCpuOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mMakoHotplugMinTimeCpuOnlineCard.setTitle(getString(R.string.min_time_cpu_online));
                mMakoHotplugMinTimeCpuOnlineCard.setProgress(CPUHotplug.getMakoHotplugMinTimeCpuOnline());
                mMakoHotplugMinTimeCpuOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mMakoHotplugMinTimeCpuOnlineCard);
            }

            if (CPUHotplug.hasMakoHotplugMinCoresOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMakoHotplugMinCoresOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mMakoHotplugMinCoresOnlineCard.setTitle(getString(R.string.min_cpu_online));
                mMakoHotplugMinCoresOnlineCard.setDescription(getString(R.string.min_cpu_online_summary));
                mMakoHotplugMinCoresOnlineCard.setProgress(CPUHotplug.getMakoHotplugMinCoresOnline() - 1);
                mMakoHotplugMinCoresOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mMakoHotplugMinCoresOnlineCard);
            }

            if (CPUHotplug.hasMakoHotplugTimer()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(String.valueOf(i));

                mMakoHotplugTimerCard = new SeekBarCardView.DSeekBarCard(list);
                mMakoHotplugTimerCard.setTitle(getString(R.string.timer));
                mMakoHotplugTimerCard.setProgress(CPUHotplug.getMakoHotplugTimer());
                mMakoHotplugTimerCard.setOnDSeekBarCardListener(this);

                views.add(mMakoHotplugTimerCard);
            }

            if (CPUHotplug.hasMakoHotplugSuspendFreq() && CPU.getFreqs() != null) {
                List<String> list = new ArrayList<>();
                for (int freq : CPU.getFreqs())
                    list.add((freq / 1000) + getString(R.string.mhz));

                mMakoSuspendFreqCard = new PopupCardView.DPopupCard(list);
                mMakoSuspendFreqCard.setTitle(getString(R.string.cpu_max_screen_off_freq));
                mMakoSuspendFreqCard.setDescription(getString(R.string.cpu_max_screen_off_freq_summary));
                mMakoSuspendFreqCard.setItem((CPUHotplug.getMakoHotplugSuspendFreq() / 1000) + getString(R.string.mhz));
                mMakoSuspendFreqCard.setOnDPopupCardListener(this);

                views.add(mMakoSuspendFreqCard);
            }
        }

        if (CPUHotplug.isMBHotplugActive() || (!CPUHotplug.hasMBHotplugEnable() && CPUHotplug.hasMBHotplug())) {
            DDivider mMBHotplugDividerCard = new DDivider();
            mMBHotplugDividerCard.setText(getString(R.string.bricked_hotplug));
            if (!CPUHotplug.hasMBHotplugEnable() && CPUHotplug.hasMBHotplug()) {
                mMBHotplugDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mMBHotplugDividerCard);

            if (CPUHotplug.hasMBHotplugScroffSingleCore()) {
                mMBHotplugScroffSingleCoreCard = new SwitchCardView.DSwitchCard();
                mMBHotplugScroffSingleCoreCard.setTitle(getString(R.string.screen_off_single_core));
                mMBHotplugScroffSingleCoreCard.setDescription(getString(R.string.screen_off_single_core_summary));
                mMBHotplugScroffSingleCoreCard.setChecked(CPUHotplug.isMBHotplugScroffSingleCoreActive());
                mMBHotplugScroffSingleCoreCard.setOnDSwitchCardListener(this);

                views.add(mMBHotplugScroffSingleCoreCard);
            }

            if (CPUHotplug.hasMBHotplugMinCpus()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMBHotplugMinCpusCard = new SeekBarCardView.DSeekBarCard(list);
                mMBHotplugMinCpusCard.setTitle(getString(R.string.min_cpu_online));
                mMBHotplugMinCpusCard.setDescription(getString(R.string.min_cpu_online_summary));
                mMBHotplugMinCpusCard.setProgress(CPUHotplug.getMBHotplugMinCpus() - 1);
                mMBHotplugMinCpusCard.setOnDSeekBarCardListener(this);

                views.add(mMBHotplugMinCpusCard);
            }

            if (CPUHotplug.hasMBHotplugMaxCpus()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMBHotplugMaxCpusCard = new SeekBarCardView.DSeekBarCard(list);
                mMBHotplugMaxCpusCard.setTitle(getString(R.string.max_cpu_online));
                mMBHotplugMaxCpusCard.setDescription(getString(R.string.max_cpu_online_summary));
                mMBHotplugMaxCpusCard.setProgress(CPUHotplug.getMBHotplugMaxCpus() - 1);
                mMBHotplugMaxCpusCard.setOnDSeekBarCardListener(this);

                views.add(mMBHotplugMaxCpusCard);
            }

            if (CPUHotplug.hasMBHotplugMaxCpusOnlineSusp()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMBHotplugMaxCpusOnlineSuspCard = new SeekBarCardView.DSeekBarCard(list);
                mMBHotplugMaxCpusOnlineSuspCard.setTitle(getString(R.string.max_cores_screen_off));
                mMBHotplugMaxCpusOnlineSuspCard.setDescription(getString(R.string.max_cores_screen_off_summary));
                mMBHotplugMaxCpusOnlineSuspCard.setProgress(CPUHotplug.getMBHotplugMaxCpusOnlineSusp() - 1);
                mMBHotplugMaxCpusOnlineSuspCard.setOnDSeekBarCardListener(this);

                views.add(mMBHotplugMaxCpusOnlineSuspCard);
            }

            if (CPUHotplug.hasMBHotplugIdleFreq() && CPU.getFreqs() != null) {
                List<String> list = new ArrayList<>();
                for (int freq : CPU.getFreqs())
                    list.add((freq / 1000) + getString(R.string.mhz));

                mMBHotplugIdleFreqCard = new PopupCardView.DPopupCard(list);
                mMBHotplugIdleFreqCard.setTitle(getString(R.string.idle_frequency));
                mMBHotplugIdleFreqCard.setDescription(getString(R.string.idle_frequency_summary));
                mMBHotplugIdleFreqCard.setItem((CPUHotplug.getMBHotplugIdleFreq() / 1000) + getString(R.string.mhz));
                mMBHotplugIdleFreqCard.setOnDPopupCardListener(this);

                views.add(mMBHotplugIdleFreqCard);
            }

            if (CPUHotplug.hasMBHotplugBoostEnable()) {
                mMBHotplugBoostEnableCard = new SwitchCardView.DSwitchCard();
                mMBHotplugBoostEnableCard.setTitle(getString(R.string.touch_boost));
                mMBHotplugBoostEnableCard.setDescription(getString(R.string.touch_boost_summary));
                mMBHotplugBoostEnableCard.setChecked(CPUHotplug.isMBHotplugBoostActive());
                mMBHotplugBoostEnableCard.setOnDSwitchCardListener(this);

                views.add(mMBHotplugBoostEnableCard);
            }

            if (CPUHotplug.hasMBHotplugBoostTime()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 51; i++)
                    list.add((i * 100) + getString(R.string.ms));

                mMBHotplugBoostTimeCard = new SeekBarCardView.DSeekBarCard(list);
                mMBHotplugBoostTimeCard.setTitle(getString(R.string.touch_boost_time));
                mMBHotplugBoostTimeCard.setProgress(CPUHotplug.getMBHotplugBoostTime() / 100);
                mMBHotplugBoostTimeCard.setOnDSeekBarCardListener(this);

                views.add(mMBHotplugBoostTimeCard);
            }

            if (CPUHotplug.hasMBHotplugCpusBoosted()) {
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.disabled));
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mMBHotplugCpusBoostedCard = new SeekBarCardView.DSeekBarCard(list);
                mMBHotplugCpusBoostedCard.setTitle(getString(R.string.cpus_boosted));
                mMBHotplugCpusBoostedCard.setDescription(getString(R.string.cpus_boosted_summary));
                mMBHotplugCpusBoostedCard.setProgress(CPUHotplug.getMBHotplugCpusBoosted());
                mMBHotplugCpusBoostedCard.setOnDSeekBarCardListener(this);

                views.add(mMBHotplugCpusBoostedCard);
            }

            if (CPUHotplug.hasMBHotplugBoostFreqs() && CPU.getFreqs() != null) {
                List<Integer> freqs = CPUHotplug.getMBHotplugBoostFreqs();
                List<String> list = new ArrayList<>();
                for (int freq : CPU.getFreqs())
                    list.add((freq / 1000) + getString(R.string.mhz));

                mMBHotplugBoostFreqsCard = new PopupCardView.DPopupCard[freqs.size()];
                for (int i = 0; i < freqs.size(); i++) {
                    mMBHotplugBoostFreqsCard[i] = new PopupCardView.DPopupCard(list);
                    mMBHotplugBoostFreqsCard[i].setDescription(getString(R.string.boost_frequecy_core, i));
                    mMBHotplugBoostFreqsCard[i].setItem((freqs.get(i) / 1000) + getString(R.string.mhz));
                    mMBHotplugBoostFreqsCard[i].setOnDPopupCardListener(this);

                    views.add(mMBHotplugBoostFreqsCard[i]);
                }
            }

            if (CPUHotplug.hasBrickedDownLockDuration()) {
                List<String> list = new ArrayList<>();
                for (int i = 300; i < 5001; i++)
                    list.add(i + getString(R.string.ms));

                mBrickedDownLockDurationCard = new SeekBarCardView.DSeekBarCard(list);
                mBrickedDownLockDurationCard.setTitle(getString(R.string.down_lock_duration));
                mBrickedDownLockDurationCard.setDescription(getString(R.string.down_lock_duration_summary));
                mBrickedDownLockDurationCard.setProgress((CPUHotplug.getBrickedDownLockDuration() - 300));
                mBrickedDownLockDurationCard.setOnDSeekBarCardListener(this);

                views.add(mBrickedDownLockDurationCard);
            }


            if (CPUHotplug.hasMBHotplugStartDelay()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 51; i++)
                    list.add((i * 1000) + getString(R.string.ms));

                mMBHotplugStartDelayCard = new SeekBarCardView.DSeekBarCard(list);
                mMBHotplugStartDelayCard.setTitle(getString(R.string.start_delay));
                mMBHotplugStartDelayCard.setDescription(getString(R.string.start_delay_summary));
                mMBHotplugStartDelayCard.setProgress(CPUHotplug.getMBHotplugStartDelay() / 1000);
                mMBHotplugStartDelayCard.setOnDSeekBarCardListener(this);

                views.add(mMBHotplugStartDelayCard);
            }

            if (CPUHotplug.hasMBHotplugDelay()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 201; i++)
                    list.add(String.valueOf(i));

                mMBHotplugDelayCard = new SeekBarCardView.DSeekBarCard(list);
                mMBHotplugDelayCard.setTitle(getString(R.string.delay));
                mMBHotplugDelayCard.setDescription(getString(R.string.delay_summary));
                mMBHotplugDelayCard.setProgress(CPUHotplug.getMBHotplugDelay());
                mMBHotplugDelayCard.setOnDSeekBarCardListener(this);

                views.add(mMBHotplugDelayCard);
            }

            if (CPUHotplug.hasMBHotplugPause()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 51; i++)
                    list.add((i * 1000) + getString(R.string.ms));

                mMBHotplugPauseCard = new SeekBarCardView.DSeekBarCard(list);
                mMBHotplugPauseCard.setTitle(getString(R.string.pause));
                mMBHotplugPauseCard.setDescription(getString(R.string.pause_summary));
                mMBHotplugPauseCard.setProgress(CPUHotplug.getMBHotplugPause() / 1000);
                mMBHotplugPauseCard.setOnDSeekBarCardListener(this);

                views.add(mMBHotplugPauseCard);
            }

            if (CPUHotplug.hasBrickedNWNS()) {

                DDivider mBrickedNWNSDividerCard = new DDivider();
                mBrickedNWNSDividerCard.setText("Runqueue Thresholds");
                views.add(mBrickedNWNSDividerCard);

                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++)
                    list.add(i + getString(R.string.percent));

                int cores = (CPU.getCoreCount() * 2);
                mBrickedNWNSCard = new SeekBarCardView.DSeekBarCard[cores];
                for (int i = 0; i < cores; i++) {
                    mBrickedNWNSCard[i] = new SeekBarCardView.DSeekBarCard(list);
                    mBrickedNWNSCard[i].setTitle(CPUHotplug.getBrickedNWNS(i, "title", getActivity()));
                    mBrickedNWNSCard[i].setDescription(CPUHotplug.getBrickedNWNS(i, "description", getActivity()));
                    mBrickedNWNSCard[i].setProgress(Utils.stringToInt(CPUHotplug.getBrickedNWNS(i, "value", getActivity())));
                    mBrickedNWNSCard[i].setOnDSeekBarCardListener(this);
                    views.add(mBrickedNWNSCard[i]);
                }
            }

            if (CPUHotplug.hasBrickedTWTS()) {

                DDivider mBrickedTWTSDividerCard = new DDivider();
                mBrickedTWTSDividerCard.setText("Time Thresholds");
                views.add(mBrickedTWTSDividerCard);

                List<String> list = new ArrayList<>();
                for (int i = 0; i < 1001; i++)
                    list.add(i + getString(R.string.ms));
                int cores = (CPU.getCoreCount() * 2);
                mBrickedTWTSCard = new SeekBarCardView.DSeekBarCard[cores];
                for (int i = 0; i < cores; i++) {
                    mBrickedTWTSCard[i] = new SeekBarCardView.DSeekBarCard(list);
                    mBrickedTWTSCard[i].setTitle(CPUHotplug.getBrickedTWTS(i, "title", getActivity()));
                    mBrickedTWTSCard[i].setDescription(CPUHotplug.getBrickedTWTS(i, "description", getActivity()));
                    mBrickedTWTSCard[i].setProgress(Utils.stringToInt(CPUHotplug.getBrickedTWTS(i, "value", getActivity())));
                    mBrickedTWTSCard[i].setOnDSeekBarCardListener(this);
                    views.add(mBrickedTWTSCard[i]);
                }
            }
        }

        if (CPUHotplug.isAlucardHotplugActive() || (!CPUHotplug.hasAlucardHotplugEnable() && CPUHotplug.hasAlucardHotplug())) {
            DDivider mAlucardDivider = new DDivider();
            mAlucardDivider.setText(getString(R.string.alucard_hotplug));
            if (!CPUHotplug.hasAlucardHotplugEnable() && CPUHotplug.hasAlucardHotplug()) {
                mAlucardDivider.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mAlucardDivider);

            if (CPUHotplug.hasAlucardHotplugHpIoIsBusy()) {
                mAlucardHotplugHpIoIsBusyCard = new SwitchCardView.DSwitchCard();
                mAlucardHotplugHpIoIsBusyCard.setTitle(getString(R.string.io_is_busy));
                mAlucardHotplugHpIoIsBusyCard.setDescription(getString(R.string.io_is_busy_summary));
                mAlucardHotplugHpIoIsBusyCard.setChecked(CPUHotplug.isAlucardHotplugHpIoIsBusyActive());
                mAlucardHotplugHpIoIsBusyCard.setOnDSwitchCardListener(this);

                views.add(mAlucardHotplugHpIoIsBusyCard);
            }

            if (CPUHotplug.hasAlucardHotplugSamplingRate()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 101; i++)
                    list.add(i + "%");

                mAlucardHotplugSamplingRateCard = new SeekBarCardView.DSeekBarCard(list);
                mAlucardHotplugSamplingRateCard.setTitle(getString(R.string.sampling_rate));
                mAlucardHotplugSamplingRateCard.setProgress(CPUHotplug.getAlucardHotplugSamplingRate() - 1);
                mAlucardHotplugSamplingRateCard.setOnDSeekBarCardListener(this);

                views.add(mAlucardHotplugSamplingRateCard);
            }

            if (CPUHotplug.hasAlucardHotplugSuspend()) {
                mAlucardHotplugSuspendCard = new SwitchCardView.DSwitchCard();
                mAlucardHotplugSuspendCard.setTitle(getString(R.string.suspend));
                mAlucardHotplugSuspendCard.setDescription(getString(R.string.suspend_summary));
                mAlucardHotplugSuspendCard.setChecked(CPUHotplug.isAlucardHotplugSuspendActive());
                mAlucardHotplugSuspendCard.setOnDSwitchCardListener(this);

                views.add(mAlucardHotplugSuspendCard);
            }

            if (CPUHotplug.hasAlucardHotplugMinCpusOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mAlucardHotplugMinCpusOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mAlucardHotplugMinCpusOnlineCard.setTitle(getString(R.string.min_cpu_online));
                mAlucardHotplugMinCpusOnlineCard.setDescription(getString(R.string.min_cpu_online_summary));
                mAlucardHotplugMinCpusOnlineCard.setProgress(CPUHotplug.getAlucardHotplugMinCpusOnline() - 1);
                mAlucardHotplugMinCpusOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mAlucardHotplugMinCpusOnlineCard);
            }

            if (CPUHotplug.hasAlucardHotplugMaxCoresLimit()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mAlucardHotplugMaxCoresLimitCard = new SeekBarCardView.DSeekBarCard(list);
                mAlucardHotplugMaxCoresLimitCard.setTitle(getString(R.string.max_cpu_online));
                mAlucardHotplugMaxCoresLimitCard.setDescription(getString(R.string.max_cpu_online_summary));
                mAlucardHotplugMaxCoresLimitCard.setProgress(CPUHotplug.getAlucardHotplugMaxCoresLimit() - 1);
                mAlucardHotplugMaxCoresLimitCard.setOnDSeekBarCardListener(this);

                views.add(mAlucardHotplugMaxCoresLimitCard);
            }

            if (CPUHotplug.hasAlucardHotplugMaxCoresLimitSleep()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i));

                mAlucardHotplugMaxCoresLimitSleepCard = new SeekBarCardView.DSeekBarCard(list);
                mAlucardHotplugMaxCoresLimitSleepCard.setTitle(getString(R.string.max_cores_screen_off));
                mAlucardHotplugMaxCoresLimitSleepCard.setDescription(getString(R.string.max_cores_screen_off_summary));
                mAlucardHotplugMaxCoresLimitSleepCard.setProgress(CPUHotplug.getAlucardHotplugMaxCoresLimitSleep() - 1);
                mAlucardHotplugMaxCoresLimitSleepCard.setOnDSeekBarCardListener(this);

                views.add(mAlucardHotplugMaxCoresLimitSleepCard);
            }

            if (CPUHotplug.hasAlucardHotplugCpuDownRate()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 101; i++)
                    list.add(i + "%");

                mAlucardHotplugCpuDownRateCard = new SeekBarCardView.DSeekBarCard(list);
                mAlucardHotplugCpuDownRateCard.setTitle(getString(R.string.cpu_down_rate));
                mAlucardHotplugCpuDownRateCard.setProgress(CPUHotplug.getAlucardHotplugCpuDownRate() - 1);
                mAlucardHotplugCpuDownRateCard.setOnDSeekBarCardListener(this);

                views.add(mAlucardHotplugCpuDownRateCard);
            }

            if (CPUHotplug.hasAlucardHotplugCpuUpRate()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 101; i++)
                    list.add(i + "%");

                mAlucardHotplugCpuUpRateCard = new SeekBarCardView.DSeekBarCard(list);
                mAlucardHotplugCpuUpRateCard.setTitle(getString(R.string.cpu_up_rate));
                mAlucardHotplugCpuUpRateCard.setProgress(CPUHotplug.getAlucardHotplugCpuUpRate() - 1);
                mAlucardHotplugCpuUpRateCard.setOnDSeekBarCardListener(this);

                views.add(mAlucardHotplugCpuUpRateCard);
            }
        }

        if (CPUHotplug.isThunderPlugActive() || (!CPUHotplug.hasThunderPlugEnable() && CPUHotplug.hasThunderPlug())) {
            DDivider mThunderPlugDividerCard = new DDivider();
            mThunderPlugDividerCard.setText(getString(R.string.thunderplug));
            if (!CPUHotplug.hasThunderPlugEnable() && CPUHotplug.hasThunderPlug()) {
                mThunderPlugDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mThunderPlugDividerCard);

	if (CPUHotplug.hasThunderPlughpstyle()) {
		mThunderPlugHPStyleCard = new PopupCardView.DPopupCard(new ArrayList<>(Arrays
				.asList(getResources().getStringArray(R.array.thunderplug_hp_style))));
		mThunderPlugHPStyleCard.setTitle(getString(R.string.hp_style));
		mThunderPlugHPStyleCard.setDescription(getString(R.string.hp_style));
		mThunderPlugHPStyleCard.setItem(CPUHotplug.getThunderPlughpstyle() - 1);
		mThunderPlugHPStyleCard.setOnDPopupCardListener(this);

		views.add(mThunderPlugHPStyleCard);
	}

            if (CPUHotplug.hasThunderPlugSuspendCpus()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++) list.add(String.valueOf(i));

                mThunderPlugSuspendCpusCard = new SeekBarCardView.DSeekBarCard(list);
                mThunderPlugSuspendCpusCard.setTitle(getString(R.string.min_cores_screen_off));
                mThunderPlugSuspendCpusCard.setDescription(getString(R.string.min_cores_screen_off_summary));
                mThunderPlugSuspendCpusCard.setProgress(CPUHotplug.getThunderPlugSuspendCpus() - 1);
                mThunderPlugSuspendCpusCard.setOnDSeekBarCardListener(this);

                views.add(mThunderPlugSuspendCpusCard);
            }

            if (CPUHotplug.hasThunderPlugEnduranceLevel()) {
                mThunderPlugEnduranceLevelCard = new PopupCardView.DPopupCard(new ArrayList<>(Arrays
                        .asList(getResources().getStringArray(R.array.thunderplug_endurance_level_items))));
                mThunderPlugEnduranceLevelCard.setTitle(getString(R.string.endurance_level));
                mThunderPlugEnduranceLevelCard.setDescription(getString(R.string.endurance_level_summary));
                mThunderPlugEnduranceLevelCard.setItem(CPUHotplug.getThunderPlugEnduranceLevel());
                mThunderPlugEnduranceLevelCard.setOnDPopupCardListener(this);

                views.add(mThunderPlugEnduranceLevelCard);
            }

            if (CPUHotplug.hasThunderPlugSamplingRate()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 51; i++) list.add(String.valueOf(i * 50));

                mThunderPlugSamplingRateCard = new SeekBarCardView.DSeekBarCard(list);
                mThunderPlugSamplingRateCard.setTitle(getString(R.string.sampling_rate));
                mThunderPlugSamplingRateCard.setProgress(CPUHotplug.getThunderPlugSamplingRate() / 50);
                mThunderPlugSamplingRateCard.setOnDSeekBarCardListener(this);

                views.add(mThunderPlugSamplingRateCard);
            }

            if (CPUHotplug.hasThunderPlugLoadThreshold()) {
                List<String> list = new ArrayList<>();
                for (int i = 11; i < 101; i++) list.add(String.valueOf(i));

                mThunderPlugLoadThresholdCard = new SeekBarCardView.DSeekBarCard(list);
                mThunderPlugLoadThresholdCard.setTitle(getString(R.string.load_threshold));
                mThunderPlugLoadThresholdCard.setDescription(getString(R.string.load_threshold_summary));
                mThunderPlugLoadThresholdCard.setProgress(CPUHotplug.getThunderPlugLoadThreshold() - 11);
                mThunderPlugLoadThresholdCard.setOnDSeekBarCardListener(this);

                views.add(mThunderPlugLoadThresholdCard);
            }

            if (CPUHotplug.hasThunderPlugTouchBoost()) {
                mThunderPlugTouchBoostCard = new SwitchCardView.DSwitchCard();
                mThunderPlugTouchBoostCard.setTitle(getString(R.string.touch_boost));
                mThunderPlugTouchBoostCard.setDescription(getString(R.string.touch_boost_summary));
                mThunderPlugTouchBoostCard.setChecked(CPUHotplug.isThunderPlugTouchBoostActive());
                mThunderPlugTouchBoostCard.setOnDSwitchCardListener(this);

                views.add(mThunderPlugTouchBoostCard);
            }

	if (CPUHotplug.hasThunderPlugSchedBoost()) {
		mThunderPlugSchedBoostCard = new SwitchCardView.DSwitchCard();
		mThunderPlugSchedBoostCard.setTitle(getString(R.string.sched_boost));
		mThunderPlugSchedBoostCard.setDescription(getString(R.string.sched_boost_summary));
		mThunderPlugSchedBoostCard.setChecked(CPUHotplug.isThunderPlugSchedBoostActive());
		mThunderPlugSchedBoostCard.setOnDSwitchCardListener(this);

		views.add(mThunderPlugSchedBoostCard);
	}

        }


        if (CPUHotplug.isZenDecisionActive() || (!CPUHotplug.hasZenDecisionEnable() && CPUHotplug.hasZenDecision())) {
            DDivider mZenDecisionDividerCard = new DDivider();
            mZenDecisionDividerCard.setText(getString(R.string.zen_decision));
            if (!CPUHotplug.hasZenDecisionEnable() && CPUHotplug.hasZenDecision()) {
                mZenDecisionDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mZenDecisionDividerCard);

            if (CPUHotplug.hasZenDecisionWakeWaitTime()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 61; i++) list.add((i * 1000) + getString(R.string.ms));

                mZenDecisionWakeWaitTimeCard = new SeekBarCardView.DSeekBarCard(list);
                mZenDecisionWakeWaitTimeCard.setTitle(getString(R.string.wake_wait_time));
                mZenDecisionWakeWaitTimeCard.setDescription(getString(R.string.wake_wait_time_summary));
                mZenDecisionWakeWaitTimeCard.setProgress(CPUHotplug.getZenDecisionWakeWaitTime() / 1000);
                mZenDecisionWakeWaitTimeCard.setOnDSeekBarCardListener(this);

                views.add(mZenDecisionWakeWaitTimeCard);
            }

            if (CPUHotplug.hasZenDecisionBatThresholdIgnore()) {
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.disabled));
                for (int i = 1; i < 101; i++) list.add(i + "%");

                mZenDecisionBatThresholdIgnoreCard = new SeekBarCardView.DSeekBarCard(list);
                mZenDecisionBatThresholdIgnoreCard.setTitle(getString(R.string.bat_threshold_ignore));
                mZenDecisionBatThresholdIgnoreCard.setDescription(getString(R.string.bat_threshold_ignore_summary));
                mZenDecisionBatThresholdIgnoreCard.setProgress(CPUHotplug.getZenDecisionBatThresholdIgnore());
                mZenDecisionBatThresholdIgnoreCard.setOnDSeekBarCardListener(this);

                views.add(mZenDecisionBatThresholdIgnoreCard);
            }
        }

        if (CPUHotplug.isAutoSmpActive() || (!CPUHotplug.hasAutoSmpEnable() && CPUHotplug.hasAutoSmp())) {
            DDivider mAutoSmpDividerCard = new DDivider();
            mAutoSmpDividerCard.setText(getString(R.string.autosmp));
            if (!CPUHotplug.hasAutoSmpEnable() && CPUHotplug.hasAutoSmp()) {
                mAutoSmpDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mAutoSmpDividerCard);

            if (CPUHotplug.hasAutoSmpCpufreqDown()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++) list.add(i + "%");

                mAutoSmpCpufreqDownCard = new SeekBarCardView.DSeekBarCard(list);
                mAutoSmpCpufreqDownCard.setTitle(getString(R.string.downrate_limits));
                mAutoSmpCpufreqDownCard.setProgress(CPUHotplug.getAutoSmpCpufreqDown());
                mAutoSmpCpufreqDownCard.setOnDSeekBarCardListener(this);

                views.add(mAutoSmpCpufreqDownCard);
            }

            if (CPUHotplug.hasAutoSmpCpufreqUp()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++) list.add(i + "%");

                mAutoSmpCpufreqUpCard = new SeekBarCardView.DSeekBarCard(list);
                mAutoSmpCpufreqUpCard.setTitle(getString(R.string.uprate_limits));
                mAutoSmpCpufreqUpCard.setProgress(CPUHotplug.getAutoSmpCpufreqUp());
                mAutoSmpCpufreqUpCard.setOnDSeekBarCardListener(this);

                views.add(mAutoSmpCpufreqUpCard);
            }

            if (CPUHotplug.hasAutoSmpCycleDown()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i <= CPU.getCoreCount(); i++) list.add(String.valueOf(i));

                mAutoSmpCycleDownCard = new SeekBarCardView.DSeekBarCard(list);
                mAutoSmpCycleDownCard.setTitle(getString(R.string.cycle_down));
                mAutoSmpCycleDownCard.setDescription(getString(R.string.cycle_down_summary));
                mAutoSmpCycleDownCard.setProgress(CPUHotplug.getAutoSmpCycleDown());
                mAutoSmpCycleDownCard.setOnDSeekBarCardListener(this);

                views.add(mAutoSmpCycleDownCard);
            }

            if (CPUHotplug.hasAutoSmpCycleUp()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i <= CPU.getCoreCount(); i++) list.add(String.valueOf(i));

                mAutoSmpCycleUpCard = new SeekBarCardView.DSeekBarCard(list);
                mAutoSmpCycleUpCard.setTitle(getString(R.string.cycle_up));
                mAutoSmpCycleUpCard.setDescription(getString(R.string.cycle_up_summary));
                mAutoSmpCycleUpCard.setProgress(CPUHotplug.getAutoSmpCycleUp());
                mAutoSmpCycleUpCard.setOnDSeekBarCardListener(this);

                views.add(mAutoSmpCycleUpCard);
            }

            if (CPUHotplug.hasAutoSmpDelay()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 501; i++) list.add(i + getString(R.string.ms));

                mAutoSmpDelayCard = new SeekBarCardView.DSeekBarCard(list);
                mAutoSmpDelayCard.setTitle(getString(R.string.delay));
                mAutoSmpDelayCard.setDescription(getString(R.string.delay_summary));
                mAutoSmpDelayCard.setProgress(CPUHotplug.getAutoSmpDelay());
                mAutoSmpDelayCard.setOnDSeekBarCardListener(this);

                views.add(mAutoSmpDelayCard);
            }

            if (CPUHotplug.hasAutoSmpMaxCpus()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++) list.add(String.valueOf(i));

                mAutoSmpMaxCpusCard = new SeekBarCardView.DSeekBarCard(list);
                mAutoSmpMaxCpusCard.setTitle(getString(R.string.max_cpu_online));
                mAutoSmpMaxCpusCard.setDescription(getString(R.string.max_cpu_online_summary));
                mAutoSmpMaxCpusCard.setProgress(CPUHotplug.getAutoSmpMaxCpus() - 1);
                mAutoSmpMaxCpusCard.setOnDSeekBarCardListener(this);

                views.add(mAutoSmpMaxCpusCard);
            }

            if (CPUHotplug.hasAutoSmpMinCpus()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i <= CPU.getCoreCount(); i++) list.add(String.valueOf(i));

                mAutoSmpMinCpusCard = new SeekBarCardView.DSeekBarCard(list);
                mAutoSmpMinCpusCard.setTitle(getString(R.string.min_cpu_online));
                mAutoSmpMinCpusCard.setDescription(getString(R.string.min_cpu_online_summary));
                mAutoSmpMinCpusCard.setProgress(CPUHotplug.getAutoSmpMinCpus() - 1);
                mAutoSmpMinCpusCard.setOnDSeekBarCardListener(this);

                views.add(mAutoSmpMinCpusCard);
            }

            if (CPUHotplug.hasAutoSmpScroffSingleCore()) {
                mAutoSmpScroffSingleCoreCard = new SwitchCardView.DSwitchCard();
                mAutoSmpScroffSingleCoreCard.setTitle(getString(R.string.screen_off_single_core));
                mAutoSmpScroffSingleCoreCard.setDescription(getString(R.string.screen_off_single_core_summary));
                mAutoSmpScroffSingleCoreCard.setChecked(CPUHotplug.isAutoSmpScroffSingleCoreActive());
                mAutoSmpScroffSingleCoreCard.setOnDSwitchCardListener(this);

                views.add(mAutoSmpScroffSingleCoreCard);
            }
        }

        if (CPUHotplug.isLazyPlugActive() || (!CPUHotplug.hasLazyPlugEnable() && CPUHotplug.hasLazyPlug())) {
            DDivider mLazyPlugDividerCard = new DDivider();
            mLazyPlugDividerCard.setText(getString(R.string.lazyplug));
            if (!CPUHotplug.hasLazyPlugEnable() && CPUHotplug.hasLazyPlug()) {
                mLazyPlugDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mLazyPlugDividerCard);

            if (CPUHotplug.hasLazyPlugCpuNrRunTreshold()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 1001; i++)
                    list.add(String.valueOf(i));

                mLazyPlugTresholdCard = new SeekBarCardView.DSeekBarCard(list);
                mLazyPlugTresholdCard.setTitle(getString(R.string.threshold));
                mLazyPlugTresholdCard.setProgress(CPUHotplug.getLazyPlugCpuNrRunTreshold());
                mLazyPlugTresholdCard.setOnDSeekBarCardListener(this);

                views.add(mLazyPlugTresholdCard);
            }

            if (CPUHotplug.hasLazyPlugNrPossibleCores()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++) list.add(String.valueOf(i + 1));

                mLazyPlugNrPossibleCoresCard = new SeekBarCardView.DSeekBarCard(list);
                mLazyPlugNrPossibleCoresCard.setTitle(getString(R.string.nrpossiblecores_limits));
                mLazyPlugNrPossibleCoresCard.setProgress(CPUHotplug.getLazyPlugNrPossibleCores());
                mLazyPlugNrPossibleCoresCard.setOnDSeekBarCardListener(this);

                views.add(mLazyPlugNrPossibleCoresCard);
            }

            if (CPUHotplug.hasLazyPlugNrRunHysteresis()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 17; i++)
                    list.add(String.valueOf(i));

                mLazyPlugHysteresisCard = new SeekBarCardView.DSeekBarCard(list);
                mLazyPlugHysteresisCard.setTitle(getString(R.string.hysteresis));
                mLazyPlugHysteresisCard.setDescription(getString(R.string.hysteresis_summary));
                mLazyPlugHysteresisCard.setProgress(CPUHotplug.getLazyPlugNrRunHysteresis());
                mLazyPlugHysteresisCard.setOnDSeekBarCardListener(this);

                views.add(mLazyPlugHysteresisCard);
            }

            if (CPUHotplug.hasLazyPlugProfile()) {
                mLazyPlugProfileCard = new PopupCardView.DPopupCard(CPUHotplug.getLazyPlugProfileMenu(getActivity()));
                mLazyPlugProfileCard.setTitle(getString(R.string.profile));
                mLazyPlugProfileCard.setDescription(getString(R.string.lazyplugprofile_summary));
                mLazyPlugProfileCard.setItem(CPUHotplug.getLazyPlugProfile());
                mLazyPlugProfileCard.setOnDPopupCardListener(this);

                views.add(mLazyPlugProfileCard);
            }

            if (CPUHotplug.hasLazyPlugTouchBoostActive()) {
                mLazyPlugTouchBoostActiveCard = new SwitchCardView.DSwitchCard();
                mLazyPlugTouchBoostActiveCard.setTitle(getString(R.string.touch_boost));
                mLazyPlugTouchBoostActiveCard.setDescription(getString(R.string.touch_boost_summary));
                mLazyPlugTouchBoostActiveCard.setChecked(CPUHotplug.isLazyPlugTouchBoostActive());
                mLazyPlugTouchBoostActiveCard.setOnDSwitchCardListener(this);

                views.add(mLazyPlugTouchBoostActiveCard);
            }
        }

        if (CPUHotplug.isDynPlugActive() || (!CPUHotplug.hasDynPlugEnable() && CPUHotplug.hasDynPlug())) {
            DDivider mDynPlugDividerCard = new DDivider();
            mDynPlugDividerCard.setText(getString(R.string.dyn_plug));
            if (!CPUHotplug.hasDynPlugEnable() && CPUHotplug.hasDynPlug()) {
                mDynPlugDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mDynPlugDividerCard);

            if (CPUHotplug.hasDynPlugUpThreshold()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 1001; i++)
                    list.add(String.valueOf(i));

                mDynPlugUpThresholdCard = new SeekBarCardView.DSeekBarCard(list);
                mDynPlugUpThresholdCard.setTitle(getString(R.string.threshold));
                mDynPlugUpThresholdCard.setProgress(CPUHotplug.getDynPlugUpThreshold());
                mDynPlugUpThresholdCard.setOnDSeekBarCardListener(this);

                views.add(mDynPlugUpThresholdCard);
            }

            if (CPUHotplug.hasDynPlugUpTimerCnt()) {
                List<String> list = new ArrayList<>();
                for (float i = 0; i < 21; i++)
                    list.add(String.valueOf(i / (float) 2).replace(".0", ""));

                mDynPlugUpTimerCntCard = new SeekBarCardView.DSeekBarCard(list);
                mDynPlugUpTimerCntCard.setTitle(getString(R.string.up_timer_cnt));
                mDynPlugUpTimerCntCard.setDescription(getString(R.string.up_timer_cnt_summary));
                mDynPlugUpTimerCntCard.setProgress(CPUHotplug.getDynPlugUpTimerCnt());
                mDynPlugUpTimerCntCard.setOnDSeekBarCardListener(this);

                views.add(mDynPlugUpTimerCntCard);
            }

            if (CPUHotplug.hasDynPlugDownTimerCnt()) {
                List<String> list = new ArrayList<>();
                for (float i = 0; i < 21; i++)
                    list.add(String.valueOf(i / (float) 2).replace(".0", ""));

                mDynPlugDownTimerCntCard = new SeekBarCardView.DSeekBarCard(list);
                mDynPlugDownTimerCntCard.setTitle(getString(R.string.down_timer_cnt));
                mDynPlugDownTimerCntCard.setDescription(getString(R.string.down_timer_cnt_summary));
                mDynPlugDownTimerCntCard.setProgress(CPUHotplug.getDynPlugDownTimerCnt());
                mDynPlugDownTimerCntCard.setOnDSeekBarCardListener(this);

                views.add(mDynPlugDownTimerCntCard);
            }

            if (CPUHotplug.hasDynPlugMaxOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++) list.add(String.valueOf(i + 1));

                mDynPlugMaxOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mDynPlugMaxOnlineCard.setTitle(getString(R.string.max_cpu_online));
                mDynPlugMaxOnlineCard.setDescription(getString(R.string.max_cpu_online_summary));
                mDynPlugMaxOnlineCard.setProgress(CPUHotplug.getDynPlugMaxOnline() - 1);
                mDynPlugMaxOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mDynPlugMaxOnlineCard);
            }

            if (CPUHotplug.hasDynPlugMinOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mDynPlugMinOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mDynPlugMinOnlineCard.setTitle(getString(R.string.min_cpu_online));
                mDynPlugMinOnlineCard.setDescription(getString(R.string.min_cpu_online_summary));
                mDynPlugMinOnlineCard.setProgress(CPUHotplug.getDynPlugMinOnline() - 1);
                mDynPlugMinOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mDynPlugMinOnlineCard);
            }
        }

        if (CPUHotplug.isMSMSleeperActive() || (!CPUHotplug.hasMSMSleeperEnable() && CPUHotplug.hasMSMSleeper())) {
            DDivider mMSMSleeperDividerCard = new DDivider();
            mMSMSleeperDividerCard.setText(getString(R.string.msm_sleeper));
            if (!CPUHotplug.hasMSMSleeperEnable() && CPUHotplug.hasMSMSleeper()) {
                mMSMSleeperDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mMSMSleeperDividerCard);

            if (CPUHotplug.hasMSMSleeperMaxOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 5; i++) list.add(i + " Cores");

                mMSMSleeperMaxOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mMSMSleeperMaxOnlineCard.setTitle(getString(R.string.msm_sleeper_max_online));
                mMSMSleeperMaxOnlineCard.setProgress(CPUHotplug.getMSMSleeperMaxOnline() - 1);
                mMSMSleeperMaxOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mMSMSleeperMaxOnlineCard);
            }

            if (CPUHotplug.hasMSMSleeperSuspendMaxOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 5; i++) list.add(i + " Cores");

                mMSMSleeperSuspendMaxOnlineCard = new SeekBarCardView.DSeekBarCard(list);
                mMSMSleeperSuspendMaxOnlineCard.setTitle(getString(R.string.msm_sleeper_suspend_max_online));
                mMSMSleeperSuspendMaxOnlineCard.setProgress(CPUHotplug.getMSMSleeperSuspendMaxOnline() - 1);
                mMSMSleeperSuspendMaxOnlineCard.setOnDSeekBarCardListener(this);

                views.add(mMSMSleeperSuspendMaxOnlineCard);
            }

            if (CPUHotplug.hasMSMSleeperUpThresh()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 101; i++) list.add(i + getString(R.string.percent));

                mMSMSleeperUpThresholdCard = new SeekBarCardView.DSeekBarCard(list);
                mMSMSleeperUpThresholdCard.setTitle(getString(R.string.msm_sleeper_up_thresh));
                mMSMSleeperUpThresholdCard.setProgress(CPUHotplug.getMSMSleeperUpThresh());
                mMSMSleeperUpThresholdCard.setOnDSeekBarCardListener(this);

                views.add(mMSMSleeperUpThresholdCard);
            }

            if (CPUHotplug.hasMSMSleeperUpCountMax()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 41; i++) list.add(i + getString(R.string.ms));

                mMSMSleeperUpCountMaxCard = new SeekBarCardView.DSeekBarCard(list);
                mMSMSleeperUpCountMaxCard.setTitle(getString(R.string.msm_sleeper_up_count_max));
                mMSMSleeperUpCountMaxCard.setProgress(CPUHotplug.getMSMSleeperUpCountMax());
                mMSMSleeperUpCountMaxCard.setOnDSeekBarCardListener(this);

                views.add(mMSMSleeperUpCountMaxCard);
            }

            if (CPUHotplug.hasMSMSleeperDownCountMax()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 41; i++) list.add(i + getString(R.string.ms));

                mMSMSleeperDownCountMaxCard = new SeekBarCardView.DSeekBarCard(list);
                mMSMSleeperDownCountMaxCard.setTitle(getString(R.string.msm_sleeper_down_count_max));
                mMSMSleeperDownCountMaxCard.setProgress(CPUHotplug.getMSMSleeperDownCountMax());
                mMSMSleeperDownCountMaxCard.setOnDSeekBarCardListener(this);

                views.add(mMSMSleeperDownCountMaxCard);
            }


        }

        // StateHelper Tunables
        if (CPUHotplug.isStateHelperActive() || (!CPUHotplug.hasStateHelperEnable() && CPUHotplug.hasStateHelper())) {
            DDivider mStateHelperDividerCard = new DDivider();
            mStateHelperDividerCard.setText(getString(R.string.state_helper));
            if (!CPUHotplug.hasStateHelperEnable() && CPUHotplug.hasStateHelper()) {
                mStateHelperDividerCard.setDescription(getString(R.string.no_enable_toggle));
            }
            views.add(mStateHelperDividerCard);

            if (CPUHotplug.hasStateHelperMaxCpusOnline()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mStateHelper_max_cpus_online_Card = new SeekBarCardView.DSeekBarCard(list);
                mStateHelper_max_cpus_online_Card.setTitle(getString(R.string.state_helper_max_cpus_online));
                mStateHelper_max_cpus_online_Card.setProgress(CPUHotplug.getStateHelperMaxCpusOnline() - 1);
                mStateHelper_max_cpus_online_Card.setOnDSeekBarCardListener(this);

                views.add(mStateHelper_max_cpus_online_Card);
            }

            if (CPUHotplug.hasStateHelperMaxCpusSuspend()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mStateHelper_max_cpus_susp_Card = new SeekBarCardView.DSeekBarCard(list);
                mStateHelper_max_cpus_susp_Card.setTitle(getString(R.string.state_helper_max_cpus_suspend));
                mStateHelper_max_cpus_susp_Card.setProgress(CPUHotplug.getStateHelperMaxCpusSuspend() - 1);
                mStateHelper_max_cpus_susp_Card.setOnDSeekBarCardListener(this);

                views.add(mStateHelper_max_cpus_susp_Card);
            }

            if (CPUHotplug.hasStateHelperBattLevelEco()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 101; i++) list.add(i + getString(R.string.percent));

                mStateHelper_batt_level_eco_Card = new SeekBarCardView.DSeekBarCard(list);
                mStateHelper_batt_level_eco_Card.setTitle(getString(R.string.state_helper_batt_level_eco));
                mStateHelper_batt_level_eco_Card.setDescription(getString(R.string.state_helper_batt_level_eco_summary));
                mStateHelper_batt_level_eco_Card.setProgress(CPUHotplug.getStateHelperBattLevelEco() - 1);
                mStateHelper_batt_level_eco_Card.setOnDSeekBarCardListener(this);

                views.add(mStateHelper_batt_level_eco_Card);
            }

            if (CPUHotplug.hasStateHelperMaxCpusEco()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mStateHelper_max_cpus_eco_Card = new SeekBarCardView.DSeekBarCard(list);
                mStateHelper_max_cpus_eco_Card.setTitle(getString(R.string.state_helper_max_cpus_eco));
                mStateHelper_max_cpus_eco_Card.setProgress(CPUHotplug.getStateHelperMaxCpusEco() - 1);
                mStateHelper_max_cpus_eco_Card.setOnDSeekBarCardListener(this);

                views.add(mStateHelper_max_cpus_eco_Card);
            }

            if (CPUHotplug.hasStateHelperBattLevelCri()) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < 101; i++) list.add(i + getString(R.string.percent));

                mStateHelper_batt_level_cri_Card = new SeekBarCardView.DSeekBarCard(list);
                mStateHelper_batt_level_cri_Card.setTitle(getString(R.string.state_helper_batt_level_cri));
                mStateHelper_batt_level_cri_Card.setDescription(getString(R.string.state_helper_batt_level_eco_summary));
                mStateHelper_batt_level_cri_Card.setProgress(CPUHotplug.getStateHelperBattLevelCri() - 1);
                mStateHelper_batt_level_cri_Card.setOnDSeekBarCardListener(this);

                views.add(mStateHelper_batt_level_cri_Card);
            }

            if (CPUHotplug.hasStateHelperMaxCpusCri()) {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < CPU.getCoreCount(); i++)
                    list.add(String.valueOf(i + 1));

                mStateHelper_max_cpus_cri_Card = new SeekBarCardView.DSeekBarCard(list);
                mStateHelper_max_cpus_cri_Card.setTitle(getString(R.string.state_helper_max_cpus_cri));
                mStateHelper_max_cpus_cri_Card.setProgress(CPUHotplug.getStateHelperMaxCpusCri() - 1);
                mStateHelper_max_cpus_cri_Card.setOnDSeekBarCardListener(this);

                views.add(mStateHelper_max_cpus_cri_Card);
            }
        }

        if (views.size() > 0) {
            addAllViews(views);
        }
    }

    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mMpdecisionCard)
            CPUHotplug.activateMpdecision(checked, getActivity());
        else if (dSwitchCard == mIntelliPlugCard)
            CPUHotplug.activateIntelliPlug(checked, getActivity());
        else if (dSwitchCard == mIntelliPlugEcoCard)
            CPUHotplug.activateIntelliPlugEco(checked, getActivity());
        else if (dSwitchCard == mIntelliPlugTouchBoostCard)
            CPUHotplug.activateIntelliPlugTouchBoost(checked, getActivity());
        else if (dSwitchCard == mIntelliPlugDebugCard)
            CPUHotplug.activateIntelliPlugDebug(checked, getActivity());
        else if (dSwitchCard == mIntelliPlugSuspendCard)
            CPUHotplug.activateIntelliPlugSuspend(checked, getActivity());
        else if (dSwitchCard == mBluPlugCard)
            CPUHotplug.activateBluPlug(checked, getActivity());
        else if (dSwitchCard == mBluPlugPowersaverModeCard)
            CPUHotplug.activateBluPlugPowersaverMode(checked, getActivity());
        else if (dSwitchCard == mMsmHotplugEnabledCard)
            CPUHotplug.activateMsmHotplug(checked, getActivity());
        else if (dSwitchCard == mMsmHotplugDebugMaskCard)
            CPUHotplug.activateMsmHotplugDebugMask(checked, getActivity());
        else if (dSwitchCard == mMsmHotplugIoIsBusyCard)
            CPUHotplug.activateMsmHotplugIoIsBusy(checked, getActivity());
        else if (dSwitchCard == mMakoHotplugEnableCard)
            CPUHotplug.activateMakoHotplug(checked, getActivity());
        else if (dSwitchCard == mMBHotplugEnableCard)
            CPUHotplug.activateMBHotplug(checked, getActivity());
        else if (dSwitchCard == mMBHotplugScroffSingleCoreCard)
            CPUHotplug.activateMBHotplugScroffSingleCore(checked, getActivity());
        else if (dSwitchCard == mMBHotplugBoostEnableCard)
            CPUHotplug.activateMBHotplugBoost(checked, getActivity());
        else if (dSwitchCard == mAlucardHotplugEnableCard)
            CPUHotplug.activateAlucardHotplug(checked, getActivity());
        else if (dSwitchCard == mAlucardHotplugHpIoIsBusyCard)
            CPUHotplug.activateAlucardHotplugHpIoIsBusy(checked, getActivity());
        else if (dSwitchCard == mAlucardHotplugSuspendCard)
            CPUHotplug.activateAlucardHotplugSuspend(checked, getActivity());
        else if (dSwitchCard == mThunderPlugEnableCard)
            CPUHotplug.activateThunderPlug(checked, getActivity());
        else if (dSwitchCard == mThunderPlugTouchBoostCard)
            CPUHotplug.activateThunderPlugTouchBoost(checked, getActivity());
        else if (dSwitchCard == mThunderPlugSchedBoostCard)
            CPUHotplug.activateThunderPlugSchedBoost(checked, getActivity());
        else if (dSwitchCard == mZenDecisionEnableCard)
            CPUHotplug.activateZenDecision(checked, getActivity());
        else if (dSwitchCard == mAutoSmpEnableCard)
            CPUHotplug.activateAutoSmp(checked, getActivity());
        else if (dSwitchCard == mAutoSmpScroffSingleCoreCard)
            CPUHotplug.activateAutoSmpScroffSingleCoreActive(checked, getActivity());
        else if (dSwitchCard == mMSMSleeperEnableCard)
            CPUHotplug.activateMSMSleeper(checked, getActivity());
        else if (dSwitchCard == mStateHelperEnableCard)
            CPUHotplug.activateStateHelper(checked, getActivity());
        else if (dSwitchCard == mLazyPlugEnableCard)
            CPUHotplug.activateLazyPlug(checked, getActivity());
        else if (dSwitchCard == mLazyPlugTouchBoostActiveCard)
            CPUHotplug.activateLazyPlugTouchBoost(checked, getActivity());
        else if (dSwitchCard == mDynPlugEnableCard)
            CPUHotplug.activateDynPlug(checked, getActivity());
        else if (dSwitchCard == mbchCard)
            CPUHotplug.activatebch(checked, getActivity());
        view.invalidate();
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }

    @Override
    public void onItemSelected(PopupCardView.DPopupCard dPopupCard, int position) {
        if (dPopupCard == mIntelliPlugProfileCard)
            CPUHotplug.setIntelliPlugProfile(position, getActivity());
        else if (dPopupCard == mIntelliPlugScreenOffMaxCard)
            CPUHotplug.setIntelliPlugScreenOffMax(position, getActivity());
        else if (dPopupCard == mBluPlugMaxFreqScreenOffCard)
            CPUHotplug.setBluPlugMaxFreqScreenOff(position, getActivity());
        else if (dPopupCard == mMsmHotplugFastLaneMinFreqCard)
            CPUHotplug.setMsmHotplugFastLaneMinFreq(CPU.getFreqs().get(position), getActivity());
        else if (dPopupCard == mMsmHotplugSuspendFreqCard)
            CPUHotplug.setMsmHotplugSuspendFreq(CPU.getFreqs().get(position), getActivity());
        else if (dPopupCard == mMakoHotplugCpuFreqUnplugLimitCard)
            CPUHotplug.setMakoHotplugCpuFreqUnplugLimit(CPU.getFreqs().get(position), getActivity());
        else if (dPopupCard == mMakoSuspendFreqCard)
            CPUHotplug.setMakoHotplugSuspendFreq(CPU.getFreqs().get(position), getActivity());
        else if (dPopupCard == mMBHotplugIdleFreqCard)
            CPUHotplug.setMBHotplugIdleFreq(CPU.getFreqs().get(position), getActivity());
        else if (dPopupCard == mThunderPlugEnduranceLevelCard)
            CPUHotplug.setThunderPlugEnduranceLevel(position, getActivity());
        else if (dPopupCard == mThunderPlugHPStyleCard)
            CPUHotplug.setThunderPlughpstyle(position + 1, getActivity());
        else if (mMBHotplugBoostFreqsCard != null ){
            for (int i = 0; i < mMBHotplugBoostFreqsCard.length; i++)
                if (dPopupCard == mMBHotplugBoostFreqsCard[i]) {
                    CPUHotplug.setMBHotplugBoostFreqs(i, CPU.getFreqs().get(position), getActivity());
                    return;
                }
        else if (dPopupCard == mLazyPlugProfileCard)
            CPUHotplug.setLazyPlugProfile(position, getActivity());
        }
    }

    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
        for (int i = 0; i < (CPU.getCoreCount() * 2); i++) {
            if (mBrickedNWNSCard != null && dSeekBarCard == mBrickedNWNSCard[i]) {
                CPUHotplug.setBrickedNWNS(i, position, getActivity());
            }
            else if (mBrickedTWTSCard != null && dSeekBarCard == mBrickedTWTSCard[i]) {
                CPUHotplug.setBrickedTWTS(i, position, getActivity());
            }
        }
        if (dSeekBarCard == mIntelliPlugHysteresisCard)
            CPUHotplug.setIntelliPlugHysteresis(position, getActivity());
        else if (dSeekBarCard == mIntelliPlugThresholdCard)
            CPUHotplug.setIntelliPlugThresold(position, getActivity());
        else if (dSeekBarCard == mIntelliPlugCpusBoostedCard)
            CPUHotplug.setIntelliPlugCpusBoosted(position, getActivity());
        else if (dSeekBarCard == mIntelliPlugMinCpusOnlineCard)
            CPUHotplug.setIntelliPlugMinCpusOnline(position + 1, getActivity());
        else if (dSeekBarCard == mIntelliPlugMaxCpusOnlineCard)
            CPUHotplug.setIntelliPlugMaxCpusOnline(position + 1, getActivity());
        else if (dSeekBarCard == mIntelliPlugMaxCpusOnlineSuspCard)
            CPUHotplug.setIntelliPlugMaxCpusOnlineSusp(position + 1, getActivity());
        else if (dSeekBarCard == mIntelliPlugSuspendDeferTimeCard)
            CPUHotplug.setIntelliPlugSuspendDeferTime(position * 10, getActivity());
        else if (dSeekBarCard == mIntelliPlugDeferSamplingCard)
            CPUHotplug.setIntelliPlugDeferSampling(position, getActivity());
        else if (dSeekBarCard == mIntelliPlugBoostLockDurationCard)
            CPUHotplug.setIntelliPlugBoostLockDuration(position + 1, getActivity());
        else if (dSeekBarCard == mIntelliPlugDownLockDurationCard)
            CPUHotplug.setIntelliPlugDownLockDuration(position + 1, getActivity());
        else if (dSeekBarCard == mIntelliPlugFShiftCard)
            CPUHotplug.setIntelliPlugFShift(position, getActivity());
        else if (dSeekBarCard == mBluPlugMinOnlineCard)
            CPUHotplug.setBluPlugMinOnline(position + 1, getActivity());
        else if (dSeekBarCard == mBluPlugMaxOnlineCard)
            CPUHotplug.setBluPlugMaxOnline(position + 1, getActivity());
        else if (dSeekBarCard == mBluPlugMaxCoresScreenOffCard)
            CPUHotplug.setBluPlugMaxCoresScreenOff(position + 1, getActivity());
        else if (dSeekBarCard == mBluPlugUpThresholdCard)
            CPUHotplug.setBluPlugUpThreshold(position, getActivity());
        else if (dSeekBarCard == mBluPlugUpTimerCntCard)
            CPUHotplug.setBluPlugUpTimerCnt(position, getActivity());
        else if (dSeekBarCard == mBluPlugDownTimerCntCard)
            CPUHotplug.setBluPlugDownTimerCnt(position, getActivity());
        else if (dSeekBarCard == mMsmHotplugMinCpusOnlineCard)
            CPUHotplug.setMsmHotplugMinCpusOnline(position + 1, getActivity());
        else if (dSeekBarCard == mMsmHotplugMaxCpusOnlineCard)
            CPUHotplug.setMsmHotplugMaxCpusOnline(position + 1, getActivity());
        else if (dSeekBarCard == mMsmHotplugCpusBoostedCard)
            CPUHotplug.setMsmHotplugCpusBoosted(position, getActivity());
        else if (dSeekBarCard == mMsmHotplugCpusOnlineSuspCard)
            CPUHotplug.setMsmHotplugMaxCpusOnlineSusp(position + 1, getActivity());
        else if (dSeekBarCard == mMsmHotplugBoostLockDurationCard)
            CPUHotplug.setMsmHotplugBoostLockDuration(position + 1, getActivity());
        else if (dSeekBarCard == mMsmHotplugDownLockDurationCard)
            CPUHotplug.setMsmHotplugDownLockDuration(position + 1, getActivity());
        else if (dSeekBarCard == mMsmHotplugHistorySizeCard)
            CPUHotplug.setMsmHotplugHistorySize(position + 1, getActivity());
        else if (dSeekBarCard == mMsmHotplugUpdateRateCard)
            CPUHotplug.setMsmHotplugUpdateRate(position, getActivity());
        else if (dSeekBarCard == mMsmHotplugFastLaneLoadCard)
            CPUHotplug.setMsmHotplugFastLaneLoad(position, getActivity());
        else if (dSeekBarCard == mMsmHotplugOfflineLoadCard)
            CPUHotplug.setMsmHotplugOfflineLoad(position, getActivity());
        else if (dSeekBarCard == mMsmHotplugSuspendMaxCpusCard)
            CPUHotplug.setMsmHotplugSuspendMaxCpus(position, getActivity());
        else if (dSeekBarCard == mMsmHotplugSuspendDeferTimeCard)
            CPUHotplug.setMsmHotplugSuspendDeferTime(position, getActivity());
        else if (dSeekBarCard == mMakoCoreOnTouchCard)
            CPUHotplug.setMakoHotplugCoresOnTouch(position + 1, getActivity());
        else if (dSeekBarCard == mMakoHotplugFirstLevelCard)
            CPUHotplug.setMakoHotplugFirstLevel(position, getActivity());
        else if (dSeekBarCard == mMakoHotplugHighLoadCounterCard)
            CPUHotplug.setMakoHotplugHighLoadCounter(position, getActivity());
        else if (dSeekBarCard == mMakoHotplugLoadThresholdCard)
            CPUHotplug.setMakoHotplugLoadThreshold(position, getActivity());
        else if (dSeekBarCard == mMakoHotplugMaxLoadCounterCard)
            CPUHotplug.setMakoHotplugMaxLoadCounter(position, getActivity());
        else if (dSeekBarCard == mMakoHotplugMinTimeCpuOnlineCard)
            CPUHotplug.setMakoHotplugMinTimeCpuOnline(position, getActivity());
        else if (dSeekBarCard == mMakoHotplugMinCoresOnlineCard)
            CPUHotplug.setMakoHotplugMinCoresOnline(position + 1, getActivity());
        else if (dSeekBarCard == mMakoHotplugTimerCard)
            CPUHotplug.setMakoHotplugTimer(position, getActivity());
        else if (dSeekBarCard == mMBHotplugMinCpusCard)
            CPUHotplug.setMBHotplugMinCpus(position + 1, getActivity());
        else if (dSeekBarCard == mMBHotplugMaxCpusCard)
            CPUHotplug.setMBHotplugMaxCpus(position + 1, getActivity());
        else if (dSeekBarCard == mMBHotplugMaxCpusOnlineSuspCard)
            CPUHotplug.setMBHotplugMaxCpusOnlineSusp(position + 1, getActivity());
        else if (dSeekBarCard == mMBHotplugBoostTimeCard)
            CPUHotplug.setMBHotplugBoostTime(position * 100, getActivity());
        else if (dSeekBarCard == mMBHotplugCpusBoostedCard)
            CPUHotplug.setMBHotplugCpusBoosted(position, getActivity());
        else if (dSeekBarCard == mBrickedDownLockDurationCard)
            CPUHotplug.setBrickedDownLockDuration(position + 300, getActivity());
        else if (dSeekBarCard == mMBHotplugStartDelayCard)
            CPUHotplug.setMBHotplugStartDelay(position * 1000, getActivity());
        else if (dSeekBarCard == mMBHotplugDelayCard)
            CPUHotplug.setMBHotplugDelay(position, getActivity());
        else if (dSeekBarCard == mMBHotplugPauseCard)
            CPUHotplug.setMBHotplugPause(position * 1000, getActivity());
        else if (dSeekBarCard == mAlucardHotplugSamplingRateCard)
            CPUHotplug.setAlucardHotplugSamplingRate(position + 1, getActivity());
        else if (dSeekBarCard == mAlucardHotplugMinCpusOnlineCard)
            CPUHotplug.setAlucardHotplugMinCpusOnline(position + 1, getActivity());
        else if (dSeekBarCard == mAlucardHotplugMaxCoresLimitCard)
            CPUHotplug.setAlucardHotplugMaxCoresLimit(position + 1, getActivity());
        else if (dSeekBarCard == mAlucardHotplugMaxCoresLimitSleepCard)
            CPUHotplug.setAlucardHotplugMaxCoresLimitSleep(position + 1, getActivity());
        else if (dSeekBarCard == mAlucardHotplugCpuDownRateCard)
            CPUHotplug.setAlucardHotplugCpuDownRate(position + 1, getActivity());
        else if (dSeekBarCard == mAlucardHotplugCpuUpRateCard)
            CPUHotplug.setAlucardHotplugCpuUpRate(position + 1, getActivity());
        else if (dSeekBarCard == mThunderPlugSuspendCpusCard)
            CPUHotplug.setThunderPlugSuspendCpus(position + 1, getActivity());
        else if (dSeekBarCard == mThunderPlugSamplingRateCard)
            CPUHotplug.setThunderPlugSamplingRate(position * 50, getActivity());
        else if (dSeekBarCard == mThunderPlugLoadThresholdCard)
            CPUHotplug.setThunderPlugLoadThreshold(position + 11, getActivity());
        else if (dSeekBarCard == mZenDecisionWakeWaitTimeCard)
            CPUHotplug.setZenDecisionWakeWaitTime(position * 1000, getActivity());
        else if (dSeekBarCard == mZenDecisionBatThresholdIgnoreCard)
            CPUHotplug.setZenDecisionBatThresholdIgnore(position, getActivity());
        else if (dSeekBarCard == mAutoSmpCpufreqDownCard)
            CPUHotplug.setAutoSmpCpufreqDown(position, getActivity());
        else if (dSeekBarCard == mAutoSmpCpufreqUpCard)
            CPUHotplug.setAutoSmpCpufreqUp(position, getActivity());
        else if (dSeekBarCard == mAutoSmpCycleDownCard)
            CPUHotplug.setAutoSmpCycleDown(position, getActivity());
        else if (dSeekBarCard == mAutoSmpCycleUpCard)
            CPUHotplug.setAutoSmpCycleUp(position, getActivity());
        else if (dSeekBarCard == mAutoSmpDelayCard)
            CPUHotplug.setAutoSmpDelay(position, getActivity());
        else if (dSeekBarCard == mAutoSmpMaxCpusCard)
            CPUHotplug.setAutoSmpMaxCpus(position + 1, getActivity());
        else if (dSeekBarCard == mAutoSmpMinCpusCard)
            CPUHotplug.setAutoSmpMinCpus(position + 1, getActivity());
        else if (dSeekBarCard == mLazyPlugHysteresisCard)
            CPUHotplug.setLazyPlugNrRunHysteresis(position, getActivity());
        else if (dSeekBarCard == mLazyPlugTresholdCard)
            CPUHotplug.setLazyPlugCpuNrRunTreshold(position, getActivity());
        else if (dSeekBarCard == mLazyPlugNrPossibleCoresCard)
            CPUHotplug.setLazyPlugNrPossibleCores(position, getActivity());
        else if (dSeekBarCard == mDynPlugUpThresholdCard)
            CPUHotplug.setDynPlugUpThreshold(position, getActivity());
        else if (dSeekBarCard == mDynPlugUpTimerCntCard)
            CPUHotplug.setDynPlugUpTimerCnt(position, getActivity());
        else if (dSeekBarCard == mDynPlugDownTimerCntCard)
            CPUHotplug.setDynPlugDownTimerCnt(position, getActivity());
        else if (dSeekBarCard == mDynPlugMaxOnlineCard)
            CPUHotplug.setDynPlugMaxOnline(position + 1, getActivity());
        else if (dSeekBarCard == mDynPlugMinOnlineCard)
            CPUHotplug.setDynPlugMinOnline(position + 1, getActivity());
        else if (dSeekBarCard == mMSMSleeperMaxOnlineCard)
            CPUHotplug.setMSMSleeperMaxOnline(position + 1, getActivity());
        else if (dSeekBarCard == mMSMSleeperSuspendMaxOnlineCard)
            CPUHotplug.setMSMSleeperSuspendMaxOnline(position + 1, getActivity());
        else if (dSeekBarCard == mMSMSleeperUpThresholdCard)
            CPUHotplug.setMSMSleeperUpThresh(position, getActivity());
        else if (dSeekBarCard == mMSMSleeperUpCountMaxCard)
            CPUHotplug.setMSMSleeperUpCountMax(position, getActivity());
        else if (dSeekBarCard == mMSMSleeperDownCountMaxCard)
            CPUHotplug.setMSMSleeperDownCountMax(position, getActivity());
        else if (dSeekBarCard == mStateHelper_batt_level_eco_Card)
            CPUHotplug.setStateHelperBattLevelEco(position + 1, getActivity());
        else if (dSeekBarCard == mStateHelper_batt_level_cri_Card)
            CPUHotplug.setStateHelperBattLevelCri(position + 1, getActivity());
        else if (dSeekBarCard == mStateHelper_max_cpus_eco_Card)
            CPUHotplug.setStateHelperMaxCpusEco(position + 1, getActivity());
        else if (dSeekBarCard == mStateHelper_max_cpus_cri_Card)
            CPUHotplug.setStateHelperMaxCpusCri(position + 1, getActivity());
        else if (dSeekBarCard == mStateHelper_max_cpus_online_Card)
            CPUHotplug.setStateHelperMaxCpusOnline(position + 1, getActivity());
        else if (dSeekBarCard == mStateHelper_max_cpus_susp_Card)
            CPUHotplug.setStateHelperMaxCpusSuspend(position + 1, getActivity());
        else if (dSeekBarCard == msmperformanceCard)
            CPUHotplug.setmsmperformance(position - 1, getActivity());
    }
}
