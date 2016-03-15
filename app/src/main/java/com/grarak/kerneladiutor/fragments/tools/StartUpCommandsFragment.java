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

package com.grarak.kerneladiutor.fragments.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.DDivider;
import com.grarak.kerneladiutor.elements.cards.CardViewItem;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.database.CommandDB;
import com.grarak.kerneladiutor.utils.root.Control;

import java.util.List;


/**
 * Created by willi on 25.04.15.
 */
public class StartUpCommandsFragment extends RecyclerViewFragment implements CardViewItem.DCardView.OnDCardListener {

    private CardViewItem.DCardView mStartUpCommandsDelete;
    private CardViewItem.DCardView[] mStartUpCommands;

    @Override
    public boolean showApplyOnBoot() {
        return false;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        listcommands();
    }

    public void listcommands () {
        CommandDB commandDB = new CommandDB(getActivity());
        List<CommandDB.CommandItem> commandItems = commandDB.getAllCommands();

        if (commandItems.size() > 0) {
            mStartUpCommandsDelete = new CardViewItem.DCardView();
            mStartUpCommandsDelete.setTitle(getString(R.string.startup_commands_delete));
            mStartUpCommandsDelete.setOnDCardListener(this);

            addView(mStartUpCommandsDelete);

            DDivider mStartUpCommandsListDividerCard = new DDivider();
            mStartUpCommandsListDividerCard.setText(getString(R.string.startup_commands_list));
            mStartUpCommandsListDividerCard.setDescription(getString(R.string.startup_commands_list_delete));
            addView(mStartUpCommandsListDividerCard);

            mStartUpCommands = new CardViewItem.DCardView[commandItems.size()];
            for (int i = 0; i < commandItems.size(); i++) {
                String command = commandItems.get(i).getCommand();
                final String path = commandItems.get(i).getPath();
                mStartUpCommands[i] = new CardViewItem.DCardView();
                mStartUpCommands[i].setDescription(command);
                mStartUpCommands[i].setOnDCardListener(this);

                addView(mStartUpCommands[i]);
            }
        }
        else {
                mStartUpCommandsDelete = new CardViewItem.DCardView();
                mStartUpCommandsDelete.setTitle(getString(R.string.startup_commands_none));

                addView(mStartUpCommandsDelete);
        }
    }

    public void forcerefresh(Context context) {
        view.invalidate();
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    Control.deletespecificcommand(getActivity(), null, null);
                    forcerefresh(getActivity());
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    public void onClick(CardViewItem.DCardView dCardView) {

        if (dCardView == mStartUpCommandsDelete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage(getString(R.string.startup_commands_delete_all)).setPositiveButton(getString(R.string.startup_commands_delete_all_yes), dialogClickListener)
                    .setNegativeButton(getString(R.string.startup_commands_delete_all_no), dialogClickListener).show();
        }
        for (int i = 0; i < mStartUpCommands.length; i++)
        if (dCardView == mStartUpCommands[i]) {
            final StringBuilder command = new StringBuilder(mStartUpCommands[i].getDescription().length());
            command.append(mStartUpCommands[i].getDescription());
            Control.deletespecificcommand(getActivity(), null, command.toString());
            forcerefresh(getActivity());
        }
    }
}
