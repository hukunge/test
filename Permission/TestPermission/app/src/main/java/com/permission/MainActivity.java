package com.permission;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickPermission(View view) {
        PermissionManager.getInstance().requestPermissions(MainActivity.this, new PermissionListener() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "onGranted", Toast.LENGTH_SHORT).show();
                Log.e("kunge.hu", "onGranted");
            }

            @Override
            public void onDenied(List<String> deniedPermission, List<String> shouldShowRationalePermission) {
                Toast.makeText(MainActivity.this, "onDenied", Toast.LENGTH_SHORT).show();
                printLog(deniedPermission);
                printLog(shouldShowRationalePermission);
            }
        });
    }

    private void printLog(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (String s: list){
            sb.append(s + "_________________");
        }
        Log.e("kunge.hu", "onDenied :" + sb.toString());
    }
}
