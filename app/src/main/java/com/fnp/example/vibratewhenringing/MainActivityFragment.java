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

    SwitchCompat mSwitchCompat = null;
    boolean mIsVibrateWhenRinging = false;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        RelativeLayout switchLayout = (RelativeLayout) v.findViewById(R.id.switch_layout);
        ((TextView) switchLayout.findViewById(R.id.title)).setText("Vibrate when ringing");
        mSwitchCompat = (SwitchCompat) switchLayout.findViewById(R.id.switchWidget);

        switchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsVibrateWhenRinging = isVibrateWhenRinging();

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && Settings.System.canWrite(getActivity())
                ))
                {
                    Settings.System.putInt(getActivity().getContentResolver()
                            , "vibrate_when_ringing", mIsVibrateWhenRinging ? 0 : 1);
                    mSwitchCompat.setChecked(isVibrateWhenRinging());
                } else {
                    startActivity(requestWriteSettingsIntent());
                }
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwitchCompat.setChecked(isVibrateWhenRinging());
    }

    private boolean isVibrateWhenRinging() {
        try {
            mIsVibrateWhenRinging = Settings.System.getInt(getActivity()
                    .getContentResolver(), "vibrate_when_ringing") == 1;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return mIsVibrateWhenRinging;
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
