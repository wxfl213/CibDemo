package com.example.cibdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.appcam.android.AppInsight;
import com.appcam.android.AppInsightConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startAppInsight();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("感知测试");
        }

        setupRTStart();
        setupRTEnd();
        setupStart();
        setupEnd();
        setupSDKInfo();
        setupSettingButton();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void startAppInsight() {

        SharedPreferences sharedPreferences = getSharedPreferences("CibAppInsight", Context.MODE_PRIVATE);
        String env = sharedPreferences.getString("env","sit");
        String token = "";
        String serverUrl = "";

        if (env.equals("dev")) {
            serverUrl = "http://139.9.30.165:30002/replayDev";
            token = "1e56879ba4f28cc7304e1d1799e75fc2";
        } else if (env.equals("sit")) {
            serverUrl = "http://139.9.30.165:30002/replaySit";
            token = "3a36a56f40b9a6ee327e01de17c6c90e";
        } else if (env.equals("uat")) {
            serverUrl = "http://139.9.30.165:30002/replayUat";
            token = "76f43f5dcdcc365ad71e369684d5c9b6";
        } else if (env.equals("local")) {
            serverUrl = "http://localhost:8082";
            token = "8a956910bcb2a63c66ee17e1e42e2d77";
        } else if (env.equals("pro")) {
            serverUrl = "https://contextawareness.cib.com.cn";
            token = "47e0d20fc817654ee5fd592c2ddc43df";
        }

        App.sharedApp().serverUrl = serverUrl;
        App.sharedApp().token = token;

        AppInsightConfiguration configuration = new AppInsightConfiguration.
                Builder(token)
                .setServerURL(serverUrl)
                .build();
        AppInsight.startWithConfiguration(configuration);
    }

    void setupRTStart() {
        Button rtStart = findViewById(R.id.rt_start);
        rtStart.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
            startActivity(intent);
        });
    }

    void setupRTEnd() {
        Button rtStart = findViewById(R.id.rt_end);

        rtStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
            startActivity(intent);
            AppInsight.stopRealTimeReplayWithCompletion(null);
        });
    }

    void setupStart() {
        Button rtStart = findViewById(R.id.start);
        rtStart.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
            startActivity(intent);
        });
    }

    void setupEnd() {
        Button rtStart = findViewById(R.id.end);

        rtStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
            startActivity(intent);
            AppInsight.stopRealTimeReplayWithCompletion(null);
        });
    }

    void setupSDKInfo() {
        Button sdk = findViewById(R.id.sdk);

        sdk.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SDKInfoActivity.class);
            startActivity(intent);
        });
    }

    void setupSettingButton() {
        Button button = findViewById(R.id.setting);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }

}