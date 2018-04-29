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

package com.kerneladiutormod.reborn.fragments.information;

import android.os.Bundle;

import com.kerneladiutormod.reborn.R;
import com.kerneladiutormod.reborn.elements.cards.CardViewItem;
import com.kerneladiutormod.reborn.fragments.RecyclerViewFragment;
import com.kerneladiutormod.reborn.utils.kernel.Info;

/**
 * Created by willi on 20.12.14.
 */
public class KernelInformationFragment extends RecyclerViewFragment {

    @Override
    public boolean showApplyOnBoot() {
        return false;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        CardViewItem.DCardView kernelVersionCard = new CardViewItem.DCardView();
        kernelVersionCard.setTitle(getString(R.string.kernel_version));
        kernelVersionCard.setDescription(Info.getKernelVersion());
        addView(kernelVersionCard);


        if (Info.hasPVSLevel() || Info.hasSpeedLevel()) {
            CardViewItem.DCardView binningCard = new CardViewItem.DCardView();
            binningCard.setTitle(getString(R.string.binning_information));
            binningCard.setDescription(Info.getBinningInfo());
            addView(binningCard);
        }

        CardViewItem.DCardView cpuCard = new CardViewItem.DCardView();
        cpuCard.setTitle(getString(R.string.cpu_information));
        cpuCard.setDescription(Info.getCpuInfo());
        addView(cpuCard);

        CardViewItem.DCardView memCard = new CardViewItem.DCardView();
        memCard.setTitle(getString(R.string.memory_information));
        memCard.setDescription(Info.getMemInfo());
        addView(memCard);

    }

}
