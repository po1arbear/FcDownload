package com.fcbox.lib.fcdownload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fcbox.lib.download.DownloadListener;
import com.fcbox.lib.download.DownloadProgress;
import com.fcbox.lib.download.FcDownload;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    TextView tvProgress;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvProgress = findViewById(R.id.tv_progress);
        TextView btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, 955);
                    } else {
                        download();
                    }
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 955) {
            if (grantResults[0] == 0) {
                download();
            }
        }

    }

    private void download() {
//        String url = "http://appmgr-1251779293.file.myqcloud.com/patch/201910/2k28zi9zfntpatch_signed_7zip-test-2.apk";
        String url = "https://a0aca9631669b3caef247b2ce1c89aae.dd.cdntips.com/imtt.dd.qq.com/16891/apk/2E8BDD7686474A7BC4A51ADC3667CABF.apk?mkey=5dc4d8ae3baf00de&f=0ce9&fsname=com.tencent.mm_7.0.8_1540.apk&csr=1bbd&cip=59.175.38.43&proto=https";
        String apkName = "fc_update1.apk";
        String apkPath = getCacheDir() + File.separator + "patch";

        FcDownload.getInstance(this).url(url).apkName(apkName).apkPath(apkPath)
                .start(new DownloadListener() {
                    @Override
                    public void onProgress(DownloadProgress progress) {
                        tvProgress.setText(String.format("%s%%", String.valueOf(progress.getProgress())));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted(String filePath) {

                    }


                });

    }
}
