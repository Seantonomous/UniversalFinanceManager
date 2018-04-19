package ufm.universalfinancemanager.net;

/**
 * Created by smh7 on 3/26/18.
 */

public interface SignupCallback {
    void onSuccessfulSignup();

    void onFailedSignup();

    void onError();
}
