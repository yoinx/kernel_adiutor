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
import com.kerneladiutormod.reborn.elements.DDivider;
import com.kerneladiutormod.reborn.elements.cards.SeekBarCardView;
import com.kerneladiutormod.reborn.elements.cards.SwitchCardView;
import com.kerneladiutormod.reborn.fragments.RecyclerViewFragment;
import com.kerneladiutormod.reborn.utils.kernel.Sound;

/**
 * Created by willi on 06.01.15.
 */
public class SoundFragment extends RecyclerViewFragment implements
        SwitchCardView.DSwitchCard.OnDSwitchCardListener,
        SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener {

    private SwitchCardView.DSwitchCard mSoundControlEnableCard;
    private SwitchCardView.DSwitchCard mHighPerfModeEnableCard;
    private SwitchCardView.DSwitchCard mwcdspkr_drv_wrndCard, mwcdHighPerfModeEnableCard;
    private SeekBarCardView.DSeekBarCard mHeadphoneGainCard, mHeadphoneGainLCard, mHeadphoneGainRCard;
    private SwitchCardView.DSwitchCard mHeadphoneGainIndependentCard;
    private SeekBarCardView.DSeekBarCard mHandsetMicrophoneGainCard;
    private SeekBarCardView.DSeekBarCard mCamMicrophoneGainCard;
    private SeekBarCardView.DSeekBarCard mSpeakerGainCard;
    private SeekBarCardView.DSeekBarCard mHeadphonePowerAmpGainCard;
    private SeekBarCardView.DSeekBarCard mMicrophoneGainCard;
    private SeekBarCardView.DSeekBarCard mVolumeGainCard;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        if (Sound.hasDriverTunables()) drivertunablesInit();

        if (Sound.hasWcdSpkr_Drv_WrndEnable()) wcdspkr_drv_wrnd_Init();
        //Only show the SOC built-in highperf mode if there isn't a third party one present.
        if (Sound.hasWcdHighPerfMode() && !Sound.hasHighPerfModeEnable()) wcdhighPerfModeEnableInit();

        if (Sound.hasThirdPartyTunables()) thirdpartytunablesInit();

        if (Sound.hasSoundControlEnable()) soundControlEnableInit();
        if (Sound.hasHighPerfModeEnable()) highPerfModeEnableInit();
        if (Sound.hasHeadphoneGain()) headphoneGainInit();
        if (Sound.hasHandsetMicrophoneGain()) handsetMicrophoneGainInit();
        if (Sound.hasCamMicrophoneGain()) camMicrophoneGainInit();
        if (Sound.hasSpeakerGain()) speakerGainInit();
        if (Sound.hasHeadphonePowerAmpGain()) headphonePowerAmpGainInit();
        if (Sound.hasMicrophoneGain()) microphoneGainInit();
        if (Sound.hasVolumeGain()) volumeGainInit();
    }


    private void drivertunablesInit() {
        DDivider mdrivertunablesDivider = new DDivider();
        mdrivertunablesDivider.setText(getString(R.string.driver_tunables));
        addView(mdrivertunablesDivider);
    }

    private void wcdspkr_drv_wrnd_Init() {
        mwcdspkr_drv_wrndCard = new SwitchCardView.DSwitchCard();
        mwcdspkr_drv_wrndCard.setTitle(getString(R.string.headset_mspkr_drv_mode));
        mwcdspkr_drv_wrndCard.setDescription(getString(R.string.headset_mspkr_drv_mode_summary));
        mwcdspkr_drv_wrndCard.setChecked(Sound.isWcdSpkr_Drv_Wrnd_Active());
        mwcdspkr_drv_wrndCard.setOnDSwitchCardListener(this);

        addView(mwcdspkr_drv_wrndCard);
    }

    private void wcdhighPerfModeEnableInit() {
        mwcdHighPerfModeEnableCard = new SwitchCardView.DSwitchCard();
        mwcdHighPerfModeEnableCard.setTitle(getString(R.string.headset_highperf_mode));
        mwcdHighPerfModeEnableCard.setDescription(getString(R.string.headset_highperf_mode_summary));
        mwcdHighPerfModeEnableCard.setChecked(Sound.isWcdHighPerfModeActive());
        mwcdHighPerfModeEnableCard.setOnDSwitchCardListener(this);

        addView(mwcdHighPerfModeEnableCard);
    }

    private void thirdpartytunablesInit() {
        DDivider mthirdpartytunablesDivider = new DDivider();
        mthirdpartytunablesDivider.setText(getString(R.string.third_party_driver_tunables));
        addView(mthirdpartytunablesDivider);
    }

    private void soundControlEnableInit() {
        mSoundControlEnableCard = new SwitchCardView.DSwitchCard();
        mSoundControlEnableCard.setDescription(getString(R.string.sound_control));
        mSoundControlEnableCard.setChecked(Sound.isSoundControlActive());
        mSoundControlEnableCard.setOnDSwitchCardListener(this);

        addView(mSoundControlEnableCard);
    }

    private void highPerfModeEnableInit() {
        mHighPerfModeEnableCard = new SwitchCardView.DSwitchCard();
        mHighPerfModeEnableCard.setTitle(getString(R.string.headset_highperf_mode));
        mHighPerfModeEnableCard.setChecked(Sound.isHighPerfModeActive());
        mHighPerfModeEnableCard.setOnDSwitchCardListener(this);

        addView(mHighPerfModeEnableCard);
    }

    private void headphoneGainInit() {

        mHeadphoneGainIndependentCard = new SwitchCardView.DSwitchCard();
        mHeadphoneGainIndependentCard.setTitle(getString(R.string.headphone_gain_independent));
        mHeadphoneGainIndependentCard.setDescription(getString(R.string.headphone_gain_independent_summary));
        mHeadphoneGainIndependentCard.setChecked(Sound.isIndependentHeadphoneGainEnabled(getActivity()));
        mHeadphoneGainIndependentCard.setOnDSwitchCardListener(this);

        addView(mHeadphoneGainIndependentCard);

        if (Sound.isIndependentHeadphoneGainEnabled(getActivity())) {

            mHeadphoneGainLCard = new SeekBarCardView.DSeekBarCard(Sound.getHeadphoneGainLimits());
            mHeadphoneGainLCard.setTitle(getString(R.string.headphone_gain_l));
            mHeadphoneGainLCard.setProgress(Sound.getHeadphoneGainLimits().indexOf(Sound.getCurHeadphoneGain("L")));
            mHeadphoneGainLCard.setOnDSeekBarCardListener(this);

            addView(mHeadphoneGainLCard);

            mHeadphoneGainRCard = new SeekBarCardView.DSeekBarCard(Sound.getHeadphoneGainLimits());
            mHeadphoneGainRCard.setTitle(getString(R.string.headphone_gain_r));
            mHeadphoneGainRCard.setProgress(Sound.getHeadphoneGainLimits().indexOf(Sound.getCurHeadphoneGain("R")));
            mHeadphoneGainRCard.setOnDSeekBarCardListener(this);

            addView(mHeadphoneGainRCard);

        }

        else {

            mHeadphoneGainCard = new SeekBarCardView.DSeekBarCard(Sound.getHeadphoneGainLimits());
            mHeadphoneGainCard.setTitle(getString(R.string.headphone_gain));
            mHeadphoneGainCard.setProgress(Sound.getHeadphoneGainLimits().indexOf(Sound.getCurHeadphoneGain("B")));
            mHeadphoneGainCard.setOnDSeekBarCardListener(this);

            addView(mHeadphoneGainCard);
        }
    }

    private void handsetMicrophoneGainInit() {
        mHandsetMicrophoneGainCard = new SeekBarCardView.DSeekBarCard(Sound.getHandsetMicrophoneGainLimits());
        mHandsetMicrophoneGainCard.setTitle(getString(R.string.handset_microphone_gain));
        mHandsetMicrophoneGainCard.setProgress(Sound.getHandsetMicrophoneGainLimits().indexOf(
                Sound.getCurHandsetMicrophoneGain()));
        mHandsetMicrophoneGainCard.setOnDSeekBarCardListener(this);

        addView(mHandsetMicrophoneGainCard);
    }

    private void camMicrophoneGainInit() {
        mCamMicrophoneGainCard = new SeekBarCardView.DSeekBarCard(Sound.getCamMicrophoneGainLimits());
        mCamMicrophoneGainCard.setTitle(getString(R.string.cam_microphone_gain));
        mCamMicrophoneGainCard.setProgress(Sound.getCamMicrophoneGainLimits().indexOf(Sound.getCurCamMicrophoneGain()));
        mCamMicrophoneGainCard.setOnDSeekBarCardListener(this);

        addView(mCamMicrophoneGainCard);
    }

    private void speakerGainInit() {
        mSpeakerGainCard = new SeekBarCardView.DSeekBarCard(Sound.getSpeakerGainLimits());
        mSpeakerGainCard.setTitle(getString(R.string.speaker_gain));
        mSpeakerGainCard.setProgress(Sound.getSpeakerGainLimits().indexOf(Sound.getCurSpeakerGain()));
        mSpeakerGainCard.setOnDSeekBarCardListener(this);

        addView(mSpeakerGainCard);
    }

    private void headphonePowerAmpGainInit() {
        mHeadphonePowerAmpGainCard = new SeekBarCardView.DSeekBarCard(Sound.getHeadphonePowerAmpGainLimits());
        mHeadphonePowerAmpGainCard.setTitle(getString(R.string.headphone_poweramp_gain));
        mHeadphonePowerAmpGainCard.setProgress(Sound.getHeadphonePowerAmpGainLimits().indexOf(
                Sound.getCurHeadphonePowerAmpGain()));
        mHeadphonePowerAmpGainCard.setOnDSeekBarCardListener(this);

        addView(mHeadphonePowerAmpGainCard);
    }

    private void microphoneGainInit() {
        mMicrophoneGainCard = new SeekBarCardView.DSeekBarCard(Sound.getMicrophoneGainLimits());
        mMicrophoneGainCard.setTitle(getString(R.string.microphone_gain));
        mMicrophoneGainCard.setProgress(Sound.getMicrophoneGainLimits().indexOf(Sound.getMicrophoneGain()));
        mMicrophoneGainCard.setOnDSeekBarCardListener(this);

        addView(mMicrophoneGainCard);
    }

    private void volumeGainInit() {
        mVolumeGainCard = new SeekBarCardView.DSeekBarCard(Sound.getVolumeGainLimits());
        mVolumeGainCard.setTitle(getString(R.string.volume_gain));
        mVolumeGainCard.setProgress(Sound.getVolumeGainLimits().indexOf(Sound.getVolumeGain()));
        mVolumeGainCard.setOnDSeekBarCardListener(this);

        addView(mVolumeGainCard);
    }

    @Override
    public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
        if (dSwitchCard == mSoundControlEnableCard)
            Sound.activateSoundControl(checked, getActivity());
        else if (dSwitchCard == mHeadphoneGainIndependentCard) {
            Sound.setIndependentHeadphoneGainEnabled(checked, getActivity());
            view.invalidate();
            getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
        else if (dSwitchCard == mHighPerfModeEnableCard)
            Sound.activateHighPerfMode(checked, getActivity());
        else if (dSwitchCard == mwcdHighPerfModeEnableCard)
            Sound.activateWcdHighPerfMode(checked, getActivity());
        else if (dSwitchCard == mwcdspkr_drv_wrndCard)
            Sound.activateWcdSpkr_Drv_Wrnd(checked, getActivity());
    }

    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
        if (dSeekBarCard == mHeadphoneGainCard)
            Sound.setHeadphoneGain(Sound.getHeadphoneGainLimits().get(position), getActivity(), "B");
        else if (dSeekBarCard == mHeadphoneGainLCard)
            Sound.setHeadphoneGain(Sound.getHeadphoneGainLimits().get(position), getActivity(), "L");
        else if (dSeekBarCard == mHeadphoneGainRCard)
            Sound.setHeadphoneGain(Sound.getHeadphoneGainLimits().get(position), getActivity(), "R");
        else if (dSeekBarCard == mHandsetMicrophoneGainCard)
            Sound.setHandsetMicrophoneGain(Sound.getHandsetMicrophoneGainLimits().get(position), getActivity());
        else if (dSeekBarCard == mCamMicrophoneGainCard)
            Sound.setCamMicrophoneGain(Sound.getCamMicrophoneGainLimits().get(position), getActivity());
        else if (dSeekBarCard == mSpeakerGainCard)
            Sound.setSpeakerGain(Sound.getSpeakerGainLimits().get(position), getActivity());
        else if (dSeekBarCard == mHeadphonePowerAmpGainCard)
            Sound.setHeadphonePowerAmpGain(Sound.getHeadphonePowerAmpGainLimits().get(position), getActivity());
        else if (dSeekBarCard == mMicrophoneGainCard)
            Sound.setMicrophoneGain(Sound.getMicrophoneGainLimits().get(position), getActivity());
        else if (dSeekBarCard == mVolumeGainCard)
            Sound.setVolumeGain(Sound.getVolumeGainLimits().get(position), getActivity());
    }
}
