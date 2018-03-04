package com.shouyi.xue.bussearch;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.click_search_bus_point_info);
        Button button2 = findViewById(R.id.click_search_bus_line);
        Button button3 = findViewById(R.id.click_search_bus_change);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ShowDetailActivity.class).putExtra(ShowDetailActivity.TYPE_KEY,0));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ShowDetailActivity.class).putExtra(ShowDetailActivity.TYPE_KEY,1));

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ShowDetailActivity.class).putExtra(ShowDetailActivity.TYPE_KEY,2));

            }
        });
    }

}
