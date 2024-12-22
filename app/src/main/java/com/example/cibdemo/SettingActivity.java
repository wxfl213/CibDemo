package com.example.cibdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        setupListView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void setupListView() {
        ListView listView = findViewById(R.id.listView);

        // 2. 准备数据
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("切换到Dev环境");
        dataList.add("切换到Sit环境");
        dataList.add("切换到Uat环境");
        dataList.add("切换到Pro环境");

        // 3. 设置适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        // 设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getSharedPreferences("CibAppInsight", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (position == 0) {
                    editor.putString("env","dev");
                } else if (position == 1) {
                    editor.putString("env","sit");
                } else if (position == 2) {
                    editor.putString("env","uat");
                } else if (position == 3) {
                    editor.putString("env","pro");
                }
                editor.apply();
                finishAffinity();
            }
        });
    }

}