package com.pengfei.tcpdump;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


//TODO 显示文件的存储路径
//TODO 显示捕获时间
//TODO 对于未安装tcpdump的设备进行推送捕获
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boolean isSuccess = SystemManager.upgradeRootPermission(getPackageCodePath());
        Log.e("CommandsHelper",""+isSuccess);

        init();
    }

    private void init() {
        final TextView textView = (TextView) findViewById(R.id.textView1);
        String oldText = textView.getText().toString();
        textView.setText(oldText + "\n\n" + "目标文件： " + CommandsHelper.DEST_FILE);

        findViewById(R.id.start_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final boolean retVal = CommandsHelper.startCapture(MainActivity.this);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "startCapture result = " + retVal, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

        findViewById(R.id.stop_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandsHelper.stopCapture(MainActivity.this);
                findViewById(R.id.start_capture).setEnabled(true);
            }
        });
    }
}
