package ufm.universalfinancemanager.login;

import javax.inject.Inject;

/**
 * Created by smh7 on 3/3/18.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    @Inject
    LoginPresenter() {}

    @Override
    public void login(String email, String password, boolean rememberPref) {

    }

    @Override
    public void signup(String email, String password, boolean rememberPref) {

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
