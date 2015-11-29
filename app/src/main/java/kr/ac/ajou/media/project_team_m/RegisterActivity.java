package kr.ac.ajou.media.project_team_m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity{

    private CallbackManager callbackManager;
    private EditText editNick, editEmail, editPassword;
    private Button buttonRegister;
    private LoginButton buttonFacebook;
    private TextView textMessage;

    private Toolbar toolbar;

    private String id, name, email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.form_register);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editNick = (EditText) findViewById(R.id.editNick);
        editPassword = (EditText) findViewById(R.id.editPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textMessage = (TextView) findViewById(R.id.textMessage);

        /*
        callbackManager = CallbackManager.Factory.create();
        if(AccessToken.getCurrentAccessToken() != null) {
            id = AccessToken.getCurrentAccessToken().getUserId().toString();
        }

        buttonFacebook = (LoginButton) findViewById(R.id.buttonFacebook);
        buttonFacebook.setReadPermissions("id");
        buttonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email, gender, birthday");
                        meRequest.setParameters(parameters);
                        meRequest.executeAsync();

                        textId.setText(id);
                        editEmail.setText(email);
                        editNick.setText(name);
                    }
                    @Override
                    public void onCancel() {
                        Toast.makeText(RegisterActivity.this, "Cancle", Toast.LENGTH_SHORT);
                    }
                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT);
                    }});*/

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String nick = editNick.getText().toString();
                String password = editPassword.getText().toString();

                if (email.length() == 0 || nick.length() == 0 || password.length() == 0) textMessage.setText("채우세요");
                RequestParams params = new RequestParams();
                params.add("id", "testing");
                params.add("nick", nick);
                params.add("email", email);
                params.add("password", password);

                IllPercentClient.post("/users", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(RegisterActivity.this, "Created!" + statusCode, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(RegisterActivity.this, "Failed!" + statusCode, Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("email", editEmail.getText().toString());
                startActivity(intent);
            }
        });

//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = editEmail.getText().toString();
//                if (email.length() == 0) return;
//
//                IllPercentClient.get("/users/"+email, null, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        try {
//                            String email = response.getString("email");
//                            String nick = response.getString("nick");
//                            textMessage.setText("[email]="+email+", " + "[nick]=" + nick);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }}});}});

        // Action bar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Manage login results
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

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
}
