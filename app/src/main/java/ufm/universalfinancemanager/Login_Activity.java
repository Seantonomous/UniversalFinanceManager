package ufm.universalfinancemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_Activity extends AppCompatActivity {
    Button login_button, signup_button;
    EditText email_field, password_field;

    String user_email, user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        login_button = (Button)findViewById(R.id.login_button);
        signup_button = (Button)findViewById(R.id.signup_button);

        email_field = (EditText)findViewById(R.id.email_field);
        password_field = (EditText)findViewById(R.id.pass_field);

        //Login button event listener
        login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_email = email_field.getText().toString();
                        user_password = password_field.getText().toString();

                        //Authentication will go here, for now just redirect
                        //TO-DISCUSS: SQL Injection prevention

                        login();
                    }
                }
        );

        signup_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Perform first time user setup
                    }
                }
        );
    }

    void login() {
        //Switch to main application activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
