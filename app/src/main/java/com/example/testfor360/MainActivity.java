package com.example.testfor360;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.testfor360.Managers.FloatViewManager;
import com.example.testfor360.Service.MyService;

public class MainActivity extends AppCompatActivity {

    Button btn_startService;
    Button btn_closeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_startService = (Button) findViewById(R.id.btn_startService);

        Log.e("TestFor360","###00###");

        btn_startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TestFor360","###11###");
                Intent intent = new Intent(MainActivity.this, MyService.class);
                Log.e("TestFor360","###22###");
                startService(intent);
                Log.e("TestFor360","###33###");
                finish();
                Log.e("TestFor360","###44###");
            }
        });


    }


}
