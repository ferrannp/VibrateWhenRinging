package com.fnp.example.vibratewhenringing;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        RelativeLayout switchLayout = (RelativeLayout) v.findViewById(R.id.switch_layout);
        ((TextView) switchLayout.findViewById(R.id.title)).setText("Vibrate when ringing");
        final SwitchCompat switchCompat = (SwitchCompat) switchLayout
                .findViewById(R.id.switchWidget);

        switchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isVibrateWhenRinging = 0;
                try {
                    isVibrateWhenRinging = Settings.System.getInt(getActivity()
                            .getContentResolver(), "vibrate_when_ringing");
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && Settings.System.canWrite(getActivity())
                ))
                {
                    Settings.System.putInt(getActivity().getContentResolver()
                            , "vibrate_when_ringing", isVibrateWhenRinging == 0 ? 1 : 0);
                    switchCompat.setChecked(!switchCompat.isChecked());
                } else {
                    startActivity(requestWriteSettingsIntent());
                }
            }
        });

        return v;
    }

    private Intent requestWriteSettingsIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        }
        return null;
    }
}
