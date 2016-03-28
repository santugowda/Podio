package com.example.santhosh.podio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.santhosh.adapter.PodioAdapter;
import com.example.santhosh.data.Item;
import com.example.santhosh.data.OrganisationItem;
import com.example.santhosh.data.WorkItem;
import com.example.santhosh.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrganisationActivity extends AppCompatActivity {

    private static final String NAME_ORGANIZATION = "name";
    private static final String NAME_WORKSPACE = "name";
    private static final String SPACES = "spaces";
    private static String TAG = OrganisationActivity.class.getSimpleName();

    private String urlJsonArry = "https://api.podio.com/org/";
    String access_token;
    String authorization;

   // CustomAdapter customAdapter;
    PodioAdapter podioAdapter;
    ArrayList<Item> listWorkspace = new ArrayList<Item>();
    Button refreshButton;
    private ListView workSpace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        access_token = getIntent().getExtras().getString("ACCESS_TOKEN");
        authorization = "OAuth2 " + access_token;

        workSpace = (ListView) findViewById(R.id.listView);
        refreshButton = (Button) findViewById(R.id.refresh_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        makeJsonArrayRequest();

        podioAdapter = new PodioAdapter(this,listWorkspace);
        workSpace.setAdapter(podioAdapter);
    }


    private void makeJsonArrayRequest() {
        JsonArrayRequest jsonArray = new JsonArrayRequest
                (urlJsonArry,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0; i<response.length();i++){
                                JSONObject name = response.getJSONObject(i);
                                String organizationName = name.getString(NAME_ORGANIZATION);
                                listWorkspace.add(new OrganisationItem(organizationName));

                                JSONArray nameObject = name.getJSONArray(SPACES);
                                for(int j=0; j<nameObject.length();j++) {
                                    JSONObject workName = nameObject.getJSONObject(j);
                                    String nameWork = workName.getString(NAME_WORKSPACE);
                                    listWorkspace.add(new WorkItem(nameWork));
                                }
                                podioAdapter.notifyDataSetChanged();
                                VolleyLog.d("Response:%n %s", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String>  params = new HashMap<String, String>();

                params.put(LoginActivity.ACCESS_TOKEN,authorization);
                params.put(LoginActivity.CILENT_ID_PARAM,"podio-puzzle");
                params.put(LoginActivity.SECRET_KEY_PARAM,"2u8c5SbylhvJ1uzeYMIsNS9fePA6hlkAyGtGjlWaN2r9FrThhcmwBh67EPHUpCHd");
                return params;
            }
        };

        VolleyController.getInstance().addToRequestQueue(jsonArray);
    }

    private void refresh() {
        listWorkspace.clear();
        makeJsonArrayRequest();
        podioAdapter.notifyDataSetChanged();
    }

//    private void logout(){
//
//        SharedPreferences preferences = getSharedPreferences(LoginActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean(LoginActivity.LOGGEDIN_SHARED_PREF, false);
//        editor.putString(LoginActivity.EMAIL_SHARED_PREF, "");
//        editor.commit();
//
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }
}
