package com.example.santhosh.podio;

public class LoginResponse {

        public String ACCESS_TOKEN;
        public String TOKEN_TYPE;
        public String EXPIRES_IN;
        public String REFRESH_TOKEN;


        public void setAccessToken(String accessToken){
            this.ACCESS_TOKEN = accessToken;
        }

        public void setTokenType(String TOKEN_TYPE){
            this.TOKEN_TYPE = TOKEN_TYPE;
        }

        public void setExpiresIn(String EXPIRES_IN){
            this.EXPIRES_IN = EXPIRES_IN;
        }

        public void setRefreshToken(String REFRESH_TOKEN){
            this.REFRESH_TOKEN = REFRESH_TOKEN;
        }

        public String getAccessToken(){
            return ACCESS_TOKEN;
        }

        public String getTokenType(){
            return TOKEN_TYPE;
        }

        public String getExpiresIn(){
            return EXPIRES_IN;
        }

        public String getRefreshToken(){
            return REFRESH_TOKEN;
        }

}
