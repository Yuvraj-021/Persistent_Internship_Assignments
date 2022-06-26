package com.example.loginapp.Screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.MainActivity;
import com.example.loginapp.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {

    EditText username_textview,password_textview;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_textview=findViewById(R.id.loginpg_username);
        password_textview=findViewById(R.id.loginpg_password);
        login=findViewById(R.id.loginpg_loginbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginCredentialValidations();

            }
        });



    }

    private void loginCredentialValidations() {
        String username=username_textview.getText().toString().trim();
        String password=password_textview.getText().toString().trim();
        if (username.isEmpty()) {
            username_textview.setError(getString(R.string.input_error_username));
            username_textview.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            password_textview.setError(getString(R.string.input_error_password));
            password_textview.requestFocus();
            return;
        }
        if (password.length() < 6) {
            password_textview.setError(getString(R.string.input_error_password_length));
            password_textview.requestFocus();
            return;
        }
        final ProgressDialog mdialog = new ProgressDialog(LoginActivity.this);
        mdialog.setMessage("Please Wait");
        mdialog.show();

        if (username.equals("admin@123") && password.equals("123456")) {
            StyleableToast.makeText(getApplicationContext(),"Login Successfull",R.style.successfull).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("username", username);
            mdialog.hide();
            startActivity(i);
        }else {
            mdialog.hide();
            StyleableToast.makeText(getApplicationContext(),"Invalid Credential",R.style.failed).show();
            return;
        }



    }
}
