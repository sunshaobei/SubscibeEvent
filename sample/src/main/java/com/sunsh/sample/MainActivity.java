package com.sunsh.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sunsh.subscibeevent.SubscribeEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SubscribeEvent.getInstance().subscribe("event",this);
        startActivity(new Intent(this,Main2Activity.class));
    }

    private void event(String s,Integer i,Boolean b){
        TextView viewById = findViewById(R.id.textview);
         s = s+i+b+".........................";
        viewById.setText(s+s+s);
        viewById.setSelected(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SubscribeEvent.getInstance().removeuObservable("event",this);
    }
}
