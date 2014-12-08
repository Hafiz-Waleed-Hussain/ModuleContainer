package com.uwanttolearn.modulecontainer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uwanttolearn.easysocial.EasySocialAuthActivity;
import com.uwanttolearn.easysocial.EasySocialCredential;
import com.uwanttolearn.easysocialfacebook.EasySocialFacebook;
import com.uwanttolearn.easysocialfacebook.utilities.EasySocialFacebookAccessTokenParse;
import com.uwanttolearn.easysocialfacebook.utilities.EasySocialFacebookPreferenceUtility;
import com.uwanttolearn.easysocialtwitter.EasySocialTwitter;

import org.json.JSONObject;


public class TwitterTestingActivity extends ActionBarActivity {

    public static final int REQUEST_CODE = 1000;
    private EasySocialTwitter _EasySocialTwitter;

    private TextView _TwitterLoginResultTextView;
    private Button _TwitterLoginButton;
    private Button _TwitterGetUserInfoButton;
    private Button _TwitterPostMessageButton;
    private ProgressDialog progressDialog;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_testing);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        _TwitterLoginResultTextView = (TextView) findViewById(R.id.TwitterTestingActivity_info_text_view);
        _TwitterLoginButton = (Button) findViewById(R.id.TwitterTestingActivity_login_button);
        _TwitterGetUserInfoButton = (Button) findViewById(R.id.TwitterTestingActivity_get_user_info_button);
        _TwitterPostMessageButton = (Button) findViewById(R.id.TwitterTestingActivity_post_message_button);

        EasySocialCredential easySocialCredential = new EasySocialCredential.Builder
                ("DEH98QqiD78uySBaRK7lcUa9c",
                        "ldR5sA6fi7vequK4ZANIEfjMKNccTO7Gx8KxN0AfyLztz4aIjc",
                        "http://www.uwanttolearn.com").
                setPermissions(null).build();
        _EasySocialTwitter = EasySocialTwitter.getInstance(easySocialCredential);

        if(EasySocialFacebookPreferenceUtility.getAccessToken(this) != null){
            buttonStateHandler();
        }
    }



    public void onLoginClick(View view){
        _EasySocialTwitter.login(this, REQUEST_CODE);
    }

//    public void onGetUserInfoClick(View view){
//        _EasySocialTwitter.getUserInfo(this, new EasySocialFacebook.UserInfoCallback() {
//            @Override
//            public void onComplete(JSONObject jsonObject) {
//                if (jsonObject == null) {
//                    _TwitterLoginResultTextView.setText("UserInfo null");
//                } else {
//                    _TwitterLoginResultTextView.setText(jsonObject.toString());
//                    userId = jsonObject.optString("id");
//                }
//                progressDialog.dismiss();
//            }
//        });
//        progressDialog.show();
//    }
//
//    public void onPostClick(View view){
//        _EasySocialTwitter.sendPost(this, userId, new EasySocialFacebook.PostInfoCallback() {
//            @Override
//            public void onComplete(String postId) {
//                if (postId == null) {
//                    _TwitterLoginResultTextView.setText("Post Message null");
//                } else {
//                    _TwitterLoginResultTextView.setText(postId);
//                }
//                progressDialog.dismiss();
//            }
//        });
//        progressDialog.show();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE){
                String accessToken = EasySocialFacebookAccessTokenParse.parseAccessToken(data);
                _TwitterLoginResultTextView.setText(accessToken);
                EasySocialFacebookPreferenceUtility.setAccessToken(this, accessToken);
                buttonStateHandler();
            }
        }else if(resultCode == RESULT_CANCELED){
            if(resultCode == REQUEST_CODE) {
                Toast.makeText(this, data.getIntExtra(EasySocialAuthActivity.ERROR_CODE, 0) + "", Toast.LENGTH_LONG).show();
                //These error codes are present in WebViewClient.
                //http://developer.android.com/reference/android/webkit/WebViewClient.html
            }
        }

    }

    private void buttonStateHandler() {
        _TwitterGetUserInfoButton.setEnabled(true);
        _TwitterPostMessageButton.setEnabled(true);
        _TwitterLoginButton.setEnabled(false);
    }
}
