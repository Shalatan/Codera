package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class loginActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    Button logInButton;
    TextView logInUsername;
    TextView logInPassword;
    TextView logInToSignUpTextView;
    Button signUpButton;
    TextView signUpEmail;
    TextView signUpPassword;
    TextView signUpUsername;
    ConstraintLayout logInLayout;
    ConstraintLayout signUpLayout;
    private String m_Text = "";


    public void forgotPassword(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Email Address");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                try
                {
                    ParseUser.requestPasswordReset("m_Text");
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void showUserList()
    {
        Intent intent = new Intent(getApplicationContext(),testingActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent)
    {
        if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN)
        {
            logIn(view);
        }
        return false;
    }

    @Override
    public void onClick(View v)
    {
        v  = this.getCurrentFocus();
        if (v != null) {
            if (v.getId() == R.id.signUpImage || v.getId() == R.id.signUpImage || v.getId() == R.id.signup_layout || v.getId() == R.id.login_layout) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }


    public void logIn(View view)
    {
        ParseUser.logInInBackground(logInUsername.getText().toString(), logInPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e)
            {
                if(user != null)
                {
                    showUserList();
                }
                else
                {
                    Toast.makeText(loginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signIn(View view)
    {
        ParseUser user = new ParseUser();
        user.setUsername(signUpUsername.getText().toString());
        user.setEmail(signUpEmail.getText().toString());
        user.setPassword(signUpPassword.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e)
            {
                if (e == null)
                {
                    showUserList();
                }
                else
                {
                    Toast.makeText(loginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) ;
    }

    public void logInToSignUP (View view)
    {
        signUpLayout.setVisibility(View.VISIBLE);
        logInLayout.setVisibility(View.INVISIBLE);
    }
    public void signUpToLogIn (View view)
    {
        signUpLayout.setVisibility(View.INVISIBLE);
        logInLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //signUpLayout.setVisibility(View.INVISIBLE);

        if(ParseUser.getCurrentUser() != null)
        {
            showUserList();
        }

        logInButton = findViewById(R.id.logInButton);
        logInUsername = findViewById(R.id.logInUsername);
        logInPassword = findViewById(R.id.logInPassword);
        logInToSignUpTextView = findViewById(R.id.logInToSignUpButton);
        signUpButton = findViewById(R.id.signUpButton);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPassword = findViewById(R.id.signUpPassword);
        signUpUsername = findViewById(R.id.signUpUsername);
        logInLayout = findViewById(R.id.login_layout);
        signUpLayout = findViewById(R.id.signup_layout);

    }
}
