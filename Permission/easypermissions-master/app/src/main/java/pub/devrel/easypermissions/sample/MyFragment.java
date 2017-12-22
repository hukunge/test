package pub.devrel.easypermissions.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.my.PermissionInfo;
import pub.devrel.easypermissions.my.RequestCode;

/**
 * Created by Kellan on 2017/11/16.
 */

public class MyFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_main, container);

        v.findViewById(R.id.button_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPermissions.of(MyFragment.this)
                        .reqCode(RequestCode.CAMERA)
                        .perms(Manifest.permission.CAMERA)
                        .callBack(new PermissionInfo() {
                            @Override
                            public void onGranted(List<String> perms) {
                                Toast.makeText(getContext(), "onGranted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(List<String> perms) {
                                Toast.makeText(getContext(), "onDenied", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return v;
    }
}
