package com.uwanttolearn.easysocialtwitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.uwanttolearn.easysocial.EasySocialAuthActivity;
import com.uwanttolearn.easysocial.EasySocialCredential;
import com.uwanttolearn.easysocial.webrequests.GetWebRequest;
import com.uwanttolearn.easysocial.webrequests.PostWebRequest;
import com.uwanttolearn.easysocial.webrequests.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hafiz Waleed Hussain on 12/6/2014.
 */
public class EasySocialTwitter {

    private static EasySocialTwitter _EasySocialFacebook;
    private static EasySocialTwitterUrlManager _EasySocialFacebookUrlManager;

    public static final EasySocialTwitter getInstance(EasySocialCredential easySocialCredential){
        if(_EasySocialFacebook == null)
            _EasySocialFacebook = new EasySocialTwitter();
            _EasySocialFacebookUrlManager = new EasySocialTwitterUrlManager(easySocialCredential);
        return _EasySocialFacebook;
    }

    private EasySocialTwitter(){
    }

    public void login(Activity activity, int requestCode){
        Intent intent = new Intent(activity, EasySocialAuthActivity.class);
        intent.putExtra(EasySocialAuthActivity.URL,_EasySocialFacebookUrlManager.getLoginUrl());
        intent.putExtra(EasySocialAuthActivity.REDIRECT_URL, _EasySocialFacebookUrlManager.getRedirectUrl());
        intent.putExtra(EasySocialAuthActivity.ACCESS_TOKEN, _EasySocialFacebookUrlManager.getAccessTokenUrl());
        activity.startActivityForResult(intent, requestCode);
    }

    public static interface UserInfoCallback{ void onComplete(JSONObject jsonObject);}

    public void getUserInfo(Context context, final UserInfoCallback userInfoCallback){

        GetWebRequest getWebRequest = new GetWebRequest(new WebRequest.Callback() {
            @Override
            public void requestComplete(String line) {
                try {
                    JSONObject jsonObject = new JSONObject(line);
                    userInfoCallback.onComplete(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    userInfoCallback.onComplete(null);
                }
            }
        });
        getWebRequest.executeRequest(_EasySocialFacebookUrlManager.getUserInfoUrl(context));
    }

    public static interface PostInfoCallback{ void onComplete(String postId);}
    public void sendPost(Context context, String facebookUserId, final PostInfoCallback postInfoCallback){
        String urlParams = "message="+"Hi Testing ";
        PostWebRequest postWebRequest = new PostWebRequest(new WebRequest.Callback() {
            @Override
            public void requestComplete(String data) {
                postInfoCallback.onComplete(data);
            }
        });
        postWebRequest.executeRequest(_EasySocialFacebookUrlManager.getPostMessageUrl(context,facebookUserId),urlParams);
    }

}