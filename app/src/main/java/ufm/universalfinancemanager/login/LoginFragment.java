package ufm.universalfinancemanager.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ufm.universalfinancemanager.R;
import ufm.universalfinancemanager.di.ActivityScoped;
import ufm.universalfinancemanager.transactionhistory.TransactionHistoryActivity;

/**
 * Created by smh7 on 3/3/18.
 */

@ActivityScoped
public class LoginFragment extends DaggerFragment implements LoginContract.View {
    @Inject
    LoginPresenter mPresenter;

    @Inject
    public LoginFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);

        final EditText email = root.findViewById(R.id.email_field);
        final EditText password = root.findViewById(R.id.pass_field);
        Button loginButton = root.findViewById(R.id.login_button);
        Button signupButton = root.findViewById(R.id.signup_button);
        final CheckBox rememberBox = root.findViewById(R.id.remember_box);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(email.getText().toString(), password.getText().toString(), rememberBox.isChecked());
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.signup(email.getText().toString(), password.getText().toString(), rememberBox.isChecked());
            }
        });

        return root;
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getContext(),
                "Couldn't connect to the login server. Check your internet connection",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoginError() {
        Toast.makeText(getContext(),
                "Invalid email/password!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSignupError() {
        Toast.makeText(getContext(),
                "Invalid email/password!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessfulLogin() {
        Toast.makeText(getContext(),
                "Login Successful",
                Toast.LENGTH_SHORT).show();

        switchToHomeActivity(false);
    }

    @Override
    public void showSuccessfulSignup() {
        Toast.makeText(getContext(),
                "Sign up Successful",
                Toast.LENGTH_SHORT).show();
        switchToHomeActivity(true);
    }

    private void switchToHomeActivity(boolean firstTime) {
        //TODO: pass in flag indicating a first time login
        //TODO: Switch to HOME activity once merged to dev
        startActivity(new Intent(getContext(), TransactionHistoryActivity.class));
    }
}
