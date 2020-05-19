package com.lcgt.instagramcloneparse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {
    String action;
    EditText usernameEditText;
    EditText passwordEditText;
    Button authenticateButton;
    TextView actionTextView;
    ConstraintLayout backgroundLayout;
    ImageView logoImageView;

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

    public void redirectToUsersList () {
        Intent intent = new Intent(getApplicationContext(), UsersListActivity.class);
        startActivity(intent);
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
                    redirectToUsersList();
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
                    redirectToUsersList();
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void initalizaeApplication() {
        authenticateButton = findViewById(R.id.authenticateButton);
        actionTextView = findViewById(R.id.actionTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        backgroundLayout = findViewById(R.id.backgroundLayout);
        logoImageView = findViewById(R.id.logoImageView);

        passwordEditText.setOnKeyListener(this);

        action = "signup";
        authenticateButton.setText("Signup");
        actionTextView.setText("Or, Log in");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if((ParseUser.getCurrentUser() != null)) {
            Log.i("Current user", ParseUser.getCurrentUser().toString());

            redirectToUsersList();
        } else {
            initalizaeApplication();
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            authenticateUser(v);
        }

        return false;
    }
}
