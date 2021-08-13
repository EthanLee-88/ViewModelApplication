package com.ethan.viewmodelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ethan.viewmodelapplication.mvvm.DataViewModel;

public class MainActivity extends AppCompatActivity {
    private Button getData;
    private DataViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData = findViewById(R.id.id_get_data);
        init();
    }
    private void init(){
        // 工厂设计模式创建 ViewModel对象
        mViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        // 设置数据回调
        mViewModel.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(), "mag = " + s, Toast.LENGTH_SHORT).show();
            }
        });
        getData.setOnClickListener((View view) -> {
            // 网络请求
            mViewModel.getData("https://www.baidu.com");
        });
    }
}