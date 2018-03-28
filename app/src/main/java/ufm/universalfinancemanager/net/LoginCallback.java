package ufm.universalfinancemanager.net;

/**
 * Created by smh7 on 3/26/18.
 */

public interface LoginCallback {
    void onSuccessfulLogin();

    void onFailedLogin();

    void onError();
}
