package com.grarak.kerneladiutor.fragments.information;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.cards.CardViewItem;
import com.grarak.kerneladiutor.fragments.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.Constants;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.kernel.CPU;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FrequencyTableFragment extends RecyclerViewFragment implements Constants {

    @Override
    protected boolean allowPullToRefresh() {
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

        for (int i = 0; i < CPU.getCoreCount(); i++) {
            Log.d(TAG, "Reading core "+i);
            // <Freq, time>
            double total_time = 0;
            Map<String, String> freq_use_list = new HashMap<>();
            try {
                FileInputStream fileInputStream = new FileInputStream(Utils.getsysfspath(CPU_TIME_IN_STATE_ARRAY, i));
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader buffreader = new BufferedReader(inputStreamReader);
                if (buffreader != null ) {
                    String line;
                    String[] linePieces;
                    while ((line = buffreader.readLine()) != null) {
                        Log.d(TAG, "Line = "+line);
                        linePieces = line.split(" ");
                        total_time = total_time + Integer.parseInt(linePieces[1]);
                        freq_use_list.put(linePieces[0], linePieces[1]);
                    }
                    fileInputStream.close();
                    inputStreamReader.close();
                    buffreader.close();
                }
            } catch (Exception ex) {
                // I don't really care about this exception...
                Log.w(TAG, "Yes you do! ");
                ex.printStackTrace();
            }
            List<Integer> allfreqs = CPU.getFreqs();

            String unused_states = "";

            LinearLayout uiStatesView = new LinearLayout(getActivity());
            uiStatesView.setOrientation(LinearLayout.VERTICAL);
            CardViewItem.DCardView frequencyCard = new CardViewItem.DCardView();
            frequencyCard.setTitle("Core: " + i + " - Time in States.  (Online: " + getDurationBreakdown((long)total_time * 10) + ")");
            frequencyCard.setView(uiStatesView);
            frequencyCard.setFullSpan(true);
            for (int x = 0; x < freq_use_list.size(); x++) {
                double freq_time = (double)Utils.stringToInt(freq_use_list.get(Integer.toString(allfreqs.get(x))));
                double pct = Math.round(freq_time / total_time * 100);
                //Limit the freqs shown to only anything with at least 1% use
                if (pct >= 1) {
                    LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity())
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
                    durText.setText(getDurationBreakdown((Utils.stringToLong(freq_use_list.get(Integer.toString(allfreqs.get(x)))) * 10)));
                    bar.setProgress((int)pct);

                    uiStatesView.addView(layout);
                } else {
                    unused_states = unused_states + (allfreqs.get(x)/ 1000 + "MHZ, ");
                }
            }
            addView(frequencyCard);
            if (!unused_states.isEmpty()) {
                CardViewItem.DCardView mUnUsedStatesCard = new CardViewItem.DCardView();
                mUnUsedStatesCard.setTitle("Core: " + i + " Unused States: (<1%)");
                mUnUsedStatesCard.setDescription(unused_states.substring(0, unused_states.length()-2));
                addView(mUnUsedStatesCard);
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
        if(millis < 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        if (days > 0 ) {
            sb.append(days);
            sb.append("D ");
        }
        if (hours > 0) {
            sb.append(hours);
            sb.append("H ");
        }
        if (minutes > 0 ) {
            sb.append(minutes);
            sb.append("M ");
        }
        if (seconds > 0) {
            sb.append(seconds);
            sb.append("S ");
        }

        return(sb.toString());
    }

}
