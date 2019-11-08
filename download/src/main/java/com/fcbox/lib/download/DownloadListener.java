package com.fcbox.lib.download;

/**
 * Author:xz
 * Date:2019/11/7 16:04
 * Desc:
 */
public interface DownloadListener {

    void onProgress(DownloadProgress progress);

    void onError(Throwable e);

    void onCompleted(String filePath);

}
