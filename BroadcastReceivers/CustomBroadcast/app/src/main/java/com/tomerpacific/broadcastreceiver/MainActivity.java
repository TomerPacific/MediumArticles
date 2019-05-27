package com.tomerpacific.broadcastreceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button sendBroadcastBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendBroadcastBtn = findViewById(R.id.sendBroadcastBtn);
        sendBroadcastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.broadcast.MY_NOTIFICATION");
                intent.putExtra("data","Hello World!");
                sendBroadcast(intent);
            }
        });
    }
}
