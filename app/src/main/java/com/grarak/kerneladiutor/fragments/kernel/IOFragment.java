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

import android.content.Context;
import android.os.Bundle;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.DDivider;
import com.grarak.kerneladiutor.elements.cards.CardViewItem;
import com.grarak.kerneladiutor.elements.cards.PopupCardView;
import com.grarak.kerneladiutor.elements.cards.SwitchCardView;
import com.grarak.kerneladiutor.fragments.PathReaderFragment;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.fragments.ViewPagerFragment;
import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.kernel.IO;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by willi on 11.04.15.
 */
public class IOFragment extends ViewPagerFragment implements Constants {

    private static IOFragment ioFragment;
    private IOPart ioPart;
    private SchedulerPart schedulerPart;
    private IO.StorageType storageType;

    @Override
    public void preInit(Bundle savedInstanceState) {
        super.preInit(savedInstanceState);
        showTabs(false);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        ioFragment = this;

        allowSwipe(false);
        addFragment(new ViewPagerItem(ioPart == null ? ioPart = new IOPart() : ioPart, null));
        addFragment(new ViewPagerItem(schedulerPart == null ? schedulerPart = new SchedulerPart() : schedulerPart, null));
    }

    @Override
    public void onSwipe(int page) {
        super.onSwipe(page);
        allowSwipe(page == 1);
    }

    @Override
    public boolean onBackPressed() {
        if (getCurrentPage() == 1) {
            setCurrentItem(0);
            return true;
        }
        return false;
    }

