package pub.devrel.easypermissions;

import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Callback interface to receive the results of {@code EasyPermissions.requestPermissions()}
 * calls.
 */
public interface PermissionCallbacks extends ActivityCompat.OnRequestPermissionsResultCallback {

    void onPermissionsGranted(int requestCode, List<String> perms);

    void onPermissionsDenied(int requestCode, List<String> perms);

}
