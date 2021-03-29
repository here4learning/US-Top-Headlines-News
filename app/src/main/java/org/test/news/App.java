package org.test.news;

import android.content.Context;
import android.os.StrictMode;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import org.test.news.api.URLBuilder;

import org.test.news.api.Api;
import org.test.news.api.NetworkModule;


public class App extends MultiDexApplication {

    private static App mInstance;

    private static Api mainApi;

    private static Api additionalApi;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(App.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());

        initRetroFitApi();
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    private void initRetroFitApi() {
        getMainApi();
        getAdditionalApi();
    }

    public static Api getMainApi() {
        if (mainApi == null) {
            mainApi = new NetworkModule(URLBuilder.BASE_URL).provideApi();
        }
        return mainApi;
    }

    public static Api getAdditionalApi() {
        if (additionalApi == null) {
            additionalApi = new NetworkModule(URLBuilder.ADDITIONAL_URL).provideApi();
        }
        return additionalApi;
    }


}
