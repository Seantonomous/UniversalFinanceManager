package ufm.universalfinancemanager;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import ufm.universalfinancemanager.di.DaggerAppComponent;

/**
 * Created by smh7 on 12/11/17.
 * This is the main application, this is responsible for pulling all of the dagger modules together
 */

public class ufmApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
