package ufm.universalfinancemanager.login;

import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.util.ActivityUtils;

/**
 * Created by smh7 on 3/3/18.
 */

public class LoginActivity extends DaggerAppCompatActivity {
    @Inject
    LoginFragment mFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        LoginFragment loginFragment = (LoginFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(loginFragment == null) {
            loginFragment = mFragment;

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }

        setTitle(" ");
    }
}
