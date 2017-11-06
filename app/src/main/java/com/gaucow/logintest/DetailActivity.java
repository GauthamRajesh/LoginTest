package com.gaucow.logintest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class DetailActivity extends AppCompatActivity {
    TextView idTextView;
    Button signOut;
    FirebaseUser user;
    WebView userFbLogoutView;
    Button finishSigningOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle b = getIntent().getExtras();
        user = FirebaseAuth.getInstance().getCurrentUser();
        idTextView = findViewById(R.id.id_acct_display);
        idTextView.setText(b.getString("id"));
        signOut = findViewById(R.id.sign_out);
        userFbLogoutView = findViewById(R.id.user_logout_webview);
        finishSigningOut = findViewById(R.id.finish_fb_sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(UserInfo info : user.getProviderData()) {
                    if(info.getProviderId().equals("facebook.com")) {
                        LoginManager.getInstance().logOut();
                        signOut.setVisibility(View.GONE);
                        finishSigningOut.setVisibility(View.VISIBLE);
                        userFbLogoutView.setVisibility(View.VISIBLE);
                        userFbLogoutView.getSettings().setJavaScriptEnabled(true);
                        userFbLogoutView.loadUrl("https://m.facebook.com/home.php");
                        Toast.makeText(getApplicationContext(), "Click the three bars in the top of the page, then scroll down " +
                                "and click the Log Out button. After you do this, click the Finish Signing Out button in the app itself.", Toast.LENGTH_LONG).show();
                        finishSigningOut.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "Signed out successfully.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                            }
                        });
                    } else if(info.getProviderId().equals("google.com")) {
                        Intent i = new Intent(DetailActivity.this, MainActivity.class);
                        i.putExtra("ComingFromDetail", true);
                        startActivity(i);
                    }
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
