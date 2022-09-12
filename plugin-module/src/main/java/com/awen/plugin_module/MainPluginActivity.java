package com.awen.plugin_module;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MainPluginActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("awen-plugin", "插件onCreate");
        try{
            setContentView(R.layout.activity_main_plugin);
        }catch (Exception e){
            e.printStackTrace();
            Log.d("awen-plugin", "异常");
        }
        setOnclick();
    }

    private void setOnclick() {
        Log.d("awen-plugin", "setOnclick");
        View show =  findViewById(R.id.btn_show);
        View start = findViewById(R.id.btn_start);
        View service = findViewById(R.id.btn_service);
        View btn_register = findViewById(R.id.btn_register);
        View btn_send_receiver = findViewById(R.id.btn_send_receiver);
        show.setOnClickListener(view -> {
            if (plugActivity != null) {
                Log.d("awen-plugin", "插件show");
                Toast.makeText(plugActivity, "哈哈", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainPluginActivity.this, "哈哈", Toast.LENGTH_SHORT).show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plugActivity != null) {
                    Log.d("awen-plugin", "插件start");
                    startActivity(new Intent(plugActivity, TopActivity.class));
                } else {
                    startActivity(new Intent(MainPluginActivity.this, TopActivity.class));
                }
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plugActivity != null) {
                    Log.d("awen-plugin", "插件service");
                    startService(new Intent(plugActivity, AwenService.class));
                } else {
                    startService(new Intent(MainPluginActivity.this, AwenService.class));
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("awen-plugin", "注册广播");
                AwenBroadcastReceiver broadCastReceiver = new AwenBroadcastReceiver();
                IntentFilter intentFilter = new IntentFilter("com.awen.pluginmodule.action");
                registerReceiver(broadCastReceiver,intentFilter);
            }
        });

        btn_send_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("awen-plugin", "发送广播");
                Intent intent = new Intent();
                intent.setAction("com.awen.pluginmodule.action");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
