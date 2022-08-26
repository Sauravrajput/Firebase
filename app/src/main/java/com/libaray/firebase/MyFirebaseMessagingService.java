package com.libaray.firebase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;



import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by indianrenter on 12/4/17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getData().get("title");
        Log.d(TAG, "title" + remoteMessage.getData().get("title"));
        showWishesNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("type"));
        Log.d(TAG, "notification" + remoteMessage.getData().toString());
        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.putExtra("startSessionTime1", true);
        intent1.putExtra("startSessionTime2", "stringTest");
        intent1.putExtra("startSessionTime3", "Android");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        //String body = remoteMessage.getData().get("body");
/*
        try {
            JSONObject responseJson = new JSONObject(body);
            String chatToken=responseJson.getString("chatToken");
            String sessionId=responseJson.getString("sessionId");
            String chatSessionId=responseJson.getString("chatSessionId");
            String title2=responseJson.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

//Create the intent that’ll fire when the user taps the notification//
/*
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.drawable.iwill_icon);
        mBuilder.setContentTitle("IWill ");
        mBuilder.setContentText("Sesion start 1");
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());*/
    }

    private void showWishesNotification(String message, String type) {

        SharedPreferences preferences = getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        int notification_num = preferences.getInt("notification_num", 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
     //  Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.notification);  //Here is FILE_NAME is the name of file that you want to play

        Intent i = null;
        i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


/*
        if (!StringUtils.isNullOrEmpty(AppSharedPreference.getString(type, "", this)))
        {
            if (type.equalsIgnoreCase("3")) {

                i = new Intent(getApplicationContext(), FeedbackActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                i = new Intent(getApplicationContext(), JourneyActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

        }
        else {
            i = new Intent(getApplicationContext(), JourneyActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
*/



        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setSound(alarmSound)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId("1") // set channel id

//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.noti))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//        {
////            builder.setColor(context.getResources().getColor(R.color.red));
//            builder.setSmallIcon(R.drawable.noti);
//            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.noti));
//        }

        NotificationManager manage = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manage.notify(notification_num, builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("1", getString(R.string.app_name), importance);
            mChannel.setDescription(message);
            mChannel.enableLights(true);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            mChannel.setSound(alarmSound, attributes); // This is IMPORTANT

            mChannel.setLightColor(ContextCompat.getColor(getApplicationContext(), R.color
                    .purple_200));
            manage.createNotificationChannel(mChannel);
        }

//        NotificationManagerCompat manage = (NotificationManagerCompat) getSystemService(NOTIFICATION_SERVICE);
//        manage.notify(notification_num,builder.build());

        SharedPreferences.Editor editor = preferences.edit();
        notification_num++;
        editor.putInt("notification_num", notification_num);
        editor.commit();
    }

}
