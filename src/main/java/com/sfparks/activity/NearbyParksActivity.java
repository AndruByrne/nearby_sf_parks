package com.sfparks.activity;

import android.app.Activity;
import android.os.Bundle;
import com.sfparks.R;

public class NearbyParksActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.parks_list_activity);
  }
}
