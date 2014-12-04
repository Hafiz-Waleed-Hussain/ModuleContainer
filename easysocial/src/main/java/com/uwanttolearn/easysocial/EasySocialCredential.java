package com.uwanttolearn.easysocial;

/**
 * Created by Hafiz Waleed Hussain on 12/4/2014.
 */
public class EasySocialCredential {

    private final String _AppId;
    private final String _AppSecretId;
    private final String _RedirectUrl;
    private final String[] _Permissions;

    private EasySocialCredential(Builder builder){
        this._AppId = builder._AppId;
        this._AppSecretId = builder._AppSecretId;
        this._RedirectUrl = builder._RedirectUrl;
        this._Permissions = builder._Permissions;
    }

    public String getRedirectUrl() {
        return _RedirectUrl;
    }

    public static class Builder{

        private final String _AppId;
        private final String _AppSecretId;
        private final String _RedirectUrl;
        private String[] _Permissions;

        public Builder(String _AppId, String _AppSecretId, String _RedirectUrl) {
            this._AppId = _AppId;
            this._AppSecretId = _AppSecretId;
            this._RedirectUrl = _RedirectUrl;
        }

        public Builder setPermissions(String[] permissions){
            this._Permissions = permissions;
            return this;
        }

        public EasySocialCredential build(){
            return new EasySocialCredential(this);
        }
    }
}
