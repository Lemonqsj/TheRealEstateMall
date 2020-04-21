package com.lemon.the_real_estate_mall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lemon.the_real_estate_mall.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.i("在main中测试");


    }
}
