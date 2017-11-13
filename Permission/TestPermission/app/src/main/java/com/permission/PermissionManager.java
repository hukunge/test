package com.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kellan on 2017/10/11.
 */

public class PermissionManager {
    private static final PermissionManager instance = new PermissionManager();
    private String[] permissions = new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] names = new String[]{"打电话", "SD卡存储"};
    private List<String> deniedList = new ArrayList<>();
    private List<String> shouldShowRationale = new ArrayList<>();
    private PermissionListener listener;

    public static PermissionManager getInstance() {
        return instance;
    }

    private PermissionManager() {
    }

    //判断哪些权限未授予
    private List<String> getDeniedPermissions() {
        List<String> pList = new ArrayList<>();
        for (String p : permissions) {
            if (ContextCompat.checkSelfPermission(App.getInstance(), p) != PackageManager.PERMISSION_GRANTED) {
                pList.add(p);
            }
        }
        return pList;
    }

    public void requestPermissions(Activity act, PermissionListener listener) {
        this.listener = listener;

        List<String> pList = getDeniedPermissions();
        if (pList.isEmpty()) {//未授予的权限为空，表示都授予了
            this.listener.onGranted();
        } else {//请求权限方法
            String[] permissions = pList.toArray(new String[pList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(act, permissions, 1);
        }
    }

    public void onRequestPermissionsResult(Activity act, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        deniedList.clear();
        shouldShowRationale.clear();

        for (int i = 0; i < grantResults.length; i++) {
            String p = permissions[i];
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {//权限被禁止了
                //判断是否勾选禁止后不再询问
                //这个方法是在用户拒绝权限后返回true
                boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(act, p);
                if (showRequestPermission) {//权限被拒绝了
//                    Log.e("kunge.hu", p + "-------权限被拒绝了");
//                    Toast.makeText(App.getInstance(), "权限被拒绝了", Toast.LENGTH_SHORT).show();
//                    break;
                    deniedList.add(p);
                } else {//当用户拒绝权限并勾选don't ask again选项后，会一直返回false
//                    Log.e("kunge.hu", p + "-------权限被禁止，且不在提示");
//                    Toast.makeText(App.getInstance(), "权限被禁止，且不在提示", Toast.LENGTH_SHORT).show();
//                    PermissionManager.getInstance().toSettingPage(act);
//                    break;
                    shouldShowRationale.add(p);
                }
            }
        }

        if (listener != null) {
            int deniedSize = deniedList.size();
            int shouldShowRationaleSize = shouldShowRationale.size();
            if (deniedSize > 0 || shouldShowRationaleSize > 0) {
                listener.onDenied(deniedList, shouldShowRationale);
            } else {
                listener.onGranted();
            }
        }
    }

    public void toSettingPage(Activity act) {
        Uri packageURI = Uri.parse("package:" + App.getInstance().getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        act.startActivity(intent);
    }
}
