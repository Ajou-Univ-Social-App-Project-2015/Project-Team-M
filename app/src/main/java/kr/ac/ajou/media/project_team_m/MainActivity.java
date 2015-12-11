package kr.ac.ajou.media.project_team_m;

import android.content.Intent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button buttonLogin, buttonRegister;
    private TextView textMessage;
    private String email, password;
    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        textMessage = (TextView) findViewById(R.id.textMessage);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();
                if (email.length() == 0) {
                    textMessage.setText("아이디를 입력하세요");
                    return;
                } else if (password.length() == 0) {
                    textMessage.setText("비밀번호를 입력하세요");
                    return;
                } else {
                    IllPercentClient.get("/users/" + email, null, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                            try {
                                JSONObject response = success.getJSONObject(0);
                                String nick = response.getString("nick");
                                String passwordstr = response.getString("password");
                                if (passwordstr.equals(password)) {
                                    textMessage.setText("Success");
                                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                                    intent.putExtra("email", editEmail.getText().toString());
                                    intent.putExtra("nick", nick);
                                    editEmail.setText("");
                                    editPassword.setText("");
                                    startActivity(intent);
                                    finish();
                                } else textMessage.setText("일치하지 않습니다.");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            textMessage.setText("뭔가 문제임");
                        }
                    });
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Action bar
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_lime);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Tool bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
