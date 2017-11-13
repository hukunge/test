package com.test.pic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.soundcloud.android.pic.Pic;
import com.soundcloud.android.pic.PickDialog;
import com.soundcloud.android.pic.PickInfo;
import com.soundcloud.android.pic.ResultInfo;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String fileName = "capture.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void picTest(View view) {

        PickDialog.showPickDialog(this, new PickInfo() {
            @Override
            public void gallery() {
                Crop.pickImage(MainActivity.this);
            }

            @Override
            public void takePic() {
                File f = new File(SdCardUtils.getPicturePath(App.getInstance()) + fileName);
                Pic.of().file(f).takePic(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //处理拍照逻辑
        File f = new File(SdCardUtils.getPicturePath(App.getInstance()) + fileName);
        Pic.actvityResult(this, f, requestCode, resultCode, data, 0.2f, new ResultInfo<String>() {
            @Override
            public void err(String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void result(String s) {
                Toast.makeText(MainActivity.this, "base64 succ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
