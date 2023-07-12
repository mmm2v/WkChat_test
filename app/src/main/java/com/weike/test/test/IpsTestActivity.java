package com.weike.test.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weike.test.R;

import java.util.ArrayList;

public class IpsTestActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_test);

        Intent intent = getIntent();
        ArrayList<String> ips = intent.getStringArrayListExtra("ips");

        LinearLayout linearLayout = findViewById(R.id.ips);
        for (int i = 0; i< ips.size(); i++) {
            TextView tv1 = new TextView(this);
            tv1.setText("ip" +  i);
            tv1.setTextSize(20);
            TextView tv2 = new TextView(this);
            tv2.setText(ips.get(i));
            linearLayout.addView(tv1);
            linearLayout.addView(tv2);
        }

    }
}