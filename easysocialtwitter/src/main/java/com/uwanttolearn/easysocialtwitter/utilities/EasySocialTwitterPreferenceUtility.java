package com.uwanttolearn.easysocialtwitter.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hafiz Waleed Hussain on 12/8/2014.
 */
public final class EasySocialTwitterPreferenceUtility {

    private static final String EASY_SOCIAL_FACEBOOK_PREFERENCE_NAME = "easy_social_facebook_preference";
    private static final String EASY_SOCIAL_FACEBOOK_ACCESS_TOKEN = "easy_social_facebook_access_token";

    public static final void setAccessToken(Context context,String accessToken){
        getEditor(context).putString(EASY_SOCIAL_FACEBOOK_ACCESS_TOKEN, accessToken).commit();
    }

    public static final String getAccessToken(Context context){
        return getSharedPreference(context).getString(EASY_SOCIAL_FACEBOOK_ACCESS_TOKEN,null);
    }



    private static final SharedPreferences.Editor getEditor(Context context){
        SharedPreferences sharedPreferences = getSharedPreference(context);
        return sharedPreferences.edit();
    }

    private static final SharedPreferences getSharedPreference(Context context){
        return context.getSharedPreferences(EASY_SOCIAL_FACEBOOK_PREFERENCE_NAME,Context.MODE_PRIVATE);
    }
}
