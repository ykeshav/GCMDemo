package com.keshav.mygcm;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class GCMRegisterService extends Service {
  private GoogleCloudMessaging gcm;
  String gcmToken;
  private SharedPreferences sharedPreference;
  private SharedPreferences.Editor editor;

  public GCMRegisterService() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
    gcm = GoogleCloudMessaging.getInstance(getBaseContext());
    sharedPreference = getSharedPreferences("first_time_login", MODE_MULTI_PROCESS);
    editor = sharedPreference.edit();
    Log.d("GCMDemo", "before sharedPreferences");
    Log.d("GCMDemo", "before sharedPreferences:" + sharedPreference.getBoolean("first_time", true));
    if (sharedPreference.getBoolean("first_time", true)) {
      editor.putBoolean("first_time", false).apply();
      Log.d("GCMDemo", "before Register");
      register();
    }
  }

  private void register() {
    new AsyncTask() {
      protected Object doInBackground(final Object... params) {
        try {
          gcmToken = gcm.register(getString(R.string.google_app_id));
          Log.i("registrationId", gcmToken);
        } catch (IOException e) {
          Log.i("Registration Error", e.getMessage());
        }
        return true;
      }
    }.execute(null, null, null);
  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
