package com.fcbox.lib.download;

/**
 * Author:xz
 * Date:2019/11/7 16:03
 * Desc:
 */
public interface IFcDownload {
    void start();
    void start(DownloadListener listener);
    void start(String downloadUrl, String apkName, String apkPath);
    void stop();
}
