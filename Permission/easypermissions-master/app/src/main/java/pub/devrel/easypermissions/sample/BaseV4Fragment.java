package pub.devrel.easypermissions.sample;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/12/22.
 */

public class BaseV4Fragment extends Fragment{

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onResult(requestCode, permissions, grantResults);
    }
}
