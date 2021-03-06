package com.gaucow.logintest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {
    Button createAccount;
    EditText userEmail, userPassword, userPasswordRepeat;
    FirebaseAuth mAuth;
    CoordinatorLayout snackbarLayout;
    FirebaseUser user;
    Context c;
    private static final String TAG = CreateAccount.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        createAccount = findViewById(R.id.createAccount);
        userEmail = findViewById(R.id.create_user_email);
        userPassword = findViewById(R.id.create_user_password);
        userPasswordRepeat = findViewById(R.id.create_user_password_repeat);
        mAuth = FirebaseAuth.getInstance();
        snackbarLayout = findViewById(R.id.coordinatorLayout);
        c = this;
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                String passwordRepeat = userPasswordRepeat.getText().toString();
                if(password.equals(passwordRepeat)) {
                    createNewAccount(email, password);
                } else {
                    Snackbar.make(snackbarLayout, "Please enter matching passwords.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    public void createNewAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(c, "Account creation successful. Check your email for a verification link.",
                                    Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                            sendEmailVerification();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(c, "Account creation failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void sendEmailVerification() {
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(c,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(c,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
