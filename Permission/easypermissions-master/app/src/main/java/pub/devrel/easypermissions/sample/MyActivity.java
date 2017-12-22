package pub.devrel.easypermissions.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.my.PermissionInfo;
import pub.devrel.easypermissions.my.RequestCode;

/**
 * Created by Kellan on 2017/11/14.
 */

public class MyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void clickCamera(View view) {

        EasyPermissions.of(this)
                .reqCode(RequestCode.CAMERA)
                .perms(Manifest.permission.CAMERA)
                .callBack(new PermissionInfo() {
                    @Override
                    public void onGranted(List<String> perms) {
                        StringBuilder sb = new StringBuilder();
                        for (String per : perms){
                            sb.append(per + " ");
                        }

                        Toast.makeText(MyActivity.this, "onGranted " + sb.toString().trim(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> perms) {
                        StringBuilder sb = new StringBuilder();
                        for (String per : perms){
                            sb.append(per + " ");
                        }

                        Toast.makeText(MyActivity.this, "onDenied " + sb.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickLocContacts(View view) {
        String[] LOCATION_AND_CONTACTS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};

        EasyPermissions.of(this)
                .reqCode(RequestCode.All)
                .perms(LOCATION_AND_CONTACTS)
                .callBack(new PermissionInfo() {
                    @Override
                    public void onGranted(List<String> perms) {
                        StringBuilder sb = new StringBuilder();
                        for (String per : perms){
                            sb.append(per + " ");
                        }

                        Toast.makeText(MyActivity.this, "onGranted " + sb.toString().trim(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> perms) {
                        StringBuilder sb = new StringBuilder();
                        for (String per : perms){
                            sb.append(per + " ");
                        }

                        Toast.makeText(MyActivity.this, "onDenied " + sb.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
