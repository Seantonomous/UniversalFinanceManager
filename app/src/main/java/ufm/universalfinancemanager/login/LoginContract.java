package ufm.universalfinancemanager.login;

import ufm.universalfinancemanager.BasePresenter;
import ufm.universalfinancemanager.BaseView;

/**
 * Created by smh7 on 3/3/18.
 */

public class LoginContract {
    public interface View extends BaseView<Presenter> {
        void showNetworkError();

        void showLoginError();

        void showSignupError();

        void showSuccessfulLogin();

        void showSuccessfulSignup();
    }

    public interface Presenter extends BasePresenter<View> {
        void login(String email, String password, boolean rememberPref);

        void signup(String email, String password, boolean rememberPref);

        void takeView(View v);

        void dropView();
    }
}
