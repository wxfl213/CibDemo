package com.example.cibdemo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.appcam.android.AppInsight;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {

    private TextView timerText;
    private int seconds = 0;
    private boolean isRunning = true; // 控制计时器是否运行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transction);

        timerText = findViewById(R.id.timerText);

        setupListView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppInsight.startRealTimeReplay();

        startTimer();
    }

    void setupListView() {
        ListView listView = findViewById(R.id.listView);

        // 2. 准备数据
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("开");
        dataList.add("始");
        dataList.add("购");
        dataList.add("买");
        dataList.add("业");
        dataList.add("务");
        dataList.add("结束购买");

        // 3. 设置适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        // 设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取被点击的项
                String item = dataList.get(position);

                if (position == dataList.size() - 1) {
                    isRunning = false;
                    AppInsight.stopRealTimeReplayWithCompletion(null);

                    finish();
                }
            }
        });
    }

    private void startTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = seconds / 60;
                int secs = seconds % 60;

                // 更新计时器文本
                String time = String.format("%02d:%02d", minutes, secs);
                timerText.setText(time);

                // 增加时间
                if (isRunning) {
                    seconds++;
                    handler.postDelayed(this, 1000); // 每隔 1 秒运行
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false; // 停止计时
    }

}