package com.sunsh.subscibeevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this,Main2Activity.class));
        SubscribeEvent.getInstance().subscribe("event", this);
    }

    private void event(String str, Integer i) {
        TextView viewById = findViewById(R.id.textview);
        viewById.setText(str + i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SubscribeEvent.getInstance().removeuObservable("event",this);
    }
}
