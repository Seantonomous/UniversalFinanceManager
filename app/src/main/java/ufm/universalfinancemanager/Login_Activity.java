/* Author: Sean Hansen
* ID: 108841276
* Date Started: 9/17/17
* Date Complete:
* Peer Review:
*   Date:
*   Team Members:
* Contributing Team Members:
*/

package ufm.universalfinancemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

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
        Intent intent = new Intent(this, Main_Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
