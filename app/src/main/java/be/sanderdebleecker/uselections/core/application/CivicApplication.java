package be.sanderdebleecker.uselections.core.application;

import android.app.Application;

import be.sanderdebleecker.uselections.core.di.components.ApplicationComponent;
import be.sanderdebleecker.uselections.core.di.components.DaggerApplicationComponent;
import be.sanderdebleecker.uselections.core.di.modules.ApplicationModule;

/**
 * @author simulan
 * @version 1.0.0
 * @since 19/05/2017
 */

public class CivicApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, "https://gist.githubusercontent.com"))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
