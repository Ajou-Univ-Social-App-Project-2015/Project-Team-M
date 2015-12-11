package kr.ac.ajou.media.project_team_m;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SettingActivity extends AppCompatActivity {

    private TextView textUsernick, textUseremail, textAlert;
    private Button buttonArticle, buttonLike;
    private String email, nick;
    // Tool bar Properties
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_setting);
        textAlert = (TextView) findViewById(R.id.textalert);
        textUseremail = (TextView) findViewById(R.id.useremail);
        textUsernick = (TextView) findViewById(R.id.usernick);
        buttonArticle = (Button) findViewById(R.id.buttonarticle);
        buttonLike = (Button) findViewById(R.id.buttonlike);

        // User email, nick
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        nick = intent.getStringExtra("nick");

        textUsernick.setText(nick);
        textUseremail.setText(email);

        buttonArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IllPercentClient.get("/articles/" + nick, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                        try {
                            textAlert.setText("");
                            for (int i = 0; i < success.length(); i++) {
                                JSONObject response = success.getJSONObject(i);
                                String title = response.getString("title");
                                textAlert.append(title+"\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        textAlert.setText("뭔가 문제임");
                    }
                });
            }
        });

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlert.setText("준비 중 입니다.");
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
                Intent intent = new Intent(SettingActivity.this, ListActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }

    // Tool bar menu
    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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
            Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("nick", nick);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
