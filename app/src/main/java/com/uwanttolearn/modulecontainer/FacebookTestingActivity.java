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

import org.json.JSONObject;

public class FacebookTestingActivity extends ActionBarActivity {

    public static final int REQUEST_CODE = 1000;
    private EasySocialFacebook _EasySocialFacebook;

    private TextView _FacebookLoginResultTextView;
    private Button _FacebookLoginButton;
    private Button _FacebookGetUserInfoButton;
    private Button _FacebookPostMessageButton;
    private ProgressDialog progressDialog;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_testing);
    progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        _FacebookLoginResultTextView = (TextView) findViewById(R.id.FacebookTestingActivity_info_text_view);
        _FacebookLoginButton = (Button) findViewById(R.id.FacebookTestingActivity_login_button);
        _FacebookGetUserInfoButton = (Button) findViewById(R.id.FacebookTestingActivity_get_user_info_button);
        _FacebookPostMessageButton = (Button) findViewById(R.id.FacebookTestingActivity_post_message_button);

        EasySocialCredential easySocialCredential = new EasySocialCredential.Builder
                ("794159763977710",
                "f558c2bf4d6e745186a91291dd386e80",
                "https://www.uwanttolearn.com/").
                setPermissions(new String[]{"email,publish_actions"}).build();
        _EasySocialFacebook = EasySocialFacebook.getInstance(easySocialCredential);

        if(EasySocialFacebookPreferenceUtility.getAccessToken(this) != null){
            buttonStateHandler();
        }
    }



    public void onLoginClick(View view){
        _EasySocialFacebook.login(this, REQUEST_CODE);
    }

    public void onGetUserInfoClick(View view){
        _EasySocialFacebook.getUserInfo(this,new EasySocialFacebook.UserInfoCallback() {
            @Override
            public void onComplete(JSONObject jsonObject) {
                if(jsonObject == null){
                    _FacebookLoginResultTextView.setText("UserInfo null");
                }else{
                    _FacebookLoginResultTextView.setText(jsonObject.toString());
                     userId = jsonObject.optString("id");
                }
                progressDialog.dismiss();
            }
        });
        progressDialog.show();
    }

    public void onPostClick(View view){
       _EasySocialFacebook.sendPost(this,userId,new EasySocialFacebook.PostInfoCallback() {
           @Override
           public void onComplete(String postId) {
               if(postId == null){
                   _FacebookLoginResultTextView.setText("Post Message null");
               }else{
                   _FacebookLoginResultTextView.setText(postId);
               }
               progressDialog.dismiss();
           }
       });
        progressDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE){
                String accessToken = EasySocialFacebookAccessTokenParse.parseAccessToken(data);
                _FacebookLoginResultTextView.setText(accessToken);
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
        _FacebookGetUserInfoButton.setEnabled(true);
        _FacebookPostMessageButton.setEnabled(true);
        _FacebookLoginButton.setEnabled(false);
    }
}
