package kr.ac.ajou.media.project_team_m;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private String id, name, email;

    private LoginButton loginButton;
    private TextView info;

    // Tool bar Properties
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);


        callbackManager = CallbackManager.Factory.create();


        if(AccessToken.getCurrentAccessToken() != null) {

//            CheckMember checkMember = new CheckMember();
//            checkMember.execute("");


            // If user already log in,
            Intent intent = new Intent(MainActivity.this, LoginCheckActivity.class);
            intent.putExtra("user", AccessToken.getCurrentAccessToken().getUserId().toString());
            startActivity(intent);
            finish();
        }



        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest meRequest = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    id = (String) response.getJSONObject().get("id");
                                    name = (String) response.getJSONObject().get("name");
                                    email = (String) response.getJSONObject().get("email");
                                    info.setText(email);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, birthday");
                meRequest.setParameters(parameters);
                meRequest.executeAsync();

                Intent intent = new Intent(MainActivity.this, LoginCheckActivity.class);
                intent.putExtra("user", email);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Cancle", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT);
            }
        });

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Manage login results
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // Tool bar menu
    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection Simplifiable If Statement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    // Facebook Properties
    private CallbackManager callbackManager;
    private TextView info;
    private LoginButton loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        // If user already log in,
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {

        }

        // If the login attempts is successful, onSucess is called.
        // IF the user cancels the login attempt, onCancle is called.
        // If an error occurs, onError is called.

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Activity transfer
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("user", loginResult.getAccessToken().getUserId());
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt cancled.");
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
                info.setText("Login attempt failed.");
            }
        });

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    // Login button starts off a new Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // Tool bar menu
    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection Simplifiable If Statement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