    public static class IOPart extends RecyclerViewFragment implements PopupCardView.DPopupCard.OnDPopupCardListener,
            CardViewItem.DCardView.OnDCardListener, SwitchCardView.DSwitchCard.OnDSwitchCardListener {

        private final List<String> readheads = new ArrayList<>();

        private final List<String> list = new ArrayList<>();

        private PopupCardView.DPopupCard mInternalSchedulerCard, mInternalSchedulerCard_SDA, mInternalSchedulerCard_DM0, mExternalSchedulerCard;

        private CardViewItem.DCardView mInternalTunableCard, mInternalTunableCard_SDA, mInternalTunableCard_DM0, mExternalTunableCard;

        private PopupCardView.DPopupCard mInternalReadAheadCard, mInternalReadAheadCard_SDA, mInternalReadAheadCard_DM0, mExternalReadAheadCard, mIOAffinityCard;

        private SwitchCardView.DSwitchCard mRotationalCard, mIOStatsCard, mIORandomCard;

        @Override
        public String getClassName() {
            return IOFragment.class.getSimpleName();
        }

        @Override
        public void init(Bundle savedInstanceState) {
            super.init(savedInstanceState);

            readheads.clear();

            internalStorageInit();
            if (IO.hasExternalStorage())
                externalStorageInit();
            if (IO.hasRotational()) RotationalInit();
            if (IO.hasIORandom()) IORandomInit();
            if (IO.hasIOStats()) IOStatsInit();
            if (IO.hasIOAffinity()) IOAffintyInit();
        }

        private void internalStorageInit() {
            DDivider mInternalStorageDivider = new DDivider();
            mInternalStorageDivider.setText(getString(R.string.internal_storage));

            addView(mInternalStorageDivider);

            for (int i = 0; i < 32; i++)
                readheads.add((i * 128 + 128) + getString(R.string.kb));

            if (IO.getSchedulers(IO.StorageType.INTERNAL) != null) {
                mInternalSchedulerCard = new PopupCardView.DPopupCard(IO.getSchedulers(IO.StorageType.INTERNAL));
                mInternalSchedulerCard.setTitle(getString(R.string.scheduler));
                mInternalSchedulerCard.setDescription(getString(R.string.scheduler_summary));
                mInternalSchedulerCard.setItem(IO.getScheduler(IO.StorageType.INTERNAL));
                mInternalSchedulerCard.setOnDPopupCardListener(this);

                addView(mInternalSchedulerCard);

                mInternalTunableCard = new CardViewItem.DCardView();
                mInternalTunableCard.setTitle(getString(R.string.scheduler_tunable));
                mInternalTunableCard.setDescription(getString(R.string.scheduler_tunable_summary));
                mInternalTunableCard.setOnDCardListener(this);

                addView(mInternalTunableCard);

                mInternalReadAheadCard = new PopupCardView.DPopupCard(readheads);
                mInternalReadAheadCard.setTitle(getString(R.string.read_ahead));
                mInternalReadAheadCard.setDescription(getString(R.string.read_ahead_summary));
                mInternalReadAheadCard.setItem(IO.getReadahead(IO.StorageType.INTERNAL) + getString(R.string.kb));
                mInternalReadAheadCard.setOnDPopupCardListener(this);

                addView(mInternalReadAheadCard);
            } else if (IO.getSchedulers(IO.StorageType.INTERNAL_SDA) != null) {
                mInternalSchedulerCard_SDA = new PopupCardView.DPopupCard(IO.getSchedulers(IO.StorageType.INTERNAL_SDA));
                mInternalSchedulerCard_SDA.setTitle(getString(R.string.scheduler));
                mInternalSchedulerCard_SDA.setDescription(getString(R.string.scheduler_summary));
                mInternalSchedulerCard_SDA.setItem(IO.getScheduler(IO.StorageType.INTERNAL_SDA));
                mInternalSchedulerCard_SDA.setOnDPopupCardListener(this);

                addView(mInternalSchedulerCard_SDA);

                mInternalTunableCard_SDA = new CardViewItem.DCardView();
                mInternalTunableCard_SDA.setTitle(getString(R.string.scheduler_tunable));
                mInternalTunableCard_SDA.setDescription(getString(R.string.scheduler_tunable_summary));
                mInternalTunableCard_SDA.setOnDCardListener(this);

                addView(mInternalTunableCard_SDA);

                mInternalReadAheadCard_SDA = new PopupCardView.DPopupCard(readheads);
                mInternalReadAheadCard_SDA.setTitle(getString(R.string.read_ahead));
                mInternalReadAheadCard_SDA.setDescription(getString(R.string.read_ahead_summary));
                mInternalReadAheadCard_SDA.setItem(IO.getReadahead(IO.StorageType.INTERNAL_SDA) + getString(R.string.kb));
                mInternalReadAheadCard_SDA.setOnDPopupCardListener(this);

                addView(mInternalReadAheadCard_SDA);
            } else if (IO.getSchedulers(IO.StorageType.INTERNAL_DM0) != null) {
                mInternalSchedulerCard_DM0 = new PopupCardView.DPopupCard(IO.getSchedulers(IO.StorageType.INTERNAL_DM0));
                mInternalSchedulerCard_DM0.setTitle(getString(R.string.scheduler));
                mInternalSchedulerCard_DM0.setDescription(getString(R.string.scheduler_summary));
                mInternalSchedulerCard_DM0.setItem(IO.getScheduler(IO.StorageType.INTERNAL_DM0));
                mInternalSchedulerCard_DM0.setOnDPopupCardListener(this);

                addView(mInternalSchedulerCard_DM0);

                mInternalTunableCard_DM0 = new CardViewItem.DCardView();
                mInternalTunableCard_DM0.setTitle(getString(R.string.scheduler_tunable));
                mInternalTunableCard_DM0.setDescription(getString(R.string.scheduler_tunable_summary));
                mInternalTunableCard_DM0.setOnDCardListener(this);

                addView(mInternalTunableCard_DM0);

                mInternalReadAheadCard_DM0 = new PopupCardView.DPopupCard(readheads);
                mInternalReadAheadCard_DM0.setTitle(getString(R.string.read_ahead));
                mInternalReadAheadCard_DM0.setDescription(getString(R.string.read_ahead_summary));
                mInternalReadAheadCard_DM0.setItem(IO.getReadahead(IO.StorageType.INTERNAL_DM0) + getString(R.string.kb));
                mInternalReadAheadCard_DM0.setOnDPopupCardListener(this);

                addView(mInternalReadAheadCard_DM0);
            }
        }

        private void externalStorageInit() {
            DDivider mExternalStorageDivider = new DDivider();
            mExternalStorageDivider.setText(getString(R.string.external_storage));

            addView(mExternalStorageDivider);

            mExternalSchedulerCard = new PopupCardView.DPopupCard(IO.getSchedulers(IO.StorageType.EXTERNAL));
            mExternalSchedulerCard.setDescription(getString(R.string.scheduler));
            mExternalSchedulerCard.setItem(IO.getScheduler(IO.StorageType.EXTERNAL));
            mExternalSchedulerCard.setOnDPopupCardListener(this);

            addView(mExternalSchedulerCard);

            mExternalTunableCard = new CardViewItem.DCardView();
            mExternalTunableCard.setDescription(getString(R.string.scheduler_tunable));
            mExternalTunableCard.setOnDCardListener(this);

            addView(mExternalTunableCard);

            mExternalReadAheadCard = new PopupCardView.DPopupCard(readheads);
            mExternalReadAheadCard.setDescription(getString(R.string.read_ahead));
            mExternalReadAheadCard.setItem(IO.getReadahead(IO.StorageType.EXTERNAL) + getString(R.string.kb));
            mExternalReadAheadCard.setOnDPopupCardListener(this);

            addView(mExternalReadAheadCard);
        }

        private void RotationalInit() {
            mRotationalCard = new SwitchCardView.DSwitchCard();
            mRotationalCard.setTitle(getString(R.string.rotational));
            mRotationalCard.setDescription(getString(R.string.rotational_summary));
            mRotationalCard.setChecked(IO.isRotationalActive());
            mRotationalCard.setOnDSwitchCardListener(this);

            addView(mRotationalCard);
        }

        private void IORandomInit() {
            mIORandomCard = new SwitchCardView.DSwitchCard();
            mIORandomCard.setTitle(getString(R.string.iorandom));
            mIORandomCard.setDescription(getString(R.string.iorandom_summary));
            mIORandomCard.setChecked(IO.isIORandomActive());
            mIORandomCard.setOnDSwitchCardListener(this);

            addView(mIORandomCard);
        }

        private void IOStatsInit() {
            mIOStatsCard = new SwitchCardView.DSwitchCard();
            mIOStatsCard.setTitle(getString(R.string.iostats));
            mIOStatsCard.setDescription(getString(R.string.iostats_summary));
            mIOStatsCard.setChecked(IO.isIOStatsActive());
            mIOStatsCard.setOnDSwitchCardListener(this);

            addView(mIOStatsCard);
        }

        private void IOAffintyInit() {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) list.add(String.valueOf(i));

            mIOAffinityCard = new PopupCardView.DPopupCard(list);
            mIOAffinityCard.setTitle(getString(R.string.ioaffitiny));
            mIOAffinityCard.setDescription(getString(R.string.ioraffinity_summary));
            mIOAffinityCard.setItem(IO.getIOAffinity());
            mIOAffinityCard.setOnDPopupCardListener(this);

            addView(mIOAffinityCard);
        }

