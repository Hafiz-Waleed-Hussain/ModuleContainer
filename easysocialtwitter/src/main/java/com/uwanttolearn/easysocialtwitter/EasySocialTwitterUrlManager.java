package com.uwanttolearn.easysocialtwitter;

import android.content.Context;

import com.uwanttolearn.easysocial.EasySocialCredential;
import com.uwanttolearn.easysocialtwitter.utilities.EasySocialTwitterPreferenceUtility;

/**
 * Created by Hafiz Waleed Hussain on 12/6/2014.
 * EasySocialFacebookUrlManager is a Package level class. In this class we handle all our Url
 * related work.
 */
class EasySocialTwitterUrlManager {

    /** Constants */
    private static final String TWITTER_API_VERSION_NUMBER = "1.1";
    private static final String TWITTER_SERVER = "https://api.twitter.com/";
    private static final String TWITTER_REQUEST_URL =  TWITTER_SERVER+TWITTER_API_VERSION_NUMBER+"/";

    /** EasySocialCredential object containing info of Social Network credentials */
    private EasySocialCredential _EasySocialCredential;

    private EasySocialTwitterUrlManager(){
        // No Argument constructor
    }


    /**
     * One argument constructor. It take Credential as parameter.
     * @param easySocialCredential
     */
    public EasySocialTwitterUrlManager(EasySocialCredential easySocialCredential) {
        _EasySocialCredential = easySocialCredential;
    }

    /**
     * This method create a Facebook Login Url and return as String.
     * @return
     */
    public String getLoginUrl() {
        String url = TWITTER_REQUEST_URL;
//                &oauth_nonce=7c014c98ac4a24791e2e69acff8c9ab5&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1418024872&oauth_token=134671530-iFy7y3WVBPZpkPnICKOFUkyqfQgJnwt8VsstqocH&oauth_version=1.0
        String permissionAsString = getPermissionsAsString(_EasySocialCredential.getPermissions());
        return String.format(url + "&oauth;_consumer_key=%s&redirect_uri=%s&scope=%s",
                _EasySocialCredential.getAppId(),
                _EasySocialCredential.getRedirectUrl(),
                permissionAsString);
    }

    /**
     * This method create a Facebook AccessToken url and return as String.
     * @return
     */
    public String getAccessTokenUrl(){
        String url = FACEBOOK_GRAPH_SERVER+"oauth/access_token?";
        return String.format(url+"client_id=%s&redirect_uri=%s&client_secret=%s&code=",
                _EasySocialCredential.getAppId(),
                getRedirectUrl(),
                _EasySocialCredential.getAppSecretId());
    }

    /**
     * This method create UserInfo Url and return as String.
     * @param context Context take as a parameter.
     * @return
     */
    public String getUserInfoUrl(Context context){
        return FACEBOOK_REQUEST_URL+"me?"+getAccessTokenAsUrlParameter(context);
    }

    /**
     * This method create Post Message Url and return as String
     * @param context Context Context take as a parameter.
     * @param facebookUserId facebookUserId which we get in UserInfo.
     * @return
     */
    public String getPostMessageUrl(Context context,String facebookUserId){
        return FACEBOOK_REQUEST_URL+facebookUserId+"/feed?"+getAccessTokenAsUrlParameter(context);
    }

    /**
     * This method only return a Redirect Url which user give when app created on a Social Network.
     * @return
     */
    public String getRedirectUrl(){
        return _EasySocialCredential.getRedirectUrl();
    }



    /**
     * This method is used to take the permissions of a SocialNetwork as String array and return as
     * a String appended with comma.
     * @param permissions
     * @return
     */
    private String getPermissionsAsString(String[] permissions) {
        if (permissions == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < permissions.length; i++) {
            stringBuilder.append(permissions[i] + ",");
        }
        try {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
        return stringBuilder.toString();
    }

    private String getAccessTokenAsUrlParameter(Context context){
        return "access_token="+ EasySocialFacebookPreferenceUtility.getAccessToken(context);
    }

}


