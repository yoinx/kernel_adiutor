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
import com.kerneladiutormod.reborn.elements.cards.SeekBarCardView;
import com.kerneladiutormod.reborn.fragments.RecyclerViewFragment;
import com.kerneladiutormod.reborn.utils.kernel.CoreControl;


import java.util.ArrayList;
import java.util.List;


public class CoreControlFragment extends RecyclerViewFragment implements
        SeekBarCardView.DSeekBarCard.OnDSeekBarCardListener {
    private SeekBarCardView.DSeekBarCard mMinLittleCard;
    private SeekBarCardView.DSeekBarCard mMaxLittleCard;
    private SeekBarCardView.DSeekBarCard mMinBigCard;
    private SeekBarCardView.DSeekBarCard mMaxBigCard;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        if (CoreControl.hasMinLittle()) MinLittleInit();
        if (CoreControl.hasMaxLittle()) MaxLittleInit();
        if (CoreControl.hasMinBig()) MinBigInit();
        if (CoreControl.hasMaxBig()) MaxBigInit();
    }
   
    private void MinLittleInit() {
     if (CoreControl.hasMinLittle()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 5; i++) list.add(String.valueOf(i));

            mMinLittleCard = new SeekBarCardView.DSeekBarCard(list);
            mMinLittleCard.setTitle(getString(R.string.minlittle));
            mMinLittleCard.setDescription(getString(R.string.minlittle_summary));
            mMinLittleCard.setProgress(CoreControl.getMinLittle());
            mMinLittleCard.setOnDSeekBarCardListener(this);

            addView(mMinLittleCard);
        }
    }

    private void MaxLittleInit() {
     if (CoreControl.hasMaxLittle()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 5; i++) list.add(String.valueOf(i));

            mMaxLittleCard = new SeekBarCardView.DSeekBarCard(list);
            mMaxLittleCard.setTitle(getString(R.string.maxlittle));
            mMaxLittleCard.setDescription(getString(R.string.maxlittle_summary));
            mMaxLittleCard.setProgress(CoreControl.getMaxLittle());
            mMaxLittleCard.setOnDSeekBarCardListener(this);

            addView(mMaxLittleCard);
        }

    }

    private void MinBigInit() {
     if (CoreControl.hasMinBig()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 5; i++) list.add(String.valueOf(i));

            mMinBigCard = new SeekBarCardView.DSeekBarCard(list);
            mMinBigCard.setTitle(getString(R.string.minbig));
            mMinBigCard.setDescription(getString(R.string.minbig_summary));
            mMinBigCard.setProgress(CoreControl.getMinBig());
            mMinBigCard.setOnDSeekBarCardListener(this);

            addView(mMinBigCard);
        }
    }

    private void MaxBigInit() {
     if (CoreControl.hasMaxBig()) {
            List<String> list = new ArrayList<>();
            for (int i = 1; i < 5; i++) list.add(String.valueOf(i));

            mMaxBigCard = new SeekBarCardView.DSeekBarCard(list);
            mMaxBigCard.setTitle(getString(R.string.maxbig));
            mMaxBigCard.setDescription(getString(R.string.maxbig_summary));
            mMaxBigCard.setProgress(CoreControl.getMaxBig());
            mMaxBigCard.setOnDSeekBarCardListener(this);

            addView(mMaxBigCard);
        }

    }

    @Override
    public void onChanged(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
    }

    @Override
    public void onStop(SeekBarCardView.DSeekBarCard dSeekBarCard, int position) {
         if (dSeekBarCard == mMinLittleCard)
            CoreControl.setMinLittle(position + 1, getActivity());
         else if (dSeekBarCard == mMaxLittleCard)
            CoreControl.setMaxLittle(position + 1, getActivity());
         else if (dSeekBarCard == mMinBigCard)
            CoreControl.setMaxLittle(position + 1, getActivity());
         else if (dSeekBarCard == mMaxBigCard)
            CoreControl.setMaxLittle(position + 1, getActivity());
}
}
