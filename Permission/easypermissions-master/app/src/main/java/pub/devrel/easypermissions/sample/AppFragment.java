package pub.devrel.easypermissions.sample;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.my.PermissionInfo;

/**
 * Created by Administrator on 2017/12/22.
 */

public class AppFragment extends BaseAppFragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_app, container);

        v.findViewById(R.id.button_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPermissions.of(AppFragment.this)
                        .reqCode(114)
                        .perms(Manifest.permission.CAMERA)
                        .callBack(new PermissionInfo() {
                            @Override
                            public void onGranted(List<String> perms) {
                                Toast.makeText(getActivity(), "onGranted", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(List<String> perms) {
                                Toast.makeText(getActivity(), "onDenied", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return v;
    }
}
