package com.example.notify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * Created by congnguyen on 5/24/21.
 */
public class MyReceiver extends BroadcastReceiver {
    final String CHANNEL_ID = "101";
    int x = 12345;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra("myAction") != null &&
                intent.getStringExtra("myAction").equals("mDoNotify")
                && intent.getStringExtra("Title")!=null
                && intent.getStringExtra("Description")!=null ){
            Log.e("Rev","revvvvvvv");
            Log.e("test: ",intent.getStringExtra("Title"));
            Log.e("des trong receiver",intent.getStringExtra("Description"));

            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel1 = new NotificationChannel(
                        CHANNEL_ID,
                        "Channel 1"
                        ,
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel1.setDescription("This is Channel 1");
                manager.createNotificationChannel(channel1);
            }

            // the em nhe, do duoc goi la singleton
            StateManager.NotificationData notificationData = StateManager.getInstance().notificationData;
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context,CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(notificationData.title)
                            .setContentText(notificationData.content)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true);
            Intent i = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            i,
                            PendingIntent.FLAG_ONE_SHOT
                    );
            builder.setContentIntent(pendingIntent);
            manager.notify(x++, builder.build());
        }
    }
}
