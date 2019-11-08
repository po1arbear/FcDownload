package com.fcbox.lib.download;

/**
 * Author:xz
 * Date:2019/11/7 17:17
 * Desc:
 */
public class DownloadProgress {

    DownloadProgress(int totalSize, int downloadSize) {
        this.totalSize = totalSize;
        this.downloadSize = downloadSize;
    }


    private int downloadSize;

    public int getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(int downloadSize) {
        this.downloadSize = downloadSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    private int totalSize;

    public int getProgress() {
        if (totalSize != 0) {
            return (int) (downloadSize * 100.0 / totalSize);
        } else {
            return 100;
        }
    }

}
