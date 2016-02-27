package com.keshav.mygcm;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GcmReceiver;

/**
 * Created by keshav on 27/2/16.
 */
public class GCMReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent != null) {
      if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
        Bundle extras = intent.getExtras();
        postNotification(new Intent(context, MainActivity.class), context, extras);
      }
    }
  }

  protected static void postNotification(Intent intentAction, Context context, Bundle extras) {
    final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentAction, Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL);
    NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
    bigPictureStyle.setBigContentTitle("GCM Demo Description");
    bigPictureStyle.setSummaryText("Hello Welcome");
    bigPictureStyle.bigPicture(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
    final Notification notification = new NotificationCompat.Builder(context)
        .setStyle(bigPictureStyle).setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Google GCM Demo")
        .setContentText(extras.getString("message"))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .getNotification();

    mNotificationManager.notify(R.string.notification_number, notification);
  }
}
