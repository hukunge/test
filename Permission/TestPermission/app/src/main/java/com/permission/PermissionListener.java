package com.permission;

import java.util.List;

/**
 * Created by Kellan on 2017/10/11.
 */

public interface PermissionListener {
    void onGranted();//所有权限都已经授权

    void onDenied(List<String> deniedPermission, List<String> shouldShowRationalePermission);//被拒绝的权限

//    void onShouldShowRationale(List<String> deniedPermission); //拒绝，且勾选不在提示的权限
}
