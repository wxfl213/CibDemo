package com.example.cibdemo

import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appcam.android.AppInsight

class SDKInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sdkinfo)

        setupListView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun setupListView() {
        val listView = findViewById<ListView>(R.id.listView)

        // 2. 准备数据
        val dataList = ArrayList<String>()
        dataList.add(App.sharedApp().serverUrl);
        dataList.add(App.sharedApp().token)
        dataList.add(App.sharedApp().configUrl())

        // 3. 设置适配器
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, dataList
        )
        listView.adapter = adapter

        // 设置点击事件
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id -> // 获取被点击的项
                val item = dataList[position]
                if (position == dataList.size - 1) {

                    AppInsight.stopRealTimeReplayWithCompletion(null)

                    finish()
                }
            }
    }
}