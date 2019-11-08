package com.fcbox.lib.download;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * Author:xz
 * Date:2019/11/7 16:06
 * Desc: 通用下载类，下载完成后默认检查MD5，可以设置isCheckMd5(false)跳过检查
 */
public class FcDownload implements IFcDownload {
    private String mDownloadUrl;
    private String mApkName;
    private String mApkPath;
    private String mMd5;
    private DownloadListener mDownloadListener;
    private boolean mIsCheckMd5 = true;
    private BaseDownloadTask mBaseDownloadTask;

    private static final String TAG = "FcDownload";

    private static final class HolderClass {
        private static final FcDownload INSTANCE = new FcDownload();
    }

    public static FcDownload getInstance(Context context) {
        FileDownloader.setup(context);
        return HolderClass.INSTANCE;
    }

    @Override
    public void start() {
        start(mDownloadListener);
    }

    @Override
    public void start(DownloadListener listener) {
        this.mDownloadListener = listener;
        start(mDownloadUrl, mApkName, mApkPath);
    }

    @Override
    public void start(String downloadUrl, String apkName, String apkPath) {
        if (canStart()) {
            startInternal(downloadUrl, apkName, apkPath);
        }
    }


    @Override
    public void stop() {
        if (mBaseDownloadTask != null) {
            mBaseDownloadTask.pause();
        }
    }

    public FcDownload url(String url) {
        this.mDownloadUrl = url;
        return this;
    }

    public FcDownload apkName(String apkName) {
        this.mApkName = apkName;
        return this;
    }

    public FcDownload apkPath(String apkPath) {
        this.mApkPath = apkPath;
        return this;
    }

    public FcDownload md5(String md5) {
        this.mMd5 = md5;
        return this;
    }

    public FcDownload isCheckMd5(boolean isCheckMd5) {
        this.mIsCheckMd5 = isCheckMd5;
        return this;
    }


    private boolean canStart() {
        if (TextUtils.isEmpty(mDownloadUrl)) {
            Log.w(TAG, "download url is empty");
            return false;
        }

        if (TextUtils.isEmpty(mApkPath)) {
            Log.w(TAG, "apk path is empty");
            return false;
        }

        if (TextUtils.isEmpty(mApkName)) {
            Log.w(TAG, "apk name is empty");
            return false;
        }


        return true;
    }


    private void startInternal(String downloadUrl, String apkName, String mApkPath) {
        // apk路径
        final String filePath = mApkPath + File.separator + apkName;
        mBaseDownloadTask = FileDownloader.getImpl().create(downloadUrl).
                setPath(filePath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        mDownloadListener.onProgress(new DownloadProgress(totalBytes, soFarBytes));
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (!mIsCheckMd5 || checkMd5(filePath)) {
                            mDownloadListener.onCompleted(filePath);
                        } else {
                            mDownloadListener.onError(new Throwable("md5 error"));
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        mDownloadListener.onError(e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                });
        mBaseDownloadTask.start();
    }


    private boolean checkMd5(String filePath) {
        File file = new File(filePath);
        if(file.exists() && file.isFile() && !TextUtils.isEmpty(mMd5) && DownloadHelper.getFileMD5String(file).equals(mMd5)){
            mMd5 = "";
            return true;
        }
        return false;
    }


}