        @Override
        public void onItemSelected(PopupCardView.DPopupCard dPopupCard, int position) {
            if (dPopupCard == mInternalSchedulerCard)
                IO.setScheduler(IO.StorageType.INTERNAL, IO.getSchedulers(IO.StorageType.INTERNAL)
                        .get(position), getActivity());
            else if (dPopupCard == mInternalSchedulerCard_SDA)
                IO.setScheduler(IO.StorageType.INTERNAL_SDA, IO.getSchedulers(IO.StorageType.INTERNAL_SDA)
                        .get(position), getActivity());
            else if (dPopupCard == mInternalSchedulerCard_DM0)
                IO.setScheduler(IO.StorageType.INTERNAL_DM0, IO.getSchedulers(IO.StorageType.INTERNAL_DM0)
                        .get(position), getActivity());
            else if (dPopupCard == mExternalSchedulerCard)
                IO.setScheduler(IO.StorageType.EXTERNAL, IO.getSchedulers(IO.StorageType.EXTERNAL)
                        .get(position), getActivity());
            else if (dPopupCard == mInternalReadAheadCard)
                IO.setReadahead(IO.StorageType.INTERNAL, Utils.stringToInt(readheads.get(position)
                        .replace(getString(R.string.kb), "")), getActivity());
            else if (dPopupCard == mInternalReadAheadCard_SDA)
                IO.setReadahead(IO.StorageType.INTERNAL_SDA, Utils.stringToInt(readheads.get(position)
                        .replace(getString(R.string.kb), "")), getActivity());
            else if (dPopupCard == mInternalReadAheadCard_DM0)
                IO.setReadahead(IO.StorageType.INTERNAL_DM0, Utils.stringToInt(readheads.get(position)
                        .replace(getString(R.string.kb), "")), getActivity());
            else if (dPopupCard == mExternalReadAheadCard)
                IO.setReadahead(IO.StorageType.EXTERNAL, Utils.stringToInt(readheads.get(position)
                        .replace(getString(R.string.kb), "")), getActivity());
            else if (dPopupCard == mIOAffinityCard)
                IO.setIOAffinity(position, getActivity());
        }

