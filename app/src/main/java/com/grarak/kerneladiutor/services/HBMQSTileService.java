package com.grarak.kerneladiutor.services;

import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.kernel.Screen;

public class HBMQSTileService extends TileService {

    @Override
    public void onTileAdded() {
        tileHBMUpdate(getQsTile());
    }

    @Override
    public void onTileRemoved() {
    }

    @Override
    public void onClick() {
        tileHBMToggle(getQsTile());
    }

    @Override
    public void onStartListening () {
        tileHBMUpdate(getQsTile());
    }

    @Override
    public void onStopListening () {

    }

    private void tileHBMUpdate (Tile mTile) {
        Icon icon =  Icon.createWithResource(getApplicationContext(), R.drawable.ic_high_brightness_off);
        if (Screen.hasScreenHBM()) {
            Log.i(Constants.TAG + ": " + getClass().getSimpleName(), "Toggling High Brightness Mode");
            if (Screen.isScreenHBMActive()) {
                Log.i(Constants.TAG + ": " + getClass().getSimpleName(), "HBM on, update tile");
                icon = Icon.createWithResource(getApplicationContext(), R.drawable.ic_high_brightness_on);
                if (mTile.getState() == Tile.STATE_INACTIVE) {
                    mTile.setState(Tile.STATE_ACTIVE);
                }
            } else if (!Screen.isScreenHBMActive()) {
                Log.i(Constants.TAG + ": " + getClass().getSimpleName(), "HBM off, update tile");
                icon = Icon.createWithResource(getApplicationContext(), R.drawable.ic_high_brightness_off);
                if (mTile.getState() == Tile.STATE_ACTIVE) {
                    mTile.setState(Tile.STATE_INACTIVE);
                }
            }
        }
        getQsTile().setIcon(icon);
        getQsTile().updateTile();
    }

    private void tileHBMToggle (Tile mTile) {
        if (Screen.hasScreenHBM()) {
            Log.i(Constants.TAG + ": " + getClass().getSimpleName(), "Toggling High Brightness Mode");
            if (Screen.isScreenHBMActive() && mTile.getState() == Tile.STATE_ACTIVE) {
                Screen.activateScreenHBM(false, getApplicationContext(), "Manual");
            } else if (!Screen.isScreenHBMActive() && mTile.getState() == Tile.STATE_INACTIVE) {
                Screen.activateScreenHBM(true, getApplicationContext(), "Manual");
            }
        }
        try{
            // Pause momentarily for sysfs changes
            // This should be done differently, but this will work for now.
            Thread.sleep(100);
        }
        catch(InterruptedException e){

        }
        tileHBMUpdate(mTile);
    }
}