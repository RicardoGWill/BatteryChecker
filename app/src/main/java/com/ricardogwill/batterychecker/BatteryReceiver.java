package com.ricardogwill.batterychecker;

import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        TextView statusLabelTV = ((MainActivity)context).findViewById(R.id.statusLabelTV);
        TextView percentageLabelTV = ((MainActivity)context).findViewById(R.id.percentageLabelTV);
        ImageView batteryIV = ((MainActivity)context).findViewById(R.id.batteryIV);

        String actionString = intent.getAction();

        if (actionString != null && actionString.equals(Intent.ACTION_BATTERY_CHANGED)) {
            // Status
            int statusInt = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String messageString = "";

            switch (statusInt) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    messageString = "Full";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    messageString = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    messageString = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    messageString = "Not Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    messageString = "Unknown";
                    break;
                default:
                    messageString = "Unknown";
                    break;
            }

            statusLabelTV.setText(messageString);

            // Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = level * 100 / scale;
            percentageLabelTV.setText(percentage + "%");

            // Image
            Resources resources = context.getResources();

            if (percentage >= 90) {
                batteryIV.setImageDrawable(resources.getDrawable(R.drawable.b100));
            } else if (percentage < 90 && percentage >= 65) {
                batteryIV.setImageDrawable(resources.getDrawable(R.drawable.b75));
            } else if (percentage < 65 && percentage >= 40) {
                batteryIV.setImageDrawable(resources.getDrawable(R.drawable.b50));
            } else if (percentage < 40 && percentage >= 15) {
                batteryIV.setImageDrawable(resources.getDrawable(R.drawable.b25));
            } else {
                batteryIV.setImageDrawable(resources.getDrawable(R.drawable.b0));
            }
        }
    }
}
