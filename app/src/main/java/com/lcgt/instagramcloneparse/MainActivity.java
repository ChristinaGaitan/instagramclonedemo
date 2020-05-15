package com.lcgt.instagramcloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    String action;
    EditText usernameEditText;
    EditText passwordEditText;
    Button authenticateButton;
    TextView actionTextView;

    public void authenticateUser(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Username and password are required!", Toast.LENGTH_SHORT).show();
        } else {
            if(action.equals("signup")) {
                Log.i("Action", "Sign up");
                signupUser(username, password);
            } else if(action.equals("login")) {
                Log.i("Action", "Log in");
                loginUser(username, password);
            }
        }
    }

    public void signupUser(String username, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.i("Action", "Sign up successful!");
                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("Action", "Sign up error! " + e.getMessage());
                }
            }
        });
    }

    public void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null) {
                    Log.i("Action", "Log in successful!");

                } else {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("Action", "Log in error! " + e.getMessage());
                }
            }
        });
    }

    public void toggleButtonAction(View view) {
        Button authenticateButton = findViewById(R.id.authenticateButton);
        TextView actionTextView = findViewById(R.id.actionTextView);

        if(action.equals("signup")) {
            action = "login";
            authenticateButton.setText("Log in");
            actionTextView.setText("Or, Signup");
        } else if (action.equals("login")) {
            action = "signup";
            authenticateButton.setText("Signup");
            actionTextView.setText("Or, Log in");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticateButton = findViewById(R.id.authenticateButton);
        actionTextView = findViewById(R.id.actionTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        action = "signup";
        authenticateButton.setText("Signup");
        actionTextView.setText("Or, Log in");
    }
}
