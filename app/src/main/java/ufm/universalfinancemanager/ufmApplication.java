package ufm.universalfinancemanager;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by smh7 on 12/11/17.
 */

public class ufmApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder.application(this).build();
    }
}
