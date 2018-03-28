package ufm.universalfinancemanager.login;

import javax.inject.Inject;

import ufm.universalfinancemanager.net.LoginCallback;
import ufm.universalfinancemanager.net.LoginManager;

/**
 * Created by smh7 on 3/3/18.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private LoginManager loginManager;

    @Inject
    LoginPresenter(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @Override
    public void login(String email, String password, boolean rememberPref) {
        loginManager.login(email, password, new LoginCallback() {
            @Override
            public void onSuccessfulLogin() {
                //Switch to home activity
                //Session token is stored by login manager
                mView.showSuccessfulLogin();
            }

            @Override
            public void onFailedLogin() {
                mView.showLoginError();
            }

            @Override
            public void onError() {
                mView.showNetworkError();
            }
        });
    }

    @Override
    public void signup(String email, String password, boolean rememberPref) {
        loginManager.login(email, password, new LoginCallback() {
            @Override
            public void onSuccessfulLogin() {
                mView.showSuccessfulSignup();
            }

            @Override
            public void onFailedLogin() {
                mView.showSignupError();
            }

            @Override
            public void onError() {
                mView.showNetworkError();
            }
        });
    }

    @Override
    public void takeView(LoginContract.View v) {
        mView = v;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
