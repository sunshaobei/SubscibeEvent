package com.sunsh.subscibeevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public void event(View view) {
        SubscribeEvent.getInstance().exposeEvent("event","text",333);
    }
}
