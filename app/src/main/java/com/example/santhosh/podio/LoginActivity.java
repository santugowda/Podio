package com.example.santhosh.podio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    private static String TAG = LoginActivity.class.getSimpleName();

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    Button mEmailSignInButton;

    private boolean loggedIn = false;


    private static final String REGISTER_URL = "https://podio.com/oauth/token";

    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS = "success";
    public static final String KEY_GRANT_TYPE = "grant_type";
    public static final String CILENT_ID_PARAM = "client_id";
    public static final String SECRET_KEY_PARAM = "client_secret";
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String ACCESS_TOKEN = "Authorization";



    private JSONObject loginJSONObj;

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
                username = mEmailView.getText().toString();
                password = mPasswordView.getText().toString();
                login();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        VolleyLog.d(TAG, "LoggedIn: " + loggedIn);

        if(loggedIn){
            Intent intent = new Intent(this, OrganisationActivity.class);
            startActivity(intent);
        }

    }

    public void login(){
        final LoginResponse loginResponse = new LoginResponse();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try{
                                setLoginResult(response,loginResponse);
                                Intent intent = new Intent(LoginActivity.this, OrganisationActivity.class);
                                intent.putExtra("ACCESS_TOKEN", loginResponse.getAccessToken());
                                startActivity(intent);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_GRANT_TYPE,"password");
                params.put(CILENT_ID_PARAM,"podio-puzzle");
                params.put(SECRET_KEY_PARAM,"2u8c5SbylhvJ1uzeYMIsNS9fePA6hlkAyGtGjlWaN2r9FrThhcmwBh67EPHUpCHd");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void setLoginResult(String response, LoginResponse loginResponse) throws JSONException {
        loginJSONObj = new JSONObject(response);
        loginResponse.setAccessToken(loginJSONObj.getString("access_token"));
        loginResponse.setExpiresIn(loginJSONObj.getString("expires_in"));
        loginResponse.setRefreshToken(loginJSONObj.getString("refresh_token"));
        loginResponse.setTokenType(loginJSONObj.getString("token_type"));
    }
}

