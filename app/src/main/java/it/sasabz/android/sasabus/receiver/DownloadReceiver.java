package it.sasabz.android.sasabus.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

import java.io.File;
import java.io.IOException;

import it.sasabz.android.sasabus.util.IOUtils;
import it.sasabz.android.sasabus.util.LogUtils;
import it.sasabz.android.sasabus.util.Utils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DownloadReceiver extends BroadcastReceiver {

    private static final String TAG = "DownloadReceiver";

    private long downloadId;
    private File zipFile;
    private WebView webView;

    public DownloadReceiver(long downloadId, File zipFile, WebView webView) {
        this.downloadId = downloadId;
        this.zipFile = zipFile;
        this.webView = webView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        LogUtils.e(TAG, "onReceive() action: " + action);

        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

            if (downloadId == this.downloadId) {
                context.unregisterReceiver(this);

                downloadObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Void>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Utils.handleException(e);
                            }

                            @Override
                            public void onNext(Void aBoolean) {
                                webView.loadUrl("javascript:reloadMap();");
                            }
                        });
            }
        }
    }

    private Observable<Void> downloadObservable() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    LogUtils.e(TAG, "Extracting zip file");
                    IOUtils.unzipFile(zipFile.getName(), zipFile.getParent());
                    LogUtils.e(TAG, "Extracted zip file");

                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
