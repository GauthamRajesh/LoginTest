package com.gaucow.logintest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
    EditText resetPasswordEmail;
    Button resetPasswordButton;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private final String TAG = ResetPassword.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        resetPasswordEmail = findViewById(R.id.reset_email);
        resetPasswordButton = findViewById(R.id.reset_password_button);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = resetPasswordEmail.getText().toString();
                if (user != null) {
                    sendForgotPasswordEmail(email);
                }
            }
        });
    }
    public void sendForgotPasswordEmail(String email) {
        if(user.getEmail().equals(email)) {
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                            }
                        }
                    });
        }
    }
}