    @Override
    public void onClick(CardViewItem.DCardView dCardView) {
        if (dCardView == mInternalTunableCard)
            ioFragment.storageType = IO.StorageType.INTERNAL;
        else if (dCardView == mInternalTunableCard_DM0)
            ioFragment.storageType = IO.StorageType.INTERNAL_DM0;
        else if (dCardView == mInternalTunableCard_SDA)
            ioFragment.storageType = IO.StorageType.INTERNAL_SDA;
        else            
            ioFragment.storageType = IO.StorageType.EXTERNAL;
        ioFragment.schedulerPart.reload();
        ioFragment.setCurrentItem(1);
        }

        @Override
        public void onChecked(SwitchCardView.DSwitchCard dSwitchCard, boolean checked) {
            if (dSwitchCard == mRotationalCard)
                IO.activaterotational(checked, getActivity());
            else if (dSwitchCard == mIORandomCard)
                IO.activateIORandom(checked, getActivity());
            else if (dSwitchCard == mIOStatsCard)
                IO.activateIOstats(checked, getActivity());
        }
    }

    public static class SchedulerPart extends PathReaderFragment {

        @Override
        public String getName() {
            if (ioFragment.storageType == IO.StorageType.INTERNAL)
                return IO.getScheduler(IO.StorageType.INTERNAL);
            else if (ioFragment.storageType == IO.StorageType.INTERNAL_DM0)
                return IO.getScheduler(IO.StorageType.INTERNAL_DM0);
            else if (ioFragment.storageType == IO.StorageType.INTERNAL_SDA)
                return IO.getScheduler(IO.StorageType.INTERNAL_SDA);
            else            
                return IO.getScheduler(IO.StorageType.EXTERNAL);
        }

        @Override
        public String getPath() {
            if (ioFragment.storageType == IO.StorageType.INTERNAL)
                return IO_INTERNAL_SCHEDULER_TUNABLE;
            else if (ioFragment.storageType == IO.StorageType.INTERNAL_DM0)
                return IO_INTERNAL_SCHEDULER_TUNABLE_DM0;
            else if (ioFragment.storageType == IO.StorageType.INTERNAL_SDA)
                return IO_INTERNAL_SCHEDULER_TUNABLE_SDA;
            else            
                return IO_EXTERNAL_SCHEDULER_TUNABLE;
        }

        @Override
        public PATH_TYPE getType() {
            return PATH_TYPE.IO;
        }

        @Override
        public String getError(Context context) {
            return context.getString(R.string.not_tunable, IO.getScheduler(ioFragment.storageType == IO.StorageType.INTERNAL ?
                    IO.StorageType.INTERNAL : IO.StorageType.EXTERNAL));
        }

    }

}
