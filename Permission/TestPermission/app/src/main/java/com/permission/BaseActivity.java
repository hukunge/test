package com.permission;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kellan on 2017/10/11.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
