package ufm.universalfinancemanager.di;

/**
 * Created by smh7 on 12/11/17.
 */

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import ufm.universalfinancemanager.addedittransaction.AddEditTransactionModule;
import ufm.universalfinancemanager.db.TransactionRepository;
import ufm.universalfinancemanager.db.source.TransactionRepositoryModule;
import ufm.universalfinancemanager.support.atomic.User;
import ufm.universalfinancemanager.support.atomic.UserModule;
import ufm.universalfinancemanager.ufmApplication;

import javax.inject.Singleton;

/**
 * This is a Dagger component. Refer to  for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component} as a singleton, the code
 * itself must ensure only one instance of the class is created.
 * //{@link AndroidSupportInjectionModule}
 * // is the module from Dagger.Android that helps with the generation
 * // and location of subcomponents.
 */
@Singleton
@Component(modules = {TransactionRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        UserModule.class})
public interface AppComponent extends AndroidInjector<ufmApplication> {

    TransactionRepository getTransactionRepository();

    User getUser();

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}

