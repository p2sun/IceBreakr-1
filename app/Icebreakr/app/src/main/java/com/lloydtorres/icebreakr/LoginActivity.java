package com.lloydtorres.icebreakr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import android.content.Intent;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity {

    private Toast mToast; // used to send messages to user

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "tMDHLeT6EZHZqhMV5jz84b33Q";
    private static final String TWITTER_SECRET = "7etYjtuv99YHAV7gGjmyOi25xzIiiNFkMDPwCipaxPkXBDfal0";
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Intent launchApp = new Intent(LoginActivity.this,Icebreakr.class);
                startActivity(launchApp);
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                showToast(getString(R.string.login_error));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    // shows messages to user through Toasts
    private void showToast(String text) {
        try { // apparently this could crash on certain configurations
            if (mToast == null) {
                mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
        catch (Exception e){
            // what do
        }
    }
}
