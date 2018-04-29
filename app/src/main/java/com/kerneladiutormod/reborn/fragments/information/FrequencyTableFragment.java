package com.kerneladiutormod.reborn.fragments.information;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kerneladiutormod.reborn.R;
import com.kerneladiutormod.reborn.elements.cards.CardViewItem;
import com.kerneladiutormod.reborn.fragments.RecyclerViewFragment;
import com.kerneladiutormod.reborn.utils.Constants;
import com.kerneladiutormod.reborn.utils.Utils;
import com.kerneladiutormod.reborn.utils.kernel.CPU;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
This rewrite is re-using code that Grarak had originally used in his fragment. Credits go to the original source.

 */
public class FrequencyTableFragment extends RecyclerViewFragment implements Constants {

    @Override
    protected boolean pullToRefreshIsEnabled() {
        return true;
    }

    @Override
    public void refreshView() {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                removeAllViews();
            }

            @Override
            protected Void doInBackground(Void... params) {
                generateView();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                refreshLayout.setRefreshing(false);
            }
        }.execute();
    }

    @Override
    public int getSpan() {
        return Utils.getScreenOrientation(getActivity()) == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;
    }

    @Override
    public boolean showApplyOnBoot() {
        return false;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        generateView();
    }

    private void generateView() {

        CardViewItem.DCardView muptimeCard = new CardViewItem.DCardView();
        muptimeCard.setTitle("System Times:");
        muptimeCard.setDescription(
        "Uptime: " + getDurationBreakdown(SystemClock.elapsedRealtime()) +
        "\nAwake Time: " + getDurationBreakdown(SystemClock.uptimeMillis()) +
        "\nDeep Sleep: " + getDurationBreakdown(SystemClock.elapsedRealtime() - SystemClock.uptimeMillis())
        );
        addView(muptimeCard);
        int wasoffline = 0;
        for (int i = 0; i < CPU.getCoreCount(); i++) {
            if (!CPU.isCoreOnline(i)) {
                wasoffline = 1;
                CPU.activateCore(i, true, getContext());
            }
            // <Freq, time>
            int total_time = 0;
            Map<Integer, Integer> freq_use_list = new HashMap<>();
            StringBuilder unusedStates = new StringBuilder();

            try {
                FileInputStream fileInputStream = new FileInputStream(Utils.getsysfspath(CPU_TIME_IN_STATE_ARRAY, i));
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader buffreader = new BufferedReader(inputStreamReader);
                if (buffreader != null ) {
                    String line;
                    String[] linePieces;
                    while ((line = buffreader.readLine()) != null) {
                        linePieces = line.split(" ");
                        total_time = total_time + Integer.parseInt(linePieces[1]);
                        freq_use_list.put(Integer.parseInt(linePieces[0]), Integer.parseInt(linePieces[1]));
                    }
                    inputStreamReader.close();
                    buffreader.close();
                }
            } catch (Exception ex) {
                Log.w(TAG, "No Time In State Stats found for core: " + i);
                ex.printStackTrace();
                // No reason to continue to card generation if there weren't any stats. Let's check the next core.
                continue;
            }

            List<Integer> allfreqs = CPU.getFreqs(i);
            LinearLayout uiStatesView = new LinearLayout(getActivity());
            uiStatesView.setOrientation(LinearLayout.VERTICAL);
            CardViewItem.DCardView frequencyCard = new CardViewItem.DCardView();
            frequencyCard.setTitle("Core: " + i + " - Time in States.  (Online: " + getDurationBreakdown(total_time * 10) + ")");
            frequencyCard.setView(uiStatesView);
            frequencyCard.setFullSpan(true);
            for (int x = 0; x < freq_use_list.size(); x++) {
                if(allfreqs.size() < x || allfreqs.get(x) == null){
                    continue;
                }

                Integer time = freq_use_list.get(allfreqs.get(x));
                if(time == null){
                    continue;
                }
                int freq_time = time;
                int pct = total_time > 0 ? (freq_time * 100) / total_time : 0;
                //Limit the freqs shown to only anything with at least 1% use
                if (pct >= 1) {
                    FrameLayout layout = (FrameLayout) LayoutInflater.from(getActivity())
                            .inflate(R.layout.state_row, uiStatesView, false);

                    // map UI elements to objects
                    TextView freqText = (TextView) layout.findViewById(R.id.ui_freq_text);
                    TextView durText = (TextView) layout.findViewById(R.id.ui_duration_text);
                    TextView perText = (TextView) layout.findViewById(R.id.ui_percentage_text);
                    ProgressBar bar = (ProgressBar) layout.findViewById(R.id.ui_bar);

                    // modify the row
                    freqText.setText(allfreqs.get(x) / 1000 + "Mhz");
                    perText.setText(pct + "%");
                    // Multiple the time_in_state time value by 10 as it is stored in UserTime Units (10ms)
                    durText.setText(getDurationBreakdown((freq_use_list.get(allfreqs.get(x))) * 10));
                    bar.setProgress(pct);

                    uiStatesView.addView(layout);
                } else {
                    if(unusedStates.length() > 0){
                        unusedStates.append(", ");
                    }
                    unusedStates.append(allfreqs.get(x)/ 1000).append("MHZ");
                }
            }
            addView(frequencyCard);
            if (unusedStates.length() > 0) {
                CardViewItem.DCardView mUnUsedStatesCard = new CardViewItem.DCardView();
                mUnUsedStatesCard.setTitle("Core: " + i + " Unused States: (<1%)");
                mUnUsedStatesCard.setDescription(unusedStates.toString());
                addView(mUnUsedStatesCard);
            }

            if (wasoffline == 1) {
                CPU.activateCore(i, false, getContext());
                wasoffline = 0;
            }
        }

    }

    /**
     * Convert a millisecond duration to a string format
     *
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     *
     * Function modified from answer here: http://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java
     */
    public static String getDurationBreakdown(long millis)
    {
        StringBuilder sb = new StringBuilder(64);
        if(millis <= 0)
        {
            sb.append("00m00s");
            return sb.toString();
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        if (days > 0) {
            sb.append(days);
            sb.append("d");
        }
        if (hours > 0) {
            sb.append(hours);
            sb.append("h");
        }
        sb.append(String.format("%02d", minutes));
        sb.append("m");
        sb.append(String.format("%02d", seconds));
        sb.append("s");
        return sb.toString();
    }

}
