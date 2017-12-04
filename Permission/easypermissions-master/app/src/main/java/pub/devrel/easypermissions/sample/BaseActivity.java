package pub.devrel.easypermissions.sample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by Kellan on 2017/11/16.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onResult(requestCode, permissions, grantResults, this);
    }
}
